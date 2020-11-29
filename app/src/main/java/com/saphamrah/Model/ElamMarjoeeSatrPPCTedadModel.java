package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class ElamMarjoeeSatrPPCTedadModel
{

    private static final String TABLE_NAME = "ElamMarjoeeSatrPPC_Tedad";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_TedadSatr = "TedadSatr";
    private static final String COLUMN_ccElamMarjoeeSatr_Tedad = "ccElamMarjoeeSatr_Tedad";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_TedadSatr() {
        return COLUMN_TedadSatr;
    }
    public static String COLUMN_ccElamMarjoeeSatr_Tedad() {
        return COLUMN_ccElamMarjoeeSatr_Tedad;
    }





    private Long ccDarkhastFaktor;
    private Integer TedadSatr;
    private Integer ccElamMarjoeeSatr_Tedad;


    public Long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(Long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public Integer getTedadSatr() {
        return TedadSatr;
    }

    public void setTedadSatr(Integer tedadSatr) {
        TedadSatr = tedadSatr;
    }

    public Integer getCcElamMarjoeeSatr_Tedad() {
        return ccElamMarjoeeSatr_Tedad;
    }

    public void setCcElamMarjoeeSatr_Tedad(Integer ccElamMarjoeeSatr_Tedad) {
        this.ccElamMarjoeeSatr_Tedad = ccElamMarjoeeSatr_Tedad;
    }


    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccElamMarjoeeSatr_Tedad" , ccElamMarjoeeSatr_Tedad);
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("TedadSatr" , TedadSatr);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }


    @NonNull
    @Override
    public String toString() {
        return "ccDarkhastFaktor : " + ccDarkhastFaktor + " , TedadSatr : " + TedadSatr + " , ccElamMarjoeeSatr_Tedad : " + ccElamMarjoeeSatr_Tedad;
    }
}
