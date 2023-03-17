package com.itlyc.auth.integration.authenticator.impl;

import com.itlyc.auth.integration.authenticator.LoginHandler;
import com.itlyc.auth.integration.entity.LoginInfo;
import com.itlyc.sys.client.SysClient;
import com.itlyc.sys.dto.CompanyUserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;

@Component
public class SmsLoginHandler implements LoginHandler {

    @Autowired
    private SysClient sysClient;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CompanyUserDTO queryUser(LoginInfo loginInfo) {
        String username = loginInfo.getAuthParameter("username").trim();
        String requestSmsCode = loginInfo.getAuthParameter("password").trim();
        CompanyUserDTO companyUserDTO = sysClient.querySysUser(username).getData();
        if(companyUserDTO == null){
            throw new OAuth2Exception("用户名不存在！");
        }
        String smsCode = "123456";
        if(StringUtils.isBlank(smsCode) || !smsCode.equals(requestSmsCode)){
            throw new OAuth2Exception("验证码有误！");
        }
        companyUserDTO.setPassword(passwordEncoder.encode(smsCode));
        return companyUserDTO;
    }

    /**
     * 短信认证方式
     * @param loginInfo 集成认证实体
     * @return
     */
    @Override
    public boolean support(LoginInfo loginInfo) {
        if(StringUtils.isNotBlank(loginInfo.getAuthType()) && loginInfo.getAuthType().equals("sms")){
            return true;
        }
        return false;
    }
}
