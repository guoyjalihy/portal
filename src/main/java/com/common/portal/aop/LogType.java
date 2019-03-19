package com.common.portal.aop;

public enum LogType {
    SECURITY(1,"安全日志"),OPERATION(2,"操作日志"),EVENT(3,"事件日志");
    private int type;
    private String desc;
    LogType(int type,String desc){
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
