package com.itlyc.common.constants;

public abstract class RocketMQConstants {
    public static final class TOPIC {
        /**
         * 推送服务
         */
        public static final String PUSH_TOPIC_NAME = "nc-push-topic";

    }

    public static final class TAGS {
        /**
         * 用户申请 消息 TAGS
         */
        public static final String USER_APPLY_TAGS = "user-apply";

        /**
         * 用户审批通过
         */
        public static final String USER_APPLY_PASS_TAGS = "user-apply-pass";

        /**
         * 用户审批拒绝
         */
        public static final String USER_APPLY_REFUSE_TAGS = "user-apply-refuse";


        /**
         * 签到成功推送
         */
        public static final String SIGN_NOTIFY_TAGS = "sign-notify";

        /**
         * 积分到账提醒-增加积分
         */
        public static final String INTEGRAL_PLUS_TAGS = "integral-plus";

        /**
         * 积分到账提醒-减少积分
         */
        public static final String INTEGRAL_MINUS_TAGS = "integral-minus";

        /**
         * 审批提醒
         */
        public static final String ATTR_NOTIFY_TAGS = "approval-notify";

    }

    public static final class CONSUMER {

        /**
         * 推送消息消费者组
         */
        public static final String USER_MSG_PUSH_CONSUMER = "USER_MSG_PUSH_CONSUMER";

        /**
         * 用户申请消息 消费者组
         */
        public static final String USER_APPLY_CONSUMER = "USER_APPLY_CONSUMER";


        /**
         * 推送消息 消费者组
         */
        public static final String NOTIFY_MSG_CONSUMER = "USER_APPLY_CONSUMER";

        /**
         * 短信发送 的消费者
         */
        public static final String SMS_VERIFY_CODE_CONSUMER = "SMS_VERIFY_CODE_CONSUMER";

    }
}
