package com.k2data.kbc.audit.model;

import java.util.Date;

public class ExceptionLog {
    private Integer id;

    private String exceptionJson;

    private String exceptionMessage;

    private Date exceptionCreateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExceptionJson() {
        return exceptionJson;
    }

    public void setExceptionJson(String exceptionJson) {
        this.exceptionJson = exceptionJson;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public Date getExceptionCreateTime() {
        return exceptionCreateTime;
    }

    public void setExceptionCreateTime(Date exceptionCreateTime) {
        this.exceptionCreateTime = exceptionCreateTime;
    }
}