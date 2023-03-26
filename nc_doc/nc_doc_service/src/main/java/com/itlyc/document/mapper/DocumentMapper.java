package com.itlyc.document.mapper;

import com.itlyc.document.entity.Collaborations;
import com.itlyc.document.entity.Folder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocumentMapper {
    // 保存文件夹
    void saveFolder(Folder folder);
    // 查询文件夹
    List<Folder> queryCompanyFolder(@Param("parentFolderIdId") Long parentFolderId, @Param("companyId") Long companyId);
    // 查询协作者
    List<Collaborations> queryCollaborationsByFileId(Long fileId);
}
