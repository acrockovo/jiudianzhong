package com.itlyc.controller;

import com.itlyc.common.vo.Result;
import com.itlyc.service.CompanyService;
import com.itlyc.sys.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    /**
     * 上传企业logo
     * @param file 文件对象
     * @return
     */
    @PostMapping("/company/uploadOSS")
    public Result<String> uploadLogo(MultipartFile file) throws IOException {
        return Result.success("上传成功", companyService.uploadLogo(file));
    }

    /**
     * 修改企业信息
     * @param company 企业对象
     * @return
     */
    @PutMapping("/company/company")
    public Result updateCompany(@RequestBody Company company){
        companyService.updateCompany(company);
        return Result.success();
    }
}
