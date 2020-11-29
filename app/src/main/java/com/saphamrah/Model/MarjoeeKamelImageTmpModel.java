package com.saphamrah.Model;

public class MarjoeeKamelImageTmpModel
{

    private static final String TABLE_NAME = "MarjoeeKamelImage_Tmp";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_Image = "Image";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_Image() {
        return COLUMN_Image;
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
