package com.itlyc.sys.dto;

import lombok.Data;

/**
 * 用户申请加入企业接收参数
 TODO 注意lombok同名属性生成getset方法 bug
 */
@Data
public class UserJoinCompanyDTO {

    //用户ID
    private Long userId;
    //申请加入目标企业ID
    private Long companyId;
    private String companyName;
    //TODO APP段提交参数 名称 userName
    private String userName;
    private String username;
    //申请原因
    private String applyReason;
    //申请头像
    private String imageUrl;
    //手机号
    private String mobile;
    //职位
    private String post;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
