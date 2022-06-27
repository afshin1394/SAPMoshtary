package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmvalModel
{

    private static final String TABLE_NAME = "Amval";
    private static final String COLUMN_ccAmval = "ccAmval";
    private static final String COLUMN_NoeAmval = "NoeAmval";
    private static final String COLUMN_NameAmval = "NameAmval";
    private static final String COLUMN_Tol = "Tol";
    private static final String COLUMN_Arz = "Arz";
    private static final String COLUMN_Ertefa = "Ertefa";
    private static final String COLUMN_Temperature = "Temperature";
    private static final String COLUMN_Barcode = "Barcode";
    private static final String COLUMN_ExtraProp_Barcode = "ExtraProp_Barcode";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccSazmanForosh = "ccSazmanForosh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccAmval() {
        return COLUMN_ccAmval;
    }
    public static String COLUMN_NoeAmval() {
        return COLUMN_NoeAmval;
    }
    public static String COLUMN_NameAmval() {
        return COLUMN_NameAmval;
    }
    public static String COLUMN_Tol() {
        return COLUMN_Tol;
    }
    public static String COLUMN_Arz() {
        return COLUMN_Arz;
    }
    public static String COLUMN_Ertefa() {
        return COLUMN_Ertefa;
    }

    public static String COLUMN_Temperature() {
        return COLUMN_Temperature;
    }
    public static String COLUMN_Barcode() {
        return COLUMN_Barcode;
    }
    public static String COLUMN_ExtraProp_Barcode() {
        return COLUMN_ExtraProp_Barcode;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccSazmanForosh () {
        return COLUMN_ccSazmanForosh;
    }


    @SerializedName("ccAmval")
    @Expose
    private Integer ccAmval;
    @SerializedName("NoeAmval")
    @Expose
    private Integer NoeAmval;
    @SerializedName("NameAmval")
    @Expose
    private String NameAmval;
    @SerializedName("Tol")
    @Expose
    private Integer Tol;
    @SerializedName("Arz")
    @Expose
    private Integer Arz;
    @SerializedName("Ertefa")
    @Expose
    private Integer Ertefa;
    @SerializedName("Temperature")
    @Expose
    private Integer Temperature;
    @SerializedName("Barcode")
    @Expose
    private String Barcode;
    @SerializedName("ccMoshtary")
    @Expose
    private Integer ccMoshtary;
    @SerializedName("ccSazmanForosh")
    @Expose
    private Integer ccSazmanForosh;

    private String ExtraProp_Barcode;


    public Integer getCcAmval() {
        return ccAmval;
    }

    public void setCcAmval(Integer ccAmval) {
        this.ccAmval = ccAmval;
    }

    public Integer getNoeAmval() {
        return NoeAmval;
    }

    public void setNoeAmval(Integer noeAmval) {
        NoeAmval = noeAmval;
    }

    public String getNameAmval() {
        return NameAmval;
    }

    public void setNameAmval(String nameAmval) {
        NameAmval = nameAmval;
    }

    public Integer getTol() {
        return Tol;
    }

    public void setTol(Integer tol) {
        Tol = tol;
    }

    public Integer getArz() {
        return Arz;
    }

    public void setArz(Integer arz) {
        Arz = arz;
    }

    public Integer getErtefa() {
        return Ertefa;
    }

    public void setErtefa(Integer ertefa) {
        Ertefa = ertefa;
    }

    public Integer getTemperature() {
        return Temperature;
    }

    public void setTemperature(Integer temperature) {
        Temperature = temperature;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public Integer getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(Integer ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }
    public Integer getCcSazmanForosh() {
        return ccSazmanForosh;
    }

    public void setCcSazmanForosh(Integer ccSazmanForosh) {
        this.ccSazmanForosh = ccSazmanForosh;
    }

    public String getExtraProp_Barcode() {
        return ExtraProp_Barcode;
    }

    public void setExtraProp_Barcode(String extraProp_Barcode) {
        ExtraProp_Barcode = extraProp_Barcode;
    }

    @Override
    public String toString() {
        return "AmvalModel{" +
                "ccAmval=" + ccAmval +
                ", NoeAmval=" + NoeAmval +
                ", NameAmval='" + NameAmval + '\'' +
                ", Tol=" + Tol +
                ", Arz=" + Arz +
                ", Ertefa=" + Ertefa +
                ", Temperature=" + Temperature +
                ", Barcode='" + Barcode + '\'' +
                ", ccMoshtary=" + ccMoshtary +
                ", ccSazmanForosh=" + ccSazmanForosh +
                '}';
    }
}
