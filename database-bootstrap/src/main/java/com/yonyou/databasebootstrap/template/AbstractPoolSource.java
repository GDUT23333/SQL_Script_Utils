package com.yonyou.databasebootstrap.template;

/**
 * @Author: Ember
 * @Date: 2021/8/12 10:50
 * @Description: 抽象源
 */
public abstract class AbstractPoolSource {

    private String host;
    private String port;
    private String userName;
    private String password;
    private String driver;
    private String url;
    private String dataBaseName;

    public AbstractPoolSource(String userName, String password,
                              String port, String host,
                              String dataBaseName) {
        this.userName = userName;
        this.password = password;
        this.driver = buildDriver();
        this.url = buildUrl(host,port,dataBaseName);
    }

    public AbstractPoolSource() {
    }

    /**
     * 生成JDBC驱动名字
     * @return
     */
    public abstract String buildDriver();

    /**
     * 生成JDBC连接URL
     * @param host
     * @param port
     * @param dataBaseName
     * @return
     */
    public abstract String buildUrl(String host,String port,String dataBaseName);

    /**
     * 生成方言
     * @return
     */
    public abstract String buildDialog();

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
