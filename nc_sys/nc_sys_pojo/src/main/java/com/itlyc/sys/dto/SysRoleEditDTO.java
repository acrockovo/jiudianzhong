package com.itlyc.sys.dto;

import lombok.Data;

import java.util.List;

@Data
public class SysRoleEditDTO {
    private Long id;
    private String roleName;
    private List<String> functionIdList;
}
