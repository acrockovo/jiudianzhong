package com.itlyc.service.impl;

import com.itlyc.mapper.CompanyUserMapper;
import com.itlyc.service.CompanyUserService;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.entity.CompanyUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {

    @Resource
    private CompanyUserMapper companyUserMapper;

    /**
     * 根据手机号查询用户
     * @param userName 手机号
     * @return
     */
    @Override
    public CompanyUserDTO querySysUser(String userName) {
        CompanyUser companyUser = companyUserMapper.findSysUser(userName);
        CompanyUserDTO companyUserDTO = new CompanyUserDTO();
        BeanUtils.copyProperties(companyUser,companyUserDTO);
        return companyUserDTO;
    }
}
