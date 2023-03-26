package com.itlyc.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 黑马程序员
 * @date 2019/10/30 18:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCollaborationDTO {

    //员工ID
    private Long id;
    //用户姓名
    private String username;
    //状态 0既不是拥有者也不是协作者 1是拥有者 2是协作者
    private int state;
    //用户头像
    private String imgUrl;
    //手机号
    private String mobile;

}
