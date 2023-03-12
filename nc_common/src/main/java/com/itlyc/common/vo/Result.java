package com.itlyc.common.vo;

import com.itlyc.common.exception.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: lyc
 * @description: 服务端提供前端返回的结果
 **/
@Data
public class Result<T> implements Serializable {

    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 返回状态码:执行业务状态
     */
    private int code;
    /**
     * 提示消息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public Result() {
    }

    public Result(Boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Result(Boolean success, int code) {
        this.success = success;
        this.code = code;
    }

    public Result(Boolean success, int code, T data) {
        this.success = success;
        this.code = code;
        this.data = data;
    }

    public Result(Boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<T>(true, ResponseEnum.SUCCESS.getCode());
    }

    public static <T> Result<T> successMessage(String message) {
        return new Result<T>(true, ResponseEnum.SUCCESS.getCode(), message);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(true, ResponseEnum.SUCCESS.getCode(), data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<T>(true, ResponseEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> error() {
        return new Result<T>(false, ResponseEnum.ERROR.getCode(), ResponseEnum.ERROR.getMessage());
    }

    public static <T> Result<T> errorMessage(String errorMessage) {
        return new Result<T>(false, ResponseEnum.ERROR.getCode(), errorMessage);
    }

    public static <T> Result<T> errorCodeMessage(int errorCode, String errorMessage) {
        return new Result<T>(false, errorCode, errorMessage);
    }
}
