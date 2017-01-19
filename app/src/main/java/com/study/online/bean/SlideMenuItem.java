package com.study.online.bean;

/**
 * 这个是个人中心界面的item
 */
public class SlideMenuItem {
    private String itme_text;
    private int itme_img;
    public SlideMenuItem(String itme_text, int itme_img){
        this.itme_img=itme_img;
        this.itme_text=itme_text;
    }
    public int getItme_img() {
        return itme_img;
    }

    public void setItme_img(int itme_img) {
        this.itme_img = itme_img;
    }

    public String getItme_text() {
        return itme_text;
    }

    public void setItme_text(String itme_text) {
        this.itme_text = itme_text;
    }
}
