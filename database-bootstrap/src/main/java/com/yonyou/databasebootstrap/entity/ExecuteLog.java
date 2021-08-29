package com.yonyou.databasebootstrap.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: Ember
 * @Date: 2021/8/25 10:09
 * @Description: 脚本执行情况记录
 */
@Entity
@Table(name = "execute_log")
@DynamicInsert
@DynamicUpdate
public class ExecuteLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "db_id")
    private Integer dbID;

    @Column(name = "s_id",insertable = false,updatable = false)
    private Integer sqlScriptId;

    @Column(name = "execute_type")
    private Integer executeType;

    @Column(name = "execute_result")
    private Integer executeResult;

    @Column(name = "execute_time")
    private String executeTime;

    @Column(name = "exception")
    private String exception;

    @OneToOne
    @JoinColumn(name = "s_id",nullable=true,referencedColumnName = "id")
    private SqlScript sqlScript;

    public ExecuteLog() {
    }

    public ExecuteLog(Integer id, Integer dbID, Integer sqlScriptId,
                      Integer executeType, Integer executeResult,
                      String executeTime, String exception) {
        this.id = id;
        this.dbID = dbID;
        this.sqlScriptId = sqlScriptId;
        this.executeType = executeType;
        this.executeResult = executeResult;
        this.executeTime = executeTime;
        this.exception = exception;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDbID() {
        return dbID;
    }

    public void setDbID(Integer dbID) {
        this.dbID = dbID;
    }

    public Integer getSqlScriptId() {
        return sqlScriptId;
    }

    public void setSqlScriptId(Integer sqlScriptId) {
        this.sqlScriptId = sqlScriptId;
    }

    public Integer getExecuteType() {
        return executeType;
    }

    public void setExecuteType(Integer executeType) {
        this.executeType = executeType;
    }

    public Integer getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(Integer executeResult) {
        this.executeResult = executeResult;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public SqlScript getSqlScript() {
        return sqlScript;
    }

    public void setSqlScript(SqlScript sqlScript) {
        this.sqlScript = sqlScript;
    }
}
