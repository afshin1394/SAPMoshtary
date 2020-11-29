package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class KalaZaribForoshModel
{

    private static final String TABLE_NAME = "KalaZaribForosh";
    private static final String COLUMN_ccKalaZaribForosh = "ccKalaZaribForosh";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_ccGorohMoshtary = "ccGorohMoshtary";
    private static final String COLUMN_ZaribForosh = "ZaribForosh";
    private static final String COLUMN_Darajeh = "Darajeh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKalaZaribForosh() {
        return COLUMN_ccKalaZaribForosh;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_ccGorohMoshtary() {
        return COLUMN_ccGorohMoshtary;
    }
    public static String COLUMN_ZaribForosh() {
        return COLUMN_ZaribForosh;
    }
    public static String COLUMN_Darajeh() {
        return COLUMN_Darajeh;
    }



    private int ccKalaCode;
    public void setCcKalaCode(int ccKalaCode){
        this.ccKalaCode = ccKalaCode;
    }
    public int getCcKalaCode(){
        return this.ccKalaCode;
    }


    private int ccKalaZaribForosh;
    public void setCcKalaZaribForosh(int ccKalaZaribForosh){
        this.ccKalaZaribForosh = ccKalaZaribForosh;
    }
    public int getCcKalaZaribForosh(){
        return this.ccKalaZaribForosh;
    }


    private int ZaribForosh;
    public void setZaribForosh(int ZaribForosh){
        this.ZaribForosh = ZaribForosh;
    }
    public int getZaribForosh(){
        return this.ZaribForosh;
    }


    private int ccGorohMoshtary;
    public void setCcGorohMoshtary(int ccGorohMoshtary){
        this.ccGorohMoshtary = ccGorohMoshtary;
    }
    public int getCcGorohMoshtary(){
        return this.ccGorohMoshtary;
    }


    private int Darajeh;
    public void setDarajeh(int Darajeh){
        this.Darajeh = Darajeh;
    }
    public int getDarajeh(){
        return this.Darajeh;
    }


    @NonNull
    @Override
    public String toString()
    {
        return  "ccKalaCode=" + ccKalaCode +
                ", ccKalaZaribForosh=" + ccKalaZaribForosh +
                ", ZaribForosh=" + ZaribForosh +
                ", ccGorohMoshtary=" + ccGorohMoshtary +
                ", Darajeh=" + Darajeh;
    }



}
