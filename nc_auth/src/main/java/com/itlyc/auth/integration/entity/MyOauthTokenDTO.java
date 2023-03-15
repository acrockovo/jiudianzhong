package com.itlyc.auth.integration.entity;

import com.itlyc.common.vo.UserInfo;
import lombok.Data;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * 认证通过后响应客户端对象
 */

@Data
public class MyOauthTokenDTO {

    private OAuth2AccessToken oauth2AccessToken;
    private UserInfo user;

}
