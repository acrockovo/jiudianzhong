package com.itlyc.notify.clent;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("notify-service")
public interface NotifyMsgClient {

    /**
     * 修改指定推送消息状态
     * @param id
     * @param status
     */
    @PutMapping("/message/status")
    public void updateNotifyMsgStatus(@RequestParam("id") String id, @RequestParam("status") String status);

}
