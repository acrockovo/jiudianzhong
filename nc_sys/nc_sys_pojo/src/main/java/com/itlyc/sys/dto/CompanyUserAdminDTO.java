package com.itlyc.sys.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompanyUserAdminDTO {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色列表
     */
    private List<String> roleIds;
}
