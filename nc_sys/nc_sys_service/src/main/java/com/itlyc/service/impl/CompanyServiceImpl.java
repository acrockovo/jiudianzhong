package com.itlyc.service.impl;

import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.mapper.CompanyMapper;
import com.itlyc.service.CompanyService;
import com.itlyc.sys.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 查询当前登录用户企业
     * @return
     */
    @Override
    public Company queryCurrentCompany() {
        Long companyId = UserHolder.getCompanyId();
        return companyMapper.queryCurrentCompany(companyId);
    }
}
