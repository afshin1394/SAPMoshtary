package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElatAdamDarkhastModel
{

    private static final String TABLE_NAME = "ElatAdamDarkhast";
    private static final String COLUMN_ccElatAdamDarkhast_NoeMoshtary = "ccElatAdamDarkhast_NoeMoshtary";
    private static final String COLUMN_ccElatAdamDarkhast = "ccElatAdamDarkhast";
    private static final String COLUMN_NameElatAdamDarkhast = "NameElatAdamDarkhast";
    private static final String COLUMN_CodeSort = "CodeSort";
    private static final String COLUMN_CodeNoe = "CodeNoe";
    private static final String COLUMN_MoshtaryFaal = "MoshtaryFaal";
    private static final String COLUMN_MoshtaryGheyreFaal = "MoshtaryGheyreFaal";
    private static final String COLUMN_GetImage = "GetImage";
    private static final String COLUMN_ccNoeMoshtary = "ccNoeMoshtary";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccElatAdamDarkhast_NoeMoshtary() {
        return COLUMN_ccElatAdamDarkhast_NoeMoshtary;
    }
    public static String COLUMN_ccElatAdamDarkhast() {
        return COLUMN_ccElatAdamDarkhast;
    }
    public static String COLUMN_NameElatAdamDarkhast() {
        return COLUMN_NameElatAdamDarkhast;
    }
    public static String COLUMN_CodeSort() {
        return COLUMN_CodeSort;
    }
    public static String COLUMN_CodeNoe() {
        return COLUMN_CodeNoe;
    }
    public static String COLUMN_MoshtaryFaal() {
        return COLUMN_MoshtaryFaal;
    }
    public static String COLUMN_MoshtaryGheyreFaal() {
        return COLUMN_MoshtaryGheyreFaal;
    }
    public static String COLUMN_GetImage() {
        return COLUMN_GetImage;
    }
    public static String COLUMN_ccNoeMoshtary() {
        return COLUMN_ccNoeMoshtary;
    }




    @SerializedName("ccElatAdamDarkhast_NoeMoshtary")
    @Expose
    private Integer ccElatAdamDarkhast_NoeMoshtary;
    @SerializedName("ccElatAdamDarkhast")
    @Expose
    private Integer ccElatAdamDarkhast;
    @SerializedName("NameElatAdamDarkhast")
    @Expose
    private String NameElatAdamDarkhast;
    @SerializedName("CodeNoe")
    @Expose
    private Integer CodeNoe;
    @SerializedName("CodeSort")
    @Expose
    private Integer CodeSort;
    @SerializedName("GhabelEstefadeh")
    @Expose
    private String GhabelEstefadeh;
    @SerializedName("MoshtaryFaal")
    @Expose
    private Integer MoshtaryFaal;
    @SerializedName("MoshtaryGheyreFaal")
    @Expose
    private Integer MoshtaryGheyreFaal;
    @SerializedName("GetImage")
    @Expose
    private Integer GetImage;
    @SerializedName("ccNoeMoshtary")
    @Expose
    private Integer ccNoeMoshtary;


    public Integer getCcElatAdamDarkhast_NoeMoshtary() {
        return ccElatAdamDarkhast_NoeMoshtary;
    }

    public void setCcElatAdamDarkhast_NoeMoshtary(Integer ccElatAdamDarkhast_NoeMoshtary) {
        this.ccElatAdamDarkhast_NoeMoshtary = ccElatAdamDarkhast_NoeMoshtary;
    }

    public Integer getCcElatAdamDarkhast() {
        return ccElatAdamDarkhast;
    }

    public void setCcElatAdamDarkhast(Integer ccElatAdamDarkhast) {
        this.ccElatAdamDarkhast = ccElatAdamDarkhast;
    }

    public String getNameElatAdamDarkhast() {
        return NameElatAdamDarkhast;
    }

    public void setNameElatAdamDarkhast(String nameElatAdamDarkhast) {
        NameElatAdamDarkhast = nameElatAdamDarkhast;
    }

    public Integer getCodeNoe() {
        return CodeNoe;
    }

    public void setCodeNoe(Integer codeNoe) {
        CodeNoe = codeNoe;
    }

    public Integer getCodeSort() {
        return CodeSort;
    }

    public void setCodeSort(Integer codeSort) {
        CodeSort = codeSort;
    }

    public String getGhabelEstefadeh() {
        return GhabelEstefadeh;
    }

    public void setGhabelEstefadeh(String ghabelEstefadeh) {
        GhabelEstefadeh = ghabelEstefadeh;
    }

    public Integer getMoshtaryFaal() {
        return MoshtaryFaal;
    }

    public void setMoshtaryFaal(Integer moshtaryFaal) {
        MoshtaryFaal = moshtaryFaal;
    }

    public Integer getMoshtaryGheyreFaal() {
        return MoshtaryGheyreFaal;
    }

    public void setMoshtaryGheyreFaal(Integer moshtaryGheyreFaal) {
        MoshtaryGheyreFaal = moshtaryGheyreFaal;
    }

    public Integer getGetImage() {
        return GetImage;
    }

    public void setGetImage(Integer getImage) {
        GetImage = getImage;
    }

    public Integer getCcNoeMoshtary() {
        return ccNoeMoshtary;
    }

    public void setCcNoeMoshtary(Integer ccNoeMoshtary) {
        this.ccNoeMoshtary = ccNoeMoshtary;
    }


    @Override
    public String toString() {
        return "ccElatAdamDarkhast_NoeMoshtary : " + ccElatAdamDarkhast_NoeMoshtary + " , ccElatAdamDarkhast : " + ccElatAdamDarkhast +
                " , NameElatAdamDarkhast : " + NameElatAdamDarkhast + " , CodeNoe : " + CodeNoe + " , CodeSort : " + CodeSort +
                " , GhabelEstefadeh : " + GhabelEstefadeh + " , MoshtaryFaal : " + MoshtaryFaal +
                " , MoshtaryGheyreFaal : " + MoshtaryGheyreFaal + " , GetImage : " + GetImage + " , ccNoeMoshtary : " + ccNoeMoshtary;
    }


}
