package com.itlyc.auth.integration.authenticator.impl;

import com.itlyc.auth.integration.authenticator.LoginHandler;
import com.itlyc.auth.integration.entity.LoginInfo;
import com.itlyc.sys.client.SysClient;
import com.itlyc.sys.dto.CompanyUserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;

/**
 * 密码认证器
 */
@Component
public class PwdLoginHandler implements LoginHandler {

    @Autowired
    private SysClient sysClient;

    /**
     * 查询用户
     * @param loginInfo 集成认证实体
     * @return
     */
    @Override
    public CompanyUserDTO queryUser(LoginInfo loginInfo) {
        String username = loginInfo.getAuthParameter("username").trim();
        CompanyUserDTO companyUserDTO = sysClient.querySysUser(username).getData();
        if(companyUserDTO == null){
            throw new OAuth2Exception("该用户不存在!");
        }
        return companyUserDTO;
    }

    @Override
    public boolean support(LoginInfo loginInfo) {
        // 规定如果请求的auth_type参数为空则为密码认证方式
        return StringUtils.isBlank(loginInfo.getAuthType());
    }
}
