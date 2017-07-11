package com.example.cashbook.history_bill;

import java.util.Date;

/**
 * Created by dsz62 on 2017/7/7.
 */

public class HistoryBill_oneRecord {
    private int id;
    private Date time;
    private int imageId;
    private String name;
    private double money;

    public HistoryBill_oneRecord(int id, Date time,int imageId, String name, double money) {
        this.id = id;
        this.imageId = imageId;
        this.name = name;
        this.money = money;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public Date getTime() {
        return time;
    }
}
