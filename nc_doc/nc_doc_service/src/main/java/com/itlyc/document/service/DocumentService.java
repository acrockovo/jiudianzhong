package com.itlyc.document.service;

import com.itlyc.common.vo.PageResult;
import com.itlyc.document.dto.CollaborationsDTO;
import com.itlyc.document.dto.DocumentDTO;
import com.itlyc.document.dto.UserCollaborationDTO;
import com.itlyc.document.entity.File;
import com.itlyc.document.entity.Folder;
import com.itlyc.sys.dto.CompanyUserDTO;

import java.util.List;

public interface DocumentService {
    // 新建文件夹
    void saveFolder(Folder folder);
    // 新建文档
    void saveFile(File file);
    // 文档及文件夹列表查询
    DocumentDTO queryAllFolderAndFile(Long parentFolderId, String keyword);
    // 查询指定文档的协作者列表
    PageResult<CompanyUserDTO> pagingCollaborations(Long fileId, Integer page, Integer pageSize);
    // 远程调用系统微服务获得所有员工记录信息 判断是是否是拥有者或者协作者
    List<UserCollaborationDTO> pagingCollaborationUsers(Long fileId);
    // 新增协作者
    void insertCollaboration(CollaborationsDTO collaborationsDTO);
    // 删除协作者
    void deleteCollaboration(CollaborationsDTO collaborationsDTO);
}
