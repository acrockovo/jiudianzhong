package com.itlyc.sys.dto;

import com.itlyc.sys.entity.Function;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author itheima
 * @since 2020-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 企业ID
     */
    private Long companyId;


    /**
     * 权限ID列表
     */
    private String functionIds;

    /**
     * 当前角色包含的权限
     */
    private List<Function> sysFunctionAbbrList;


}
