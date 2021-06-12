package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class KardexSatrModel
{

    private static final String TABLE_NAME = "KardexSatr";
    private static final String COLUMN_ccKardexSatr = "ccKardexSatr";
    private static final String COLUMN_ccKardex = "ccKardex";
    private static final String COLUMN_ccTaminKonandeh = "ccTaminKonandeh";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_ShomarehBach = "ShomarehBach";
    private static final String COLUMN_TarikhTolid = "TarikhTolid";
    private static final String COLUMN_TarikhEngheza = "TarikhEngheza";
    private static final String COLUMN_Tedad3 = "Tedad3";
    private static final String COLUMN_ccElat = "ccElat";
    private static final String COLUMN_NameElat = "NameElat";
    private static final String COLUMN_CodeNoeKala = "CodeNoeKala";
    private static final String COLUMN_CodeKalaOld = "CodeKalaOld";
    private static final String COLUMN_NameKala = "NameKala";
    private static final String COLUMN_ccElamMarjoeeForoshandeh = "ccElamMarjoeeForoshandeh";
    private static final String COLUMN_ccMarjoeeMamorPakhsh = "ccMarjoeeMamorPakhsh";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_TarikhTolidShamsi = "TarikhTolidShamsi";
    private static final String COLUMN_GheymatKharid = "GheymatKharid";
    private static final String COLUMN_GheymatForosh = "GheymatForosh";
    private static final String COLUMN_GheymatForoshKhales = "GheymatForoshKhales";
    private static final String COLUMN_GheymatMasrafKonandeh = "GheymatMasrafKonandeh";
    private static final String COLUMN_ccAnbarGhesmat = "ccAnbarGhesmat";
    private static final String COLUMN_GheymatForoshAsli = "GheymatForoshAsli";
    private static final String COLUMN_ccKala = "ccKala";









    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKardexSatr() {
        return COLUMN_ccKardexSatr;
    }
    public static String COLUMN_ccKardex() {
        return COLUMN_ccKardex;
    }
    public static String COLUMN_ccTaminKonandeh() {
        return COLUMN_ccTaminKonandeh;
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
    public static String COLUMN_ccElat() {
        return COLUMN_ccElat;
    }
    public static String COLUMN_NameElat() {
        return COLUMN_NameElat;
    }
    public static String COLUMN_CodeNoeKala() {
        return COLUMN_CodeNoeKala;
    }
    public static String COLUMN_CodeKalaOld() {
        return COLUMN_CodeKalaOld;
    }
    public static String COLUMN_NameKala() {
        return COLUMN_NameKala;
    }
    public static String COLUMN_ccElamMarjoeeForoshandeh() {
        return COLUMN_ccElamMarjoeeForoshandeh;
    }
    public static String COLUMN_ccMarjoeeMamorPakhsh() {
        return COLUMN_ccMarjoeeMamorPakhsh;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_TarikhTolidShamsi() {
        return COLUMN_TarikhTolidShamsi;
    }
    public static String COLUMN_GheymatKharid() {
        return COLUMN_GheymatKharid;
    }
    public static String COLUMN_GheymatForosh() {
        return COLUMN_GheymatForosh;
    }
    public static String COLUMN_GheymatForoshKhales() {
        return COLUMN_GheymatForoshKhales;
    }
    public static String COLUMN_GheymatMasrafKonandeh() {
        return COLUMN_GheymatMasrafKonandeh;
    }
    public static String COLUMN_ccAnbarGhesmat() {
        return COLUMN_ccAnbarGhesmat;
    }
    public static String COLUMN_GheymatForoshAsli() {
        return COLUMN_GheymatForoshAsli;
    }

    public static String COLUMN_ccKala() {
        return COLUMN_ccKala;
    }




    private int ccKardexSatr;
    public void setCcKardexSatr(int ccKardexSatr) {
        this.ccKardexSatr = ccKardexSatr;
    }
    public int getCcKardexSatr() {
        return ccKardexSatr;
    }


    private long ccKardex;
    public void setCcKardex(long ccKardex) {
        this.ccKardex = ccKardex;
    }
    public long getCcKardex() {
        return ccKardex;
    }


    private int ccTaminKonandeh;
    public int getCcTaminKonandeh() {
        return ccTaminKonandeh;
    }
    public void setCcTaminKonandeh(int ccTaminKonandeh) {
        this.ccTaminKonandeh = ccTaminKonandeh;
    }


    private int ccKalaCode;
    public int getCcKalaCode() {
        return ccKalaCode;
    }
    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }


    private String ShomarehBach;
    public String getShomarehBach() {
        return ShomarehBach;
    }
    public void setShomarehBach(String shomarehBach) {
        ShomarehBach = shomarehBach;
    }


    private String TarikhTolid;
    public String getTarikhTolid() {
        return TarikhTolid;
    }
    public void setTarikhTolid(String tarikhTolid) {
        TarikhTolid = tarikhTolid;
    }


    private String TarikhEngheza;
    public String getTarikhEngheza() {
        return TarikhEngheza;
    }
    public void setTarikhEngheza(String tarikhEngheza) {
        TarikhEngheza = tarikhEngheza;
    }


    private int Tedad3;
    public int getTedad3() {
        return Tedad3;
    }
    public void setTedad3(int tedad3) {
        Tedad3 = tedad3;
    }


    private int ccElat;
    public int getCcElat() {
        return ccElat;
    }
    public void setCcElat(int ccElat) {
        this.ccElat = ccElat;
    }


    private String NameElat;
    public String getNameElat() {
        return NameElat;
    }
    public void setNameElat(String nameElat) {
        NameElat = nameElat;
    }


    private int CodeNoeKala;
    public int getCodeNoeKala() {
        return CodeNoeKala;
    }
    public void setCodeNoeKala(int codeNoeKala) {
        CodeNoeKala = codeNoeKala;
    }


    private String CodeKalaOld;
    public String getCodeKalaOld() {
        return CodeKalaOld;
    }
    public void setCodeKalaOld(String codeKalaOld) {
        CodeKalaOld = codeKalaOld;
    }


    private String NameKala;
    public String getNameKala() {
        return NameKala;
    }
    public void setNameKala(String nameKala) {
        NameKala = nameKala;
    }


    private long ccElamMarjoeeForoshandeh;
    public long getCcElamMarjoeeForoshandeh() {
        return ccElamMarjoeeForoshandeh;
    }
    public void setCcElamMarjoeeForoshandeh(long ccElamMarjoeeForoshandeh) {
        this.ccElamMarjoeeForoshandeh = ccElamMarjoeeForoshandeh;
    }


    private int ccMarjoeeMamorPakhsh;
    public int getCcMarjoeeMamorPakhsh() {
        return ccMarjoeeMamorPakhsh;
    }
    public void setCcMarjoeeMamorPakhsh(int ccMarjoeeMamorPakhsh) {
        this.ccMarjoeeMamorPakhsh = ccMarjoeeMamorPakhsh;
    }


    private int ccMoshtary;
    public int getCcMoshtary() {
        return ccMoshtary;
    }
    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }


    private String TarikhTolidShamsi;
    public String getTarikhTolidShamsi() {
        return TarikhTolidShamsi;
    }
    public void setTarikhTolidShamsi(String tarikhTolidShamsi) {
        TarikhTolidShamsi = tarikhTolidShamsi;
    }


    private double GheymatKharid;
    public double getGheymatKharid() {
        return GheymatKharid;
    }
    public void setGheymatKharid(double gheymatKharid) {
        GheymatKharid = gheymatKharid;
    }


    private double GheymatForosh;
    public double getGheymatForosh() {
        return GheymatForosh;
    }
    public void setGheymatForosh(double gheymatForosh) {
        GheymatForosh = gheymatForosh;
    }


    private double GheymatForoshKhales;
    public double getGheymatForoshKhales() {
        return GheymatForoshKhales;
    }
    public void setGheymatForoshKhales(double gheymatForoshKhales) {
        GheymatForoshKhales = gheymatForoshKhales;
    }


    private double GheymatMasrafKonandeh;
    public double getGheymatMasrafKonandeh() {
        return GheymatMasrafKonandeh;
    }
    public void setGheymatMasrafKonandeh(double gheymatMasrafKonandeh) {
        GheymatMasrafKonandeh = gheymatMasrafKonandeh;
    }


    private int ccAnbarGhesmat;
    public int getCcAnbarGhesmat() {
        return ccAnbarGhesmat;
    }
    public void setCcAnbarGhesmat(int ccAnbarGhesmat) {
        this.ccAnbarGhesmat = ccAnbarGhesmat;
    }


    private double GheymatForoshAsli;
    public double getGheymatForoshAsli() {
        return GheymatForoshAsli;
    }
    public void setGheymatForoshAsli(double gheymatForoshAsli) {
        GheymatForoshAsli = gheymatForoshAsli;
    }


    private int ccKala;

    public int getCcKala() {
        return ccKala;
    }

    public void setCcKala(int ccKala) {
        this.ccKala = ccKala;
    }







    @NonNull
    @Override
    public String toString()
    {
        return  "ccKardexSatr=" + ccKardexSatr +
                ", ccKardex=" + ccKardex +
                ", ccTaminKonandeh=" + ccTaminKonandeh +
                ", ccKalaCode=" + ccKalaCode +
                ", ShomarehBach='" + ShomarehBach + '\'' +
                ", TarikhTolid='" + TarikhTolid + '\'' +
                ", TarikhEngheza='" + TarikhEngheza + '\'' +
                ", Tedad3=" + Tedad3 +
                ", ccElat=" + ccElat +
                ", NameElat='" + NameElat + '\'' +
                ", CodeNoeKala=" + CodeNoeKala +
                ", CodeKalaOld='" + CodeKalaOld + '\'' +
                ", NameKala='" + NameKala + '\'' +
                ", ccElamMarjoeeForoshandeh=" + ccElamMarjoeeForoshandeh +
                ", ccMarjoeeMamorPakhsh=" + ccMarjoeeMamorPakhsh +
                ", ccMoshtary=" + ccMoshtary +
                ", TarikhTolidShamsi='" + TarikhTolidShamsi + '\'' +
                ", GheymatKharid=" + GheymatKharid +
                ", GheymatForosh=" + GheymatForosh +
                ", GheymatForoshKhales=" + GheymatForoshKhales +
                ", GheymatMasrafKonandeh=" + GheymatMasrafKonandeh +
                ", ccAnbarGhesmat=" + ccAnbarGhesmat +
                ", GheymatForoshAsli=" + GheymatForoshAsli +
                " , ccKala=" + ccKala ;

    }


    // for convert string json
    public JSONObject toJsonForKardexSatr()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccKardexSatr_Tablet" , ccKardexSatr);
            jsonObject.put("ccKardex" , ccKardex);
            jsonObject.put("ccTaminKonandeh" , ccTaminKonandeh);
            jsonObject.put("ccKalaCode" , ccKalaCode);
            jsonObject.put("CodeNoeKala" , CodeNoeKala);
            jsonObject.put("ShomarehBach" , ShomarehBach);
            jsonObject.put("TarikhTolid" , TarikhTolid);
            jsonObject.put("TarikhEngheza" , TarikhEngheza);
            jsonObject.put("Tedad3" , Tedad3);
            jsonObject.put("GheymatKharid" , GheymatKharid);
            jsonObject.put("GheymatForosh" , GheymatForosh);
            jsonObject.put("GheymatForoshKhales" , GheymatForoshKhales);
            jsonObject.put("GheymatForoshAsli" , GheymatForoshAsli);
            jsonObject.put("GheymatMasrafKonandeh" , GheymatMasrafKonandeh);
            jsonObject.put("ccElat" , ccElat);
            jsonObject.put("NameElat" , NameElat);
            jsonObject.put("CodeKalaOld" , CodeKalaOld);
            jsonObject.put("NameKala" , NameKala);
            jsonObject.put("ccElamMarjoeeForoshandeh" , ccElamMarjoeeForoshandeh);
            jsonObject.put("ccMarjoeeMamorPakhsh" , ccMarjoeeMamorPakhsh);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("TarikhTolidShamsi" , TarikhTolidShamsi);
            jsonObject.put("ccAnbarGhesmat" , ccAnbarGhesmat);
            jsonObject.put("ccKala" , ccKala);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONArray toJsonArrayKardexSatr(ArrayList<KardexSatrModel> models)
    {
        try
        {
            JSONArray jsonArray = new JSONArray();
            for (KardexSatrModel model : models)
            {
                JSONObject jsonObject = model.toJsonForKardexSatr();
                if (jsonObject != null)
                {
                    jsonArray.put(jsonObject);
                }
            }
            return jsonArray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public JSONObject toJsonForKardexForSend(KardexSatrModel model) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ccKardexSatr", model.getCcKardexSatr());
            jsonObject.put("ccKardex", model.getCcKardex());
            jsonObject.put("ccTaminKonandeh", model.getCcTaminKonandeh());
            jsonObject.put("ccKalaCode", model.getCcKalaCode());
            jsonObject.put("ShomarehBach", model.getShomarehBach());
            jsonObject.put("TarikhTolid", model.getTarikhTolid());
            jsonObject.put("TarikhEngheza", model.getTarikhEngheza());
            jsonObject.put("Tedad3", model.getTedad3());
            jsonObject.put("ccElat", model.getCcElat());
            jsonObject.put("NameElat", model.getNameElat());
            jsonObject.put("CodeNoeKala", model.getCodeNoeKala());
            jsonObject.put("CodeKalaOld", model.getCodeKalaOld());
            jsonObject.put("NameKala", model.getNameKala());
            jsonObject.put("ccElamMarjoeeForoshandeh", model.getCcElamMarjoeeForoshandeh());
            jsonObject.put("ccMarjoeeMamorPakhsh", model.getCcElamMarjoeeForoshandeh());
            jsonObject.put("ccMoshtary", model.getCcMoshtary());
            jsonObject.put("TarikhTolidShamsi", model.getTarikhTolidShamsi());
            jsonObject.put("GheymatKharid", model.getGheymatKharid());
            jsonObject.put("GheymatForosh", model.getGheymatForosh());
            jsonObject.put("GheymatForoshKhales", model.getGheymatForoshKhales());
            jsonObject.put("GheymatMasrafKonandeh", model.getGheymatMasrafKonandeh());
            jsonObject.put("ccAnbarGhesmat", model.getCcAnbarGhesmat());
            jsonObject.put("GheymatForoshAsli", model.getGheymatForoshAsli());
            jsonObject.put("ccKala", model.getCcKala());


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }




}
