package com.saphamrah.Model;

import androidx.annotation.NonNull;


import org.json.JSONObject;

public class ElamMarjoeePPCModel
{

    private static final String TABLE_NAME = "ElamMarjoeePPC";
    private static final String COLUMN_ccElamMarjoeePPC = "ccElamMarjoeePPC";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_TarikhElamMarjoee = "TarikhElamMarjoee";
    private static final String COLUMN_Elat = "Elat";
    private static final String COLUMN_TedadAghlamMarjoee = "TedadAghlamMarjoee";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccElamMarjoeePPC() {
        return COLUMN_ccElamMarjoeePPC;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_TarikhElamMarjoee() {
        return COLUMN_TarikhElamMarjoee;
    }
    public static String COLUMN_Elat() {
        return COLUMN_Elat;
    }
    public static String COLUMN_TedadAghlamMarjoee() {
        return COLUMN_TedadAghlamMarjoee;
    }



    private int ccElamMarjoeePPC;
    private long ccDarkhastFaktor;
    private int ccForoshandeh;
    private int ccMoshtary;
    private String TarikhElamMarjoee;
    private String Elat;
    private int TedadAghlamMarjoee;


    public int getCcElamMarjoeePPC() {
        return ccElamMarjoeePPC;
    }

    public void setCcElamMarjoeePPC(int ccElamMarjoeePPC) {
        this.ccElamMarjoeePPC = ccElamMarjoeePPC;
    }

    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getTarikhElamMarjoee() {
        return TarikhElamMarjoee;
    }

    public void setTarikhElamMarjoee(String tarikhElamMarjoee) {
        TarikhElamMarjoee = tarikhElamMarjoee;
    }

    public String getElat() {
        return Elat;
    }

    public void setElat(String elat) {
        Elat = elat;
    }

    public int getTedadAghlamMarjoee() {
        return TedadAghlamMarjoee;
    }

    public void setTedadAghlamMarjoee(int tedadAghlamMarjoee) {
        TedadAghlamMarjoee = tedadAghlamMarjoee;
    }


    public JSONObject toJsonString(int tedadSatr , int ccMarkazForosh , int noeForoshandehMamorPakhsh , int ccAfrad)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccElamMarjoeePPC" , ccElamMarjoeePPC);
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("NoeForoshandeh" , noeForoshandehMamorPakhsh);
            jsonObject.put("ccAfradForoshandeh" , ccAfrad);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("TarikhElamMarjoee" , TarikhElamMarjoee);
            jsonObject.put("Elat" , Elat);
            jsonObject.put("TedadAghlamMarjoee" , tedadSatr);
            //jsonObject.put("elamMarjoeeSatrPPCs" , jsonArrayElamMarjoeeSatrPPC);
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
        return "ccElamMarjoeePPC : " + ccElamMarjoeePPC + " , ccDarkhastFaktor : " + ccDarkhastFaktor +
                " , ccForoshandeh : " + ccForoshandeh + " , ccMoshtary : " + ccMoshtary +
                " , TarikhElamMarjoee : " + TarikhElamMarjoee + " , Elat : " + Elat + " , TedadAghlamMarjoee : " + TedadAghlamMarjoee;
    }


}
