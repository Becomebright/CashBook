package com.example.cashbook.TuLingRobot;

import java.util.Date;

public class Message {
    private String name;
    private String msg;
    private Date time;
    private Type type;

    public enum Type{//类型枚举，发送，接收
        INCOME,OUTCOME
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
