package com.itlyc.notify.listener;

import com.itlyc.common.constants.RocketMQConstants;
import com.itlyc.notify.dto.NotifyMessage;
import com.itlyc.notify.service.NotifyMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = RocketMQConstants.CONSUMER.NOTIFY_MSG_CONSUMER,//消费者组名称
        topic = RocketMQConstants.TOPIC.PUSH_TOPIC_NAME, //话题名称（消息一级分类）
        selectorExpression = "*"//标签名称（消息二级分类）
)
public class NotifyMessageListener implements RocketMQListener<NotifyMessage> {


    @Autowired
    private NotifyMessageService notifyMessageService;

    /**
     * 监听器逻辑，发送推送消息
     * @param notifyMessage
     */
    @Override
    public void onMessage(NotifyMessage notifyMessage) {
        log.info("消息推送微服务-监听到推送消息:{}", notifyMessage.toString());
        notifyMessageService.sendMsgToUser(notifyMessage);
    }
}
