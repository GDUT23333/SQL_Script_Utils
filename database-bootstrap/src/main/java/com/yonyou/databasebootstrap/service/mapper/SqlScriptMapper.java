package com.yonyou.databasebootstrap.service.mapper;

import com.alibaba.fastjson.JSON;
import com.yonyou.databasebootstrap.entity.SqlScript;
import com.yonyou.databasebootstrap.service.pojo.ConvertSqlScript;

/**
 * @Author: Ember
 * @Date: 2021/8/25 14:06
 * @Description: 脚本转换类
 */
public class SqlScriptMapper {

    public static SqlScript sqlScriptMapper(ConvertSqlScript script){
        SqlScript sqlScript = new SqlScript();
        sqlScript.setVersion(script.getScriptVersion());
        sqlScript.setComment(script.getComment());
        sqlScript.setContent(JSON.toJSON(script).toString());
        return sqlScript;
    }
}
