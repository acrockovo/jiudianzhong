package com.itlyc.service;

import com.itlyc.sys.entity.Role;

import java.util.List;

public interface RoleService {
    // 根据角色id列表查询角色信息
    List<Role> findRoleByIds(List<String> roleIdList);
}
