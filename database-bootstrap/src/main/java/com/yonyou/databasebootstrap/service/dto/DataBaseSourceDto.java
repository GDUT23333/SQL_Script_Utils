package com.yonyou.databasebootstrap.service.dto;

/**
 * @Author: Ember
 * @Date: 2021/8/13 15:56
 * @Description:
 */
public class DataBaseSourceDto {

    private Integer id;
    private Integer status;
    private String driver;
    private String dataBaseName;
    private String host;
    private String port;
    private String createTime;

    public DataBaseSourceDto() {
    }

    public DataBaseSourceDto(Integer id, Integer status,
                             String driver, String dataBaseName,
                             String host, String port,
                             String createTime) {
        this.id = id;
        this.status = status;
        this.driver = driver;
        this.dataBaseName = dataBaseName;
        this.host = host;
        this.port = port;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
