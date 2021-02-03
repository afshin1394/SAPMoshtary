package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoshtaryGharardadKalaModel {


    public MoshtaryGharardadKalaModel() {
    }

    private static final String TABLE_NAME = "MoshtaryGhrardadKala";
    private static final String COLUMN_radif = "radif";
    private static final String COLUMN_ccMoshtaryGharardad = "ccMoshtaryGharardad";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_MablaghForosh = "MablaghForosh";
    private static final String COLUMN_MablaghMasrafKonandeh = "MablaghMasrafKonandeh";
    private static final String COLUMN_ControlMablagh = "ControlMablagh";
    private static final String COLUMN_ExtraPropCcSazmanForosh="ExtraPropCcSazmanForosh";


    public static String TableName() {
        return TABLE_NAME;
    }

    public static String COLUMN_radif() {
        return COLUMN_radif;
    }

    public static String COLUMN_ccMoshtaryGharardad() {
        return COLUMN_ccMoshtaryGharardad;
    }

    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }

    public static String COLUMN_MablaghForosh() {
        return COLUMN_MablaghForosh;
    }

    public static String COLUMN_MablaghMasrafKonandeh() {
        return COLUMN_MablaghMasrafKonandeh;
    }

    public static String COLUMN_ControlMablagh() {
        return COLUMN_ControlMablagh;
    }

    public static String COLUMN_ExtraPropCcSazmanForosh(){ return COLUMN_ExtraPropCcSazmanForosh; }


    @SerializedName("Radif")
    @Expose
    private int Radif;
    @SerializedName("ccMoshtaryGharardad")
    @Expose
    private int ccMoshtaryGharardad;
    @SerializedName("ccKalaCode")
    @Expose
    private int ccKalaCode;
    @SerializedName("MablaghForosh")
    @Expose
    private long mablaghForosh;
    @SerializedName("MablaghMasrafKonandeh")
    @Expose
    private long mablaghMasrafKonandeh;
    @SerializedName("ControlMablagh")
    @Expose
    private int controlMablagh;

    private int extraprop_ccSazmanForosh;


    public int getRadif() {
        return Radif;
    }

    public void setRadif(int radif) {
        Radif = radif;
    }

    public int getCcMoshtaryGharardad() {
        return ccMoshtaryGharardad;
    }

    public void setCcMoshtaryGharardad(int ccMoshtaryGharardad) {
        this.ccMoshtaryGharardad = ccMoshtaryGharardad;
    }

    public int getCcKalaCode() {
        return ccKalaCode;
    }

    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }

    public long getMablaghForosh() {
        return mablaghForosh;
    }

    public void setMablaghForosh(long mablaghForosh) {
        this.mablaghForosh = mablaghForosh;
    }

    public long getMablaghMasrafKonandeh() {
        return mablaghMasrafKonandeh;
    }

    public void setMablaghMasrafKonandeh(long mablaghMasrafKonandeh) {
        this.mablaghMasrafKonandeh = mablaghMasrafKonandeh;
    }

    public int getControlMablagh() {
        return controlMablagh;
    }

    public void setControlMablagh(int controlMablagh) {
        this.controlMablagh = controlMablagh;
    }

    public int getExtraprop_ccSazmanForosh() { return extraprop_ccSazmanForosh; }

    public void setExtraprop_ccSazmanForosh(int extraprop_ccSazmanForosh) { this.extraprop_ccSazmanForosh = extraprop_ccSazmanForosh; }

    @Override
    public String toString() {
        return "MoshtaryGharardadKalaModel{" +
                "Radif=" + Radif +
                ", ccMoshtaryGharardad=" + ccMoshtaryGharardad +
                ", ccKalaCode=" + ccKalaCode +
                ", mablaghForosh=" + mablaghForosh +
                ", mablaghMasrafKonandeh=" + mablaghMasrafKonandeh +
                ", controlMablagh=" + controlMablagh +
                ", extraprop_ccSazmanForosh=" + extraprop_ccSazmanForosh +
                '}';
    }

    //for not fetched Api
    public MoshtaryGharardadKalaModel(int radif) {
        Radif = radif;
    }
}
