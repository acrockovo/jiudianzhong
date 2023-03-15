package com.itlyc.common.threadLocals;


import com.itlyc.common.vo.UserInfo;

/**
 * @description: 实现ThreadLocal 存取删操作
 **/
public class UserHolder {

    private static ThreadLocal<UserInfo> TL = new ThreadLocal<>();

    /**
     * 存储用户ID 到当前线程
     *
     * @param user
     */
    public static void setUser(UserInfo user) {
        TL.set(user);
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserInfo getUser() {
        return TL.get();
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
        return TL.get().getId();
    }



    /**
     * 获取当前登录用户公司ID
     *
     * @return
     */
    public static Long getCompanyId() {
        return TL.get().getCompanyId();
    }

    /**
     * 获取用户名称
     *
     * @return
     */
    public static String getUserName() {
        return TL.get().getUsername();
    }

    /**
     * 删除ThreadLocal中线程用户信息
     */
    public static void remove() {
        TL.remove();
    }
}
