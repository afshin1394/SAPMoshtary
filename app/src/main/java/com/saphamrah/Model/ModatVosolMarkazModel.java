package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class ModatVosolMarkazModel
{

    private static final String TABLE_NAME = "ModatVosolMarkaz";
    private static final String COLUMN_ccModatVosolMarkaz = "ccModatVosolMarkaz";
    private static final String COLUMN_ccModatVosol = "ccModatVosol";
    private static final String COLUMN_ccMarkazSazmanForoshSakhtarForosh = "ccMarkazSazmanForoshSakhtarForosh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccModatVosolMarkaz() {
        return COLUMN_ccModatVosolMarkaz;
    }
    public static String COLUMN_ccModatVosol() {
        return COLUMN_ccModatVosol;
    }
    public static String COLUMN_ccMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_ccMarkazSazmanForoshSakhtarForosh;
    }




    private int ccModatVosolMarkaz;
    public void setCcModatVosolMarkaz(int ccModatVosolMarkaz){
        this.ccModatVosolMarkaz = ccModatVosolMarkaz;
    }
    public int getCcModatVosolMarkaz(){
        return this.ccModatVosolMarkaz;
    }


    private int ccModatVosol;
    public void setCcModatVosol(int ccModatVosol){
        this.ccModatVosol = ccModatVosol;
    }
    public int getCcModatVosol(){
        return this.ccModatVosol;
    }


    private int ccMarkazSazmanForoshSakhtarForosh;
    public void setCcMarkazSazmanForoshSakhtarForosh(int ccMarkazSazmanForoshSakhtarForosh){
        this.ccMarkazSazmanForoshSakhtarForosh = ccMarkazSazmanForoshSakhtarForosh;
    }
    public int getCcMarkazSazmanForoshSakhtarForosh(){
        return this.ccMarkazSazmanForoshSakhtarForosh;
    }


    @NonNull
    @Override
    public String toString() {
        return "ccModatVosolMarkaz : " + ccModatVosolMarkaz + " , ccModatVosol : " + ccModatVosol +
                " , ccMarkazSazmanForoshSakhtarForosh : " + ccMarkazSazmanForoshSakhtarForosh;
    }



}
