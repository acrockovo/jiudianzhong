package com.itlyc.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 企业员工表
 * </p>
 *
 * @author itheima
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_company_user")
public class CompanyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

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
    private LocalDateTime timeEntry;

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
    private Boolean enable;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 头像地址
     */
    private String imageUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 版本号
     */
    private Integer version;


}
