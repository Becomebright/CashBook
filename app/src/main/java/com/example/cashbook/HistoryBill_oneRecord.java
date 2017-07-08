package com.example.cashbook;

import java.util.Date;

/**
 * Created by dsz62 on 2017/7/7.
 */

public class HistoryBill_oneRecord {
    private Date time;
    private int imageId;
    private String name;
    private double money;

    public HistoryBill_oneRecord(Date time,int imageId, String name, double money) {
        this.imageId = imageId;
        this.name = name;
        this.money = money;
        this.time = time;
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
