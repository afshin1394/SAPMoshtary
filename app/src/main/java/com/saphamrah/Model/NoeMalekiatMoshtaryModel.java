package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class NoeMalekiatMoshtaryModel
{

    private static final String TABLE_NAME = "NoeMalekiatMoshtary";
    private static final String COLUMN_ccNoeMalekiatMoshtary = "ccNoeMalekiatMoshtary";
    private static final String COLUMN_NameNoeMalekiatMoshtary = "NameNoeMalekiatMoshtary";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccNoeMalekiatMoshtary() {
        return COLUMN_ccNoeMalekiatMoshtary;
    }
    public static String COLUMN_NameNoeMalekiatMoshtary() {
        return COLUMN_NameNoeMalekiatMoshtary;
    }


    private int ccNoeMalekiatMoshtary;
    public void setCcNoeMalekiatMoshtary(int ccNoeMalekiatMoshtary){
        this.ccNoeMalekiatMoshtary = ccNoeMalekiatMoshtary;
    }
    public int getCcNoeMalekiatMoshtary(){
        return this.ccNoeMalekiatMoshtary;
    }


    private String NameNoeMalekiatMoshtary;
    public void setNameNoeMalekiatMoshtary(String NameNoeMalekiatMoshtary){
        this.NameNoeMalekiatMoshtary = NameNoeMalekiatMoshtary;
    }
    public String getNameNoeMalekiatMoshtary(){
        return this.NameNoeMalekiatMoshtary;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "ccNoeMalekiatMoshtary : " + ccNoeMalekiatMoshtary + " , NameNoeMalekiatMoshtary : " + NameNoeMalekiatMoshtary;
    }



}
