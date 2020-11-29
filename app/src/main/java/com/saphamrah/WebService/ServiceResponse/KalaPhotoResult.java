package com.saphamrah.WebService.ServiceResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KalaPhotoResult {
    @SerializedName("ccKalaCode")
    @Expose
    private int ccKalaCode;



    @SerializedName("image")
    @Expose
    private String image;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public int getCcKalaCode() {
        return ccKalaCode;
    }

    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }
}
