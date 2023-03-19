package com.itlyc.controller;

import com.itlyc.common.vo.PageResult;
import com.itlyc.common.vo.Result;
import com.itlyc.service.CompanyUserService;
import com.itlyc.service.OrganizationService;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.dto.DepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrganizationController {

    @Autowired
    private CompanyUserService companyUserService;
    @Autowired
    private OrganizationService organizationService;

    /**
     * 分页获取部门成员列表
     * @param page 页码
     * @param pageSize 每页条数
     * @return
     */
    @GetMapping("/organization/members/simple")
    public Result<PageResult<CompanyUserDTO>> queryCompanyMembersByPage(
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize){
        return Result.success("查询成功", companyUserService.queryCompanyMembersByPage(page,pageSize));
    }

    /**
     * 查询当前企业下部门列表
     * @return
     */
    @GetMapping("/organization/department")
    public Result<List<DepartmentDTO>> queryDepartmentByTree(){
        return Result.success("查询部门列表成功", organizationService.queryDepartmentByTree());
    }
}
