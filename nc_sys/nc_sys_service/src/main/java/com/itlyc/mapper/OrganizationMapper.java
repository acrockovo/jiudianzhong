package com.itlyc.mapper;

import com.itlyc.sys.dto.DepartmentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrganizationMapper {
    // 查询当前企业下部门列表
    List<DepartmentDTO> queryDepartmentByTree(@Param("companyId") Long companyId, @Param("parentId") Long parentId);
    // 根据parentId查询子部门信息
    List<DepartmentDTO> queryDepartmentByTreeByParentId(@Param("id") Long id,@Param("companyId") Long companyId);
}
