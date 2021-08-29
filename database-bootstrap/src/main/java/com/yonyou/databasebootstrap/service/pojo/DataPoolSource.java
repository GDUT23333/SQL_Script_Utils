package com.yonyou.databasebootstrap.service.pojo;

import java.util.Objects;

/**
 * @Author: Ember
 * @Date: 2021/8/11 13:02
 * @Description: 连接池配置
 */
public class DataPoolSource {

    private String sourceName;
    private String driver;
    private String dataBaseName;
    private String host;
    private String port;
    private String userName;
    private String password;
    private Integer status;

    public DataPoolSource() {
    }

    public DataPoolSource(String sourceName, String driver,
                          String dataBaseName, String host,
                          String port, String userName,
                          String password, Integer status) {
        this.sourceName = sourceName;
        this.driver = driver;
        this.dataBaseName = dataBaseName;
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        DataPoolSource that = (DataPoolSource) o;
        return Objects.equals(driver, that.driver) &&
                Objects.equals(dataBaseName, that.dataBaseName) &&
                Objects.equals(host, that.host) &&
                Objects.equals(port, that.port) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(password, that.password) &&
                Objects.equals(status,that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, dataBaseName, host, port, userName, password);
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

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

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
