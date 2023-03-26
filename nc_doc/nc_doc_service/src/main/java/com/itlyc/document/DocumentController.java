package com.itlyc.document;

import com.itlyc.common.vo.PageResult;
import com.itlyc.common.vo.Result;
import com.itlyc.document.dto.CollaborationsDTO;
import com.itlyc.document.dto.DocumentDTO;
import com.itlyc.document.dto.UserCollaborationDTO;
import com.itlyc.document.entity.File;
import com.itlyc.document.entity.Folder;
import com.itlyc.document.service.DocumentService;
import com.itlyc.document.service.FileService;
import com.itlyc.sys.dto.CompanyUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class DocumentController {

    private final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;
    @Autowired
    private FileService fileService;

    /**
     * 保存文件夹
     * @param folder 文件夹对象
     * @return
     */
    @PostMapping("/document/insertFolder")
    public Result saveFolder(@RequestBody Folder folder){
        try {
            documentService.saveFolder(folder);
        } catch (Exception exception) {
            logger.error("文档中心保存文件夹失败,{},{}", exception.getMessage(), exception);
        }
        return Result.success();
    }

    /**
     * 添加文件
     * 移动端
     */
    @PostMapping(value = "/document/insertFile")
    public Result saveFile(@RequestBody File File){
        try {
            documentService.saveFile(File);
        } catch (Exception exception) {
            logger.error("文档中心保存文档失败,{},{}", exception.getMessage(), exception);
        }
        return Result.success();
    }

    /**
     * 文档及文件夹列表查询
     * @param parentFolderId 父级文件夹id
     * @param keyword 查询关键字
     * @return
     */
    @GetMapping("/document/listFolderAndFile")
    public Result<DocumentDTO> queryAllFolderAndFile(
            @RequestParam(value = "parentFoldId", defaultValue = "0") Long parentFolderId,
            @RequestParam(value = "keyword",required = false) String keyword){
        return Result.success(documentService.queryAllFolderAndFile(parentFolderId, keyword));
    }

    /**
     * 根据文件id查询文档
     * @param fileId 文档id
     * @return
     */
    @GetMapping("/document/getFileByFileId")
    public Result<File> queryFileByFileId(@RequestParam("id") Long fileId){
        return Result.success(fileService.queryFileByFileId(fileId));
    }

    /**
     * 移动端
     * 查询指定文档的协作者列表
     * @param page
     * @param pageSize
     * @param fileId
     * @return
     */
    @GetMapping("/document/pagingCollaborations")
    public Result<PageResult<CompanyUserDTO>> pagingCollaborations(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam("id") Long fileId
    ){
        return Result.success(documentService.pagingCollaborations(fileId, page, pageSize));
    }

    /**
     * 远程调用系统微服务获得所有员工记录信息 判断是是否是拥有者或者协作者
     * @param fileId 文档ID
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/document/pagingUsers")
    public Result<List<UserCollaborationDTO>> pagingCollaborationUsers(@RequestParam("id") Long fileId) throws Exception {
        return Result.success(documentService.pagingCollaborationUsers(fileId));
    }

    /**
     * 添加协作者
     * 移动端
     */
    @PostMapping(value = "/document/insertCollaboration")
    public Result insertCollaboration(@RequestBody CollaborationsDTO collaborationsDTO) throws Exception {
        documentService.insertCollaboration(collaborationsDTO);
        return Result.success();
    }

    /**
     * 删除协作者
     * 移动端
     */
    @PostMapping(value = "/document/deleteCollaboration")
    public Result deleteCollaboration(@RequestBody CollaborationsDTO collaborationsDTO) throws Exception {
        documentService.deleteCollaboration(collaborationsDTO);
        return Result.success();
    }
}
