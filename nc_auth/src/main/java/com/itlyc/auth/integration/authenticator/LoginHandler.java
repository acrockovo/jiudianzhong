package com.itlyc.auth.integration.authenticator;

import com.itlyc.auth.integration.entity.LoginInfo;
import com.itlyc.sys.dto.CompanyUserDTO;

/**
 * 自定义认证器
 */
public interface LoginHandler {
    /**
     * 远程调用系统微服务得到用户信息
     *
     * @param loginInfo 集成认证实体
     * @return 用户表实体
     */
    CompanyUserDTO queryUser(LoginInfo loginInfo);

    /**
     * 认证器各个实现类
     * 判断当前客户端提交认证类型是否支持当前认证器
     *
     * @param loginInfo 集成认证实体
     * @return true:采用当前认证器  false：不支持
     */
    boolean support(LoginInfo loginInfo);
}
