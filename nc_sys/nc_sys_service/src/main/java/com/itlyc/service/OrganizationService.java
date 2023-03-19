package com.itlyc.service;

import com.itlyc.common.vo.PageResult;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.dto.DepartmentDTO;
import com.itlyc.sys.dto.MemberExcelRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OrganizationService {
    // 查询当前企业下部门列表
    List<DepartmentDTO> queryDepartmentByTree();
    // 分页获取部门成员列表
    PageResult<CompanyUserDTO> queryCompanyMembers(Integer page, Integer pageSize, Long departmentId, String keyword);
    // PC:通讯录Json导入导入
    void excelImport(MemberExcelRequest memberExcelChineseDTOs);
    // 在服务器端接收上传excel文件 解析excel文件中内容，批量导入员工记录
    void uploadExcel(MultipartFile file) throws IOException;
}
