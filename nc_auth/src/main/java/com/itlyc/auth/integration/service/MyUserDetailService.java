package com.itlyc.auth.integration.service;

import com.itlyc.auth.integration.authenticator.LoginHandler;
import com.itlyc.auth.integration.entity.LoginInfo;
import com.itlyc.auth.integration.threadlocals.LoginInfoHolder;
import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.BeanHelper;
import com.itlyc.common.util.JsonUtils;
import com.itlyc.common.vo.UserInfo;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private List<LoginHandler> loginHandlerList;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("用户详情服务被调用了: {}", userName);
        LoginInfo loginInfo = LoginInfoHolder.get();
        log.info("当前用户提交的参数: {}", loginInfo);

        LoginHandler loginHandler = chooseLoginHandler(loginInfo);
        CompanyUserDTO companyUserDTO = loginHandler.queryUser(loginInfo);
        // 后台管理系统 只有管理员才能登陆
        String clientId = loginInfo.getAuthParameter("client_id");
        if("pc_client".equals(clientId)){
            //说明用户登录后台
            checkUserPermission(companyUserDTO);
        }

        List<SimpleGrantedAuthority> authorityList = getCompanyUserAuthority(companyUserDTO);
        if(CollectionUtils.isEmpty(authorityList)){
            //如果没有查询到权限 返回默认 游客/访客 角色
            authorityList =  Arrays.asList(new SimpleGrantedAuthority("ROLE_USER_TOURIST"));
        }

        UserInfo userInfo = BeanHelper.copyProperties(companyUserDTO, UserInfo.class);
        UserHolder.setUser(userInfo);

        return new User(JsonUtils.toJsonStr(userInfo),companyUserDTO.getPassword(),authorityList);
    }

    /**
     * 查询角色权限列表
     * @param companyUserDTO
     * @return
     */
    private List<SimpleGrantedAuthority> getCompanyUserAuthority(CompanyUserDTO companyUserDTO) {
        List<SimpleGrantedAuthority> roleAuthorityList = null;
        List<SimpleGrantedAuthority> functionAuthorityList = null;
        // 角色
        if(!CollectionUtils.isEmpty(companyUserDTO.getSysRoles())){
            roleAuthorityList = companyUserDTO.getSysRoles().stream().map(role -> {
            String roleName = role.getRoleName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            return simpleGrantedAuthority;
            }).collect(Collectors.toList());
        }
        // 权限
        if(!CollectionUtils.isEmpty(companyUserDTO.getSysFunctions())) {
                functionAuthorityList = companyUserDTO.getSysFunctions().stream().map(function -> {
                String functionName = function.getName();
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(functionName);
                return simpleGrantedAuthority;
            }).collect(Collectors.toList());
        }
        if(!CollectionUtils.isEmpty(roleAuthorityList)){
            roleAuthorityList.addAll(functionAuthorityList);
        }
        return roleAuthorityList;
    }

    /**
     * 检验是否为企业管理员
     * @param companyUserDTO 用户
     */
    private void checkUserPermission(CompanyUserDTO companyUserDTO) {
        Boolean isAdmin = false;
        if(CollectionUtils.isEmpty(companyUserDTO.getSysRoles())){
            throw new OAuth2Exception("您不是企业管理员，不能登录后台系统!");
        }
        for (Role role : companyUserDTO.getSysRoles()) {
            if(StringUtils.startsWith(role.getRoleName(),"ROLE_ADMIN_")){
                isAdmin = true;
                break;
            }
        }
        if(!isAdmin){
            throw new OAuth2Exception("您不是企业管理员，不能登录后台系统!");
        }
    }

    /**
     * 选择认证器
     * @param loginInfo 认证参数对象
     * @return
     */
    private LoginHandler chooseLoginHandler(LoginInfo loginInfo) {
        for (LoginHandler loginHandler : loginHandlerList) {
            boolean b = loginHandler.support(loginInfo);
            if(b){
                return loginHandler;
            }
        }
        return null;
    }
}
