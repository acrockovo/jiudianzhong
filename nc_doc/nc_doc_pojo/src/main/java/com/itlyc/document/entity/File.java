package com.itlyc.document.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件表 文档-文件表
 * </p>
 *
 * @author itlyc
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("doc_file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件夹id 文件夹id
     */
    private Long folderId;

    /**
     * 创建人id 创建人
     */
    private Long companyUserId;

    /**
     * 文件名称 文件名
     */
    private String name;

    /**
     * 文件路径 文件路径
     */
    private String filePath;

    /**
     * 文档大小，单位为K
     */
    private String fileSize;

    /**
     * 是否可用 1可用 0禁用
     */
    private Boolean enable;

    /**
     * 协作人文件权限 文件权限：0为公共读，1为公共读写，2为私有
     */
    private Integer authority;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private Long updateUserId;

    private Long companyId;

    /**
     * 文档内容
     */
    private String content;

    /**
     * 文档类型：1本地文档 2云文档
     */
    private Integer type;

    /**
     * 文档分类ID
     */
    private Long categoryId;

    /**
     * 当前状态 0：草稿  1：提交（待审核） 2：审核失败 3：人工审核  8：审核通过
     */
    private Integer status;

    /**
     * 是否热点
     */
    private Boolean ifHot;


}
