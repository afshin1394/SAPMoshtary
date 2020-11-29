package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class MoshtaryTaghiratModel
{

    private static final String TABLE_NAME = "MoshtaryTaghirat";
    private static final String COLUMN_ccMoshtaryTaghirat = "ccMoshtaryTaghirat";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_CodeMely = "CodeMely";
    private static final String COLUMN_ShenasehMely = "ShenasehMely";
    private static final String COLUMN_ccUser = "ccUser";
    private static final String COLUMN_CodeMelyImage = "CodeMelyImage";
    private static final String COLUMN_ExtraProp_IsSendToSql = "ExtraProp_IsSendToSql";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtaryTaghirat() {
        return COLUMN_ccMoshtaryTaghirat;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_CodeMely() {
        return COLUMN_CodeMely;
    }
    public static String COLUMN_ShenasehMely() {
        return COLUMN_ShenasehMely;
    }
    public static String COLUMN_ccUser() {
        return COLUMN_ccUser;
    }
    public static String COLUMN_CodeMelyImage() {
        return COLUMN_CodeMelyImage;
    }
    public static String COLUMN_ExtraProp_IsSendToSql() {
        return COLUMN_ExtraProp_IsSendToSql;
    }



    private int ccMoshtaryTaghirat;
    private int ccMoshtary;
    private String CodeMely;
    private String ShenasehMely;
    private int ccUser;
    private byte[] CodeMelyImage;
    private int ExtraProp_IsSendToSql;



    public int getCcMoshtaryTaghirat() {
        return ccMoshtaryTaghirat;
    }

    public void setCcMoshtaryTaghirat(int ccMoshtaryTaghirat) {
        this.ccMoshtaryTaghirat = ccMoshtaryTaghirat;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getCodeMely() {
        return CodeMely;
    }

    public void setCodeMely(String codeMely) {
        CodeMely = codeMely;
    }

    public String getShenasehMely() {
        return ShenasehMely;
    }

    public void setShenasehMely(String shenasehMely) {
        ShenasehMely = shenasehMely;
    }

    public int getCcUser() {
        return ccUser;
    }

    public void setCcUser(int ccUser) {
        this.ccUser = ccUser;
    }

    public byte[] getCodeMelyImage() {
        return CodeMelyImage;
    }

    public void setCodeMelyImage(byte[] codeMelyImage) {
        CodeMelyImage = codeMelyImage;
    }

    public int getExtraProp_IsSendToSql() {
        return ExtraProp_IsSendToSql;
    }

    public void setExtraProp_IsSendToSql(int extraProp_IsSendToSql) {
        ExtraProp_IsSendToSql = extraProp_IsSendToSql;
    }


    @NonNull
    @Override
    public String toString() {
        return "ccMoshtaryTaghirat : " + ccMoshtaryTaghirat + " , ccMoshtary : " + ccMoshtary + " , CodeMely : " + CodeMely +
                " , ShenasehMely : " + ShenasehMely + " , ccUser : " + ccUser + " , ExtraProp_IsSendToSql : " + ExtraProp_IsSendToSql;
    }


}
