package com.example.cashbook;

import org.litepal.crud.DataSupport;

import java.sql.Date;

/**
 * Created by dsz62 on 2017/7/6.
 */

public class Consumption extends DataSupport {
    private String kind;
    private double money;
    private java.util.Date date;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }
}
