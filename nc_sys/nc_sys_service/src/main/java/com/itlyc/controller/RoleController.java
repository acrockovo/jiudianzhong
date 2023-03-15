package com.itlyc.controller;

import com.itlyc.common.vo.Result;
import com.itlyc.service.RoleService;
import com.itlyc.sys.dto.RoleDTO;
import com.itlyc.sys.dto.SysRoleEditDTO;
import com.itlyc.sys.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询当前登录用户角色权限信息
     * @return
     */
    @GetMapping("/company/role")
    public Result<List<RoleDTO>> queryCompanyRoleList(){
        return Result.success(roleService.queryCompanyRoleList());
    }

    @PutMapping("/company/role")
    public Result updateRole(@RequestBody SysRoleEditDTO sysRoleEditDTO){
        roleService.updateRole(sysRoleEditDTO);
        return Result.success();
    }
}
