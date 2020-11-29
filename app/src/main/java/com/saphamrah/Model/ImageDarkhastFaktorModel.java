package com.saphamrah.Model;

public class ImageDarkhastFaktorModel
{

    private static final String TABLE_NAME = "Image_DarkhastFaktor";
    private static final String COLUMN_Image_DarkhastFaktor = "Image_DarkhastFaktor";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_Image_DarkhastFaktor() {
        return COLUMN_Image_DarkhastFaktor;
    }


    private byte[] ImageDarkhastFaktor;
    public byte[] getImageDarkhastFaktor() {
        return ImageDarkhastFaktor;
    }
    public void setImageDarkhastFaktor(byte[] imageDarkhastFaktor) {
        ImageDarkhastFaktor = imageDarkhastFaktor;
    }


}
