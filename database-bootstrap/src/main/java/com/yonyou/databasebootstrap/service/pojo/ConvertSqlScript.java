package com.yonyou.databasebootstrap.service.pojo;

import javax.persistence.*;

/**
 * @Author: Ember
 * @Date: 2021/8/13 9:28
 * @Description: SQl脚本
 */

public class ConvertSqlScript {

    private Integer id;
    private String scriptVersion;
    private String comment;
    private String[] up;
    private String[] down;

    public ConvertSqlScript(String scriptVersion, String comment, String[] up, String[] down) {
        this.scriptVersion = scriptVersion;
        this.comment = comment;
        this.up = up;
        this.down = down;
    }

    public ConvertSqlScript() {
    }

    public String getScriptVersion() {
        return scriptVersion;
    }

    public void setScriptVersion(String scriptVersion) {
        this.scriptVersion = scriptVersion;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getUp() {
        return up;
    }

    public void setUp(String[] up) {
        this.up = up;
    }

    public String[] getDown() {
        return down;
    }

    public void setDown(String[] down) {
        this.down = down;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
