package com.yonyou.databasebootstrap.utils;

/**
 * @Author: Ember
 * @Date: 2021/8/13 15:58
 * @Description: 错误码
 */
public enum CodeEnumsUtils {
    /**
     * 一般返回
     */
    SUCCESS(200,"success"),
    FAILED(500,"failed"),

    /**
     * 前端传来数据库参数校验规则
     */
    DRIVER_IS_EMPTY(501,"数据库驱动能为空"),
    IP_IS_EMPTY(502,"IP地址不能为空"),
    PORT_IS_EMPTY(503,"端口号不能为空"),
    DATABASE_IS_EMPTY(504,"数据库名称不能为空"),
    USER_IS_EMPTY(505,"账号不能为空"),
    PASSWORD_IS_NULL(506,"密码不能为NULL"),
    SOURCE_NAME_IS_NULL(507,"连接名字不能为NULL"),
    SOURCE_NAME_IS_EMPTY(507,"连接名字不能为空"),
    STATUS_IS_NULL(508,"环境不能为NULL"),
    STATUS_IS_EMPTY(509,"环境不能为空"),
    STATUS_IS_NOT_MATCH(510,"环境参数不合法"),

    /**
     * 解析脚本出现异常
     */
    SCRIPT_FORMAT_MISTAKE(511,"脚本格式错误"),
    SCRIPT_IO_EXCEPTION(512,"解析Text文件出现IO异常"),
    VERSION_IS_EMPTY(513,"脚本版本不能为空"),
    COMMENT_IS_EMPTY(514,"脚本描述不能为空"),
    UP_IS_EMPTY(515,"执行的sql不能为空"),
    DOWN_IS_EMPTY(516,"回滚的SQL不能为空"),


    /**
     * 数据库驱动不存在
     */
    DRIVER_UN_EXIST(10001,"不支持该数据库"),

    /**
     * 参数不匹配
     */
    PARAM_UN_MATCH(10002,"参数不匹配"),

    /**
     * 脚本不存在
     */
    SCRIPT_UN_EXIST(10003,"脚本不存在"),

    /**
     * 数据源为空
     */
    DATASOURCE_IS_EMPTY(10004,"数据源为空，请先建立数据源"),

    /**
     * 数据源连接不通过
     */
    CONNECT_FAILED(10005,"数据库连接失败"),

    /**
     * 执行脚本失败
     */
    EXECUTE_FAILED(10006,"执行数据库脚本失败,请查看日志！"),

    ;
    private Integer code;
    private String msg;

    CodeEnumsUtils(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
