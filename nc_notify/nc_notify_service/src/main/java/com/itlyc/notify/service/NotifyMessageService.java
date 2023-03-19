package com.itlyc.notify.service;

import com.itlyc.notify.dto.NotifyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotifyMessageService {

    /**
     * 向用户手机设备推送通知
     *
     * @param notifyMessage
     */
    public void sendMsgToUser(NotifyMessage notifyMessage) {
        try {
            log.info("已向极光推送消息+{}",notifyMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("推送微服务：推送设备通知失败，{}", e.getMessage());
        }
    }
}
