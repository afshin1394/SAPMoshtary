package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class ElamMarjoeeSatrPPCModel
{

    private static final String TABLE_NAME = "ElamMarjoeeSatrPPC";
    private static final String COLUMN_ccElamMarjoeeSatrPPC = "ccElamMarjoeeSatrPPC";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccElamMarjoeePPC = "ccElamMarjoeePPC";
    private static final String COLUMN_ccElatMarjoeeKala = "ccElatMarjoeeKala";
    private static final String COLUMN_CodeNoeMarjoee = "CodeNoeMarjoee";
    private static final String COLUMN_ccKala = "ccKala";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_ShomarehBach = "ShomarehBach";
    private static final String COLUMN_TarikhTolid = "TarikhTolid";
    private static final String COLUMN_TarikhEngheza = "TarikhEngheza";
    private static final String COLUMN_Tedad3 = "Tedad3";
    private static final String COLUMN_Fee = "Fee";
    private static final String COLUMN_ccTaminkonandeh = "ccTaminkonandeh";
    private static final String COLUMN_GheymatMasrafkonandeh = "GheymatMasrafkonandeh";
    private static final String COLUMN_GheymatForoshAsli = "GheymatForoshAsli";
    private static final String COLUMN_GheymatKharid = "GheymatKharid";
    private static final String COLUMN_IsMabna = "IsMabna";
    private static final String COLUMN_ExtraProp_ccMoshtary = "ExtraProp_ccMoshtary";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccElamMarjoeeSatrPPC() {
        return COLUMN_ccElamMarjoeeSatrPPC;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ccElamMarjoeePPC() {
        return COLUMN_ccElamMarjoeePPC;
    }
    public static String COLUMN_ccElatMarjoeeKala() {
        return COLUMN_ccElatMarjoeeKala;
    }
    public static String COLUMN_CodeNoeMarjoee() {
        return COLUMN_CodeNoeMarjoee;
    }
    public static String COLUMN_ccKala() {
        return COLUMN_ccKala;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_ShomarehBach() {
        return COLUMN_ShomarehBach;
    }
    public static String COLUMN_TarikhTolid() {
        return COLUMN_TarikhTolid;
    }
    public static String COLUMN_TarikhEngheza() {
        return COLUMN_TarikhEngheza;
    }
    public static String COLUMN_Tedad3() {
        return COLUMN_Tedad3;
    }
    public static String COLUMN_Fee() {
        return COLUMN_Fee;
    }
    public static String COLUMN_ccTaminkonandeh() {
        return COLUMN_ccTaminkonandeh;
    }
    public static String COLUMN_GheymatMasrafkonandeh() {
        return COLUMN_GheymatMasrafkonandeh;
    }
    public static String COLUMN_GheymatForoshAsli() {
        return COLUMN_GheymatForoshAsli;
    }
    public static String COLUMN_GheymatKharid() {
        return COLUMN_GheymatKharid;
    }
    public static String COLUMN_IsMabna() {
        return COLUMN_IsMabna;
    }
    public static String COLUMN_ExtraProp_ccMoshtary() {
        return COLUMN_ExtraProp_ccMoshtary;
    }



    private int ccElamMarjoeeSatrPPC;
    private long ccDarkhastFaktor;
    private int ccElamMarjoeePPC;
    private int ccElatMarjoeeKala;
    private int CodeNoeMarjoee;
    private int ccKala;
    private int ccKalaCode;
    private String ShomarehBach;
    private String TarikhTolid;
    private String TarikhEngheza;
    private int Tedad3;
    private double Fee;
    private int ccTaminkonandeh;
    private double GheymatMasrafkonandeh;
    private double GheymatForoshAsli;
    private double GheymatKharid;
    private int IsMabna;
    private int ExtraProp_ccMoshtary;

    public int getCcElamMarjoeeSatrPPC() {
        return ccElamMarjoeeSatrPPC;
    }

    public void setCcElamMarjoeeSatrPPC(int ccElamMarjoeeSatrPPC) {
        this.ccElamMarjoeeSatrPPC = ccElamMarjoeeSatrPPC;
    }

    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getCcElamMarjoeePPC() {
        return ccElamMarjoeePPC;
    }

    public void setCcElamMarjoeePPC(int ccElamMarjoeePPC) {
        this.ccElamMarjoeePPC = ccElamMarjoeePPC;
    }

    public int getCcElatMarjoeeKala() {
        return ccElatMarjoeeKala;
    }

    public void setCcElatMarjoeeKala(int ccElatMarjoeeKala) {
        this.ccElatMarjoeeKala = ccElatMarjoeeKala;
    }

    public int getCodeNoeMarjoee() {
        return CodeNoeMarjoee;
    }

    public void setCodeNoeMarjoee(int codeNoeMarjoee) {
        CodeNoeMarjoee = codeNoeMarjoee;
    }

    public int getCcKala() {
        return ccKala;
    }

    public void setCcKala(int ccKala) {
        this.ccKala = ccKala;
    }

    public int getCcKalaCode() {
        return ccKalaCode;
    }

    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }

    public String getShomarehBach() {
        return ShomarehBach;
    }

    public void setShomarehBach(String shomarehBach) {
        ShomarehBach = shomarehBach;
    }

    public String getTarikhTolid() {
        return TarikhTolid;
    }

    public void setTarikhTolid(String tarikhTolid) {
        TarikhTolid = tarikhTolid;
    }

    public String getTarikhEngheza() {
        return TarikhEngheza;
    }

    public void setTarikhEngheza(String tarikhEngheza) {
        TarikhEngheza = tarikhEngheza;
    }

    public int getTedad3() {
        return Tedad3;
    }

    public void setTedad3(int tedad3) {
        Tedad3 = tedad3;
    }

    public double getFee() {
        return Fee;
    }

    public void setFee(double fee) {
        Fee = fee;
    }

    public int getCcTaminkonandeh() {
        return ccTaminkonandeh;
    }

    public void setCcTaminkonandeh(int ccTaminkonandeh) {
        this.ccTaminkonandeh = ccTaminkonandeh;
    }

    public double getGheymatMasrafkonandeh() {
        return GheymatMasrafkonandeh;
    }

    public void setGheymatMasrafkonandeh(double gheymatMasrafkonandeh) {
        GheymatMasrafkonandeh = gheymatMasrafkonandeh;
    }


    public double getGheymatForoshAsli() {
        return GheymatForoshAsli;
    }

    public void setGheymatForoshAsli(double gheymatForoshAsli) {
        GheymatForoshAsli = gheymatForoshAsli;
    }
    public double getGheymatKharid() {
        return GheymatKharid;
    }

    public void setGheymatKharid(double gheymatKharid) {
        GheymatKharid = gheymatKharid;
    }

    public int getIsMabna() {
        return IsMabna;
    }

    public void setIsMabna(int isMabna) {
        IsMabna = isMabna;
    }

    public int getExtraProp_ccMoshtary() {
        return ExtraProp_ccMoshtary;
    }

    public void setExtraProp_ccMoshtary(int extraProp_ccMoshtary) {
        ExtraProp_ccMoshtary = extraProp_ccMoshtary;
    }

    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccElamMarjoeeSatrPPC" , ccElamMarjoeeSatrPPC);
            jsonObject.put("ccElamMarjoeePPC" , ccElamMarjoeePPC);
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("ccElatMarjoeeKala" , ccElatMarjoeeKala);
            jsonObject.put("CodeNoeMarjoee" , CodeNoeMarjoee);
            jsonObject.put("ccKala" , ccKala);
            jsonObject.put("ccKalaCode" , ccKalaCode);
            jsonObject.put("ShomarehBach" , ShomarehBach);
            jsonObject.put("TarikhTolid" , TarikhTolid == null ? "" : TarikhTolid);
            jsonObject.put("TarikhEngheza" , TarikhEngheza == null ? "" : TarikhEngheza);
            jsonObject.put("Tedad3" , Tedad3);
            jsonObject.put("Fee" , Fee);
            jsonObject.put("ccTaminkonandeh" , ccTaminkonandeh);
            jsonObject.put("GheymatMasrafkonandeh" , GheymatMasrafkonandeh);
            jsonObject.put("GheymatForoshAsli" , GheymatForoshAsli);
            jsonObject.put("GheymatKharid" , GheymatKharid);
            jsonObject.put("IsMabna" , IsMabna);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "ElamMarjoeeSatrPPCModel{" +
                "ccElamMarjoeeSatrPPC=" + ccElamMarjoeeSatrPPC +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccElamMarjoeePPC=" + ccElamMarjoeePPC +
                ", ccElatMarjoeeKala=" + ccElatMarjoeeKala +
                ", CodeNoeMarjoee=" + CodeNoeMarjoee +
                ", ccKala=" + ccKala +
                ", ccKalaCode=" + ccKalaCode +
                ", ShomarehBach='" + ShomarehBach + '\'' +
                ", TarikhTolid='" + TarikhTolid + '\'' +
                ", TarikhEngheza='" + TarikhEngheza + '\'' +
                ", Tedad3=" + Tedad3 +
                ", Fee=" + Fee +
                ", ccTaminkonandeh=" + ccTaminkonandeh +
                ", GheymatMasrafkonandeh=" + GheymatMasrafkonandeh +
                ", GheymatForoshAsli=" + GheymatForoshAsli +
                ", GheymatKharid=" + GheymatKharid +
                ", IsMabna=" + IsMabna +
                '}';
    }
}
