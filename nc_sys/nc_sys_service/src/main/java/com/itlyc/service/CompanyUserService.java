package com.itlyc.service;

import com.itlyc.sys.dto.CompanyUserDTO;

import java.util.List;

public interface CompanyUserService {
    // 根据手机号查询用户
    CompanyUserDTO querySysUser(String userName);
    // 查询当前企业下所有的管理员用户
    List<CompanyUserDTO> queryCompanyAdmins();
}
