package com.itlyc.mapper;

import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.entity.CompanyUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyUserMapper {
    // 根据手机号查询用户
    CompanyUser findSysUser(String userName);
    // 连接查询管理员用户
    List<CompanyUserDTO> queryCompanyAdmins(@Param("roleNameLike") String roleNameLike, @Param("companyId") Long companyId);
}
