package com.saphamrah.Model;

import android.util.Base64;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class PrintFaktorModel {

    @SerializedName("CodeMoshtary")
    @Expose
    private String CodeMoshtary;
    @SerializedName("NameMoshtary")
    @Expose
    private String NameMoshtary;
    @SerializedName("UniqID_Tablet")
    @Expose
    private String UniqID_Tablet;
    @SerializedName("ShomarehFaktor")
    @Expose
    private String ShomarehFaktor;
    @SerializedName("MablaghFaktor")
    @Expose
    private Double MablaghFaktor;
    @SerializedName("Radif")
    @Expose
    private int Radif;


    public String getCodeMoshtary() {
        return CodeMoshtary;
    }

    public void setCodeMoshtary(String codeMoshtary) {
        CodeMoshtary = codeMoshtary;
    }

    public String getNameMoshtary() {
        return NameMoshtary;
    }

    public void setNameMoshtary(String nameMoshtary) {
        NameMoshtary = nameMoshtary;
    }

    public String getUniqID_Tablet() {
        return UniqID_Tablet;
    }

    public void setUniqID_Tablet(String uniqID_Tablet) {
        UniqID_Tablet = uniqID_Tablet;
    }

    public String getShomarehFaktor() {
        return ShomarehFaktor;
    }

    public void setShomarehFaktor(String shomarehFaktor) {
        ShomarehFaktor = shomarehFaktor;
    }

    public Double getMablaghFaktor() {
        return MablaghFaktor;
    }

    public void setMablaghFaktor(Double mablaghFaktor) {
        MablaghFaktor = mablaghFaktor;
    }

    public int getRadif() {
        return Radif;
    }

    public void setRadif(int radif) {
        Radif = radif;
    }

    public String getTABLE_NAME() {
        return "PrintFaktor";
    }

    public String getCOLUMN_CodeMoshtary() {
        return "CodeMoshtary";
    }

    public String getCOLUMN_NameMoshtary() {
        return "NameMoshtary";
    }

    public String getCOLUMN_UniqID_Tablet() {
        return "UniqID_Tablet";
    }

    public String getCOLUMN_ShomarehFaktor() {
        return "ShomarehFaktor";
    }

    public String getCOLUMN_MablaghFaktor() {
        return "MablaghFaktor";
    }

    public String getCOLUMN_Radif() {
        return "Radif";
    }


}
