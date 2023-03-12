package com.itlyc.common.exception.advice;

import com.itlyc.common.exception.enums.ResponseEnum;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 自定义异常
 *
 * @author: itlyc
 * @create: 2021-07-23 12:13
 */
@Data
public class NcException extends RuntimeException {

    /**
     * 执行业务状态码
     */
    private Integer code;

    public NcException(Integer code) {
        this.code = code;
    }

    public NcException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public NcException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
