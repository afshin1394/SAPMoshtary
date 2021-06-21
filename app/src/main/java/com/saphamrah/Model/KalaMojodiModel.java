package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class KalaMojodiModel
{
    private static final String TABLE_NAME = "KalaMojodi";
    private static final String COLUMN_ccKalaMojodi = "ccKalaMojodi";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_Tedad = "Tedad";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_TarikhDarkhast = "TarikhDarkhast";
    private static final String COLUMN_ShomarehBach = "ShomarehBach";
    private static final String COLUMN_TarikhTolid = "TarikhTolid";
    private static final String COLUMN_GheymatMasrafKonandeh = "GheymatMasrafKonandeh";
    private static final String COLUMN_GheymatForosh = "GheymatForosh";
    private static final String COLUMN_ccTaminKonandeh = "ccTaminKonandeh";
    private static final String COLUMN_ForJayezeh = "ForJayezeh";
    private static final String COLUMN_ZamaneSabt = "ZamaneSabt";
    private static final String COLUMN_IsAdamForosh = "IsAdamForosh";
    private static final String COLUMN_Max_Mojody = "Max_Mojody";
    private static final String COLUMN_Max_MojodyByShomarehBach = "Max_MojodyByShomarehBach";
    private static final String COLUMN_ccAfrad = "ccAfrad";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKalaMojodi() {
        return COLUMN_ccKalaMojodi;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_Tedad() {
        return COLUMN_Tedad;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_TarikhDarkhast() {
        return COLUMN_TarikhDarkhast;
    }
    public static String COLUMN_ShomarehBach() {
        return COLUMN_ShomarehBach;
    }
    public static String COLUMN_TarikhTolid() {
        return COLUMN_TarikhTolid;
    }
    public static String COLUMN_GheymatMasrafKonandeh() {
        return COLUMN_GheymatMasrafKonandeh;
    }
    public static String COLUMN_GheymatForosh() {
        return COLUMN_GheymatForosh;
    }
    public static String COLUMN_ccTaminKonandeh() {
        return COLUMN_ccTaminKonandeh;
    }
    public static String COLUMN_ForJayezeh() {
        return COLUMN_ForJayezeh;
    }
    public static String COLUMN_ZamaneSabt() {
        return COLUMN_ZamaneSabt;
    }
    public static String COLUMN_IsAdamForosh() {
        return COLUMN_IsAdamForosh;
    }
    public static String COLUMN_Max_Mojody() {
        return COLUMN_Max_Mojody;
    }
    public static String COLUMN_Max_MojodyByShomarehBach() {
        return COLUMN_Max_MojodyByShomarehBach;
    }
    public static String COLUMN_ccAfrad() {
        return COLUMN_ccAfrad;
    }



    private int ccKalaMojodi;
    private int ccKalaCode;
    private int ccForoshandeh;
    private int Tedad;
    private long ccDarkhastFaktor;
    private String TarikhDarkhast;
    private String ShomarehBach;
    private String TarikhTolid;
    private int GheymatMasrafKonandeh;
    private int GheymatForosh;
    private int ccTaminKonandeh;
    private int ForJayezeh;
    private String ZamaneSabt;
    private int IsAdamForosh;
    private int Max_Mojody;
    private int Max_MojodyByShomarehBach;
    private int ccAfrad;

    public int getGheymatForoshAsli() {
        return gheymatForoshAsli;
    }

    public void setGheymatForoshAsli(int gheymatForoshAsli) {
        this.gheymatForoshAsli = gheymatForoshAsli;
    }

    private int gheymatForoshAsli;


    public int getCcKalaMojodi() {
        return ccKalaMojodi;
    }
    public void setCcKalaMojodi(int ccKalaMojodi) {
        this.ccKalaMojodi = ccKalaMojodi;
    }

    public int getCcKalaCode() {
        return ccKalaCode;
    }
    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }

    public int getCcForoshandeh() {
        return ccForoshandeh;
    }
    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getTedad() {
        return Tedad;
    }
    public void setTedad(int tedad) {
        Tedad = tedad;
    }

    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }
    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public String getTarikhDarkhast() {
        return TarikhDarkhast;
    }
    public void setTarikhDarkhast(String tarikhDarkhast) {
        TarikhDarkhast = tarikhDarkhast;
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

    public int getGheymatMasrafKonandeh() {
        return GheymatMasrafKonandeh;
    }
    public void setGheymatMasrafKonandeh(int gheymatMasrafKonandeh) {
        GheymatMasrafKonandeh = gheymatMasrafKonandeh;
    }

    public int getGheymatForosh() {
        return GheymatForosh;
    }
    public void setGheymatForosh(int gheymatForosh) {
        GheymatForosh = gheymatForosh;
    }

    public int getCcTaminKonandeh() {
        return ccTaminKonandeh;
    }
    public void setCcTaminKonandeh(int ccTaminKonandeh) {
        this.ccTaminKonandeh = ccTaminKonandeh;
    }

    public int getForJayezeh() {
        return ForJayezeh;
    }
    public void setForJayezeh(int forJayezeh) {
        ForJayezeh = forJayezeh;
    }

    public String getZamaneSabt()
    {
        return ZamaneSabt;
    }
    public void setZamaneSabt(String zamaneSabt)
    {
        ZamaneSabt = zamaneSabt;
    }

    public int getIsAdamForosh()
    {
        return IsAdamForosh;
    }
    public void setIsAdamForosh(int isAdamForosh)
    {
        IsAdamForosh = isAdamForosh;
    }

    public int getMax_Mojody()
    {
        return Max_Mojody;
    }
    public void setMax_Mojody(int max_Mojody)
    {
        Max_Mojody = max_Mojody;
    }

    public int getMax_MojodyByShomarehBach()
{
    return Max_MojodyByShomarehBach;
}
    public void setMax_MojodyByShomarehBach(int max_MojodyByShomarehBach)
    {
        Max_MojodyByShomarehBach = max_MojodyByShomarehBach;
    }

    public int getCcAfrad()
    {
        return ccAfrad;
    }
    public void setCcAfrad(int ccAfrad)
    {
        this.ccAfrad = ccAfrad;
    }

    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccKalaMojodi" , ccKalaMojodi);
            jsonObject.put("ccKalaCode" , ccKalaCode);
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("Tedad" , Tedad);
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("TarikhDarkhast" , TarikhDarkhast);
            jsonObject.put("ShomarehBach" , ShomarehBach);
            jsonObject.put("TarikhTolid" , TarikhTolid);
            jsonObject.put("GheymatMasrafKonandeh" , GheymatMasrafKonandeh);
            jsonObject.put("GheymatForosh" , GheymatForosh);
            jsonObject.put("ccTaminKonandeh" , ccTaminKonandeh);
            jsonObject.put("ForJayezeh" , ForJayezeh);
            jsonObject.put("ZamaneSabt" , ZamaneSabt);
            jsonObject.put("IsAdamForosh" , IsAdamForosh);
            jsonObject.put("Max_Mojody" , Max_Mojody);
            jsonObject.put("Max_MojodyByShomarehBach" , Max_MojodyByShomarehBach);
            jsonObject.put("ccAfrad" , ccAfrad);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "KalaMojodiModel{" +
                "ccKalaMojodi=" + ccKalaMojodi +
                ", ccKalaCode=" + ccKalaCode +
                ", ccForoshandeh=" + ccForoshandeh +
                ", Tedad=" + Tedad +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", TarikhDarkhast='" + TarikhDarkhast + '\'' +
                ", ShomarehBach='" + ShomarehBach + '\'' +
                ", TarikhTolid='" + TarikhTolid + '\'' +
                ", GheymatMasrafKonandeh=" + GheymatMasrafKonandeh +
                ", GheymatForosh=" + GheymatForosh +
                ", ccTaminKonandeh=" + ccTaminKonandeh +
                ", ForJayezeh=" + ForJayezeh +
                ", ZamaneSabt='" + ZamaneSabt + '\'' +
                ", IsAdamForosh=" + IsAdamForosh +
                ", Max_Mojody=" + Max_Mojody +
                ", Max_MojodyByShomarehBach=" + Max_MojodyByShomarehBach +
                ", ccAfrad=" + ccAfrad +
                '}';
    }
}
