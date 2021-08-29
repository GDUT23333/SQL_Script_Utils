package com.yonyou.databasebootstrap.utils;

import com.yonyou.databasebootstrap.service.pojo.DataPoolSource;
import com.yonyou.databasebootstrap.service.exception.FailedException;
import com.yonyou.databasebootstrap.template.AbstractPoolSource;
import com.yonyou.databasebootstrap.template.MySqlPoolSource;
import com.yonyou.databasebootstrap.template.Orcl11gPoolSource;

/**
 * @Author: Ember
 * @Date: 2021/8/12 11:34
 * @Description: 数据源工厂
 */
public class PoolSourceFactoryUtils {

    private final static String MYSQL = "mysql";
    private final static String ORCL_11G = "orcl_11g";

    /**
     * 匹配数据源驱动
     * @param poolSourceDto
     * @return
     * @throws FailedException
     */
    public static AbstractPoolSource changePoolSource(DataPoolSource poolSourceDto) throws FailedException {
        String driver = poolSourceDto.getDriver();
        switch (driver){
            case (MYSQL):{
                return new MySqlPoolSource(poolSourceDto);
            }
            case (ORCL_11G):{
                return new Orcl11gPoolSource(poolSourceDto);
            }
            default:{
                throw new FailedException(CodeEnumsUtils.DRIVER_UN_EXIST);
            }
        }
    }
}
