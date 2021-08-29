package com.yonyou.databasebootstrap.template;

import com.yonyou.databasebootstrap.service.pojo.DataPoolSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Ember
 * @Date: 2021/8/12 11:41
 * @Description: Orcl11g数据源
 */
public class Orcl11gPoolSource extends AbstractPoolSource{

    private static final Logger logger = LoggerFactory.getLogger(Orcl11gPoolSource.class);

    /**
     * 预加载固定值
     */
    private static final String ORACLE = "oracle11g";
    private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
    private static final String DIALOG = "org.hibernate.dialect.Oracle10gDialect";

    public Orcl11gPoolSource(DataPoolSource source) {
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
        builder.append("jdbc:oracle:thin:@");
        builder.append(host);
        builder.append(":");
        builder.append(port);
        builder.append("/");
        builder.append(dataBaseName);
        return builder.toString();
    }

    @Override
    public String buildDialog() {
        return DIALOG;
    }
}
