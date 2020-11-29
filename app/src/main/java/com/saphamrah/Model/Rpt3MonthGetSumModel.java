package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rpt3MonthGetSumModel {
    private String TABLE_NAME = "RptThreeMonthPurchase";
    private String COLUMN_ccMoshtary = "ccMoshtary";
    private String COLUMN_CodeMoshtary = "CodeMoshtary";
    private String COLUMN_NameMoshtary = "NameMoshtary";
    private String COLUMN_summablagh = "summablagh";
    private String COLUMN_tedad = "tedad";
    
    
    
    public String getCOLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public String getCOLUMN_CodeMoshtary() {
        return COLUMN_CodeMoshtary;
    }
    public String getCOLUMN_NameMoshtary() {
        return COLUMN_NameMoshtary;
    }
    public String getCOLUMN_summablagh() {
        return COLUMN_summablagh;
    }
    public String getCOLUMN_tedad() {
        return COLUMN_tedad;
    }

    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary ;
    @SerializedName("CodeMoshtary")
    @Expose
    private String CodeMoshtary;
    @SerializedName("NameMoshtary")
    @Expose
    private String NameMoshtary;
    @SerializedName("summablagh")
    @Expose
    private long sumMablagh ;
    @SerializedName("ccMoshtary")
    @Expose
    private int tedad ;

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getCodeMoshtary() {
        return CodeMoshtary;
    }

    public void setCodeMoshtary(String codeMoshtary) {
        CodeMoshtary = codeMoshtary;
    }

    public String getNameMoshtary() {
        return NameMoshtary;
    }

    public void setNameMoshtary(String nameMoshtary) {
        NameMoshtary = nameMoshtary;
    }

    public long getSumMablagh() {
        return sumMablagh;
    }

    public void setSumMablagh(long sumMablagh) {
        this.sumMablagh = sumMablagh;
    }

    public int getTedad() {
        return tedad;
    }

    public void setTedad(int tedad) {
        this.tedad = tedad;
    }
}
