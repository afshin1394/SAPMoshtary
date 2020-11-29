package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class TizerTablighatPegahModel
{

    private static final String TABLE_NAME = "TizerTablighatPegah";
    private static final String COLUMN_ccTizer = "ccTizer";
    private static final String COLUMN_NameTizer = "NameTizer";
    private static final String COLUMN_NameTizer_Farsi = "NameTizer_Farsi";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccTizer() {
        return COLUMN_ccTizer;
    }
    public static String COLUMN_NameTizer() {
        return COLUMN_NameTizer;
    }
    public static String COLUMN_NameTizer_Farsi() {
        return COLUMN_NameTizer_Farsi;
    }



    private int ccTizer;
    public int getCcTizer() {
        return ccTizer;
    }
    public void setCcTizer(int ccTizer) {
        this.ccTizer = ccTizer;
    }


    private String NameTizer;
    public String getNameTizer() {
        return NameTizer;
    }
    public void setNameTizer(String nameTizer) {
        NameTizer = nameTizer;
    }


    private String NameTizer_Farsi;
    public String getNameTizer_Farsi() {
        return NameTizer_Farsi;
    }
    public void setNameTizer_Farsi(String nameTizer_Farsi) {
        NameTizer_Farsi = nameTizer_Farsi;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "ccTizer : " + ccTizer + " , NameTizer : " + NameTizer + " , NameTizer_Farsi : " + NameTizer_Farsi;
    }


}
