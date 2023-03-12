package com.itlyc.common.exception.advice;

import com.itlyc.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ResponseBody
@ControllerAdvice  //对controller层进行AOP增强，异常通知
public class BasicExceptionAdvice {


    /**
     * 处理RuntimeException类型异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handlerRuntimeErr(RuntimeException e) {
        //return new Result<>(false, 500, "执行业务失败！"+e.getMessage());
        return Result.errorCodeMessage(500, e.getMessage());
    }


    /**
     * 处理NcException类型异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NcException.class)
    public Result<String> handlerRuntimeErr(NcException e) {
        log.error(e.getMessage());
        return Result.errorCodeMessage(e.getCode(), e.getMessage());
    }
}
