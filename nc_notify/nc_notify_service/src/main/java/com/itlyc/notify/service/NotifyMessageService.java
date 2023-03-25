package com.itlyc.notify.service;

import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.notify.dto.NotifyMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NotifyMessageService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 向用户手机设备推送通知
     *
     * @param notifyMessage
     */
    public void sendMsgToUser(NotifyMessage notifyMessage) {
        try {
            log.info("已向极光推送消息+{}",notifyMessage);
            mongoTemplate.save(notifyMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("推送微服务：推送设备通知失败，{}", e.getMessage());
        }
    }

    /**
     * 获取推送历史记录
     * @param id 消息唯一标识
     * @param type 推送消息类型 取值见NotifyMessageConstant
     * @param sponsor 发起方(可以是手机号，或者用户ID)
     * @param receiver 接收方（可以是手机号，或者用户ID）
     * @return
     */
    public List<NotifyMessage> queryPushMessage(String id, String type, String sponsor, String receiver) {
        // 申请者 接收者 必须有一个值
        if(StringUtils.isBlank(sponsor) && StringUtils.isBlank(receiver)){
            throw new NcException(ResponseEnum.INVALID_PARAM_ERROR);
        }
        Query query = new Query();
        if (id != null) {
            //根据唯一标识
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        }
        if (StringUtils.isNotBlank(type)) {
            //推送消息类型
            query.addCriteria(Criteria.where("messageType").is(type));
        }
        if (StringUtils.isNotBlank(sponsor)) {
            //发起方
            Criteria criteria = Criteria.where("applyUserId").is(Long.valueOf(sponsor));
            query.addCriteria(new Criteria().orOperator(criteria));
        }
        if (StringUtils.isNotBlank(receiver)) {
            //接收方
            Criteria criteria1 = Criteria.where("targets").in(receiver);
            Criteria criteria2 = Criteria.where("approveUserId").is(Long.valueOf(receiver));
            query.addCriteria(new Criteria().orOperator(criteria1, criteria2));
        }
        //根据创建时间倒序排序
        query.with(Sort.by(Sort.Order.desc("createTime")));
        return mongoTemplate.find(query, NotifyMessage.class, "notifyMessage");
    }

    /**
     * 修改MongDB中 推送记录状态
     * @param id
     * @param status
     */
    public void updateNotifyMsgStatus(String id, String status) {
        //更新条件
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        //更新数据
        Update update = new Update();
        update.set("useStatus", "1");
        update.set("approveStatue", status);
        mongoTemplate.updateFirst(query, update, "notifyMessage");
    }
}
