package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GorohModel
{


    private static final String TABLE_NAME = "Goroh";
    private static final String COLUMN_ccGoroh = "ccGoroh";
    private static final String COLUMN_NameGoroh = "NameGoroh";
    private static final String COLUMN_ccGorohLink = "ccGorohLink";
    private static final String COLUMN_ccRoot = "ccRoot";
    private static final String COLUMN_CodeNoeGoroh = "CodeNoeGoroh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccGoroh() {
        return COLUMN_ccGoroh;
    }
    public static String COLUMN_NameGoroh() {
        return COLUMN_NameGoroh;
    }
    public static String COLUMN_ccGorohLink() {
        return COLUMN_ccGorohLink;
    }
    public static String COLUMN_ccRoot() {
        return COLUMN_ccRoot;
    }
    public static String COLUMN_CodeNoeGoroh() {
        return COLUMN_CodeNoeGoroh;
    }



    @SerializedName("ccGoroh")
    @Expose
    private Integer ccGoroh;
    @SerializedName("NameGoroh")
    @Expose
    private String NameGoroh;
    @SerializedName("ccGorohLink")
    @Expose
    private Integer ccGorohLink;
    @SerializedName("ccRoot")
    @Expose
    private Integer ccRoot;
    @SerializedName("CodeNoeGoroh")
    @Expose
    private Integer CodeNoeGoroh;
    @SerializedName("CodeGoroh")
    @Expose
    private String CodeGoroh;



    public Integer getCcGoroh() {
        return ccGoroh;
    }

    public void setCcGoroh(Integer ccGoroh) {
        this.ccGoroh = ccGoroh;
    }

    public String getNameGoroh() {
        return NameGoroh;
    }

    public void setNameGoroh(String nameGoroh) {
        NameGoroh = nameGoroh;
    }

    public Integer getCcGorohLink() {
        return ccGorohLink;
    }

    public void setCcGorohLink(Integer ccGorohLink) {
        this.ccGorohLink = ccGorohLink;
    }

    public Integer getCcRoot() {
        return ccRoot;
    }

    public void setCcRoot(Integer ccRoot) {
        this.ccRoot = ccRoot;
    }

    public Integer getCodeNoeGoroh() {
        return CodeNoeGoroh;
    }

    public void setCodeNoeGoroh(Integer codeNoeGoroh) {
        CodeNoeGoroh = codeNoeGoroh;
    }

    public String getCodeGoroh() {
        return CodeGoroh;
    }

    public void setCodeGoroh(String codeGoroh) {
        CodeGoroh = codeGoroh;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "GorohModel{" +
                "ccGoroh=" + ccGoroh +
                ", NameGoroh='" + NameGoroh + '\'' +
                ", ccGorohLink=" + ccGorohLink +
                ", ccRoot=" + ccRoot +
                ", CodeNoeGoroh=" + CodeNoeGoroh +
                ", CodeGoroh=" + CodeGoroh +
                '}';
    }
}
