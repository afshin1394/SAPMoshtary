package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class TakhfifSenfiModel
{

    private static final String TABLE_NAME = "TakhfifSenfi";
    public static final String COLUMN_Radif ="Radif";
    private static final String COLUMN_ccTakhfifSenfi = "ccTakhfifSenfi";
    private static final String COLUMN_CodeNoe = "CodeNoe";
    private static final String COLUMN_SharhTakhfif = "SharhTakhfif";
    private static final String COLUMN_NoeTedadRial = "NoeTedadRial";
    private static final String COLUMN_NameNoeFieldMoshtary = "NameNoeFieldMoshtary";
    private static final String COLUMN_ccNoeFieldMoshtary = "ccNoeFieldMoshtary";
    private static final String COLUMN_Darajeh = "Darajeh";
    private static final String COLUMN_ccMarkazSazmanForosh = "ccMarkazSazmanForosh";
    private static final String COLUMN_ccNoeSenf = "ccNoeSenf";
    private static final String COLUMN_NoeVosol = "NoeVosol";
    private static final String COLUMN_NameNoeSenf = "NameNoeSenf";
    private static final String COLUMN_NoeGheymat="NoeGheymat";
    private static final String COLUMN_ccMoshtaryGharardad="ccMoshtaryGharardad";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_Radif(){
        return COLUMN_Radif;
    }
    public static String COLUMN_ccTakhfifSenfi() {
        return COLUMN_ccTakhfifSenfi;
    }
    public static String COLUMN_CodeNoe() {
        return COLUMN_CodeNoe;
    }
    public static String COLUMN_SharhTakhfif() {
        return COLUMN_SharhTakhfif;
    }
    public static String COLUMN_NoeTedadRial() {
        return COLUMN_NoeTedadRial;
    }
    public static String COLUMN_NameNoeFieldMoshtary() {
        return COLUMN_NameNoeFieldMoshtary;
    }
    public static String COLUMN_ccNoeFieldMoshtary() {
        return COLUMN_ccNoeFieldMoshtary;
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
    public static String COLUMN_NameNoeSenf(){return COLUMN_NameNoeSenf; }
    public static String COLUMN_NoeGheymat() {
        return COLUMN_NoeGheymat;
    }
    public static String COLUMN_ccMoshtaryGharardad() {
        return COLUMN_ccMoshtaryGharardad;
    }

    public static String COLUMN_NoeVosol() {
        return COLUMN_NoeVosol;
    }

    private int Radif;

    public int getRadif() {
        return Radif;
    }

    public void setRadif(int radif) {
        Radif = radif;
    }

    private int ccTakhfifSenfi;
    public int getCcTakhfifSenfi() {
        return ccTakhfifSenfi;
    }
    public void setCcTakhfifSenfi(int ccTakhfifSenfi) {
        this.ccTakhfifSenfi = ccTakhfifSenfi;
    }


    private int CodeNoe;
    public int getCodeNoe() {
        return CodeNoe;
    }
    public void setCodeNoe(int codeNoe) {
        CodeNoe = codeNoe;
    }


    private String SharhTakhfif;
    public String getSharhTakhfif() {
        return SharhTakhfif;
    }
    public void setSharhTakhfif(String sharhTakhfif) {
        SharhTakhfif = sharhTakhfif;
    }


    private int NoeTedadRial;
    public int getNoeTedadRial() {
        return NoeTedadRial;
    }
    public void setNoeTedadRial(int noeTedadRial) {
        NoeTedadRial = noeTedadRial;
    }


    private int NameNoeFieldMoshtary;
    public int getNameNoeFieldMoshtary() {
        return NameNoeFieldMoshtary;
    }
    public void setNameNoeFieldMoshtary(int nameNoeFieldMoshtary) {
        NameNoeFieldMoshtary = nameNoeFieldMoshtary;
    }


    private int ccNoeFieldMoshtary;
    public int getCcNoeFieldMoshtary() {
        return ccNoeFieldMoshtary;
    }
    public void setCcNoeFieldMoshtary(int ccNoeFieldMoshtary) {
        this.ccNoeFieldMoshtary = ccNoeFieldMoshtary;
    }


    private int Darajeh;
    public int getDarajeh() {
        return Darajeh;
    }
    public void setDarajeh(int darajeh) {
        Darajeh = darajeh;
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


    private int ccMoshtaryGharardad;

    public int getCcMoshtrayGharardad() {
        return ccMoshtaryGharardad;
    }

    public void setCcMoshtrayGharardad(int ccMoshtrayGharardad) {
        this.ccMoshtaryGharardad = ccMoshtrayGharardad;
    }

    private int NoeGheymat;

    public int getNoeGheymat() {
        return NoeGheymat;
    }

    public void setNoeGheymat(int noeGheymat) {
        NoeGheymat = noeGheymat;
    }



    private int NoeVosol;

    public int getNoeVosol() {
        return NoeVosol;
    }

    public void setNoeVosol(int noeVosol) {
        NoeVosol = noeVosol;
    }

    @Override
    public String toString() {
        return "TakhfifSenfiModel{" +
                "Radif=" + Radif +
                ", ccTakhfifSenfi=" + ccTakhfifSenfi +
                ", CodeNoe=" + CodeNoe +
                ", SharhTakhfif='" + SharhTakhfif + '\'' +
                ", NoeTedadRial=" + NoeTedadRial +
                ", NameNoeFieldMoshtary=" + NameNoeFieldMoshtary +
                ", ccNoeFieldMoshtary=" + ccNoeFieldMoshtary +
                ", Darajeh=" + Darajeh +
                ", ccMarkazSazmanForosh=" + ccMarkazSazmanForosh +
                ", ccNoeSenf=" + ccNoeSenf +
                ", NameNoeSenf='" + NameNoeSenf + '\'' +
                ", ccMoshtaryGharardad=" + ccMoshtaryGharardad +
                ", NoeGheymat=" + NoeGheymat +
                ", NoeVosol=" + NoeVosol +
                '}';
    }
}
