package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class KardexModel
{

    private static final String TABLE_NAME = "Kardex";
    private static final String COLUMN_ccKardex = "ccKardex";
    private static final String COLUMN_ccMarkazAnbar = "ccMarkazAnbar";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_ccAnbar = "ccAnbar";
    private static final String COLUMN_CodeNoeForm = "CodeNoeForm";
    private static final String COLUMN_CodeNoeAmalyat = "CodeNoeAmalyat";
    private static final String COLUMN_CodeVazeiat = "CodeVazeiat";
    private static final String COLUMN_CodeNoeAnbar = "CodeNoeAnbar";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_MarjoeeKamel = "MarjoeeKamel";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccAfradMamurPakhsh = "ccAfradMamurPakhsh";
    private static final String COLUMN_ccRefrence = "ccRefrence";
    private static final String COLUMN_ExtraProp_IsOld = "ExtraProp_IsOld";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKardex() {
        return COLUMN_ccKardex;
    }
    public static String COLUMN_ccMarkazAnbar() {
        return COLUMN_ccMarkazAnbar;
    }
    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_ccAnbar() {
        return COLUMN_ccAnbar;
    }
    public static String COLUMN_CodeNoeForm() {
        return COLUMN_CodeNoeForm;
    }
    public static String COLUMN_CodeNoeAmalyat() {
        return COLUMN_CodeNoeAmalyat;
    }
    public static String COLUMN_CodeVazeiat() {
        return COLUMN_CodeVazeiat;
    }
    public static String COLUMN_CodeNoeAnbar() {
        return COLUMN_CodeNoeAnbar;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_MarjoeeKamel() {
        return COLUMN_MarjoeeKamel;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccAfradMamurPakhsh() {
        return COLUMN_ccAfradMamurPakhsh;
    }
    public static String COLUMN_ccRefrence() {
        return COLUMN_ccRefrence;
    }
    public static String COLUMN_ExtraProp_IsOld() {
        return COLUMN_ExtraProp_IsOld;
    }




    private int ccKardex;
    public int getCcKardex() {
        return ccKardex;
    }
    public void setCcKardex(int ccKardex) {
        this.ccKardex = ccKardex;
    }


    private int ccMarkazAnbar;
    public int getCcMarkazAnbar() {
        return ccMarkazAnbar;
    }
    public void setCcMarkazAnbar(int ccMarkazAnbar) {
        this.ccMarkazAnbar = ccMarkazAnbar;
    }


    private int ccMarkazForosh;
    public int getCcMarkazForosh() {
        return ccMarkazForosh;
    }
    public void setCcMarkazForosh(int ccMarkazForosh) {
        this.ccMarkazForosh = ccMarkazForosh;
    }


    private int ccAnbar;
    public int getCcAnbar() {
        return ccAnbar;
    }
    public void setCcAnbar(int ccAnbar) {
        this.ccAnbar = ccAnbar;
    }


    private int CodeNoeForm;
    public int getCodeNoeForm() {
        return CodeNoeForm;
    }
    public void setCodeNoeForm(int codeNoeForm) {
        CodeNoeForm = codeNoeForm;
    }


    private int CodeNoeAmalyat;
    public int getCodeNoeAmalyat() {
        return CodeNoeAmalyat;
    }
    public void setCodeNoeAmalyat(int codeNoeAmalyat) {
        CodeNoeAmalyat = codeNoeAmalyat;
    }


    private int CodeVazeiat;
    public int getCodeVazeiat() {
        return CodeVazeiat;
    }
    public void setCodeVazeiat(int codeVazeiat) {
        CodeVazeiat = codeVazeiat;
    }


    private int CodeNoeAnbar;
    public int getCodeNoeAnbar() {
        return CodeNoeAnbar;
    }
    public void setCodeNoeAnbar(int codeNoeAnbar) {
        CodeNoeAnbar = codeNoeAnbar;
    }


    private int ccMoshtary;
    public int getCcMoshtary() {
        return ccMoshtary;
    }
    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }


    private int MarjoeeKamel;
    public int getMarjoeeKamel() {
        return MarjoeeKamel;
    }
    public void setMarjoeeKamel(int marjoeeKamel) {
        MarjoeeKamel = marjoeeKamel;
    }


    private int ccForoshandeh;
    public int getCcForoshandeh() {
        return ccForoshandeh;
    }
    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }


    private int ccAfradMamurPakhsh;
    public int getCcAfradMamurPakhsh() {
        return ccAfradMamurPakhsh;
    }
    public void setCcAfradMamurPakhsh(int ccAfradMamurPakhsh) {
        this.ccAfradMamurPakhsh = ccAfradMamurPakhsh;
    }


    private int ccRefrence;
    public int getCcRefrence() {
        return ccRefrence;
    }
    public void setCcRefrence(int ccRefrence) {
        this.ccRefrence = ccRefrence;
    }


    private int ExtraProp_IsOld;
    public int getExtraProp_IsOld() {
        return ExtraProp_IsOld;
    }
    public void setExtraProp_IsOld(int extraProp_IsOld) {
        ExtraProp_IsOld = extraProp_IsOld;
    }


    @NonNull
    @Override
    public String toString()
    {
        return  "ccKardex=" + ccKardex +
                ", ccMarkazAnbar=" + ccMarkazAnbar +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", ccAnbar=" + ccAnbar +
                ", CodeNoeForm=" + CodeNoeForm +
                ", CodeNoeAmalyat=" + CodeNoeAmalyat +
                ", CodeVazeiat=" + CodeVazeiat +
                ", CodeNoeAnbar=" + CodeNoeAnbar +
                ", ccMoshtary=" + ccMoshtary +
                ", MarjoeeKamel=" + MarjoeeKamel +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccAfradMamurPakhsh=" + ccAfradMamurPakhsh +
                ", ccRefrence=" + ccRefrence +
                ", ExtraProp_IsOld=" + ExtraProp_IsOld;
    }



}
