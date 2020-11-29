package com.saphamrah.Model;


public class MoshtaryAmargarModel
{

    private static final String TABLE_NAME = "MoshtaryAmargar";
    private static final String COLUMN_ccPorseshnameh = "ccPorseshnameh";
    private static final String COLUMN_ccMahal = "ccMahal";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_NameMaghazeh = "NameMaghazeh";
    private static final String COLUMN_Telephone = "Telephone";
    private static final String COLUMN_Address = "Address";
    private static final String COLUMN_Longitude_x = "Longitude_x";
    private static final String COLUMN_Latitude_y = "Latitude_y";
    private static final String COLUMN_Olaviat = "Olaviat";
    private static final String COLUMN_Image = "Image";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccPorseshnameh() {
        return COLUMN_ccPorseshnameh;
    }
    public static String COLUMN_ccMahal() {
        return COLUMN_ccMahal;
    }
    public static String COLUMN_NameMoshtary() {
        return COLUMN_NameMoshtary;
    }
    public static String COLUMN_NameMaghazeh() {
        return COLUMN_NameMaghazeh;
    }
    public static String COLUMN_Telephone() {
        return COLUMN_Telephone;
    }
    public static String COLUMN_Address() {
        return COLUMN_Address;
    }
    public static String COLUMN_Longitude_x() {
        return COLUMN_Longitude_x;
    }
    public static String COLUMN_Latitude_y() {
        return COLUMN_Latitude_y;
    }
    public static String COLUMN_Olaviat() {
        return COLUMN_Olaviat;
    }
    public static String COLUMN_Image() {
        return COLUMN_Image;
    }





    private int ccPorseshnameh;
    private int ccMahal;
    private String NameMoshtary;
    private String NameMaghazeh;
    private int Telephone;
    private String Address;
    private String Longitude_x;
    private String Latitude_y;
    private int Olaviat;
    private byte[] Image;


    public int getCcPorseshnameh() {
        return ccPorseshnameh;
    }

    public void setCcPorseshnameh(int ccPorseshnameh) {
        this.ccPorseshnameh = ccPorseshnameh;
    }

    public int getCcMahal() {
        return ccMahal;
    }

    public void setCcMahal(int ccMahal) {
        this.ccMahal = ccMahal;
    }

    public String getNameMoshtary() {
        return NameMoshtary;
    }

    public void setNameMoshtary(String nameMoshtary) {
        NameMoshtary = nameMoshtary;
    }

    public String getNameMaghazeh() {
        return NameMaghazeh;
    }

    public void setNameMaghazeh(String nameMaghazeh) {
        NameMaghazeh = nameMaghazeh;
    }

    public int getTelephone() {
        return Telephone;
    }

    public void setTelephone(int telephone) {
        Telephone = telephone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLongitude_x() {
        return Longitude_x;
    }

    public void setLongitude_x(String longitude_x) {
        Longitude_x = longitude_x;
    }

    public String getLatitude_y() {
        return Latitude_y;
    }

    public void setLatitude_y(String latitude_y) {
        Latitude_y = latitude_y;
    }

    public int getOlaviat() {
        return Olaviat;
    }

    public void setOlaviat(int olaviat) {
        Olaviat = olaviat;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }



}
