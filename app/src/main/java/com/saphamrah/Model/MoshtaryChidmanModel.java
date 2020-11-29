package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class MoshtaryChidmanModel
{

    private static final String TABLE_NAME = "MoshtaryChidman";
    private static final String COLUMN_ccMoshtaryChidman = "ccMoshtaryChidman";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccMasir = "ccMasir";
    private static final String COLUMN_TarikhMasir = "TarikhMasir";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtaryChidman() {
        return COLUMN_ccMoshtaryChidman;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccMasir() {
        return COLUMN_ccMasir;
    }
    public static String COLUMN_TarikhMasir() {
        return COLUMN_TarikhMasir;
    }



    private int ccMoshtaryChidman;
    public int getCcMoshtaryChidman() {
        return ccMoshtaryChidman;
    }
    public void setCcMoshtaryChidman(int ccMoshtaryChidman) {
        this.ccMoshtaryChidman = ccMoshtaryChidman;
    }


    private int ccMoshtary;
    public int getCcMoshtary() {
        return ccMoshtary;
    }
    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }


    private int ccMasir;
    public int getCcMasir() {
        return ccMasir;
    }
    public void setCcMasir(int ccMasir) {
        this.ccMasir = ccMasir;
    }


    private String TarikhMasir;
    public String getTarikhMasir() {
        return TarikhMasir;
    }
    public void setTarikhMasir(String tarikhMasir) {
        TarikhMasir = tarikhMasir;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "ccMoshtaryChidman : " + ccMoshtaryChidman + " , ccMoshtary : " + ccMoshtary + " , ccMasir : " + ccMasir +
                " , TarikhMasir : " + TarikhMasir;
    }



}
