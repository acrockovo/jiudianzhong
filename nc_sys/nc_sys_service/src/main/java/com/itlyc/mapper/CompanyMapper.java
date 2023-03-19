package com.itlyc.mapper;

import com.itlyc.sys.entity.Company;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyMapper {
    // 查询当前登录用户企业
    Company queryCurrentCompany(Long companyId);
    // 修改企业信息
    void updateCompany(Company company);
    // 查询企业列表
    List<Company> queryCompanyList(@Param("keyword") String keyword, @Param("industryId") String industryId);
}
