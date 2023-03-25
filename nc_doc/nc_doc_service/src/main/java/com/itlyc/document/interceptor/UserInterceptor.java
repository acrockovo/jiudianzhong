package com.itlyc.document.interceptor;

import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.JsonUtils;
import com.itlyc.common.vo.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 执行时机，Security校验解析JWT令牌过滤器之后执行
 * 从Security上下文中获取用户主体信息
 * @author: itlycheima
 * @create: 2023-03-25
 */
@Component
public class UserInterceptor implements HandlerInterceptor {


    /**
     * 从Security上下文中获取用户信息，将用户信息存入ThreadLocal
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从Security上下文中获取用户信息
        try {
            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
            String principal = (String) authentication1.getPrincipal();
            UserInfo userInfo = JsonUtils.jsonToPojo(principal, UserInfo.class);
            //将用户信息存入ThreadLocal
            UserHolder.setUser(userInfo);
        } catch (Exception e) {
        }
        return true;
    }


    /**
     * 调用完目标方法将ThreadLocal中信息清除
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}
