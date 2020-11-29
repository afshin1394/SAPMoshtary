package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkazModel
{

    private static final String TABLE_NAME = "Markaz";
    private static final String COLUMN_ccMarkaz = "ccMarkaz";
    private static final String COLUMN_CodeMarkaz = "CodeMarkaz";
    private static final String COLUMN_NameMarkaz = "NameMarkaz";
    private static final String COLUMN_latitude_y = "latitude_y";
    private static final String COLUMN_longitude_x = "longitude_x";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMarkaz() {
        return COLUMN_ccMarkaz;
    }
    public static String COLUMN_CodeMarkaz() {
        return COLUMN_CodeMarkaz;
    }
    public static String COLUMN_NameMarkaz() {
        return COLUMN_NameMarkaz;
    }
    public static String COLUMN_latitude_y() {
        return COLUMN_latitude_y;
    }
    public static String COLUMN_longitude_x() {
        return COLUMN_longitude_x;
    }



    @SerializedName("ccMarkaz")
    @Expose
    private Integer ccMarkaz;
    @SerializedName("CodeMarkaz")
    @Expose
    private String CodeMarkaz;
    @SerializedName("NameMarkaz")
    @Expose
    private String NameMarkaz;
    @SerializedName("longitude_x")
    @Expose
    private float longitude_x;
    @SerializedName("latitude_y")
    @Expose
    private float latitude_y;


    public Integer getCcMarkaz() {
        return ccMarkaz;
    }

    public void setCcMarkaz(Integer ccMarkaz) {
        this.ccMarkaz = ccMarkaz;
    }

    public String getCodeMarkaz() {
        return CodeMarkaz;
    }

    public void setCodeMarkaz(String codeMarkaz) {
        CodeMarkaz = codeMarkaz;
    }

    public String getNameMarkaz() {
        return NameMarkaz;
    }

    public void setNameMarkaz(String nameMarkaz) {
        NameMarkaz = nameMarkaz;
    }

    public float getLongitude_x() {
        return longitude_x;
    }

    public void setLongitude_x(float longitude_x) {
        this.longitude_x = longitude_x;
    }

    public float getLatitude_y() {
        return latitude_y;
    }

    public void setLatitude_y(float latitude_y) {
        this.latitude_y = latitude_y;
    }

    @Override
    public String toString() {
        return "ccMarkaz : " + ccMarkaz + " , CodeMarkaz : " + CodeMarkaz + " , NameMarkaz : " + NameMarkaz +
                " , longitude_x : " + longitude_x + " , latitude_y : " + latitude_y;
    }

}
