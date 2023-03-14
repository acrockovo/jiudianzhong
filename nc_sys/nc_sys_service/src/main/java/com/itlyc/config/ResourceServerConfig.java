package com.itlyc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Configuration
@EnableResourceServer  //开启资源服务器
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //用户对某个控制层的方法是否具有访问权限
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private PermitUrlProperties permitUrlProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        List<String> permitUrlList = permitUrlProperties.getPermitUrlList();

        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                    .authorizeRequests()
                    .antMatchers(permitUrlList.toArray(new String[]{})).permitAll()  //匿名访问
             		 //控制权限方式一:采用过滤器 拦截请求
                     //配置其他的rest接口 安全策略
                    //.antMatchers("/test/user").hasAuthority("p1") //访问保存用户接口必须有p1权限
                .anyRequest().authenticated()  //剩余其他的请求 需要认证后方可访问
                .and()
                .httpBasic();
    }
}
