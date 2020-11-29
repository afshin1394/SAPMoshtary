package com.saphamrah.Model;

import androidx.annotation.NonNull;

import java.util.Date;

public class TaghvimTatilModel
{

    private static final String TABLE_NAME = "TaghvimTatil";
    private static final String COLUMN_ccTaghvimTatil = "ccTaghvimTatil";
    private static final String COLUMN_ccMarkaz = "ccMarkaz";
    private static final String COLUMN_TarikhTatily = "TarikhTatily";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccTaghvimTatil() {
        return COLUMN_ccTaghvimTatil;
    }
    public static String COLUMN_ccMarkaz() {
        return COLUMN_ccMarkaz;
    }
    public static String COLUMN_TarikhTatily() {
        return COLUMN_TarikhTatily;
    }


    private int ccTaghvimTatil;
    private int ccMarkaz;
    private String TarikhTatily;


    public void setCcMarkaz(int ccMarkaz){
        this.ccMarkaz = ccMarkaz;
    }
    public int getCcMarkaz(){
        return this.ccMarkaz;
    }

    public void setCcTaghvimTatil(int ccTaghvimTatil){
        this.ccTaghvimTatil = ccTaghvimTatil;
    }
    public int getCcTaghvimTatil(){
        return this.ccTaghvimTatil;
    }

    public void setTarikhTatily(String TarikhTatily){
        this.TarikhTatily = TarikhTatily;
    }
    public String getTarikhTatily(){
        return this.TarikhTatily;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "TaghvimTatilModel{" +
                "ccTaghvimTatil=" + ccTaghvimTatil +
                ", ccMarkaz=" + ccMarkaz +
                ", TarikhTatily='" + TarikhTatily + '\'' +
                '}';
    }
}
