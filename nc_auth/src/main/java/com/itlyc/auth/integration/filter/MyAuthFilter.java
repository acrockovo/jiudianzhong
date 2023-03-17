package com.itlyc.auth.integration.filter;

import com.itlyc.auth.integration.entity.LoginInfo;
import com.itlyc.auth.integration.threadlocals.LoginInfoHolder;
import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.RequestParameterWrapper;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class MyAuthFilter extends GenericFilterBean {

    AntPathRequestMatcher antPathRequestMatcher = null;

    public MyAuthFilter(){
        this.antPathRequestMatcher = new AntPathRequestMatcher("/oauth/token","POST");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(antPathRequestMatcher.matches(request)){
            RequestParameterWrapper parameterWrapper = new RequestParameterWrapper(request);
            LoginInfo loginInfo = new LoginInfo();
            String auth_type = parameterWrapper.getParameter("auth_type");
            loginInfo.setAuthType(auth_type);
            // 获取参数列表
            loginInfo.setAuthParameters(parameterWrapper.getParameterMap());
            LoginInfoHolder.set(loginInfo);
            // 请求继续转发
            filterChain.doFilter(request,response);
            // 删除threadlocal中保存的信息
            LoginInfoHolder.remove();
            UserHolder.remove();
        }
    }
}
