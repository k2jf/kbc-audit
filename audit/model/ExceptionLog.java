package com.k2data.kbc.audit.model;

import java.util.Date;

public class ExceptionLog {
    private Integer id;

    private String cExceptionJson;

    private String cExceptionMessage;

    private Date cExceptionCreateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getcExceptionJson() {
        return cExceptionJson;
    }

    public void setcExceptionJson(String cExceptionJson) {
        this.cExceptionJson = cExceptionJson == null ? null : cExceptionJson.trim();
    }

    public String getcExceptionMessage() {
        return cExceptionMessage;
    }

    public void setcExceptionMessage(String cExceptionMessage) {
        this.cExceptionMessage = cExceptionMessage == null ? null : cExceptionMessage.trim();
    }

    public Date getcExceptionCreateTime() {
        return cExceptionCreateTime;
    }

    public void setcExceptionCreateTime(Date cExceptionCreateTime) {
        this.cExceptionCreateTime = cExceptionCreateTime;
    }
}