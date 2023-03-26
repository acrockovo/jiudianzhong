package com.itlyc.document.service;

import com.itlyc.common.vo.Result;
import com.itlyc.document.entity.File;

import java.util.List;

public interface FileService {
    // 新建文档
    void save(File file);
    // 查询当前企业中 当前文件夹下 公共读 公共读写 文档  状态为审核通过
    List<File> queryCompanyFiles(Long companyId, Long all_read, Long all_read_write, Long parentFolderId, Long userId, String keyword);
    // 根据文档id查询文档
    File queryFileByFileId(Long fileId);
    // 更新文档
    void updateFile(File file);
}
