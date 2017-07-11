package com.example.cashbook;

import org.litepal.crud.DataSupport;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by dsz62 on 2017/7/6.
 */

public class Consumption extends DataSupport {
    private  int id;
    private java.util.Date date;
    private int imgId;
    private String kind;
    private float money;

    public int getId() {
        return id;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public  String getFormatDate() {
        SimpleDateFormat sy = new SimpleDateFormat("MMdd");
        String dateFormated = sy.format(date);
        return dateFormated;
    }
}
