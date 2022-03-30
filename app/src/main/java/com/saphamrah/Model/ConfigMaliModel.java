package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigMaliModel {

    private static final String TABLE_NAME = "ConfigMali";
    private static final String COLUMN_ccConfigMali = "ccConfigMali";
    private static final String COLUMN_ccMarkazSazmanForosh = "ccMarkazSazmanForosh";
    private static final String COLUMN_TedadRoozMojozeTarikhCheckMoshtary = "TedadRoozMojozeTarikhCheckMoshtary";
    private static final String COLUMN_DarsadMojozeTakhirCheckMoshtary = "DarsadMojozeTakhirCheckMoshtary";
    private static final String COLUMN_DarsadMojozeTajilCheckMoshtary = "DarsadMojozeTajilCheckMoshtary";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccConfigMali() {
        return COLUMN_ccConfigMali;
    }
    public static String COLUMN_ccMarkazSazmanForosh() { return COLUMN_ccMarkazSazmanForosh; }
    public static String COLUMN_TedadRoozMojozeTarikhCheckMoshtary() {
        return COLUMN_TedadRoozMojozeTarikhCheckMoshtary;
    }
    public static String COLUMN_DarsadMojozeTakhirCheckMoshtary() {
        return COLUMN_DarsadMojozeTakhirCheckMoshtary;
    }
    public static String COLUMN_DarsadMojozeTajilCheckMoshtary() {
        return COLUMN_DarsadMojozeTajilCheckMoshtary;
    }
    @SerializedName("ccConfigMali")
    @Expose
    int ccConfigMali;
    @SerializedName("ccMarkazSazmanForosh")
    @Expose
    int ccMarkazSazmanForosh;
    @SerializedName("TedadRoozMojozeTarikhCheckMoshtary")
    @Expose
    int TedadRoozMojozeTarikhCheckMoshtary;
    @SerializedName("DarsadMojozeTakhirCheckMoshtary")
    @Expose
    int DarsadMojozeTakhirCheckMoshtary;
    @SerializedName("DarsadMojozeTajilCheckMoshtary")
    @Expose
    int DarsadMojozeTajilCheckMoshtary;


    public int getCcConfigMali() {
        return ccConfigMali;
    }

    public void setCcConfigMali(int ccConfigMali) {
        this.ccConfigMali = ccConfigMali;
    }

    public int getCcMarkazSazmanForosh() {
        return ccMarkazSazmanForosh;
    }

    public void setCcMarkazSazmanForosh(int ccMarkazSazmanForosh) {
        this.ccMarkazSazmanForosh = ccMarkazSazmanForosh;
    }

    public int getTedadRoozMojozeTarikhCheckMoshtary() {
        return TedadRoozMojozeTarikhCheckMoshtary;
    }

    public void setTedadRoozMojozeTarikhCheckMoshtary(int tedadRoozMojozeTarikhCheckMoshtary) {
        TedadRoozMojozeTarikhCheckMoshtary = tedadRoozMojozeTarikhCheckMoshtary;
    }

    public int getDarsadMojozeTakhirCheckMoshtary() {
        return DarsadMojozeTakhirCheckMoshtary;
    }

    public void setDarsadMojozeTakhirCheckMoshtary(int darsadMojozeTakhirCheckMoshtary) {
        DarsadMojozeTakhirCheckMoshtary = darsadMojozeTakhirCheckMoshtary;
    }

    public int getDarsadMojozeTajilCheckMoshtary() {
        return DarsadMojozeTajilCheckMoshtary;
    }

    public void setDarsadMojozeTajilCheckMoshtary(int darsadMojozeTajilCheckMoshtary) {
        DarsadMojozeTajilCheckMoshtary = darsadMojozeTajilCheckMoshtary;
    }
}
