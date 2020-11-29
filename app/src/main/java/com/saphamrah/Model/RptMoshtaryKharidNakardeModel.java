package com.saphamrah.Model;

public class RptMoshtaryKharidNakardeModel {


    private static final String TABLE_NAME = "RptMoshtarianKharidNakardeh";
    public static final String  COLUMN_Radif="Radif";
    private static final String COLUMN_ccMoshtary = "CodeMoshtary";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_TarikhFaktor = "TarikhFaktor";

    public static String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public static String getCOLUMN_Radif() {
        return COLUMN_Radif;
    }

    public static String getCOLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }

    public static String getCOLUMN_NameMoshtary() {
        return COLUMN_NameMoshtary;
    }

    public static String getCOLUMN_TarikhFaktor() {
        return COLUMN_TarikhFaktor;
    }


    private int radif;
    private String CodeMoshtary;
    private String NameMoshtary;
    private String TarikhFaktor;


    public int getRadif() {
        return radif;
    }

    public void setRadif(int radif) {
        this.radif = radif;
    }


    public static String getTableName() {
        return TABLE_NAME;
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

    public String getTarikhFaktor() {
        return TarikhFaktor;
    }

    public void setTarikhFaktor(String tarikhFaktor) {
        TarikhFaktor = tarikhFaktor;
    }

    @Override
    public String toString() {
        return "MoshtaryKharidNakardeModel{" +
                "TABLE_NAME='" + TABLE_NAME + '\'' +
                ", COLUMN_ccMoshtary='" + COLUMN_ccMoshtary + '\'' +
                ", COLUMN_NameMoshtary='" + COLUMN_NameMoshtary + '\'' +
                ", COLUMN_TarikhFaktor='" + COLUMN_TarikhFaktor + '\'' +
                ", ccMoshtary=" + CodeMoshtary +
                ", nameMoshtary='" + NameMoshtary + '\'' +
                ", tarikhFaktor='" + TarikhFaktor + '\'' +
                '}';
    }
}
