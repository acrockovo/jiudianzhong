package com.itlyc.common.exception.enums;

/**
 * 封装了 异常业务状态码  以及提示消息
 */

public enum ResponseEnum {

    //列举所有可能
    AGE_NOT_NULL(400, "年龄不能为空！"),
    SUCCESS(200, "操作成功！"),
    ERROR(500, "操作失败！"),

    INVALID_FILE_TYPE(400, "无效的文件类型！"),
    INVALID_PARAM_ERROR(400, "无效的请求参数！"),
    INVALID_PHONE_NUMBER(400, "无效的手机号码"),
    INVALID_VERIFY_CODE(400, "验证码错误！"),
    INVALID_USERNAME_PASSWORD(400, "无效的用户名和密码！"),
    INVALID_SERVER_ID_SECRET(400, "无效的服务id和密钥！"),
    INVALID_NOTIFY_PARAM(400, "回调参数有误！"),
    INVALID_NOTIFY_SIGN(400, "回调签名有误！"),

    DATA_TRANSFER_ERROR(500, "数据转换异常！"),
    INSERT_OPERATION_FAIL(500, "新增操作失败！"),
    UPDATE_OPERATION_FAIL(500, "更新操作失败！"),
    DELETE_OPERATION_FAIL(500, "删除操作失败！"),
    FILE_UPLOAD_ERROR(500, "文件上传失败！"),
    DIRECTORY_WRITER_ERROR(500, "目录写入失败！"),
    FILE_WRITER_ERROR(500, "文件写入失败！"),
    SEND_MESSAGE_ERROR(500, "短信发送失败！"),
    CODE_IMAGE_ERROR(500, "验证码错误！"),

    USER_MOBILE_EXISTS(500, "该手机号已经被注册！"),
    USER_NOT_REGISTER(500, "当前用户还未进行注册！"),
    USER_NOT_JOIN_COMPANY(500, "请加入企业后再使用该功能！"),
    USER_NOT_COMPANY_ADMIN(500, "您不是企业管理员！"),
    USER_NOT_MATCH_ATTGROUP(500, "未查询到用户考勤组,请先配置！"),
    USER_NOT_FOUND(500, "没有查询到用户！"),

    COMPANY_ADMIN_NOT_EXISTS(500, "没有找到对应企业管理员！"),
    COMPANY_NOT_FOUND(500, "企业不存在！"),
    WROK_NUM_EXISTS(500, "当前工号已经存在！"),

    COMPANY_USER_NOT_FOUND(404, "企业员工不存在！"),
    SMS_CODE_TIMEOUT(404, "验证码超时，请重新发送！"),

    PUNCH_INVALID_AREA(500, "打卡地点不在考勤点范围内！"),
    PUNCH_INVALID_DAY(500, "非工作日无需打卡！"),
    PUNCH_ALREADY(500, "已经打卡，无需重复打卡！"),
    SIGN_DATA_NULL(404, "未检索到签到数据！"),

    MESSAGE_PARAMS_LOST(500, "查询参数缺失！"),

    UNAUTHORIZED(401, "登录失效或未登录！"),
    FOBIDDEN(403, "禁止访问！"),
    SIGNDATA_NOT_FOUND(500, "当前检索条件没有签到数据！"),

    ROLE_NOT_FOUND(403, "角色列表不存在！"),
    ROLE_SYS_NOT_FOUND(403, "系统管理员角色不存在！"),
    SYS_PERMISSSION_NOT_FOUND(403, "当前企业无权限"),


    DOC_NOT_FOUND(403, "文档不存在或者已经删除！"),
    DOC_NOT_ALLOWED(403, "只有作者才能设置协作者！"),
    FILE_NOT_ALLOWED_MODIFY(403, "您没有权限修改文档！"),
    FILE_NOT_BELONG_YOU(403, "您不是该文档拥有者，无法设置权限！"),
    ;

    //1.提供属性
    private Integer code;
    private String message;
    //提供构造

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    //提供获取属性值方法
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
