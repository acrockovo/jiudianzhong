package com.itlyc.service;

import com.itlyc.common.vo.PageResult;
import com.itlyc.sys.dto.CompanyUserAdminDTO;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.entity.CompanyUser;

import java.util.List;

public interface CompanyUserService {
    // 根据手机号查询用户
    CompanyUserDTO querySysUser(String userName);
    // 查询当前企业下所有的管理员用户
    List<CompanyUserDTO> queryCompanyAdmins();
    // 分页获取部门成员列表
    PageResult<CompanyUserDTO> queryCompanyMembersByPage(Integer page, Integer pageSize);
    // 新增子管理员
    int addSubAdmin(CompanyUserAdminDTO companyUserAdminDTO);
    // 移动端用户注册
    Long register(CompanyUser companyUser, String checkcode);
}
