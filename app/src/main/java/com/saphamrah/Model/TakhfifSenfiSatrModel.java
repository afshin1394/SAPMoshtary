package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class TakhfifSenfiSatrModel
{

    private static final String TABLE_NAME = "TakhfifSenfiSatr";
    private static final String COLUMN_ccTakhfifSenfiSatr = "ccTakhfifSenfiSatr";
    private static final String COLUMN_ccTakhfifSenfi = "ccTakhfifSenfi";
    private static final String COLUMN_NameNoeField = "NameNoeField";
    private static final String COLUMN_ccNoeField = "ccNoeField";
    private static final String COLUMN_Az = "Az";
    private static final String COLUMN_Ta = "Ta";
    private static final String COLUMN_CodeNoeBastehBandy = "CodeNoeBastehBandy";
    private static final String COLUMN_BeEza = "BeEza";
    private static final String COLUMN_CodeNoeBastehBandyBeEza = "CodeNoeBastehBandyBeEza";
    private static final String COLUMN_DarsadTakhfif = "DarsadTakhfif";



    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccTakhfifSenfiSatr() {
        return COLUMN_ccTakhfifSenfiSatr;
    }
    public static String COLUMN_ccTakhfifSenfi() {
        return COLUMN_ccTakhfifSenfi;
    }
    public static String COLUMN_NameNoeField() {
        return COLUMN_NameNoeField;
    }
    public static String COLUMN_ccNoeField() {
        return COLUMN_ccNoeField;
    }
    public static String COLUMN_Az() {
        return COLUMN_Az;
    }
    public static String COLUMN_Ta() {
        return COLUMN_Ta;
    }
    public static String COLUMN_CodeNoeBastehBandy() {
        return COLUMN_CodeNoeBastehBandy;
    }
    public static String COLUMN_BeEza() {
        return COLUMN_BeEza;
    }
    public static String COLUMN_CodeNoeBastehBandyBeEza() {
        return COLUMN_CodeNoeBastehBandyBeEza;
    }
    public static String COLUMN_DarsadTakhfif() {
        return COLUMN_DarsadTakhfif;
    }



    private int ccTakhfifSenfiSatr;
    public int getCcTakhfifSenfiSatr() {
        return ccTakhfifSenfiSatr;
    }
    public void setCcTakhfifSenfiSatr(int ccTakhfifSenfiSatr) {
        this.ccTakhfifSenfiSatr = ccTakhfifSenfiSatr;
    }


    private int ccTakhfifSenfi;
    public int getCcTakhfifSenfi() {
        return ccTakhfifSenfi;
    }
    public void setCcTakhfifSenfi(int ccTakhfifSenfi) {
        this.ccTakhfifSenfi = ccTakhfifSenfi;
    }


    private int NameNoeField;
    public int getNameNoeField() {
        return NameNoeField;
    }
    public void setNameNoeField(int nameNoeField) {
        NameNoeField = nameNoeField;
    }


    private int ccNoeField;
    public int getCcNoeField() {
        return ccNoeField;
    }
    public void setCcNoeField(int ccNoeField) {
        this.ccNoeField = ccNoeField;
    }


    private double Az;
    public double getAz() {
        return Az;
    }
    public void setAz(double az) {
        Az = az;
    }


    private double Ta;
    public double getTa() {
        return Ta;
    }
    public void setTa(double ta) {
        Ta = ta;
    }


    private int CodeNoeBastehBandy;
    public int getCodeNoeBastehBandy() {
        return CodeNoeBastehBandy;
    }
    public void setCodeNoeBastehBandy(int codeNoeBastehBandy) {
        CodeNoeBastehBandy = codeNoeBastehBandy;
    }


    private double BeEza;
    public double getBeEza() {
        return BeEza;
    }
    public void setBeEza(double beEza) {
        BeEza = beEza;
    }


    private int CodeNoeBastehBandyBeEza;
    public int getCodeNoeBastehBandyBeEza() {
        return CodeNoeBastehBandyBeEza;
    }
    public void setCodeNoeBastehBandyBeEza(int codeNoeBastehBandyBeEza) {
        CodeNoeBastehBandyBeEza = codeNoeBastehBandyBeEza;
    }


    private double DarsadTakhfif;
    public double getDarsadTakhfif() {
        return DarsadTakhfif;
    }
    public void setDarsadTakhfif(double darsadTakhfif) {
        DarsadTakhfif = darsadTakhfif;
    }


    @NonNull
    @Override
    public String toString()
    {
        return  "ccTakhfifSenfiSatr=" + ccTakhfifSenfiSatr +
                ", ccTakhfifSenfi=" + ccTakhfifSenfi +
                ", NameNoeField=" + NameNoeField +
                ", ccNoeField=" + ccNoeField +
                ", Az=" + Az +
                ", Ta=" + Ta +
                ", CodeNoeBastehBandy=" + CodeNoeBastehBandy +
                ", BeEza=" + BeEza +
                ", CodeNoeBastehBandyBeEza=" + CodeNoeBastehBandyBeEza +
                ", DarsadTakhfif=" + DarsadTakhfif ;
    }


}
