package com.yonyou.databasebootstrap.utils;


/**
 * @Author: Ember
 * @Date: 2021/8/11 11:35
 * @Description: 响应工具类
 */
public class ResponseUtils<T> {

    private Integer code;
    private String msg;
    private long time;
    private T data;

    public ResponseUtils() {
        this.time = System.currentTimeMillis();
    }

    public ResponseUtils(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.time = System.currentTimeMillis();
    }

    public ResponseUtils(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    public ResponseUtils(CodeEnumsUtils enumsUtils, T data) {
        this(enumsUtils);
        this.data = data;
    }

    public ResponseUtils(CodeEnumsUtils enumsUtils){
        this.time = System.currentTimeMillis();
        this.code = enumsUtils.getCode();
        this.msg = enumsUtils.getMsg();
    }

    public static ResponseUtils success(){
        return new ResponseUtils(CodeEnumsUtils.SUCCESS);
    }

    public static ResponseUtils failed(){
        return new ResponseUtils(CodeEnumsUtils.FAILED);
    }

    public static ResponseUtils success(String msg){
        return new ResponseUtils(200,msg);
    }

    public static ResponseUtils failed(String msg){
        return new ResponseUtils(500,msg);
    }

    public static <T>ResponseUtils<T> success(T data){
        return new ResponseUtils<T>(CodeEnumsUtils.SUCCESS,data);
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


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
