package com.itlyc.auth.integration.advice;

import com.itlyc.auth.integration.entity.MyOauthTokenDTO;
import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * @author itlyc
 * 自定义切面类，重新封装响应结果Result数据
 */
@Component
@Aspect
@Slf4j
public class AuthTokenAspect {

    /**
     * 采用@Around环绕通知是可以改变controller目标方法返回值的
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = pjp.proceed();
        Result result = null;
        if (proceed != null) {
            ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>)proceed;
            OAuth2AccessToken body = responseEntity.getBody();
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                MyOauthTokenDTO myOauthTokenDTO = new MyOauthTokenDTO();
                myOauthTokenDTO.setOauth2AccessToken(body);
                //从认证后存入ThreadLocal中获取当前认证用户信息
                myOauthTokenDTO.setUser(UserHolder.getUser());
                result =  Result.success("认证成功", myOauthTokenDTO);
            } else {
                log.error("error:{}", responseEntity.toString());
                result = Result.errorCodeMessage(401, "认证失败");
            }
        }
         return ResponseEntity.status(result.getCode()).body(result);
    }
}
