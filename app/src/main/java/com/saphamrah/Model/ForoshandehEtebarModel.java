package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class ForoshandehEtebarModel
{

    private static final String TABLE_NAME = "ForoshandehEtebar";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_SaghfEtebarRiali = "SaghfEtebarRiali";
    private static final String COLUMN_SaghfEtebarAsnad = "SaghfEtebarAsnad";
    private static final String COLUMN_SaghfEtebarTedadi = "SaghfEtebarTedadi";
    private static final String COLUMN_SaghfEtebarModat = "SaghfEtebarModat";
    private static final String COLUMN_EtebarRialAsnadShakhsi = "EtebarRialAsnadShakhsi";
    private static final String COLUMN_EtebarTedadAsnadShakhsi = "EtebarTedadAsnadShakhsi";
    private static final String COLUMN_EtebarModatAsnadShakhsi = "EtebarModatAsnadShakhsi";
    private static final String COLUMN_EtebarRialAsnadMoshtary = "EtebarRialAsnadMoshtary";
    private static final String COLUMN_EtebarTedadAsnadMoshtary = "EtebarTedadAsnadMoshtary";
    private static final String COLUMN_EtebarModatAsnadMoshtary = "EtebarModatAsnadMoshtary";
    private static final String COLUMN_EtebarRialMoavagh = "EtebarRialMoavagh";
    private static final String COLUMN_EtebarTedadMoavagh = "EtebarTedadMoavagh";
    private static final String COLUMN_EtebarModatMoavagh = "EtebarModatMoavagh";
    private static final String COLUMN_EtebarRialBargashty = "EtebarRialBargashty";
    private static final String COLUMN_EtebarTedadBargashty = "EtebarTedadBargashty";
    private static final String COLUMN_EtebarModatBargashty = "EtebarModatBargashty";
    private static final String COLUMN_ModatVosol = "ModatVosol";
    private static final String COLUMN_RialAsnad = "RialAsnad";
    private static final String COLUMN_TedadAsnad = "TedadAsnad";
    private static final String COLUMN_ModatAsnad= "ModatAsnad";
    private static final String COLUMN_RialMoavagh = "RialMoavagh";
    private static final String COLUMN_TedadMoavagh = "TedadMoavagh";
    private static final String COLUMN_ModatMoavagh = "ModatMoavagh";
    private static final String COLUMN_RialBargashty = "RialBargashty";
    private static final String COLUMN_TedadBargashty = "TedadBargashty";
    private static final String COLUMN_ModatBargashty = "ModatBargashty";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_SaghfEtebarRiali() {
        return COLUMN_SaghfEtebarRiali;
    }
    public static String COLUMN_SaghfEtebarAsnad() {
        return COLUMN_SaghfEtebarAsnad;
    }
    public static String COLUMN_SaghfEtebarTedadi() {
        return COLUMN_SaghfEtebarTedadi;
    }
    public static String COLUMN_SaghfEtebarModat() {
        return COLUMN_SaghfEtebarModat;
    }
    public static String COLUMN_EtebarRialAsnadShakhsi() {
        return COLUMN_EtebarRialAsnadShakhsi;
    }
    public static String COLUMN_EtebarTedadAsnadShakhsi() {
        return COLUMN_EtebarTedadAsnadShakhsi;
    }
    public static String COLUMN_EtebarModatAsnadShakhsi() {
        return COLUMN_EtebarModatAsnadShakhsi;
    }
    public static String COLUMN_EtebarRialAsnadMoshtary() {
        return COLUMN_EtebarRialAsnadMoshtary;
    }
    public static String COLUMN_EtebarTedadAsnadMoshtary() {
        return COLUMN_EtebarTedadAsnadMoshtary;
    }
    public static String COLUMN_EtebarModatAsnadMoshtary() {
        return COLUMN_EtebarModatAsnadMoshtary;
    }
    public static String COLUMN_EtebarRialMoavagh() {
        return COLUMN_EtebarRialMoavagh;
    }
    public static String COLUMN_EtebarTedadMoavagh() {
        return COLUMN_EtebarTedadMoavagh;
    }
    public static String COLUMN_EtebarModatMoavagh() {
        return COLUMN_EtebarModatMoavagh;
    }
    public static String COLUMN_EtebarRialBargashty() {
        return COLUMN_EtebarRialBargashty;
    }
    public static String COLUMN_EtebarTedadBargashty() {
        return COLUMN_EtebarTedadBargashty;
    }
    public static String COLUMN_EtebarModatBargashty() {
        return COLUMN_EtebarModatBargashty;
    }
    public static String COLUMN_ModatVosol() {
        return COLUMN_ModatVosol;
    }
    public static String COLUMN_RialAsnad() {
        return COLUMN_RialAsnad;
    }
    public static String COLUMN_TedadAsnad() {
        return COLUMN_TedadAsnad;
    }
    public static String COLUMN_ModatAsnad() {
        return COLUMN_ModatAsnad;
    }
    public static String COLUMN_RialMoavagh() {
        return COLUMN_RialMoavagh;
    }
    public static String COLUMN_TedadMoavagh() {
        return COLUMN_TedadMoavagh;
    }
    public static String COLUMN_ModatMoavagh() {
        return COLUMN_ModatMoavagh;
    }
    public static String COLUMN_RialBargashty() {
        return COLUMN_RialBargashty;
    }
    public static String COLUMN_TedadBargashty() {
        return COLUMN_TedadBargashty;
    }
    public static String COLUMN_ModatBargashty() {
        return COLUMN_ModatBargashty;
    }



    private int ccForoshandeh;
    private long SaghfEtebarRiali;
    private long SaghfEtebarAsnad;
    private int SaghfEtebarTedadi;
    private int SaghfEtebarModat;
    private long EtebarRialAsnadShakhsi;
    private int EtebarTedadAsnadShakhsi;
    private int EtebarModatAsnadShakhsi;
    private long EtebarRialAsnadMoshtary;
    private int EtebarTedadAsnadMoshtary;
    private int EtebarModatAsnadMoshtary;
    private long EtebarRialMoavagh;
    private int EtebarTedadMoavagh;
    private int EtebarModatMoavagh;
    private long EtebarRialBargashty;
    private int EtebarTedadBargashty;
    private int EtebarModatBargashty;
    private int ModatVosol;
    private long RialAsnad;
    private int TedadAsnad;
    private int ModatAsnad;
    private long RialMoavagh;
    private int TedadMoavagh;
    private int ModatMoavagh;
    private long RialBargashty;
    private int TedadBargashty;
    private int ModatBargashty;


    public int getCcForoshandeh()
    {
        return ccForoshandeh;
    }
    public void setCcForoshandeh(int ccForoshandeh)
    {
        this.ccForoshandeh = ccForoshandeh;
    }

    public long getSaghfEtebarRiali()
    {
        return SaghfEtebarRiali;
    }
    public void setSaghfEtebarRiali(long saghfEtebarRiali)
    {
        SaghfEtebarRiali = saghfEtebarRiali;
    }

    public long getSaghfEtebarAsnad()
    {
        return SaghfEtebarAsnad;
    }
    public void setSaghfEtebarAsnad(long saghfEtebarAsnad)
    {
        SaghfEtebarAsnad = saghfEtebarAsnad;
    }

    public int getSaghfEtebarTedadi()
    {
        return SaghfEtebarTedadi;
    }
    public void setSaghfEtebarTedadi(int saghfEtebarTedadi)
    {
        SaghfEtebarTedadi = saghfEtebarTedadi;
    }

    public int getSaghfEtebarModat()
    {
        return SaghfEtebarModat;
    }
    public void setSaghfEtebarModat(int saghfEtebarModat)
    {
        SaghfEtebarModat = saghfEtebarModat;
    }

    public long getEtebarRialAsnadShakhsi()
    {
        return EtebarRialAsnadShakhsi;
    }
    public void setEtebarRialAsnadShakhsi(long etebarRialAsnadShakhsi)
    {
        EtebarRialAsnadShakhsi = etebarRialAsnadShakhsi;
    }

    public int getEtebarTedadAsnadShakhsi()
    {
        return EtebarTedadAsnadShakhsi;
    }
    public void setEtebarTedadAsnadShakhsi(int etebarTedadAsnadShakhsi)
    {
        EtebarTedadAsnadShakhsi = etebarTedadAsnadShakhsi;
    }

    public int getEtebarModatAsnadShakhsi()
    {
        return EtebarModatAsnadShakhsi;
    }
    public void setEtebarModatAsnadShakhsi(int etebarModatAsnadShakhsi)
    {
        EtebarModatAsnadShakhsi = etebarModatAsnadShakhsi;
    }

    public long getEtebarRialAsnadMoshtary()
    {
        return EtebarRialAsnadMoshtary;
    }
    public void setEtebarRialAsnadMoshtary(long etebarRialAsnadMoshtary)
    {
        EtebarRialAsnadMoshtary = etebarRialAsnadMoshtary;
    }

    public int getEtebarTedadAsnadMoshtary()
    {
        return EtebarTedadAsnadMoshtary;
    }
    public void setEtebarTedadAsnadMoshtary(int etebarTedadAsnadMoshtary)
    {
        EtebarTedadAsnadMoshtary = etebarTedadAsnadMoshtary;
    }

    public int getEtebarModatAsnadMoshtary()
    {
        return EtebarModatAsnadMoshtary;
    }
    public void setEtebarModatAsnadMoshtary(int etebarModatAsnadMoshtary)
    {
        EtebarModatAsnadMoshtary = etebarModatAsnadMoshtary;
    }

    public long getEtebarRialMoavagh()
    {
        return EtebarRialMoavagh;
    }
    public void setEtebarRialMoavagh(long etebarRialMoavagh)
    {
        EtebarRialMoavagh = etebarRialMoavagh;
    }

    public int getEtebarTedadMoavagh()
    {
        return EtebarTedadMoavagh;
    }
    public void setEtebarTedadMoavagh(int etebarTedadMoavagh)
    {
        EtebarTedadMoavagh = etebarTedadMoavagh;
    }

    public int getEtebarModatMoavagh()
    {
        return EtebarModatMoavagh;
    }
    public void setEtebarModatMoavagh(int etebarModatMoavagh)
    {
        EtebarModatMoavagh = etebarModatMoavagh;
    }

    public long getEtebarRialBargashty()
    {
        return EtebarRialBargashty;
    }
    public void setEtebarRialBargashty(long etebarRialBargashty)
    {
        EtebarRialBargashty = etebarRialBargashty;
    }

    public int getEtebarTedadBargashty()
    {
        return EtebarTedadBargashty;
    }
    public void setEtebarTedadBargashty(int etebarTedadBargashty)
    {
        EtebarTedadBargashty = etebarTedadBargashty;
    }

    public int getEtebarModatBargashty()
    {
        return EtebarModatBargashty;
    }
    public void setEtebarModatBargashty(int etebarModatBargashty)
    {
        EtebarModatBargashty = etebarModatBargashty;
    }

    public int getModatVosol()
    {
        return ModatVosol;
    }
    public void setModatVosol(int modatVosol)
    {
        ModatVosol = modatVosol;
    }



    public long getRialAsnad()
    {
        return RialAsnad;
    }
    public void setRialAsnad(long rialAsnad)
    {
        RialAsnad = rialAsnad;
    }

    public int getTedadAsnad()
    {
        return TedadAsnad;
    }
    public void setTedadAsnad(int tedadAsnad)
    {
        TedadAsnad = tedadAsnad;
    }

    public int getModatAsnad()
    {
        return ModatAsnad;
    }
    public void setModatAsnad(int modatAsnad)
    {
        ModatAsnad = modatAsnad;
    }

    public long getRialMoavagh()
    {
        return RialMoavagh;
    }
    public void setRialMoavagh(long rialMoavagh)
    {
        RialMoavagh = rialMoavagh;
    }

    public int getTedadMoavagh()
    {
        return TedadMoavagh;
    }
    public void setTedadMoavagh(int tedadMoavagh)
    {
        TedadMoavagh = tedadMoavagh;
    }

    public int getModatMoavagh()
    {
        return ModatMoavagh;
    }
    public void setModatMoavagh(int modatMoavagh)
    {
        ModatMoavagh = modatMoavagh;
    }

    public long getRialBargashty()
    {
        return RialBargashty;
    }
    public void setRialBargashty(long rialBargashty)
    {
        RialBargashty = rialBargashty;
    }

    public int getTedadBargashty()
    {
        return TedadBargashty;
    }
    public void setTedadBargashty(int tedadBargashty)
    {
        TedadBargashty = tedadBargashty;
    }

    public int getModatBargashty()
    {
        return ModatBargashty;
    }
    public void setModatBargashty(int modatBargashty)
    {
        ModatBargashty = modatBargashty;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "ForoshandehEtebarModel{" +
                "ccForoshandeh=" + ccForoshandeh +
                ", SaghfEtebarRiali=" + SaghfEtebarRiali +
                ", SaghfEtebarAsnad=" + SaghfEtebarAsnad +
                ", SaghfEtebarTedadi=" + SaghfEtebarTedadi +
                ", SaghfEtebarModat=" + SaghfEtebarModat +
                ", EtebarRialAsnadShakhsi=" + EtebarRialAsnadShakhsi +
                ", EtebarTedadAsnadShakhsi=" + EtebarTedadAsnadShakhsi +
                ", EtebarModatAsnadShakhsi=" + EtebarModatAsnadShakhsi +
                ", EtebarRialAsnadMoshtary=" + EtebarRialAsnadMoshtary +
                ", EtebarTedadAsnadMoshtary=" + EtebarTedadAsnadMoshtary +
                ", EtebarModatAsnadMoshtary=" + EtebarModatAsnadMoshtary +
                ", EtebarRialMoavagh=" + EtebarRialMoavagh +
                ", EtebarTedadMoavagh=" + EtebarTedadMoavagh +
                ", EtebarModatMoavagh=" + EtebarModatMoavagh +
                ", EtebarRialBargashty=" + EtebarRialBargashty +
                ", EtebarTedadBargashty=" + EtebarTedadBargashty +
                ", EtebarModatBargashty=" + EtebarModatBargashty +
                ", ModatVosol=" + ModatVosol +
                ", RialAsnad=" + RialAsnad +
                ", TedadAsnad=" + TedadAsnad +
                ", ModatAsnad=" + ModatAsnad +
                ", RialMoavagh=" + RialMoavagh +
                ", TedadMoavagh=" + TedadMoavagh +
                ", ModatMoavagh=" + ModatMoavagh +
                ", RialBargashty=" + RialBargashty +
                ", TedadBargashty=" + TedadBargashty +
                ", ModatBargashty=" + ModatBargashty +
                '}';
    }
}
