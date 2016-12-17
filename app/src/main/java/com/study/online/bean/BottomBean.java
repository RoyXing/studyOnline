package com.study.online.bean;

/**
 * Created by roy on 2016/12/16.
 */

public class BottomBean {

    private String name;
    private int itemNormal;
    private int itemPress;

    public BottomBean(int itemPress,int itemNormal, String name) {
        this.itemNormal = itemNormal;
        this.itemPress = itemPress;
        this.name = name;
    }

    public BottomBean() {
    }

    public int getItemNormal() {
        return itemNormal;
    }

    public void setItemNormal(int itemNormal) {
        this.itemNormal = itemNormal;
    }

    public int getItemPress() {
        return itemPress;
    }

    public void setItemPress(int itemPress) {
        this.itemPress = itemPress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
