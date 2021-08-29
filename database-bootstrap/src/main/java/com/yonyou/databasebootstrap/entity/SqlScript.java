package com.yonyou.databasebootstrap.entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @Author: Ember
 * @Date: 2021/8/13 12:15
 * @Description: Sql脚本Entity
 */
@Entity
@Table(name = "script_log")
@DynamicUpdate
@DynamicInsert
public class SqlScript {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "script_version")
    private String version;

    @Column(name = "script_comment")
    private String comment;

    @Column(name = "script_content")
    private String content;

    @Column(name = "execute_type")
    private Integer executeType;

    @Column(name = "db_id")
    private Integer dbId;

    @Column(name = "execute_time")
    private String executeTime;

    @Column(name = "exception")
    private String exception;

    public SqlScript() {
    }

    public SqlScript(Integer id, String version,
                     String comment, String content,
                     Integer executeType, Integer dbId,
                     String executeTime) {
        this.id = id;
        this.version = version;
        this.comment = comment;
        this.content = content;
        this.executeType = executeType;
        this.dbId = dbId;
        this.executeTime = executeTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getExecuteType() {
        return executeType;
    }

    public void setExecuteType(Integer executeType) {
        this.executeType = executeType;
    }

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
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

}
