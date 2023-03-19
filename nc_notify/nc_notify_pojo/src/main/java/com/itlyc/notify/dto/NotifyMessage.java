package com.itlyc.notify.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotifyMessage {
    /**
     * 消息id
     */
    private String _id;
    /**
     * 消息类型:团队申请，  审批消息等
     */
    private String messageType;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 企业id
     */
    private Long companyId;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息摘要
     */
    private String content;

    /**
     * 消息使用状态 已读:1 未读:0
     */
    private Integer useStatus = 0;
    /**
     * 消息发送目标人(目标用户手机号)
     */
    private List<String> targets;

    /**
     * 发起方ID
     */
    private Long applyUserId;

    //发起方用户名称
    private String applyUsername;

    /**
     * 接收方ID
     */
    private Long approveUserId;

    /**
     * 审批人用户名称
     */
    private String approveUsername;

    /**
     * 审批状态 0未审核 1已审核
     */
    private Integer approveStatue = 0;


    /**
     * 审核结果 0 拒绝请求 1：审核通过
     */
    private String approveResult;

    /**
     * 审核备注
     */
    private String approveRemark;
}
