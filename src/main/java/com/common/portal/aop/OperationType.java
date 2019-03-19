package com.common.portal.aop;

public enum OperationType {

    ADD("新增或修改"),
    UPDATE("新增或修改"),
    DELETE("删除"),
    LOGIN("登入"),
    LOGOUT("注销"),
    REMOTE("远程输出"),
    BAK("备份");

    private String name;

    OperationType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
