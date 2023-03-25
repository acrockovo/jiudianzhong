package com.itlyc.document.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件夹表 文档-文件夹表
 * </p>
 *
 * @author itlyc
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("doc_folder")
public class Folder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 企业id 企业id
     */
    private Long companyId;

    /**
     * 父目录id
     */
    private Long parentId;

    /**
     * 文件夹名称 文件夹名称
     */
    private String name;

    /**
     * 排序 排序：最小为0
     */
    @TableField("`order`")
    private Integer order;

    /**
     * 创建人 创建人
     */
    private Long companyUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 使用状态 使用状态：1为可用，0为不可用
     */
    private Integer status;


}
