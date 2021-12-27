package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class RptKharidKalaModel
{

    private static final String TABLE_NAME = "Rpt_KharidKala";
    private static final String COLUMN_Radif = "Radif";
    private static final String COLUMN_ccKalaGoroh = "ccKalaGoroh";
    private static final String COLUMN_NameKalaGoroh = "NameKalaGoroh";
    private static final String COLUMN_CodeGoroh = "CodeGoroh";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_CodeKala = "CodeKala";
    private static final String COLUMN_NameKala = "NameKala";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_MablaghKharid = "MablaghKharid";
    private static final String COLUMN_Karton = "Karton";
    private static final String COLUMN_Basteh = "Basteh";
    private static final String COLUMN_Adad = "Adad";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_Radif() {
        return COLUMN_Radif;
    }
    public static String COLUMN_ccKalaGoroh() {
        return COLUMN_ccKalaGoroh;
    }
    public static String COLUMN_NameKalaGoroh() {
        return COLUMN_NameKalaGoroh;
    }
    public static String COLUMN_CodeGoroh() {
        return COLUMN_CodeGoroh;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_CodeKala() {
        return COLUMN_CodeKala;
    }
    public static String COLUMN_NameKala() {
        return COLUMN_NameKala;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_MablaghKharid() {
        return COLUMN_MablaghKharid;
    }
    public static String COLUMN_Karton() {
        return COLUMN_Karton;
    }
    public static String COLUMN_Basteh() {
        return COLUMN_Basteh;
    }
    public static String COLUMN_Adad() {
        return COLUMN_Adad;
    }




    private int Radif;
    public void setRadif(int Radif){
        this.Radif = Radif;
    }
    public int getRadif(){
        return this.Radif;
    }


    private int ccKalaGoroh;
    public void setCcKalaGoroh(int ccKalaGoroh){
        this.ccKalaGoroh = ccKalaGoroh;
    }
    public int getCcKalaGoroh(){
        return this.ccKalaGoroh;
    }


    private String NameKalaGoroh;
    public void setNameKalaGoroh(String NameKalaGoroh){
        this.NameKalaGoroh = NameKalaGoroh;
    }
    public String getNameKalaGoroh(){
        return this.NameKalaGoroh;
    }


    private String CodeGoroh;
    public void setCodeGoroh(String CodeGoroh){
        this.CodeGoroh = CodeGoroh;
    }
    public String getCodeGoroh(){
        return this.CodeGoroh;
    }


    private int ccKalaCode;
    public void setCcKalaCode(int ccKalaCode){
        this.ccKalaCode = ccKalaCode;
    }
    public int getCcKalaCode(){
        return this.ccKalaCode;
    }


    private String CodeKala;
    public void setCodeKala(String CodeKala){
    this.CodeKala = CodeKala;
}
    public String getCodeKala(){
        return this.CodeKala;
    }


    private String NameKala;
    public void setNameKala(String NameKala){
        this.NameKala = NameKala;
    }
    public String getNameKala(){
        return this.NameKala;
    }


    private int ccMoshtary;
    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }
    public int getCcMoshtary(){
        return this.ccMoshtary;
    }


    private long MablaghKharid;
    public void setMablaghKharid(long MablaghKharid){
        this.MablaghKharid = MablaghKharid;
    }
    public long getMablaghKharid(){
        return this.MablaghKharid;
    }


    private int Karton;
    public void setKarton(int Karton){
        this.Karton = Karton;
    }
    public int getKarton(){
        return this.Karton;
    }


    private int Basteh;
    public void setBasteh(int Basteh){
        this.Basteh = Basteh;
    }
    public int getBasteh(){
        return this.Basteh;
    }


    private int Adad;
    public void setAdad(int Adad){
        this.Adad = Adad;
    }
    public int getAdad(){
        return this.Adad;
    }


    @NonNull
    @Override
    public String toString()
    {
        return  "Radif=" + Radif +
                ", ccKalaGoroh=" + ccKalaGoroh +
                ", NameKalaGoroh='" + NameKalaGoroh + '\'' +
                ", CodeGoroh='" + CodeGoroh + '\'' +
                ", ccKalaCode=" + ccKalaCode +
                ", CodeKala='" + CodeKala + '\'' +
                ", NameKala='" + NameKala + '\'' +
                ", ccMoshtary=" + ccMoshtary +
                ", MablaghKharid=" + MablaghKharid +
                ", Karton=" + Karton +
                ", Basteh=" + Basteh +
                ", Adad=" + Adad;
    }


}
