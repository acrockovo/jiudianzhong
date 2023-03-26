package com.itlyc.document.service.impl;

import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.CountUtil;
import com.itlyc.document.dto.DocumentDTO;
import com.itlyc.document.entity.File;
import com.itlyc.document.entity.FileHistory;
import com.itlyc.document.entity.Folder;
import com.itlyc.document.enums.DocumentEnum;
import com.itlyc.document.mapper.DocumentMapper;
import com.itlyc.document.mapper.FileMapper;
import com.itlyc.document.service.DocumentService;
import com.itlyc.document.service.FileHistoryService;
import com.itlyc.document.service.FileService;
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
}
