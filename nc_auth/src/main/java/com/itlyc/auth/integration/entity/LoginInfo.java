package com.itlyc.auth.integration.entity;

import lombok.Data;

import java.util.Map;

@Data
public class LoginInfo {

    /**
     * 请求登录认证类型
     */
    private String authType;

    /**
     * 请求登录认证参数集合
     */
    private Map<String, String[]> authParameters;

    /**
     * 通过参数名称获取登录参数的值
     * @param parameter 参数名称
     * @return 参数值
     */
    public String getAuthParameter(String parameter){
        String[] values = this.authParameters.get(parameter);
        if(values != null && values.length > 0){
            return values[0];
        }
        return null;
    }
}
