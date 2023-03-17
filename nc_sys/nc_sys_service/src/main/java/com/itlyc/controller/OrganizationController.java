package com.itlyc.controller;

import com.itlyc.common.vo.PageResult;
import com.itlyc.common.vo.Result;
import com.itlyc.service.CompanyUserService;
import com.itlyc.sys.dto.CompanyUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationController {

    @Autowired
    private CompanyUserService companyUserService;

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
}
