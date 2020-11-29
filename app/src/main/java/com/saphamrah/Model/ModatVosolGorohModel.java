package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class ModatVosolGorohModel
{

    private static final String TABLE_NAME = "ModatVosolGoroh";
    private static final String COLUMN_ccModatVosolGoroh = "ccModatVosolGoroh";
    private static final String COLUMN_ccModatVosol = "ccModatVosol";
    private static final String COLUMN_ccGoroh = "ccGoroh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccModatVosolGoroh() {
        return COLUMN_ccModatVosolGoroh;
    }
    public static String COLUMN_ccModatVosol() {
        return COLUMN_ccModatVosol;
    }
    public static String COLUMN_ccGoroh() {
        return COLUMN_ccGoroh;
    }



    private int ccModatVosolGoroh;
    public void setCcModatVosolGoroh(int ccModatVosolGoroh){
        this.ccModatVosolGoroh = ccModatVosolGoroh;
    }
    public int getCcModatVosolGoroh(){
        return this.ccModatVosolGoroh;
    }


    private int ccModatVosol;
    public void setCcModatVosol(int ccModatVosol){
        this.ccModatVosol = ccModatVosol;
    }
    public int getCcModatVosol(){
        return this.ccModatVosol;
    }


    private int ccGoroh;
    public void setCcGoroh(int ccGoroh){
        this.ccGoroh = ccGoroh;
    }
    public int getCcGoroh(){
        return this.ccGoroh;
    }


    @NonNull
    @Override
    public String toString() {
        return "ccModatVosolGoroh : " + ccModatVosolGoroh + " , ccModatVosol : " + ccModatVosol + " , ccGoroh : " + ccGoroh;
    }



}
