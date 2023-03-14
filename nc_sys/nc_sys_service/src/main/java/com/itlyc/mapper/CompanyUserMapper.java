package com.itlyc.mapper;

import com.itlyc.sys.entity.CompanyUser;

public interface CompanyUserMapper {
    // 根据手机号查询用户
    CompanyUser findSysUser(String userName);
}
