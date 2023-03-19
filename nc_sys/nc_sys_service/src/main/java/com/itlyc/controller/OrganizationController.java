package com.itlyc.controller;

import com.itlyc.common.vo.PageResult;
import com.itlyc.common.vo.Result;
import com.itlyc.service.CompanyUserService;
import com.itlyc.service.OrganizationService;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.dto.DepartmentDTO;
import com.itlyc.sys.dto.MemberExcelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    /**
     * 分页获取部门成员列表
     * @param page 当前页
     * @param pageSize 页大小
     * @param departmentId 部门ID
     * @param keyword 查询关键字
     * @throws Exception
     */
    @GetMapping(value = "/organization/members")
    public Result<PageResult<CompanyUserDTO>> queryCompanyMembers(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam(value = "departmentId", required = false) Long departmentId,
            @RequestParam(value = "keyword", required = false) String keyword) throws Exception {
        return Result.success(organizationService.queryCompanyMembers(page, pageSize, departmentId, keyword));
    }

    /**
     * PC:通讯录Json导入导入
     */
    @PostMapping(value = "/organization/members/list")
    public Result uploadExcel(@RequestBody MemberExcelRequest memberExcelChineseDTOs) throws Exception {
        organizationService.excelImport(memberExcelChineseDTOs);
        return Result.success();
    }

    /**
     * 在服务器端接收上传excel文件 解析excel文件中内容，批量导入员工记录
     * @param file
     * @return
     */
    @PostMapping("/organization/members/uploadXls")
    public Result uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        organizationService.uploadExcel(file);
        return Result.success();
    }
}
