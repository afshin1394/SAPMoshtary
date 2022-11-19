package com.saphamrah.customer.data.local;

public class MenuModel {
    private String title;
    private int imageRes;

    public MenuModel(String title, int imageRes) {
        this.title = title;
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
