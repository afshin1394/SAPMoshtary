package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class TakhfifNaghdyModel
{

    public static final int CODE_NOE_MOHASEBEH_ADAD = 1;
    public static final int CODE_NOE_MOHASEBEH_FORMUL = 2;

    private static final String TABLE_NAME = "TakhfifNaghdy";
    private static final String COLUMN_ccTakhfifNaghdy = "ccTakhfifNaghdy";
    private static final String COLUMN_SharhTakhfif = "SharhTakhfif";
    private static final String COLUMN_ccNoeFieldMoshtary = "ccNoeFieldMoshtary";
    private static final String COLUMN_CodeNoeMohasebeh = "CodeNoeMohasebeh";
    private static final String COLUMN_DarsadTakhfif = "DarsadTakhfif";
    private static final String COLUMN_CodeNoe = "CodeNoe";
    private static final String COLUMN_NameNoeFieldMoshtary = "NameNoeFieldMoshtary";
    private static final String COLUMN_Darajeh = "Darajeh";
    private static final String COLUMN_ccMarkazSazmanForosh = "ccMarkazSazmanForosh";
    private static final String COLUMN_ccNoeSenf = "ccNoeSenf";
    private static final String COLUMN_NameNoeSenf = "NameNoeSenf";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccTakhfifNaghdy() {
        return COLUMN_ccTakhfifNaghdy;
    }
    public static String COLUMN_SharhTakhfif() {
        return COLUMN_SharhTakhfif;
    }
    public static String COLUMN_ccNoeFieldMoshtary() {
        return COLUMN_ccNoeFieldMoshtary;
    }
    public static String COLUMN_CodeNoeMohasebeh() {
        return COLUMN_CodeNoeMohasebeh;
    }
    public static String COLUMN_DarsadTakhfif() {
        return COLUMN_DarsadTakhfif;
    }
    public static String COLUMN_CodeNoe() {
        return COLUMN_CodeNoe;
    }
    public static String COLUMN_NameNoeFieldMoshtary() {
        return COLUMN_NameNoeFieldMoshtary;
    }
    public static String COLUMN_Darajeh() {
        return COLUMN_Darajeh;
    }
    public static String COLUMN_ccMarkazSazmanForosh() {
        return COLUMN_ccMarkazSazmanForosh;
    }
    public static String COLUMN_ccNoeSenf() {
        return COLUMN_ccNoeSenf;
    }
    public static String COLUMN_NameNoeSenf() {
        return COLUMN_NameNoeSenf;
    }



    private int ccTakhfifNaghdy;
    public int getCcTakhfifNaghdy() {
        return ccTakhfifNaghdy;
    }
    public void setCcTakhfifNaghdy(int ccTakhfifNaghdy) {
        this.ccTakhfifNaghdy = ccTakhfifNaghdy;
    }


    private String SharhTakhfif;
    public String getSharhTakhfif() {
        return SharhTakhfif;
    }
    public void setSharhTakhfif(String sharhTakhfif) {
        SharhTakhfif = sharhTakhfif;
    }


    private int ccNoeFieldMoshtary;
    public int getCcNoeFieldMoshtary() {
        return ccNoeFieldMoshtary;
    }
    public void setCcNoeFieldMoshtary(int ccNoeFieldMoshtary) {
        this.ccNoeFieldMoshtary = ccNoeFieldMoshtary;
    }


    private int CodeNoeMohasebeh;
    public int getCodeNoeMohasebeh() {
        return CodeNoeMohasebeh;
    }
    public void setCodeNoeMohasebeh(int codeNoeMohasebeh) {
        CodeNoeMohasebeh = codeNoeMohasebeh;
    }


    private double DarsadTakhfif;
    public double getDarsadTakhfif() {
        return DarsadTakhfif;
    }
    public void setDarsadTakhfif(double darsadTakhfif) {
        DarsadTakhfif = darsadTakhfif;
    }


    private int CodeNoe;
    public int getCodeNoe() {
        return CodeNoe;
    }
    public void setCodeNoe(int codeNoe) {
        CodeNoe = codeNoe;
    }


    private int NameNoeFieldMoshtary;
    public int getNameNoeFieldMoshtary() {
        return NameNoeFieldMoshtary;
    }
    public void setNameNoeFieldMoshtary(int nameNoeFieldMoshtary) {
        NameNoeFieldMoshtary = nameNoeFieldMoshtary;
    }


    private int Darajeh;
    public int getDarajeh()
    {
        return Darajeh;
    }
    public void setDarajeh(int darajeh)
    {
        this.Darajeh = darajeh;
    }


    private int ccMarkazSazmanForosh;
    public int getCcMarkazSazmanForosh()
    {
        return ccMarkazSazmanForosh;
    }
    public void setCcMarkazSazmanForosh(int ccMarkazSazmanForosh)
    {
        this.ccMarkazSazmanForosh = ccMarkazSazmanForosh;
    }

    private int ccNoeSenf;
    public int getCcNoeSenf()
    {
        return ccNoeSenf;
    }
    public void setCcNoeSenf(int ccNoeSenf)
    {
        this.ccNoeSenf = ccNoeSenf;
    }


    private String NameNoeSenf;
    public String getNameNoeSenf() {
        return NameNoeSenf;
    }
    public void setNameNoeSenf(String NameNoeSenf) {
        NameNoeSenf = NameNoeSenf;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "TakhfifNaghdyModel{" +
                "ccTakhfifNaghdy=" + ccTakhfifNaghdy +
                ", SharhTakhfif='" + SharhTakhfif + '\'' +
                ", ccNoeFieldMoshtary=" + ccNoeFieldMoshtary +
                ", CodeNoeMohasebeh=" + CodeNoeMohasebeh +
                ", DarsadTakhfif=" + DarsadTakhfif +
                ", CodeNoe=" + CodeNoe +
                ", NameNoeFieldMoshtary=" + NameNoeFieldMoshtary +
                ", Darajeh=" + Darajeh +
                ", ccMarkazSazmanForosh=" + ccMarkazSazmanForosh +
                ", ccNoeSenf=" + ccNoeSenf +
                ", NameNoeSenf=" + NameNoeSenf +
                '}';
    }
}
