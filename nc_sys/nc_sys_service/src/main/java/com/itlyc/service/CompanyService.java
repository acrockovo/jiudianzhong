package com.itlyc.service;

import com.itlyc.sys.entity.Company;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompanyService {
    // 查询当前登录用户企业
    Company queryCurrentCompany();
    // 上传企业logo
    String uploadLogo(MultipartFile file) throws IOException;
    // 修改企业信息
    void updateCompany(Company company);
}
