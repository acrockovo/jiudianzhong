package com.itlyc.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //安全拦截机制（最重要）
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                /**
                 * permitAll() 允许匿名访问（不需要认证，不需要权限） actuator：节点健康检查端点-rest接口匿名访问
                 */
                .antMatchers("/actuator/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin();
    }

    /**
     * 密码模式下需要：用户认证时需要的认证管理和用户信息来源
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
