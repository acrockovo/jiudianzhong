package com.itlyc.service;

import com.itlyc.sys.dto.RoleDTO;
import com.itlyc.sys.dto.SysRoleEditDTO;
import com.itlyc.sys.entity.Role;

import java.util.List;

public interface RoleService {
    // 根据角色id列表查询角色信息
    List<Role> findRoleByIds(List<String> roleIdList);
    // 查询当前登录用户角色权限信息
    List<RoleDTO> queryCompanyRoleList();
    // 修改角色信息
    void updateRole(SysRoleEditDTO sysRoleEditDTO);
}
