package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandModel
{

    private static final String TABLE_NAME = "Brand";
    private static final String COLUMN_ccBrand = "ccBrand";
    private static final String COLUMN_NameBrand = "NameBrand";
    private static final String COLUMN_ccKalaGoroh = "ccKalaGoroh";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccBrand() {
        return COLUMN_ccBrand;
    }
    public static String COLUMN_NameBrand() {
        return COLUMN_NameBrand;
    }
    public static String COLUMN_ccKalaGoroh() {
        return COLUMN_ccKalaGoroh;
    }



    @SerializedName("ccBrand")
    @Expose
    private Integer ccBrand;
    @SerializedName("NameBrand")
    @Expose
    private String NameBrand;
    @SerializedName("Id")
    @Expose
    private Integer Id;

    private int ccKalaGoroh;


    public Integer getCcBrand() {
        return ccBrand;
    }
    public void setCcBrand(Integer ccBrand) {
        this.ccBrand = ccBrand;
    }

    public String getNameBrand() {
        return NameBrand;
    }
    public void setNameBrand(String nameBrand) {
        NameBrand = nameBrand;
    }

    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }


    public int getCcKalaGoroh()
    {
        return ccKalaGoroh;
    }
    public void setCcKalaGoroh(int ccKalaGoroh)
    {
        this.ccKalaGoroh = ccKalaGoroh;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "BrandModel{" +
                "ccBrand=" + ccBrand +
                ", NameBrand='" + NameBrand + '\'' +
                ", Id=" + Id +
                ", ccKalaGoroh=" + ccKalaGoroh +
                '}';
    }


}
