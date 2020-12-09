package com.saphamrah.Model;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.mail.iap.ByteArray;

import java.util.Arrays;

public class KalaPhotoModel {
    private static final String TABLE_NAME = "KalaPhoto";
    private static final String COLUMN_ccKalaPhoto = "ccKalaPhoto";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_IMAGE = "Photo";

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getCOLUMN_ccKalaPhoto() {
        return COLUMN_ccKalaPhoto;
    }

    public static String getCOLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }

    public static String getColumnImage() {
        return COLUMN_IMAGE;
    }



    private int ccKalaPhotoDb;
    private int ccKalaCodeDb;
    private byte[] imageDb;

    public int getCcKalaPhotoDb() {
        return ccKalaPhotoDb;
    }

    public void setCcKalaPhotoDb(int ccKalaPhotoDb) {
        this.ccKalaPhotoDb = ccKalaPhotoDb;
    }

    public int getCcKalaCodeDb() {
        return ccKalaCodeDb;
    }

    public void setCcKalaCodeDb(int ccKalaCodeDb) {
        this.ccKalaCodeDb = ccKalaCodeDb;
    }



    public byte[] getImageDb() {
        return imageDb;
    }

    public void setImageDb(byte[] imageDb) {
        this.imageDb = imageDb;
    }



    private int ccKalaPhoto;

    public int getCcKalaPhoto() {
        return ccKalaPhoto;
    }

    public void setCcKalaPhoto(int ccKalaPhoto) {
        this.ccKalaPhoto = ccKalaPhoto;
    }


    @Override
    public String toString() {
        return "KalaPhotoModel{" +
                "ccKalaPhotoDb=" + ccKalaPhotoDb +
                ", ccKalaCodeDb=" + ccKalaCodeDb +
                ", imageDb=" + Arrays.toString(imageDb) +
                ", ccKalaPhoto=" + ccKalaPhoto +
                '}';
    }
}
