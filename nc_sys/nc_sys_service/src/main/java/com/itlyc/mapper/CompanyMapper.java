package com.itlyc.mapper;

import com.itlyc.sys.entity.Company;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyMapper {
    // 查询当前登录用户企业
    Company queryCurrentCompany(Long companyId);
}
