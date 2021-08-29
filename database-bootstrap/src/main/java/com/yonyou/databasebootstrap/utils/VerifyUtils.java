package com.yonyou.databasebootstrap.utils;

import com.yonyou.databasebootstrap.service.exception.FailedException;
import com.yonyou.databasebootstrap.service.pojo.ConvertSqlScript;
import com.yonyou.databasebootstrap.service.pojo.DataPoolSource;
import org.springframework.util.StringUtils;

/**
 * @Author: Ember
 * @Date: 2021/8/23 18:20
 * @Description: 校验工具
 */
public class VerifyUtils {

    /**
     * 环境
     */
    private static final Integer TEST = 0;
    private static final Integer PREPARE = 1;
    private static final Integer PRODUCT = 2;
    private static final Integer ZERO = 0;

    /**
     * 检验前端传来的数据库参数是否有误
     * @param source
     * @param isTest
     * @throws FailedException
     */
    public static void verifySource(DataPoolSource source,Boolean isTest) throws FailedException {
        if(StringUtils.isEmpty(source.getDriver())){
            throw new FailedException(CodeEnumsUtils.DRIVER_IS_EMPTY);
        }
        if(StringUtils.isEmpty(source.getHost())){
            throw new FailedException(CodeEnumsUtils.IP_IS_EMPTY);
        }
        if(StringUtils.isEmpty(source.getHost())){
            throw new FailedException(CodeEnumsUtils.PORT_IS_EMPTY);
        }
        if(StringUtils.isEmpty(source.getDataBaseName())){
            throw new FailedException(CodeEnumsUtils.DATABASE_IS_EMPTY);
        }
        if(StringUtils.isEmpty(source.getUserName())){
            throw new FailedException(CodeEnumsUtils.USER_IS_EMPTY);
        }
        if(source.getPassword() == null){
            throw new FailedException(CodeEnumsUtils.PASSWORD_IS_NULL);
        }
        if(isTest){
            if(source.getSourceName() == null){
                throw new FailedException(CodeEnumsUtils.SOURCE_NAME_IS_NULL);
            }
            if(source.getStatus() == null ){
                throw new FailedException(CodeEnumsUtils.STATUS_IS_NULL);
            }
        }else{
            if(StringUtils.isEmpty(source.getSourceName())){
                throw new FailedException(CodeEnumsUtils.SOURCE_NAME_IS_EMPTY);
            }
            if(StringUtils.isEmpty(source.getStatus())){
                throw new FailedException(CodeEnumsUtils.STATUS_IS_EMPTY);
            }
        }

        if(source.getStatus().equals(TEST) ||
                source.getStatus().equals(PREPARE) ||
                source.getStatus().equals(PRODUCT)){
            return;
        }
        throw new FailedException(CodeEnumsUtils.STATUS_IS_NOT_MATCH);
    }

    /**
     * 检验解析出来的脚本是否完善
     * @param script
     */
    public static void verifyScript(ConvertSqlScript script) throws FailedException {
        if(StringUtils.isEmpty(script.getScriptVersion())){
            throw new FailedException(CodeEnumsUtils.VERSION_IS_EMPTY);
        }
        if(StringUtils.isEmpty(script.getComment())){
            throw new FailedException(CodeEnumsUtils.COMMENT_IS_EMPTY);
        }
        if(script.getUp() != null && script.getUp().length <= ZERO){
            throw new FailedException(CodeEnumsUtils.UP_IS_EMPTY);
        }
        if(script.getDown() != null && script.getDown().length <= ZERO){
            throw new FailedException(CodeEnumsUtils.DOWN_IS_EMPTY);
        }
    }

}
