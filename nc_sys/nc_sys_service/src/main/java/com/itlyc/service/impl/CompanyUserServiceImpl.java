package com.itlyc.service.impl;

import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.mapper.CompanyUserMapper;
import com.itlyc.service.CompanyUserService;
import com.itlyc.service.FunctionService;
import com.itlyc.service.RoleService;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.entity.CompanyUser;
import com.itlyc.sys.entity.Function;
import com.itlyc.sys.entity.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {

    @Resource
    private CompanyUserMapper companyUserMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FunctionService functionService;

    /**
     * 根据手机号查询用户
     * @param userName 手机号
     * @return
     */
    @Override
    public CompanyUserDTO querySysUser(String userName) {

        CompanyUser companyUser = companyUserMapper.findSysUser(userName);
        CompanyUserDTO companyUserDTO = new CompanyUserDTO();
        try {
            BeanUtils.copyProperties(companyUser,companyUserDTO);
        } catch (BeansException e) {
            throw new NcException(ResponseEnum.ERROR);
        }
        // 获取角色列表
        String roleIds = companyUserDTO.getRoleIds();
        if(StringUtils.isNotBlank(roleIds)){
            String[] roleIdArr = StringUtils.split(roleIds, ",");
            List<String> roleIdList = Arrays.asList(roleIdArr);
            // 查询角色列表
            List<Role> roleList = roleService.findRoleByIds(roleIdList);
            companyUserDTO.setSysRoles(roleList);

            List<Function> allFunctionList = new ArrayList<>();
            for (Role role : roleList) {
                String functionIds = role.getFunctionIds();
                if(StringUtils.isNotBlank(functionIds)){
                    List<String> functionIdList = Arrays.asList(StringUtils.split(functionIds, ","));
                    // 查询权限列表
                    List<Function> functionList = functionService.findFunctionByIds(functionIdList);
                    allFunctionList.addAll(functionList);
                }
            }
            // 对权限列表尽心去重
            allFunctionList = allFunctionList.stream().distinct().collect(Collectors.toList());
            companyUserDTO.setSysFunctions(allFunctionList);
        }

        return companyUserDTO;
    }
}
