package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class NoeHesabModel
{

    private static final String TABLE_NAME = "NoeHesab";
    private static final String COLUMN_ccNoeHesab = "ccNoeHesab";
    private static final String COLUMN_NameNoeHesab = "NameNoeHesab";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccNoeHesab() {
        return COLUMN_ccNoeHesab;
    }
    public static String COLUMN_NameNoeHesab() {
        return COLUMN_NameNoeHesab;
    }


    private int ccBank;
    public void setCcBank(int ccBank){
        this.ccBank = ccBank;
    }
    public int getCcBank(){
        return this.ccBank;
    }


    private int ccNoeHesab;
    public void setCcNoeHesab(int ccNoeHesab){
        this.ccNoeHesab = ccNoeHesab;
    }
    public int getCcNoeHesab(){
        return this.ccNoeHesab;
    }


    private String NameNoeHesab;
    public void setNameNoeHesab(String NameNoeHesab){
        this.NameNoeHesab = NameNoeHesab;
    }
    public String getNameNoeHesab(){
        return this.NameNoeHesab;
    }


    private boolean Faal;
    public void setFaal(boolean Faal){
        this.Faal = Faal;
    }
    public boolean getFaal(){
        return this.Faal;
    }


    private boolean Jary;
    public void setJary(boolean Jary){
        this.Jary = Jary;
    }
    public boolean getJary(){
        return this.Jary;
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
        return "Id : " + Id + " , ccNoeHesab : " + ccNoeHesab + " , NameNoeHesab : " + NameNoeHesab + " , ccBank : " + ccBank +
                " , Faal : " + Faal + " , Jary : " + Jary;
    }



}
