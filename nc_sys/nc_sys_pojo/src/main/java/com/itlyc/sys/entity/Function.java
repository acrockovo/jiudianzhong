package com.itlyc.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author itheima
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_function")
public class Function implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 菜单名称 名称
     */
    private String title;

    /**
     * icon路径
     */
    private String icon;

    /**
     * 排序 最小为0
     */
    private Integer orderby;

    /**
     * 匹配路径
     */
    private String path;

    /**
     * 使用状态 使用状态，1为可用0为不可用
     */
    private Integer status;

    /**
     * 颜色取值
     */
    private String color;

    /**
     * 企业ID
     */
    private Long companyId;

    /**
     * 是否为菜单 1：是菜单 0：权限
     */
    private Boolean ifMenu;

    /**
     * 所属菜单ID
     */
    private Long parentId;


}
