package com.itlyc.doc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author itlyc
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("doc_file_history")
public class FileHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文档ID
     */
    private Long fileId;

    /**
     * 文档名称
     */
    private String fileName;

    /**
     * 创建或者更新者id
     */
    private Long companyUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否是创建文件 true代表创建 false代表编辑
     */
    private Boolean ifCreate;

    /**
     * 文档内容
     */
    private String content;


}
