package com.itlyc.service;

import com.itlyc.sys.entity.Company;

public interface CompanyService {
    // 查询当前登录用户企业
    Company queryCurrentCompany();
}
