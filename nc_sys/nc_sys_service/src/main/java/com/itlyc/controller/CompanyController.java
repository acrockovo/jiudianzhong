package com.itlyc.controller;

import com.itlyc.common.vo.Result;
import com.itlyc.service.CompanyService;
import com.itlyc.sys.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 查询当前用户登录企业
     * @return
     */
    @GetMapping("/company/company")
    public Result<Company> queryCurrentCompany(){
        return Result.success("查询成功",  companyService.queryCurrentCompany());
    }
}
