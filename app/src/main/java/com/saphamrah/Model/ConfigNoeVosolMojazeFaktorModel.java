package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigNoeVosolMojazeFaktorModel {



    @SerializedName("ccConfigNoeVosolMojazeFaktor")
    @Expose
    private int ccConfigNoeVosolMojazeFaktor;
    @SerializedName("CodeNoeVosol_Tablet")
    @Expose
    private int CodeNoeVosol_Tablet;
    @SerializedName("CodeNoeVosolAzMoshtary")
    @Expose
    private int CodeNoeVosolAzMoshtary;
    @SerializedName("CodeVazeiat")
    @Expose
    private int CodeVazeiat;
    @SerializedName("txtNoeVosol")
    @Expose
    private String txtNoeVosol;
    @SerializedName("IsPishDariaft")
    @Expose
    private int IsPishDariaft;

    // setter and getter


    public int getCcConfigNoeVosolMojazeFaktor() {
        return ccConfigNoeVosolMojazeFaktor;
    }

    public void setCcConfigNoeVosolMojazeFaktor(int ccConfigNoeVosolMojazeFaktor) {
        this.ccConfigNoeVosolMojazeFaktor = ccConfigNoeVosolMojazeFaktor;
    }

    public int getCodeNoeVosol_Tablet() {
        return CodeNoeVosol_Tablet;
    }

    public void setCodeNoeVosol_Tablet(int codeNoeVosol_Tablet) {
        CodeNoeVosol_Tablet = codeNoeVosol_Tablet;
    }

    public int getCodeNoeVosolAzMoshtary() {
        return CodeNoeVosolAzMoshtary;
    }

    public void setCodeNoeVosolAzMoshtary(int codeNoeVosolAzMoshtary) {
        CodeNoeVosolAzMoshtary = codeNoeVosolAzMoshtary;
    }

    public int getIsPishDariaft() {
        return IsPishDariaft;
    }

    public void setIsPishDariaft(int isPishDariaft) {
        IsPishDariaft = isPishDariaft;
    }

    public int getCodeVazeiat() {
        return CodeVazeiat;
    }

    public void setCodeVazeiat(int codeVazeiat) {
        CodeVazeiat = codeVazeiat;
    }

    public String getTxtNoeVosol() {
        return txtNoeVosol;
    }

    public void setTxtNoeVosol(String txtNoeVosol) {
        this.txtNoeVosol = txtNoeVosol;
    }

    public String getTABLE_NAME() {
        return "ConfigNoeVosolMojazeFaktor";
    }

    public String getCOLUMN_ccConfig_NoeVosolMojaze_Faktor() {
        return "ccConfigNoeVosolMojazeFaktor";
    }

    public String getCOLUMN_CodeNoeVosol_Tablet() {
        return "CodeNoeVosol_Tablet";
    }

    public String getCOLUMN_CodeNoeVosolAzMoshtary() {
        return "CodeNoeVosolAzMoshtary";
    }

    public String getCOLUMN_CodeVazeiat() {
        return "CodeVazeiat";
    }

    public String getCOLUMN_txtNoeVosol() {
        return "txtNoeVosol";
    }
    public String getCOLUMN_IsPishDariaft() {
        return "IsPishDariaft";
    }


}
