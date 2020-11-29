package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class MoshtaryAmargarImageModel
{

    private static final String TABLE_NAME = "MoshtaryAmargar_Image";
    private static final String COLUMN_ccPorseshnameh = "ccPorseshnameh";
    private static final String COLUMN_Image = "Image";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccPorseshnameh() {
        return COLUMN_ccPorseshnameh;
    }
    public static String COLUMN_Image() {
        return COLUMN_Image;
    }


    private int ccPorseshnameh;
    public int getCcPorseshnameh() {
        return ccPorseshnameh;
    }
    public void setCcPorseshnameh(int ccPorseshnameh) {
        this.ccPorseshnameh = ccPorseshnameh;
    }


    private byte[] Image;
    public byte[] getImage() {
        return Image;
    }
    public void setImage(byte[] image) {
        Image = image;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "ccPorseshnameh : " + ccPorseshnameh + " , Image : " + Image;
    }


}
