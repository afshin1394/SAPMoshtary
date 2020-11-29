package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class KalaOlaviatModel
{

    private static final String TABLE_NAME = "KalaOlaviat";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_Olaviat = "Olaviat";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_Olaviat() {
        return COLUMN_Olaviat;
    }


    private int ccKalaCode;
    public void setCcKalaCode(int ccKalaCode){
        this.ccKalaCode = ccKalaCode;
    }
    public int getCcKalaCode(){
        return this.ccKalaCode;
    }


    private int Olaviat;
    public void setOlaviat(int Olaviat){
        this.Olaviat = Olaviat;
    }
    public int getOlaviat(){
        return this.Olaviat;
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
        return "ccKalaCode : " + ccKalaCode + " , Olaviat : " + Olaviat + " , Id : " + Id;
    }



}
