package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkazShahrMarkaziModel
{

    private static final String TABLE_NAME = "Markaz_ShahrMarkazi";
    private static final String COLUMN_ccMarkaz_ShahrMarkazi = "ccMarkaz_ShahrMarkazi";
    private static final String COLUMN_ccMarkaz = "ccMarkaz";
    private static final String COLUMN_ccMahaleh = "ccMahaleh";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMarkaz_ShahrMarkazi()
    {
        return COLUMN_ccMarkaz_ShahrMarkazi;
    }
    public static String COLUMN_ccMarkaz()
    {
        return COLUMN_ccMarkaz;
    }
    public static String COLUMN_ccMahaleh()
    {
        return COLUMN_ccMahaleh;
    }


    @SerializedName("ccMarkaz_ShahrMarkazi")
    @Expose
    private Integer ccMarkazShahrMarkazi;
    @SerializedName("ccMarkaz")
    @Expose
    private Integer ccMarkaz;
    @SerializedName("ccMahaleh")
    @Expose
    private Integer ccMahaleh;


    public Integer getCcMarkazShahrMarkazi()
    {
        return ccMarkazShahrMarkazi;
    }
    public void setCcMarkazShahrMarkazi(Integer ccMarkazShahrMarkazi)
    {
        this.ccMarkazShahrMarkazi = ccMarkazShahrMarkazi;
    }

    public Integer getCcMarkaz()
    {
        return ccMarkaz;
    }
    public void setCcMarkaz(Integer ccMarkaz)
    {
        this.ccMarkaz = ccMarkaz;
    }

    public Integer getCcMahaleh()
    {
        return ccMahaleh;
    }
    public void setCcMahaleh(Integer ccMahaleh)
    {
        this.ccMahaleh = ccMahaleh;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "MarkazShahrMarkaziModel{" +
                "ccMarkazShahrMarkazi=" + ccMarkazShahrMarkazi +
                ", ccMarkaz=" + ccMarkaz +
                ", ccMahaleh=" + ccMahaleh +
                '}';
    }
}
