package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DarkhastFaktorKalaPishnahadiModel {
    private String TABLE_NAME = "DarkhastFaktorKalaPishnahadi";

    private String COLUMN_ccDarkhastFaktorKalaPishnahadi = "ccDarkhastFaktorKalaPishnahadi";

    private String COLUMN_ccKalaCode = "ccKalaCode";

    private String COLUMN_MinTarikhFaktor = "MinTarikhFaktor";

    private String COLUMN_tedad = "Tedad";

    private String COLUMN_CcMoshtary = "ccMoshtary";


    public String getTableName(){
        return TABLE_NAME;
    }
    public String getCOLUMN_ccDarkhastFaktorKalaPishnahadi() {
        return COLUMN_ccDarkhastFaktorKalaPishnahadi;
    }
    public String getCOLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }

    public String getCOLUMN_MinTarikhFaktor() {
        return COLUMN_MinTarikhFaktor;
    }

    public String getCOLUMN_tedad() {
        return COLUMN_tedad;
    }
    public String getCOLUMN_ccMoshtary() {
        return COLUMN_CcMoshtary;
    }

    @SerializedName("ccDarkhastFaktorKalaPishnahadi")
    @Expose
    private int ccDarkhastFaktorKalaPishnahadi ;

    @SerializedName("ccKalaCode")
    @Expose
    private int ccKalaCode;

    @SerializedName("MinTarikhFaktor")
    @Expose
    private String minTarikhFaktor;


    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;

    @SerializedName("Tedad")
    @Expose
    private int tedad ;

    public int getCcDarkhastFaktorKalaPishnahadi() {
        return ccDarkhastFaktorKalaPishnahadi;
    }

    public void setCcDarkhastFaktorKalaPishnahadi(int ccDarkhastFaktorKalaPishnahadi) {
        this.ccDarkhastFaktorKalaPishnahadi = ccDarkhastFaktorKalaPishnahadi;
    }

    public int getCcKalaCode() {
        return ccKalaCode;
    }

    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }

    public String getMinTarikhFaktor() {
        return minTarikhFaktor;
    }

    public void setMinTarikhFaktor(String minTarikhFaktor) {
        this.minTarikhFaktor = minTarikhFaktor;
    }

    public int getTedad() {
        return tedad;
    }

    public void setTedad(int tedad) {
        this.tedad = tedad;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }
}