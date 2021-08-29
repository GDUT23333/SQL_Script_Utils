package com.yonyou.databasebootstrap.template;

import com.yonyou.databasebootstrap.service.pojo.DataPoolSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Ember
 * @Date: 2021/8/12 11:06
 * @Description: MySQL数据源
 */
public class MySqlPoolSource extends AbstractPoolSource{

    private static final Logger logger = LoggerFactory.getLogger(MySqlPoolSource.class);

    /**
     * 预加载固定值
     */
    private static final String MYSQL = "mysql";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DIALOG = "org.hibernate.dialect.MySQL5InnoDBDialect";

    public MySqlPoolSource(DataPoolSource source) {
        super(source.getUserName(), source.getPassword(),
                source.getPort(), source.getHost(),
                source.getDataBaseName());
    }

    @Override
    public String buildDriver() {
        return DRIVER_NAME;
    }

    @Override
    public String buildUrl(String host, String port, String dataBaseName) {
        StringBuilder builder = new StringBuilder();
        builder.append("jdbc:mysql://");
        builder.append(host);
        builder.append(":");
        builder.append(port);
        builder.append("/");
        builder.append(dataBaseName);
        builder.append("?");
        builder.append("characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true&allowMultiQueries=true");
        return builder.toString();
    }

    @Override
    public String buildDialog() {
        return DIALOG;
    }
}
