package com.itlyc.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : itlyc
 * @description:
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {

    /**
     * 用户信息
     */
    private Long id;
    private String username;
    private String roleDesc;

    private String mobile;
    private String imageUrl;
    private Boolean enable;
    /**
     *  公司用户相关信息
     */
    private Long companyId;
    private Long departmentId;
    private String post;
    private String workNumber;

}
