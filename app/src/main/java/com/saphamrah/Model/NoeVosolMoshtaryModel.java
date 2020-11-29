package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoeVosolMoshtaryModel {

    @SerializedName("ccMarkazSazmanForosh")
    @Expose
    private int ccMarkazSazmanForosh;

    @SerializedName("ccNoeMoshtary")
    @Expose
    private int ccNoeMoshtary;

    @SerializedName("ccDarajeh")
    @Expose
    private int ccDarajeh;

    @SerializedName("CodeNoeVosolAzMoshtary")
    @Expose
    private int CodeNoeVosolAzMoshtary;

    @SerializedName("CodeNoeVosol")
    @Expose
    private int CodeNoeVosol;

    @SerializedName("NameNoeVosol")
    @Expose
    private String NameNoeVosol;

    @SerializedName("NameNoeVosolAzMoshtary")
    @Expose
    private String NameNoeVosolAzMoshtary;


    public int getCcMarkazSazmanForosh() {
        return ccMarkazSazmanForosh;
    }

    public void setCcMarkazSazmanForosh(int ccMarkazSazmanForosh) {
        this.ccMarkazSazmanForosh = ccMarkazSazmanForosh;
    }

    public int getCcNoeMoshtary() {
        return ccNoeMoshtary;
    }

    public void setCcNoeMoshtary(int ccNoeMoshtary) {
        this.ccNoeMoshtary = ccNoeMoshtary;
    }

    public int getCcDarajeh() {
        return ccDarajeh;
    }

    public void setCcDarajeh(int ccDarajeh) {
        this.ccDarajeh = ccDarajeh;
    }

    public int getCodeNoeVosolAzMoshtary() {
        return CodeNoeVosolAzMoshtary;
    }

    public void setCodeNoeVosolAzMoshtary(int codeNoeVosolAzMoshtary) {
        CodeNoeVosolAzMoshtary = codeNoeVosolAzMoshtary;
    }

    public int getCodeNoeVosol() {
        return CodeNoeVosol;
    }

    public void setCodeNoeVosol(int codeNoeVosol) {
        CodeNoeVosol = codeNoeVosol;
    }

    public String getNameNoeVosol() {
        return NameNoeVosol;
    }

    public void setNameNoeVosol(String nameNoeVosol) {
        NameNoeVosol = nameNoeVosol;
    }

    public String getNameNoeVosolAzMoshtary() {
        return NameNoeVosolAzMoshtary;
    }

    public void setNameNoeVosolAzMoshtary(String nameNoeVosolAzMoshtary) {
        NameNoeVosolAzMoshtary = nameNoeVosolAzMoshtary;
    }



    public String getCOLUM_TABLE_NAME(){
        return "NoeVosolMoshtary";
    }

    public String getCOLUMN_ccMarkazSazmanForosh() {
        return "ccMarkazSazmanForosh";
    }
    public String getCOLUMN_ccNoeMoshtary() {
        return "ccNoeMoshtary";
    }
    public String getCOLUMN_ccDarajeh() {
        return "ccDarajeh";
    }
    public String getCOLUMN_CodeNoeVosolAzMoshtary() {
        return "CodeNoeVosolAzMoshtary";
    }
    public String getCOLUMN_CodeNoeVosol() {
        return "CodeNoeVosol";
    }
    public String getCOLUMN_NameNoeVosol() {
        return "NameNoeVosol";
    }
    public String getCOLUMN_NameNoeVosolAzMoshtary() {
        return "NameNoeVosolAzMoshtary";
    }


    @NonNull
    @Override
    public String toString() {
        return "NoeVosolMoshtaryModel{" +
                "ccMarkazSazmanForosh=" + ccMarkazSazmanForosh +
                ", ccNoeMoshtary=" + ccNoeMoshtary +
                ", ccDarajeh='" + ccDarajeh + '\'' +
                ", CodeNoeVosolAzMoshtary='" + CodeNoeVosolAzMoshtary + '\'' +
                ", CodeNoeVosol='" + CodeNoeVosol + '\'' +
                ", NameNoeVosol=" + NameNoeVosol +
                ", NameNoeVosolAzMoshtary=" + NameNoeVosolAzMoshtary +
                '}';
    }
}
