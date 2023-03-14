package com.itlyc.service.impl;

import com.itlyc.mapper.RoleMapper;
import com.itlyc.service.RoleService;
import com.itlyc.sys.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    /**
     * 根据角色列表查询角色信息
     * @param roleIdList 角色列表
     * @return
     */
    @Override
    public List<Role> findRoleByIds(List<String> roleIdList) {
        return roleMapper.findRoleByIds(roleIdList);
    }
}
