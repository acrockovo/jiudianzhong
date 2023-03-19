package com.itlyc.service;

import com.itlyc.common.vo.PageResult;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.dto.DepartmentDTO;

import java.util.List;

public interface OrganizationService {
    // 查询当前企业下部门列表
    List<DepartmentDTO> queryDepartmentByTree();
    // 分页获取部门成员列表
    PageResult<CompanyUserDTO> queryCompanyMembers(Integer page, Integer pageSize, Long departmentId, String keyword);
}
