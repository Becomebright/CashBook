package com.example.cashbook;

import org.litepal.crud.DataSupport;

/**
 * Created by dsz62 on 2017/7/6.
 */

public class Choice extends DataSupport {
    private String name;
    private int imageId;

    public Choice(String name, int imageId) {
        this.name = name; this.imageId = imageId;
    }
    public String getName() {
        return this.name;
    }
    public int getImageId() {
        return  this.imageId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
