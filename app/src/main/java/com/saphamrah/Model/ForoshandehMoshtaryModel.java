package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class ForoshandehMoshtaryModel
{

    private static final String TABLE_NAME = "ForoshandehMoshtary";
    private static final String COLUMN_ccForoshandehMoshtary = "ccForoshandehMoshtary";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccMasir = "ccMasir";
    private static final String COLUMN_Olaviat = "Olaviat";
    private static final String COLUMN_SaatVisitAz = "SaatVisitAz";
    private static final String COLUMN_SaatVisitTa = "SaatVisitTa";



    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccForoshandehMoshtary() {
        return COLUMN_ccForoshandehMoshtary;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccMasir() {
        return COLUMN_ccMasir;
    }
    public static String COLUMN_Olaviat() {
        return COLUMN_Olaviat;
    }
    public static String COLUMN_SaatVisitAz() {
        return COLUMN_SaatVisitAz;
    }
    public static String COLUMN_SaatVisitTa() {
        return COLUMN_SaatVisitTa;
    }


    @SerializedName("ccForoshandehMoshtary")
    @Expose
    private int ccForoshandehMoshtary;
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("ccMasir")
    @Expose
    private int ccMasir;
    @SerializedName("Olaviat")
    @Expose
    private double Olaviat;
    @SerializedName("SaatVisitAz")
    @Expose
    private String SaatVisitAz;
    @SerializedName("SaatVisitTa")
    @Expose
    private String SaatVisitTa;


    public void setCcForoshandehMoshtary(int ccForoshandehMoshtary){
        this.ccForoshandehMoshtary = ccForoshandehMoshtary;
    }

    public int getCcForoshandehMoshtary(){
        return this.ccForoshandehMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcMoshtary(){
        return this.ccMoshtary;
    }

    public void setCcMasir(int ccMasir){
        this.ccMasir = ccMasir;
    }

    public int getCcMasir(){
        return this.ccMasir;
    }

    public void setOlaviat(double Olaviat){
        this.Olaviat = Olaviat;
    }

    public double getOlaviat(){
        return this.Olaviat;
    }

    public void setSaatVisitAz(String SaatVisitAz){
        this.SaatVisitAz = SaatVisitAz;
    }

    public String getSaatVisitAz(){
        return this.SaatVisitAz;
    }

    public void setSaatVisitTa(String SaatVisitTa){
        this.SaatVisitTa = SaatVisitTa;
    }

    public String getSaatVisitTa(){
        return this.SaatVisitTa;
    }



    public String toJsonString(int ccForoshandeh)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(COLUMN_ccForoshandehMoshtary() , ccForoshandehMoshtary);
        jsonObject.addProperty(COLUMN_ccMoshtary() , ccMoshtary);
        jsonObject.addProperty(COLUMN_ccMasir() , ccMasir);
        jsonObject.addProperty(COLUMN_Olaviat() , Olaviat);
        jsonObject.addProperty(COLUMN_SaatVisitAz() , SaatVisitAz);
        jsonObject.addProperty(COLUMN_SaatVisitTa() , SaatVisitTa);
        jsonObject.addProperty("ccForoshandeh" , ccForoshandeh);
        return jsonObject.toString();
    }

    public JSONObject toJsonObject(int ccForoshandeh)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("ccMasir" , ccMasir);
            jsonObject.put("SaatVisitAz" , SaatVisitAz);
            jsonObject.put("SaatVisitTa" , SaatVisitTa);
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
        return "ccForoshandehMoshtary : " + ccForoshandehMoshtary + " , ccMoshtary : " + ccMoshtary + " , ccMasir : " + ccMasir +
                " , Olaviat : " + Olaviat + " , SaatVisitAz : " + SaatVisitAz + " , SaatVisitTa : " + SaatVisitTa;
    }


}
