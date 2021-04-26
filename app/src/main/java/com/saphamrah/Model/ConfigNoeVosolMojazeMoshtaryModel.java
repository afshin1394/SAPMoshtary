package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigNoeVosolMojazeMoshtaryModel {



    @SerializedName("ccConfigNoeVosolMojazeMoshtary")
    @Expose
    private int ccConfigNoeVosolMojazeMoshtary;
    @SerializedName("CodeNoeVosol_Tablet")
    @Expose
    private int CodeNoeVosol_Tablet;
    @SerializedName("ccDarajeh")
    @Expose
    private int ccDarajeh;
    @SerializedName("ccNoeMoshtary")
    @Expose
    private int ccNoeMoshtary;
    @SerializedName("CodeVazeiat")
    @Expose
    private int CodeVazeiat;
    @SerializedName("txtNoeVosol")
    @Expose
    private String txtNoeVosol;
    @SerializedName("IsPishDariaft")
    @Expose
    private int IsPishDariaft;

    public int getCcConfigNoeVosolMojazeMoshtary() {
        return ccConfigNoeVosolMojazeMoshtary;
    }

    public void setCcConfigNoeVosolMojazeMoshtary(int ccConfigNoeVosolMojazeMoshtary) {
        this.ccConfigNoeVosolMojazeMoshtary = ccConfigNoeVosolMojazeMoshtary;
    }

    public int getCodeNoeVosol_Tablet() {
        return CodeNoeVosol_Tablet;
    }

    public void setCodeNoeVosol_Tablet(int codeNoeVosol_Tablet) {
        CodeNoeVosol_Tablet = codeNoeVosol_Tablet;
    }

    public int getCcDarajeh() {
        return ccDarajeh;
    }

    public void setCcDarajeh(int ccDarajeh) {
        this.ccDarajeh = ccDarajeh;
    }

    public int getCcNoeMoshtary() {
        return ccNoeMoshtary;
    }

    public void setCcNoeMoshtary(int ccNoeMoshtary) {
        this.ccNoeMoshtary = ccNoeMoshtary;
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

    public int getIsPishDariaft() {
        return IsPishDariaft;
    }

    public void setIsPishDariaft(int isPishDariaft) {
        IsPishDariaft = isPishDariaft;
    }

    public String getTABLE_NAME() {
        return "ConfigNoeVosolMojazeMoshtary";
    }

    public String getCOLUMNccConfigNoeVosolMojazeMoshtary() {
        return "ccConfigNoeVosolMojazeMoshtary";
    }
    public String getCOLUMNCodeNoeVosol_Tablet() {
        return "CodeNoeVosol_Tablet";
    }

    public String getCOLUMN_ccDarajeh() {
        return "ccDarajeh";
    }

    public String getCOLUMN_ccNoeMoshtary() {
        return "ccNoeMoshtary";
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
