package com.saphamrah.Model;

public class KardexAnbarakModel
{

    private static final String TABLE_NAME = "KardexAnbarak";
    private static final String COLUMN_ccKardexAnbarak = "ccKardexAnbarak";
    private static final String COLUMN_ccAnbarak = "ccAnbarak";
    private static final String COLUMN_CodeNoeForm = "CodeNoeForm";
    private static final String COLUMN_CodeNoeAmalyat = "CodeNoeAmalyat";
    private static final String COLUMN_ccRefrence = "ccRefrence";
    private static final String COLUMN_CodeVazeiat = "CodeVazeiat";
    private static final String COLUMN_ccUser = "ccUser";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ExtraProp_IsSend = "ExtraProp_IsSend";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKardexAnbarak() {
        return COLUMN_ccKardexAnbarak;
    }
    public static String COLUMN_ccAnbarak() {
        return COLUMN_ccAnbarak;
    }
    public static String COLUMN_CodeNoeForm() {
        return COLUMN_CodeNoeForm;
    }
    public static String COLUMN_CodeNoeAmalyat() {
        return COLUMN_CodeNoeAmalyat;
    }
    public static String COLUMN_ccRefrence() {
        return COLUMN_ccRefrence;
    }
    public static String COLUMN_CodeVazeiat() {
        return COLUMN_CodeVazeiat;
    }
    public static String COLUMN_ccUser() {
        return COLUMN_ccUser;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ExtraProp_IsSend() {
        return COLUMN_ExtraProp_IsSend;
    }


    private long ccKardexAnbarak;
    private int ccAnbarak;
    private int CodeNoeForm;
    private int CodeNoeAmalyat;
    private long ccRefrence;
    private int CodeVazeiat;
    private int ccUser;
    private int ccMoshtary;
    private int ExtraProp_IsSend;


    public long getCcKardexAnbarak() {
        return ccKardexAnbarak;
    }

    public void setCcKardexAnbarak(long ccKardexAnbarak) {
        this.ccKardexAnbarak = ccKardexAnbarak;
    }

    public int getCcAnbarak() {
        return ccAnbarak;
    }

    public void setCcAnbarak(int ccAnbarak) {
        this.ccAnbarak = ccAnbarak;
    }

    public int getCodeNoeForm() {
        return CodeNoeForm;
    }

    public void setCodeNoeForm(int codeNoeForm) {
        CodeNoeForm = codeNoeForm;
    }

    public int getCodeNoeAmalyat() {
        return CodeNoeAmalyat;
    }

    public void setCodeNoeAmalyat(int codeNoeAmalyat) {
        CodeNoeAmalyat = codeNoeAmalyat;
    }

    public long getCcRefrence() {
        return ccRefrence;
    }

    public void setCcRefrence(long ccRefrence) {
        this.ccRefrence = ccRefrence;
    }

    public int getCodeVazeiat() {
        return CodeVazeiat;
    }

    public void setCodeVazeiat(int codeVazeiat) {
        CodeVazeiat = codeVazeiat;
    }

    public int getCcUser() {
        return ccUser;
    }

    public void setCcUser(int ccUser) {
        this.ccUser = ccUser;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public int getExtraProp_IsSend() {
        return ExtraProp_IsSend;
    }

    public void setExtraProp_IsSend(int extraProp_IsSend) {
        ExtraProp_IsSend = extraProp_IsSend;
    }
}
