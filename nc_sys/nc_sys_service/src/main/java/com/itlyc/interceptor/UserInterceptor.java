package com.itlyc.interceptor;

import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.JsonUtils;
import com.itlyc.common.vo.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userInfoJson = (String) authentication.getPrincipal();
            UserInfo userInfo = JsonUtils.fromJson(userInfoJson, UserInfo.class);
            UserHolder.setUser(userInfo);
        } catch (Exception exception) {
            // 有异常静默处理
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}
