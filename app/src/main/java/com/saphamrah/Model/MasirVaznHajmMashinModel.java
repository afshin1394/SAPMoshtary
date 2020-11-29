package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasirVaznHajmMashinModel
{


    private static final String TABLE_NAME = "Masir_VaznHajmMashin";
    private static final String COLUMN_ccMasir = "ccMasir";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_VaznMashin = "VaznMashin";
    private static final String COLUMN_HajmMashin = "HajmMashin";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMasir() {
        return COLUMN_ccMasir;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_VaznMashin() {
        return COLUMN_VaznMashin;
    }
    public static String COLUMN_HajmMashin() {
        return COLUMN_HajmMashin;
    }



    @SerializedName("ccMasir")
    @Expose
    private int ccMasir;
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("VaznMashin")
    @Expose
    private int VaznMashin;
    @SerializedName("HajmMashin")
    @Expose
    private float HajmMashin;


    public void setCcMasir(int ccMasir){
        this.ccMasir = ccMasir;
    }

    public int getCcMasir(){
        return this.ccMasir;
    }

    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcMoshtary(){
        return this.ccMoshtary;
    }

    public void setVaznMashin(int VaznMashin){
        this.VaznMashin = VaznMashin;
    }

    public int getVaznMashin(){
        return this.VaznMashin;
    }

    public void setHajmMashin(float HajmMashin){
        this.HajmMashin = HajmMashin;
    }

    public float getHajmMashin(){
        return this.HajmMashin;
    }


    @Override
    public String toString()
    {
        return "ccMasir : " + ccMasir + " , ccMoshtary : " + ccMoshtary + " , VaznMashin : " + VaznMashin + " , HajmMashin : " + HajmMashin;
    }



}
