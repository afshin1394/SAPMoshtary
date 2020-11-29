package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class TedadFaktorMoshtaryModel
{

    private static final String TABLE_NAME = "TedadFaktorMoshtary";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_TedadFaktorMoshtary = "TedadFaktorMoshtary";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_TedadFaktorMoshtary() {
        return COLUMN_TedadFaktorMoshtary;
    }


    private int ccMoshtary;
    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }
    public int getCcMoshtary(){
        return this.ccMoshtary;
    }


    private int TedadFaktorMoshtary;
    public void setTedadFaktorMoshtary(int TedadFaktorMoshtary){
        this.TedadFaktorMoshtary = TedadFaktorMoshtary;
    }
    public int getTedadFaktorMoshtary(){
        return this.TedadFaktorMoshtary;
    }


    private int Id;
    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "Id : " + Id + " , ccMoshtary : " + ccMoshtary + " , TedadFaktorMoshtary : " + TedadFaktorMoshtary;
    }


}
