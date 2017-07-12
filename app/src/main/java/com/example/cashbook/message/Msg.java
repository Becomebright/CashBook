package com.example.cashbook.message;

/**
 * Created by dsz62 on 2017/7/5.
 */

public class Msg {
    public static final int TYPE_RECEIVED = 0;

    public static final int TYPE_SENT = 1;

    protected String content;

    protected   int type;

    public Msg(){}

    public Msg(String content, int type){
        this.content = content; this.type = type;
    }
    public String getContent() {
        return this.content;
    }
    public int getType() {
        return this.type;
    }
}
