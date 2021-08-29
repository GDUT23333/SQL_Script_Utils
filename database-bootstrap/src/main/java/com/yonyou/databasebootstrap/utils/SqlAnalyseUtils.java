package com.yonyou.databasebootstrap.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.yonyou.databasebootstrap.service.exception.FailedException;
import com.yonyou.databasebootstrap.service.pojo.ConvertSqlScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Author: Ember
 * @Date: 2021/8/12 17:04
 * @Description: SQL解析
 */
public class SqlAnalyseUtils {

    private static Logger logger = LoggerFactory.getLogger(SqlAnalyseUtils.class);

    /**
     * 多个sql字符串解析
     * @param sqls
     * @return
     */
    public static String[] parseSql(String sqls){
        String[] split = sqls.split(";");
        return split;
    }

    /**
     * sql text文件解析
     * @param sqls
     */
    public static ConvertSqlScript parseSql(MultipartFile sqls) throws FailedException {
        try {
            File temp = File.createTempFile("temp",null);
            sqls.transferTo(temp);
            FileReader fileReader = new FileReader(temp);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder builder = new StringBuilder();
            String line = "";
            while((line=reader.readLine())!=null) {
                builder.append(line);
            }
            reader.close();
            ConvertSqlScript convertSqlScript = JSON.toJavaObject(JSON.parseObject(builder.toString()), ConvertSqlScript.class);
            return convertSqlScript;
        }catch (JSONException e){
            logger.error("sql脚本格式不正确，无法转换，请检查脚本格式,cause:{}",e.getCause());
            e.printStackTrace();
            throw new FailedException(CodeEnumsUtils.SCRIPT_FORMAT_MISTAKE);
        }
        catch (IOException e) {
            logger.error("解析sql脚本出现IO异常,cause:{}",e.getCause());
            e.printStackTrace();
            throw new FailedException(CodeEnumsUtils.SCRIPT_IO_EXCEPTION);
        }
    }
}
