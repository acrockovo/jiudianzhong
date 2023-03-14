package com.itlyc.mapper;

import com.itlyc.sys.entity.Role;

import java.util.List;

public interface RoleMapper {
    // 根据角色列表查询角色信息
    List<Role> findRoleByIds(List<String> ids);
}
