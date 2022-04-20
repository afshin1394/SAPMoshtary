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
    private static final String COLUMN_IsMabna = "IsMabna";


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
    public static String COLUMN_IsMabna() {
        return COLUMN_IsMabna;
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
    private int Fee;
    private int ccTaminkonandeh;
    private int GheymatMasrafkonandeh;
    private int GheymatForoshAsli;
    private int IsMabna;

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

    public int getFee() {
        return Fee;
    }

    public void setFee(int fee) {
        Fee = fee;
    }

    public int getCcTaminkonandeh() {
        return ccTaminkonandeh;
    }

    public void setCcTaminkonandeh(int ccTaminkonandeh) {
        this.ccTaminkonandeh = ccTaminkonandeh;
    }

    public int getGheymatMasrafkonandeh() {
        return GheymatMasrafkonandeh;
    }

    public void setGheymatMasrafkonandeh(int gheymatMasrafkonandeh) {
        GheymatMasrafkonandeh = gheymatMasrafkonandeh;
    }


    public int getGheymatForoshAsli() {
        return GheymatForoshAsli;
    }

    public void setGheymatForoshAsli(int gheymatForoshAsli) {
        GheymatForoshAsli = gheymatForoshAsli;
    }

    public int getIsMabna() {
        return IsMabna;
    }

    public void setIsMabna(int isMabna) {
        IsMabna = isMabna;
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
                ", IsMabna=" + IsMabna +
                '}';
    }
}
