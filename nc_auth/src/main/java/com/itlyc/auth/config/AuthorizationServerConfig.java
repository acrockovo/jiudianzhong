package com.itlyc.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 注册bcrypt加密对象
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 定义用户详情服务：框架会调用该对象 UserDetailsService（封装用户名密码，权限）  查询用户信息用于判断用户认证信息合法。
     * TODO：将来将用户存入MySQL数据库中
     *
     * @return
     */
    /*@Bean
    public UserDetailsService userDetailsService(){
        //在内存中提供自定义的用户名 密码
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        //在内存中自定义用户名称：jack 密码：jack 权限：p1
        manager.createUser(User.withUsername("third").password(passwordEncoder().encode("third")).authorities("user:query").build());
        manager.createUser(User.withUsername("jack").password(passwordEncoder().encode("jack")).authorities("p1").build());
        manager.createUser(User.withUsername("rose").password(passwordEncoder().encode("rose")).authorities("p2").build());
        return manager;
    }*/

    /**
     * 配置客户端详情：移动端、web端，第三方应用 定义客户端ID，秘钥
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //客户端信息暂存储在内存中,也可改为security提供的表存储
        //授权码模式下需要的客户端信息如下
        clients.inMemory()
                .withClient("third_client")  //客户端标识
                .secret(passwordEncoder().encode("third_secret"))  //客户端的秘钥
                .authorizedGrantTypes("authorization_code")
                .scopes("all")
                .autoApprove("all")
                .redirectUris("http://www.baidu.com")  //设置回调地址

                //密码模式需要的客户端信息
                .and()
                .withClient("app_client")  //客户端标识
                .secret(passwordEncoder().encode("app_secret"))  //客户端的秘钥
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("all") //设置回调地址

                .and()
                .withClient("pc_client")
                .secret(passwordEncoder().encode("pc_secret"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("all");
    }

    /**
     * 注入令牌策略
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .tokenServices(tokenService());
    }

    /**
     * **令牌端点的安全约束，对密码模式表单提交允许**
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients();  //支持密码模式下表单登录
    }

    /**
     * 配置令牌服务相关信息
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices();
        //是否支持刷新令牌
        service.setSupportRefreshToken(true);
        //指定令牌产生加强对象(改为jwt令牌)
        service.setTokenEnhancer(jwtAccessTokenConverter);
        //令牌存储
        service.setTokenStore(tokenStore);
        // 令牌有效期2小时 默认12小时
        service.setAccessTokenValiditySeconds(7200);
        // 刷新令牌默认有效期3天 默认30天
        service.setRefreshTokenValiditySeconds(259200);
        return service;
    }
}
