package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class KalaGorohModel
{

    private static final String TABLE_NAME = "KalaGoroh";
    private static final String COLUMN_ccKalaGoroh = "ccKalaGoroh";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_ccGoroh = "ccGoroh";
    private static final String COLUMN_NameGoroh = "NameGoroh";
    private static final String COLUMN_ccGorohLink = "ccGorohLink";
    private static final String COLUMN_ccRoot = "ccRoot";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKalaGoroh() {
        return COLUMN_ccKalaGoroh;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
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


    private int ccKalaGoroh;
    public int getCcKalaGoroh() {
        return ccKalaGoroh;
    }
    public void setCcKalaGoroh(int ccKalaGoroh) {
        this.ccKalaGoroh = ccKalaGoroh;
    }


    private int ccKalaCode;
    public void setCcKalaCode(int ccKalaCode){
        this.ccKalaCode = ccKalaCode;
    }
    public int getCcKalaCode(){
        return this.ccKalaCode;
    }


    private String NameKala;
    public void setNameKala(String NameKala){
        this.NameKala = NameKala;
    }
    public String getNameKala(){
        return this.NameKala;
    }


    private int ccGoroh;
    public void setCcGoroh(int ccGoroh){
        this.ccGoroh = ccGoroh;
    }
    public int getCcGoroh(){
        return this.ccGoroh;
    }


    private String NameGoroh;
    public void setNameGoroh(String NameGoroh){
        this.NameGoroh = NameGoroh;
    }
    public String getNameGoroh(){
        return this.NameGoroh;
    }


    private int CodeNoeGoroh;
    public void setCodeNoeGoroh(int CodeNoeGoroh){
        this.CodeNoeGoroh = CodeNoeGoroh;
    }
    public int getCodeNoeGoroh(){
        return this.CodeNoeGoroh;
    }


    private int ccGorohLink;
    public void setCcGorohLink(int ccGorohLink){
        this.ccGorohLink = ccGorohLink;
    }
    public int getCcGorohLink(){
        return this.ccGorohLink;
    }


    private int ccRoot;
    public void setCcRoot(int ccRoot){
        this.ccRoot = ccRoot;
    }
    public int getCcRoot(){
        return this.ccRoot;
    }


    private int CodeGoroh;
    public void setCodeGoroh(int CodeGoroh){
        this.CodeGoroh = CodeGoroh;
    }
    public int getCodeGoroh(){
        return this.CodeGoroh;
    }



    @NonNull
    @Override
    public String toString()
    {
        return  "ccKalaCode=" + ccKalaCode +
                ", NameKala='" + NameKala + '\'' +
                ", ccGoroh=" + ccGoroh +
                ", NameGoroh='" + NameGoroh + '\'' +
                ", CodeNoeGoroh=" + CodeNoeGoroh +
                ", ccGorohLink=" + ccGorohLink +
                ", ccRoot=" + ccRoot +
                ", CodeGoroh=" + CodeGoroh;
    }




}
