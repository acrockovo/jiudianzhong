package com.itlyc.auth.integration.threadlocals;

import com.itlyc.auth.integration.entity.LoginInfo;

public class LoginInfoHolder {

    private static final ThreadLocal<LoginInfo> holder = new ThreadLocal<>();

    /**
     * 存储用户认证信息
     * @param loginInfo
     */
    public static void set(LoginInfo loginInfo){
        holder.set(loginInfo);
    }

    /**
     * 获取用户认证信息
     * @return
     */
    public static LoginInfo get(){
        return holder.get();
    }

    /**
     * 删除用户认证信息
     */
    public static void remove(){
        holder.remove();
    }
}
