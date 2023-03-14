package com.itlyc.sys.dto;

import com.itlyc.sys.entity.Function;
import com.itlyc.sys.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 企业员工DTO对象
 * </p>
 *
 * @author itheima
 * @since 2021-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 企业id 企业id
     */
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 部门id 部门id
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 岗位 岗位
     */
    private String post;

    /**
     * 工号 工号
     */
    private String workNumber;

    /**
     * 邮箱 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 固定电话 固定电话
     */
    private String tel;

    /**
     * 办公地点 办公地点
     */
    private String officeAddress;

    /**
     * 入职时间 入职时间
     */
    private Date timeEntry;

    /**
     * 角色ID
     */
    private String roleIds;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态 1激活、2未激活、3注销
     */
    private Integer status;

    /**
     * 头像地址
     */
    private String imageUrl;

    /**
     * 员工角色列表
     */
    private List<Role> sysRoles;

    /**
     * 员工权限列表
     */
    private List<Function> sysFunctions;

    /**
     * 状态 0：不可用 1：可用
     */
    private Boolean enable;

}
