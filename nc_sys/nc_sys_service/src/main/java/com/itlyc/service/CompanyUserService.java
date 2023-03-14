package com.itlyc.service;

import com.itlyc.sys.dto.CompanyUserDTO;

public interface CompanyUserService {
    // 根据手机号查询用户
    CompanyUserDTO querySysUser(String userName);
}
