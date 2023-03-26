package com.itlyc.document.service.impl;

import com.github.pagehelper.PageHelper;
import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.BeanHelper;
import com.itlyc.common.util.CountUtil;
import com.itlyc.common.vo.PageResult;
import com.itlyc.document.dto.CollaborationsDTO;
import com.itlyc.document.dto.DocumentDTO;
import com.itlyc.document.dto.UserCollaborationDTO;
import com.itlyc.document.entity.Collaborations;
import com.itlyc.document.entity.File;
import com.itlyc.document.entity.FileHistory;
import com.itlyc.document.entity.Folder;
import com.itlyc.document.enums.DocumentEnum;
import com.itlyc.document.mapper.DocumentMapper;
import com.itlyc.document.mapper.FileMapper;
import com.itlyc.document.service.DocumentService;
import com.itlyc.document.service.FileHistoryService;
import com.itlyc.document.service.FileService;
import com.itlyc.sys.client.SysClient;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Resource
    private DocumentMapper documentMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileHistoryService fileHistoryService;
    @Autowired
    private SysClient sysClient;

    /**
     * 保存文件夹
     * @param folder 文件夹对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFolder(Folder folder) {
        folder.setCompanyId(UserHolder.getCompanyId());
        folder.setOrder(0);
        folder.setCompanyUserId(UserHolder.getUserId());
        logger.info("文档中心保存文件夹+{}",folder);
        documentMapper.saveFolder(folder);
    }

    /**
     * 新建文档
     * @param file 文档对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFile(File file) {
        //1.设置文档相关属性
        Long userId = UserHolder.getUserId();
        file.setCompanyUserId(userId);
        file.setCompanyId(UserHolder.getCompanyId());
        file.setFileSize(CountUtil.getSize(file.getContent().length()));
        file.setUpdateUserId(userId);
        file.setStatus(DocumentEnum.FILE_STATUS_WAIT_APPROVE.getVal());
        fileService.save(file);

        //2.保存文档操作记录
        FileHistory fileHistory = new FileHistory();
        fileHistory.setFileId(file.getId());
        fileHistory.setFileName(file.getName());
        fileHistory.setCompanyUserId(file.getCompanyUserId());
        fileHistory.setIfCreate(true);
        fileHistory.setContent(file.getContent());
        fileHistoryService.save(fileHistory);
    }

    /**
     * 文档及文件夹列表查询
     * @param parentFolderId 父级文件夹id
     * @param keyword 查询关键字
     * @return
     */
    @Override
    public DocumentDTO queryAllFolderAndFile(Long parentFolderId, String keyword) {
        DocumentDTO documentDTO = new DocumentDTO();
        // 查询文件夹
        documentDTO.setFolders(queryCompanyFolder(parentFolderId));
        // 查询文档
        documentDTO.setFiles(queryCompanyFiles(parentFolderId,keyword));
        return documentDTO;
    }

    /**
     * 查询文档
     * @param parentFolderId 文件夹id
     * @param keyword 关键字
     * @return
     */
    private List<File> queryCompanyFiles(Long parentFolderId, String keyword) {
        Long companyId = UserHolder.getCompanyId();
        // 公共读
        Long all_read = 0L;
        // 公共读写
        Long all_read_write = 1L;
        Long userId = UserHolder.getUserId();
        List<File> fileList = fileService.queryCompanyFiles(companyId, all_read, all_read_write, parentFolderId, userId, keyword);
        if(!CollectionUtils.isEmpty(fileList)){
            return fileList.stream().distinct().collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 查询文件夹
     * @param parentFolderId 文件夹id
     * @return
     */
    private List<Folder> queryCompanyFolder(Long parentFolderId) {
        Long companyId = UserHolder.getCompanyId();
        return documentMapper.queryCompanyFolder(parentFolderId,companyId);
    }

    /**
     * 查询指定文档的协作者列表
     * @param fileId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<CompanyUserDTO> pagingCollaborations(Long fileId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Collaborations> collaborations = documentMapper.queryCollaborationsByFileId(fileId);
        if(!CollectionUtils.isEmpty(collaborations)){
            List<Long> userIds = collaborations.stream().map(Collaborations::getCompanyUserId).collect(Collectors.toList());
            List<CompanyUserDTO> companyUserDTOList = sysClient.queryCompanyUserByIds(userIds).getData();
            return new PageResult<>(1L,2L,companyUserDTOList);
        }
        return null;
    }

    /**
     * 新增协助作者时查询企业员工列表
     * @param fileId 文档id
     * @return
     */
    @Override
    public List<UserCollaborationDTO> pagingCollaborationUsers(Long fileId) {
        // 查询该企业下所有员工信息
        List<CompanyUserDTO> companyUserDTOList = sysClient.queryAllCompanyUser().getData();
        // 查询当前文档信息
        File file = fileService.queryFileByFileId(fileId);
        List<Collaborations> collaborations = documentMapper.queryCollaborationsByFileId(fileId);
        List<UserCollaborationDTO> userCollaborationDTOS = BeanHelper.copyWithCollection(companyUserDTOList, UserCollaborationDTO.class);
        if(!CollectionUtils.isEmpty(userCollaborationDTOS)){
            // 循环所有员工
            for (UserCollaborationDTO userCollaborationDTO : userCollaborationDTOS) {
                // 声明协作者标识 0既不是拥有者也不是协作者 1是拥有者 2是协作者
                Integer status = 0;
                // 判断是否是作者
                boolean equals = file.getCompanyUserId().equals(userCollaborationDTO.getId());
                status = equals ? DocumentEnum.USER_TYPE_AUTHOR.getVal() : DocumentEnum.USER_TYPE_NONE.getVal();
                // 判断是否有协作者
                if(!CollectionUtils.isEmpty(collaborations)){
                    // 拿到协作者id列表
                    List<Long> userIds = collaborations.stream().map(Collaborations::getCompanyUserId).collect(Collectors.toList());
                    if(userIds.contains(userCollaborationDTO.getId())){
                        status = DocumentEnum.USER_TYPE_COLLABORATION.getVal();
                    }
                }
                userCollaborationDTO.setState(status);
            }
            return userCollaborationDTOS;
        }
        return null;
    }

    /**
     * 新增协作者
     * @param collaborationsDTO
     */
    @Override
    public void insertCollaboration(CollaborationsDTO collaborationsDTO) {
        this.validDoc(collaborationsDTO);
        //新增文档协作者记录
        Collaborations collaborations = BeanHelper.copyProperties(collaborationsDTO, Collaborations.class);
        //TODO 前端提交员工id参数名称 userId
        collaborations.setCompanyUserId(collaborationsDTO.getUserId());
        boolean b = documentMapper.saveCollaboration(collaborations);
        if(!b){
            throw new NcException(ResponseEnum.INSERT_OPERATION_FAIL);
        }
    }

    /**
     * 判断文档是否有权限操作-是否为作者
     * @param collaborationsDTO
     */
    private void validDoc(CollaborationsDTO collaborationsDTO) {
        File file = fileService.queryFileByFileId(collaborationsDTO.getFileId());
        if(file == null){
            throw new NcException(ResponseEnum.DOC_NOT_FOUND);
        }
        //1.2判断文档状态
        if(!file.getEnable()){
            throw new NcException(ResponseEnum.DOC_NOT_FOUND);
        }
        //1.2 判断文档拥有者
        Long companyUserId = UserHolder.getUserId();
        if(!file.getCompanyUserId().equals(companyUserId)){
            throw new NcException(ResponseEnum.DOC_NOT_ALLOWED);
        }
    }

    /**
     * 删除协作者
     * @param collaborationsDTO
     */
    @Override
    public void deleteCollaboration(CollaborationsDTO collaborationsDTO) {
        this.validDoc(collaborationsDTO);
        boolean b = documentMapper.deleteCollaboration(collaborationsDTO.getUserId(),collaborationsDTO.getFileId());
        if(!b){
            throw new NcException(ResponseEnum.DELETE_OPERATION_FAIL);
        }
    }
}
