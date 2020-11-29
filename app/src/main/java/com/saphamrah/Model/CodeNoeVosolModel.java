package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class CodeNoeVosolModel
{

    private static final String TABLE_NAME = "CodeNoeVosol";
    private static final String COLUMN_CodeNoeVosol = "CodeNoeVosol";
    private static final String COLUMN_txtNoeVosol = "txtNoeVosol";
    private static final String COLUMN_ViewInPPC = "ViewInPPC";
    private static final String COLUMN_CodeNoeSanad_dp = "CodeNoeSanad_dp";
    private static final String COLUMN_CodeNoeCheck_dp = "CodeNoeCheck_dp";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_CodeNoeVosol() {
        return COLUMN_CodeNoeVosol;
    }
    public static String COLUMN_txtNoeVosol() {
        return COLUMN_txtNoeVosol;
    }
    public static String COLUMN_ViewInPPC() {
        return COLUMN_ViewInPPC;
    }
    public static String COLUMN_CodeNoeSanad_dp() {
        return COLUMN_CodeNoeSanad_dp;
    }
    public static String COLUMN_CodeNoeCheck_dp() {
        return COLUMN_CodeNoeCheck_dp;
    }



    private int CodeNoeVosol;
    public void setCodeNoeVosol(int CodeNoeVosol){
        this.CodeNoeVosol = CodeNoeVosol;
    }
    public int getCodeNoeVosol(){
        return this.CodeNoeVosol;
    }


    private String txtNoeVosol;
    public void setTxtNoeVosol(String txtNoeVosol){
        this.txtNoeVosol = txtNoeVosol;
    }
    public String getTxtNoeVosol(){
        return this.txtNoeVosol;
    }


    private int ViewInPPC;
    public void setViewInPPC(int ViewInPPC){
        this.ViewInPPC = ViewInPPC;
    }
    public int getViewInPPC(){
        return this.ViewInPPC;
    }


    private int CodeNoeCheck_dp;
    public void setCodeNoeCheck_dp(int CodeNoeCheck_dp){
        this.CodeNoeCheck_dp = CodeNoeCheck_dp;
    }
    public int getCodeNoeCheck_dp(){
        return this.CodeNoeCheck_dp;
    }


    private int CodeNoeSanad_dp;
    public void setCodeNoeSanad_dp(int CodeNoeSanad_dp){
        this.CodeNoeSanad_dp = CodeNoeSanad_dp;
    }
    public int getCodeNoeSanad_dp(){
        return this.CodeNoeSanad_dp;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "CodeNoeVosol : " + CodeNoeVosol + " , txtNoeVosol : " + txtNoeVosol + " , ViewInPPC : " + ViewInPPC +
                " , CodeNoeCheck_dp : " + CodeNoeCheck_dp + " , CodeNoeSanad_dp : " + CodeNoeSanad_dp;
    }



}
