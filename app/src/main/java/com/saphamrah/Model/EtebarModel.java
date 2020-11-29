package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EtebarModel
{

    private static final String TABLE_NAME = "Etebar";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_RialBargashty = "RialBargashty";
    private static final String COLUMN_TedadBargashty = "TedadBargashty";
    private static final String COLUMN_ModatBargashty = "ModatBargashty";
    private static final String COLUMN_flag_SabtNaghd = "flag_SabtNaghd";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_RialBargashty() {
        return COLUMN_RialBargashty;
    }
    public static String COLUMN_TedadBargashty() {
        return COLUMN_TedadBargashty;
    }
    public static String COLUMN_ModatBargashty() {
        return COLUMN_ModatBargashty;
    }
    public static String COLUMN_flag_SabtNaghd() {
        return COLUMN_flag_SabtNaghd;
    }




    @SerializedName("ccForoshandeh")
    @Expose
    private int ccForoshandeh;
    @SerializedName("RialBargashty")
    @Expose
    private long rialBargashty;
    @SerializedName("TedadBargashty")
    @Expose
    private int tedadBargashty;
    @SerializedName("ModatBargashty")
    @Expose
    private int modatBargashty;
    @SerializedName("flag_SabtNaghd")
    @Expose
    private int flagSabtNaghd;


    public int getCcForoshandeh() {
        return ccForoshandeh;
    }
    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public long getRialBargashty() {
        return rialBargashty;
    }
    public void setRialBargashty(long rialBargashty) {
        this.rialBargashty = rialBargashty;
    }

    public int getTedadBargashty() {
        return tedadBargashty;
    }
    public void setTedadBargashty(int tedadBargashty) {
        this.tedadBargashty = tedadBargashty;
    }

    public int getModatBargashty() {
        return modatBargashty;
    }
    public void setModatBargashty(int modatBargashty) {
        this.modatBargashty = modatBargashty;
    }

    public int getFlagSabtNaghd() {
        return flagSabtNaghd;
    }
    public void setFlagSabtNaghd(int flagSabtNaghd) {
        this.flagSabtNaghd = flagSabtNaghd;
    }




}
