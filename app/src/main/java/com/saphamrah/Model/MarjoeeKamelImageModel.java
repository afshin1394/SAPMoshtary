package com.saphamrah.Model;

public class MarjoeeKamelImageModel
{

    private static final String TABLE_NAME = "MarjoeeKamel_Image";
    private static final String COLUMN_ccKardex = "ccKardex";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_Image = "Image";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKardex() {
        return COLUMN_ccKardex;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_Image() {
        return COLUMN_Image;
    }




    private int ccKardex;
    public int getCcKardex() {
        return ccKardex;
    }
    public void setCcKardex(int ccKardex) {
        this.ccKardex = ccKardex;
    }


    private int ccMoshtary;
    public int getCcMoshtary() {
        return ccMoshtary;
    }
    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }


    private byte[] Image;
    public byte[] getImage() {
        return Image;
    }
    public void setImage(byte[] image) {
        Image = image;
    }



}
