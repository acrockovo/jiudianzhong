package com.itlyc.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
     * @author itheima
     * @date 2019/11/28 17:42
     */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberExcelDTO {

    private String username;

    private String mobile;

    private String departmentName;

    private String post;

    private String workNumber;

    private String ifManager;

    private String email;

    private String address;

    private String remark;

    private String timeEntry;

    private String enable;

    private String roleDesc;

}
