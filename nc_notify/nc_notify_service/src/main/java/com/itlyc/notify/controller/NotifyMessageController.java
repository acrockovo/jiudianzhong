package com.itlyc.notify.controller;

import com.itlyc.common.vo.Result;
import com.itlyc.notify.dto.NotifyMessage;
import com.itlyc.notify.service.NotifyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotifyMessageController {

    @Autowired
    private NotifyMessageService notifyMessageService;

    /**
     * 获取推送历史记录
     * @param id 消息唯一标识
     * @param type 推送消息类型 取值见NotifyMessageConstant
     * @param sponsor 发起方(可以是手机号，或者用户ID)
     * @param receiver 接收方（可以是手机号，或者用户ID）
     * @return
     */
    @GetMapping("/message")
    public Result<List<NotifyMessage>> queryPushMessage(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "userId", required = false) String sponsor,
            @RequestParam(value = "targetId", required = false) String receiver){
        return Result.success(notifyMessageService.queryPushMessage(id, type, sponsor, receiver));
    }

    /**
     * 修改指定推送消息状态
     * @param id
     * @param status
     */
    @PutMapping("/message/status")
    public void updateNotifyMsgStatus(@RequestParam("id") String id, @RequestParam("status") String status){
        notifyMessageService.updateNotifyMsgStatus(id, status);
    }

}
