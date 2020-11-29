package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoshtaryBrandModel
{

    private static final String TABLE_NAME = "MoshtaryBrand";
    private static final String COLUMN_ccMoshtaryBrand = "ccMoshtaryBrand";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccBrand = "ccBrand";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtaryBrand() {
        return COLUMN_ccMoshtaryBrand;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccBrand() {
        return COLUMN_ccBrand;
    }



    @SerializedName("ccMoshtaryBrand")
    @Expose
    private Integer ccMoshtaryBrand;
    @SerializedName("ccMoshtary")
    @Expose
    private Integer ccMoshtary;
    @SerializedName("ccBrand")
    @Expose
    private Integer ccBrand;


    public int getCcMoshtaryBrand()
    {
        return ccMoshtaryBrand;
    }
    public void setCcMoshtaryBrand(int ccMoshtaryBrand)
    {
        this.ccMoshtaryBrand = ccMoshtaryBrand;
    }

    public int getCcMoshtary()
    {
        return ccMoshtary;
    }
    public void setCcMoshtary(int ccMoshtary)
    {
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcBrand()
    {
        return ccBrand;
    }
    public void setCcBrand(int ccBrand)
    {
        this.ccBrand = ccBrand;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "MoshtaryBrandModel{" +
                "ccMoshtaryBrand=" + ccMoshtaryBrand +
                ", ccMoshtary=" + ccMoshtary +
                ", ccBrand=" + ccBrand +
                '}';
    }


}
