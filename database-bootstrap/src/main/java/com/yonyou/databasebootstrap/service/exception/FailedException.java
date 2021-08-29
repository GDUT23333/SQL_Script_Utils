package com.yonyou.databasebootstrap.service.exception;


import com.yonyou.databasebootstrap.utils.CodeEnumsUtils;

/**
 * @Author: Ember
 * @Date: 2021/8/12 16:45
 * @Description: 自定义异常
 */
public class FailedException extends Exception {

    private Integer code;

    public FailedException(Integer code, String message) {
        super(message);
    }

    public FailedException(CodeEnumsUtils enumsUtils) {
        super(enumsUtils.getMsg());
        this.code = enumsUtils.getCode();
    }

    public FailedException(CodeEnumsUtils enumsUtils,String msg) {
        super(msg);
        this.code = enumsUtils.getCode();
    }
}
