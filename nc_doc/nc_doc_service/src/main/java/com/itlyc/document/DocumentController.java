package com.itlyc.document;

import com.itlyc.common.vo.Result;
import com.itlyc.document.dto.DocumentDTO;
import com.itlyc.document.entity.File;
import com.itlyc.document.entity.Folder;
import com.itlyc.document.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class DocumentController {

    private final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

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
}
