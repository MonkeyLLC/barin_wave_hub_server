package com.llc.search_service.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Log implements Serializable {
    private String type;
    private String uri;
    private String args;
    private String clazz;
    private String method;
    private Integer line;
    private String message;
    private String notes;
    private String level;
    private String userId;
    private String userName;
    private Long time;

    public Log() {
    }
    public Log(String level){
        
    }
}
