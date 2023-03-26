package com.itlyc.document.mapper;

import com.itlyc.document.entity.File;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {
    // 新建文档
    void save(File file);
    // 当前企业中 当前文件夹下 公共读 公共读写  作者 协作者 文档  状态为审核通过
    List<File> queryCompanyFiles(@Param("companyId") Long companyId, @Param("all_read") Long all_read,
                                 @Param("all_read_write") Long all_read_write, @Param("parentFolderId") Long parentFolderId,
                                 @Param("userId") Long userId, @Param("keyword") String keyword);
    // 根据文档id查询文档
    File queryFileByFileId(Long fileId);
}
