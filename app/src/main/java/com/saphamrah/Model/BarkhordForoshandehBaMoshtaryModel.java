package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BarkhordForoshandehBaMoshtaryModel
{

    private static final String TABLE_NAME = "BarkhordForoshandehBaMoshtary";
    private static final String COLUMN_ccBarkhordForoshandeh = "ccBarkhordForoshandeh";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_Tarikh = "Tarikh";
    private static final String COLUMN_Tozihat = "Tozihat";
    private static final String COLUMN_IsFavorite = "IsFavorite";
    private static final String COLUMN_ExtraProp_IsOld = "ExtraProp_IsOld";
    private static final String COLUMN_ExtraProp_Recent = "ExtraProp_Recent";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccBarkhordForoshandeh() {
        return COLUMN_ccBarkhordForoshandeh;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_Tarikh() {
        return COLUMN_Tarikh;
    }
    public static String COLUMN_Tozihat() {
        return COLUMN_Tozihat;
    }
    public static String COLUMN_IsFavorite() {
        return COLUMN_IsFavorite;
    }
    public static String COLUMN_ExtraProp_IsOld() {
        return COLUMN_ExtraProp_IsOld;
    }
    public static String COLUMN_ExtraProp_Recent() {
        return COLUMN_ExtraProp_Recent;
    }







    @SerializedName("ccBarkhordForoshandeh")
    @Expose
    private Integer ccBarkhordForoshandeh;
    @SerializedName("ccForoshandeh")
    @Expose
    private Integer ccForoshandeh;
    @SerializedName("ccMoshtary")
    @Expose
    private Integer ccMoshtary;
    @SerializedName("Tarikh")
    @Expose
    private String Tarikh;
    @SerializedName("Tozihat")
    @Expose
    private String Tozihat;
    @SerializedName("IsFavorite")
    @Expose
    private int IsFavorite;

    private int ExtraProp_IsOld;

    private int ExtraProp_IsRecent;



    public Integer getCcBarkhordForoshandeh() {
        return ccBarkhordForoshandeh;
    }

    public void setCcBarkhordForoshandeh(Integer ccBarkhordForoshandeh) {
        this.ccBarkhordForoshandeh = ccBarkhordForoshandeh;
    }

    public Integer getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(Integer ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public Integer getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(Integer ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getTarikh() {
        return Tarikh;
    }

    public void setTarikh(String tarikh) {
        Tarikh = tarikh;
    }

    public String getTozihat() {
        return Tozihat;
    }

    public void setTozihat(String tozihat) {
        Tozihat = tozihat;
    }

    public int getExtraProp_IsOld() {
        return ExtraProp_IsOld;
    }

    public void setExtraProp_IsOld(int extraProp_IsOld) {
        ExtraProp_IsOld = extraProp_IsOld;
    }


    public int getIsFavorite() {
        return IsFavorite;
    }


    public int ExtraProp_IsRecent() {
        return ExtraProp_IsRecent;
    }

    public void setExtraProp_IsRecent(int extraProp_IsRecent) {
        ExtraProp_IsRecent = extraProp_IsRecent;
    }

    public void setIsFavorite(int isFavorite) {
        IsFavorite = isFavorite;
    }

    public String toJsonString(BarkhordForoshandehBaMoshtaryModel model)
    {
        String tarikh = model.getTarikh() == null ? "" : model.getTarikh();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(COLUMN_ccBarkhordForoshandeh() , model.getCcBarkhordForoshandeh());
        jsonObject.addProperty(COLUMN_ccForoshandeh() , model.getCcForoshandeh());
        jsonObject.addProperty(COLUMN_ccMoshtary() , model.getCcMoshtary());
        jsonObject.addProperty(COLUMN_Tarikh() , tarikh);
        jsonObject.addProperty(COLUMN_Tozihat() , model.getTozihat());
        jsonObject.addProperty(COLUMN_IsFavorite() , model.getIsFavorite());
        return jsonObject.toString();
    }


    @Override
    public String toString() {
        return "BarkhordForoshandehBaMoshtaryModel{" +
                "ccBarkhordForoshandeh=" + ccBarkhordForoshandeh +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccMoshtary=" + ccMoshtary +
                ", Tarikh='" + Tarikh + '\'' +
                ", Tozihat='" + Tozihat + '\'' +
                ", IsFavorite=" + IsFavorite +
                ", ExtraProp_IsOld=" + ExtraProp_IsOld +
                ", ExtraProp_IsRecent=" + ExtraProp_IsRecent +
                '}';
    }
}
