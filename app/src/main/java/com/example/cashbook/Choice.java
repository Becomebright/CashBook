package com.example.cashbook;

/**
 * Created by dsz62 on 2017/7/6.
 */

public class Choice {
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
}
