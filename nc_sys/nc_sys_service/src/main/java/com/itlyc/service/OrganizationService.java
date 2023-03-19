package com.itlyc.service;

import com.itlyc.sys.dto.DepartmentDTO;

import java.util.List;

public interface OrganizationService {
    // 查询当前企业下部门列表
    List<DepartmentDTO> queryDepartmentByTree();
}
