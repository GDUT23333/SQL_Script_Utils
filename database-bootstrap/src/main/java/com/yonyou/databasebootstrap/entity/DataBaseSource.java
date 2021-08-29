package com.yonyou.databasebootstrap.entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @Author: Ember
 * @Date: 2021/8/11 13:06
 * @Description: 连接池配置
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "db_log")
public class DataBaseSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "db_status")
    private Integer status;

    @Column(name = "db_type")
    @Size(max = 255)
    private String driver;

    @Column(name = "db")
    @Size(max = 255)
    private String dataBaseName;

    @Column(name = "source_name")
    @Size(max = 255)
    private String sourceName;

    @Column(name = "db_host")
    @Size(max = 255)
    private String host;

    @Column(name = "db_port")
    @Size(max = 255)
    private String port;

    @Column(name = "db_username")
    @Size(max = 255)
    private String userName;

    @Column(name = "db_password")
    @Size(max = 255)
    private String password;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "code")
    private String code;

    public DataBaseSource() {
    }

    public DataBaseSource(Integer id, Integer status, @Size(max = 255) String driver,
                          @Size(max = 255) String dataBaseName,
                          @Size(max = 255) String sourceName,
                          @Size(max = 255) String host,
                          @Size(max = 255) String port,
                          @Size(max = 255) String userName,
                          @Size(max = 255) String password,
                          String code,
                          String createTime) {
        this.id = id;
        this.status = status;
        this.driver = driver;
        this.dataBaseName = dataBaseName;
        this.sourceName = sourceName;
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.createTime = createTime;
        this.code = code;
    }

    public DataBaseSource(Integer id, Integer status, @Size(max = 255) String driver,
                          @Size(max = 255) String dataBaseName,
                          @Size(max = 255) String sourceName,
                          @Size(max = 255) String host,
                          @Size(max = 255) String port,
                          String createTime) {
        this.id = id;
        this.status = status;
        this.driver = driver;
        this.dataBaseName = dataBaseName;
        this.sourceName = sourceName;
        this.host = host;
        this.port = port;
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
