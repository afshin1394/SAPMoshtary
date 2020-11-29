package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class TaghiratVersionPPCModel
{

    private static final String TABLE_NAME = "TaghiratVersion_PPC";
    private static final String COLUMN_ccTaghirat = "ccTaghirat";
    private static final String COLUMN_Noe = "Noe";
    private static final String COLUMN_PocketPCVersion = "PocketPCVersion";
    private static final String COLUMN_Sharh = "Sharh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccTaghirat() {
        return COLUMN_ccTaghirat;
    }
    public static String COLUMN_Noe() {
        return COLUMN_Noe;
    }
    public static String COLUMN_PocketPCVersion() {
        return COLUMN_PocketPCVersion;
    }
    public static String COLUMN_Sharh() {
        return COLUMN_Sharh;
    }



    private int ccTaghirat;
    public void setCcTaghirat(int ccTaghirat){
        this.ccTaghirat = ccTaghirat;
    }
    public int getCcTaghirat(){
        return this.ccTaghirat;
    }


    private int Noe;
    public void setNoe(int Noe){
        this.Noe = Noe;
    }
    public int getNoe(){
        return this.Noe;
    }


    private String PocketPCVersion;
    public void setPocketPCVersion(String PocketPCVersion){
        this.PocketPCVersion = PocketPCVersion;
    }
    public String getPocketPCVersion(){
        return this.PocketPCVersion;
    }


    private String Sharh;
    public void setSharh(String Sharh){
        this.Sharh = Sharh;
    }
    public String getSharh(){
        return this.Sharh;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "ccTaghirat : " + ccTaghirat + " , Noe : " + Noe + " , PocketPCVersion : " + PocketPCVersion + " , Sharh : " + Sharh;
    }



}
