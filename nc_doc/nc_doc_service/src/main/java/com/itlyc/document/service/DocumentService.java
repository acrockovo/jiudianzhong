package com.itlyc.document.service;

import com.itlyc.document.dto.DocumentDTO;
import com.itlyc.document.entity.File;
import com.itlyc.document.entity.Folder;

public interface DocumentService {
    // 新建文件夹
    void saveFolder(Folder folder);
    // 新建文档
    void saveFile(File file);
    // 文档及文件夹列表查询
    DocumentDTO queryAllFolderAndFile(Long parentFolderId, String keyword);
}
