package com.itlyc.service.impl;

import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.BeanHelper;
import com.itlyc.mapper.RoleMapper;
import com.itlyc.service.CompanyUserService;
import com.itlyc.service.FunctionService;
import com.itlyc.service.RoleService;
import com.itlyc.sys.dto.RoleDTO;
import com.itlyc.sys.entity.CompanyUser;
import com.itlyc.sys.entity.Function;
import com.itlyc.sys.entity.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Autowired
    private FunctionService functionService;
    /**
     * 根据角色列表查询角色信息
     * @param roleIdList 角色列表
     * @return
     */
    @Override
    public List<Role> findRoleByIds(List<String> roleIdList) {
        return roleMapper.findRoleByIds(roleIdList);
    }

    /**
     * 查询当前登录用户角色权限信息
     * @return
     */
    @Override
    public List<RoleDTO> queryCompanyRoleList() {
        Long companyId = UserHolder.getCompanyId();
        List<Role> roleList = roleMapper.findRoleByCompanyId(companyId);
        List<RoleDTO> roleDTOS = BeanHelper.copyWithCollection(roleList, RoleDTO.class);
        if(!CollectionUtils.isEmpty(roleDTOS)){
            for (RoleDTO roleDTO : roleDTOS) {
                String functionIds = roleDTO.getFunctionIds();
                if(StringUtils.isNotBlank(functionIds)){
                    List<String> functionIdList = Arrays.asList(StringUtils.split(functionIds, ","));
                    List<Function> functionList = functionService.findFunctionByIds(functionIdList);
                    roleDTO.setSysFunctionAbbrList(functionList);
                }
            }
        }
        return roleDTOS;
    }
}
