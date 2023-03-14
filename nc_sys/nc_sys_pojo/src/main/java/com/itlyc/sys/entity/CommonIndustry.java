package com.itlyc.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 行业字典表 企业-行业字典表
 * </p>
 *
 * @author lyc
 * @since 2022-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_common_industry")
public class CommonIndustry implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id id
     */
    private String id;

    /**
     * 行业名称 行业名称
     */
    private String name;

    /**
     * 父行业id 父行业id
     */
    private String parentId;

    /**
     * 使用状态 使用状态：1为可用，0为不可用
     */
    private Integer status;


}
