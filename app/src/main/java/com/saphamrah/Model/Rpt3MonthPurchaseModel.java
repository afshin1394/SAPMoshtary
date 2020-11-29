package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rpt3MonthPurchaseModel
{

    @SerializedName("summablagh")
    @Expose
    private long sumMablagh ;
    @SerializedName("tedad")
    @Expose
    private int tedad ;

    // Gson
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("ccDarkhastFaktor")
    @Expose
    private int ccDarkhastFaktor;
    @SerializedName("CodeMoshtary")
    @Expose
    private String CodeMoshtary;
    @SerializedName("NameMoshtary")
    @Expose
    private String NameMoshtary;
    @SerializedName("ShomarehFaktor")
    @Expose
    private String ShomarehFaktor;
    @SerializedName("TarikhFaktor")
    @Expose
    private String TarikhFaktor;
    @SerializedName("MablaghKhalesFaktor")
    @Expose
    private long MablaghKhalesFaktor;
    @SerializedName("MarjoeeKamel")
    @Expose
    private int MarjoeeKamel;



    // DB Model
    private String TABLE_NAME = "RptThreeMonthPurchase";
    private String COLUMN_ccMoshtary = "ccMoshtary";
    private String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private String COLUMN_CodeMoshtary = "CodeMoshtary";
    private String COLUMN_NameMoshtary = "NameMoshtary";
    private String COLUMN_ShomarehFaktor = "ShomarehFaktor";
    private String COLUMN_TarikhFaktor = "TarikhFaktor";
    private String COLUMN_MablaghKhalesFaktor = "MablaghKhalesFaktor";
    private String COLUMN_MarjoeeKamel = "MarjoeeKamel";
    // get sum

    private String COLUMN_summablagh = "summablagh";
    private String COLUMN_tedad = "tedad";


    public String getTableName(){
        return TABLE_NAME;
    }
    public String getCOLUMN_summablagh() {
        return COLUMN_summablagh;
    }
    public String getCOLUMN_tedad() {
        return COLUMN_tedad;
    }

    public String getCOLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }

    public String getCOLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }

    public String getCOLUMN_CodeMoshtary() {
        return COLUMN_CodeMoshtary;
    }

    public String getCOLUMN_NameMoshtary() {
        return COLUMN_NameMoshtary;
    }

    public String getCOLUMN_ShomarehFaktor() {
        return COLUMN_ShomarehFaktor;
    }

    public String getCOLUMN_TarikhFaktor() {
        return COLUMN_TarikhFaktor;
    }

    public String getCOLUMN_MablaghKhalesFaktor() {
        return COLUMN_MablaghKhalesFaktor;
    }

    public String getCOLUMN_MarjoeeKamel() {
        return COLUMN_MarjoeeKamel;
    }

    /*  public static String TableName() {
            return TABLE_NAME;
        }
        public static String COLUMN_ccMoshtary() {
            return COLUMN_ccMoshtary;
        }
        public static String COLUMN_ccDarkhastFaktor() {
            return COLUMN_ccDarkhastFaktor;
        }
        public static String COLUMN_CodeMoshtary() {
            return COLUMN_CodeMoshtary;
        }
        public static String COLUMN_NameMoshtary() {
            return COLUMN_NameMoshtary;
        }
        public static String COLUMN_ShomarehFaktor() {
            return COLUMN_ShomarehFaktor;
        }
        public static String COLUMN_TarikhFaktor() {
            return COLUMN_TarikhFaktor;
        }
        public static String COLUMN_MablaghKhalesFaktor() {
            return COLUMN_MablaghKhalesFaktor;
        }
        public static String COLUMN_MarjoeeKamel() {
            return COLUMN_MarjoeeKamel;
        }
    */




    //set and get
    public String getTABLE_NAME() {
        return TABLE_NAME;
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
    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(int ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
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

    public String getShomarehFaktor() {
        return ShomarehFaktor;
    }

    public void setShomarehFaktor(String shomarehFaktor) {
        ShomarehFaktor = shomarehFaktor;
    }

    public String getTarikhFaktor() {
        return TarikhFaktor;
    }

    public void setTarikhFaktor(String tarikhFaktor) {
        TarikhFaktor = tarikhFaktor;
    }

    public long getMablaghKhalesFaktor() {
        return MablaghKhalesFaktor;
    }

    public void setMablaghKhalesFaktor(long mablaghKhalesFaktor) {
        MablaghKhalesFaktor = mablaghKhalesFaktor;
    }

    public int getMarjoeeKamel() {
        return MarjoeeKamel;
    }

    public void setMarjoeeKamel(int marjoeeKamel) {
        MarjoeeKamel = marjoeeKamel;
    }
}
