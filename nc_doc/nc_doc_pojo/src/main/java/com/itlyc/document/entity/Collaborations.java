package com.itlyc.document.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("doc_collaborations")
public class Collaborations implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文档ID
     */
    private Long fileId;

    /**
     * 员工ID
     */
    private Long companyUserId;


}
