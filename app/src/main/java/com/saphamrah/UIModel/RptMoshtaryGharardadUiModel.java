package com.saphamrah.UIModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RptMoshtaryGharardadUiModel {
    private static final String COLUMN_CodeKala = "CodeKala";
    private static final String COLUMN_MablaghMasrafKonandeh = "MablaghMasrafKonandeh";
    private static final String COLUMN_MablaghForosh = "MablaghForosh";
    private static final String COLUMN_NameKala = "NameKala";
    private static final String COLUMN_ControlMablagh = "ControlMablagh";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";

    public static String COLUMN_CodeKala() {
        return COLUMN_CodeKala;
    }
    public static String COLUMN_MablaghMasrafKonandeh() {
        return COLUMN_MablaghMasrafKonandeh;
    }
    public static String COLUMN_MablaghForosh() {
        return COLUMN_MablaghForosh;
    }
    public static String COLUMN_NameKala() {
        return COLUMN_NameKala;
    }
    public static String COLUMN_ControlMablagh() {
        return COLUMN_ControlMablagh;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }

    @SerializedName("CodeKala")
    @Expose
    private int CodeKala;
    @SerializedName("MablaghMasrafKonandeh")
    @Expose
    private long MablaghMasrafKonandeh;
    @SerializedName("MablaghForosh")
    @Expose
    private long MablaghForosh;
    @SerializedName("NameKala")
    @Expose
    private String NameKala;
    @SerializedName("ControlMablagh")
    @Expose
    private int ControlMablagh;
    @SerializedName("ccKalaCode")
    @Expose
    private int ccKalaCode;

    public int getCodeKala() {
        return CodeKala;
    }

    public void setCodeKala(int codeKala) {
        CodeKala = codeKala;
    }

    public long getMablaghMasrafKonandeh() {
        return MablaghMasrafKonandeh;
    }

    public void setMablaghMasrafKonandeh(long mablaghMasrafKonandeh) {
        MablaghMasrafKonandeh = mablaghMasrafKonandeh;
    }

    public long getMablaghForosh() {
        return MablaghForosh;
    }

    public void setMablaghForosh(long mablaghForosh) {
        MablaghForosh = mablaghForosh;
    }

    public String getNameKala() {
        return NameKala;
    }

    public void setNameKala(String nameKala) {
        NameKala = nameKala;
    }

    public int getControlMablagh() {
        return ControlMablagh;
    }

    public void setControlMablagh(int controlMablagh) {
        ControlMablagh = controlMablagh;
    }

    public int getCcKalaCode() {
        return ccKalaCode;
    }

    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }
}
