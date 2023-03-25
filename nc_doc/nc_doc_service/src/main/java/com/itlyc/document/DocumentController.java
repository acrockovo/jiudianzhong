package com.itlyc.document;

import com.itlyc.common.vo.Result;
import com.itlyc.document.entity.File;
import com.itlyc.document.entity.Folder;
import com.itlyc.document.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    /**
     * 保存文件夹
     * @param folder 文件夹对象
     * @return
     */
    @PostMapping("/document/insertFolder")
    public Result saveFolder(@RequestBody Folder folder){
        documentService.saveFolder(folder);
        return Result.success();
    }

}
