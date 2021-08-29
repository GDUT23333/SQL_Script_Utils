package com.yonyou.databasebootstrap.service.exception.handler;

import com.yonyou.databasebootstrap.service.exception.FailedException;
import com.yonyou.databasebootstrap.utils.ResponseUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Ember
 * @Date: 2021/8/16 12:47
 * @Description: 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = FailedException.class)
    public ResponseUtils handler(FailedException exception){
        return ResponseUtils.failed(exception.getMessage());
    }
}
