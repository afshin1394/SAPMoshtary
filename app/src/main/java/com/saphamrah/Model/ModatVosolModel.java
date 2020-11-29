package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class ModatVosolModel
{

    private static final String TABLE_NAME = "ModatVosol";
    private static final String COLUMN_ccModatVosol = "ccModatVosol";
    private static final String COLUMN_ccMarkazSazmanForoshSakhtarForosh = "ccMarkazSazmanForoshSakhtarForosh";
    private static final String COLUMN_CodeNoe = "CodeNoe";
    private static final String COLUMN_SharhModatVosol = "SharhModatVosol";
    private static final String COLUMN_Az = "Az";
    private static final String COLUMN_Ta = "Ta";
    private static final String COLUMN_ModatVosol = "ModatVosol";
    private static final String COLUMN_Darajeh = "Darajeh";
    private static final String COLUMN_ccBrand = "ccBrand";
    private static final String COLUMN_ccGorohKala = "ccGorohKala";
    private static final String COLUMN_ccGoroh = "ccGoroh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccModatVosol() {
        return COLUMN_ccModatVosol;
    }
    public static String COLUMN_ccMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_ccMarkazSazmanForoshSakhtarForosh;
    }
    public static String COLUMN_CodeNoe() {
        return COLUMN_CodeNoe;
    }
    public static String COLUMN_SharhModatVosol() {
        return COLUMN_SharhModatVosol;
    }
    public static String COLUMN_Az() {
        return COLUMN_Az;
    }
    public static String COLUMN_Ta() {
        return COLUMN_Ta;
    }
    public static String COLUMN_ModatVosol() {
        return COLUMN_ModatVosol;
    }
    public static String COLUMN_Darajeh() {
        return COLUMN_Darajeh;
    }
    public static String COLUMN_ccBrand() {
        return COLUMN_ccBrand;
    }
    public static String COLUMN_ccGorohKala() {
        return COLUMN_ccGorohKala;
    }
    public static String COLUMN_ccGoroh() {
        return COLUMN_ccGoroh;
    }




    private int ccModatVosol;
    public void setCcModatVosol(int ccModatVosol){
        this.ccModatVosol = ccModatVosol;
    }
    public int getCcModatVosol(){
        return this.ccModatVosol;
    }


    private int CodeNoe;
    public void setCodeNoe(int CodeNoe){
        this.CodeNoe = CodeNoe;
    }
    public int getCodeNoe(){
        return this.CodeNoe;
    }


    private String SharhModatVosol;
    public void setSharhModatVosol(String SharhModatVosol){
        this.SharhModatVosol = SharhModatVosol;
    }
    public String getSharhModatVosol(){
        return this.SharhModatVosol;
    }


    private String FromDate;
    public void setFromDate(String FromDate){
        this.FromDate = FromDate;
    }
    public String getFromDate(){
        return this.FromDate;
    }


    private String EndDate;
    public void setEndDate(String EndDate){
        this.EndDate = EndDate;
    }
    public String getEndDate(){
        return this.EndDate;
    }


    private float Az;
    public void setAz(float Az){
        this.Az = Az;
    }
    public float getAz(){
        return this.Az;
    }


    private float Ta;
    public void setTa(float Ta){
        this.Ta = Ta;
    }
    public float getTa(){
        return this.Ta;
    }


    private int ModatVosol;
    public void setModatVosol(int ModatVosol){
        this.ModatVosol = ModatVosol;
    }
    public int getModatVosol(){
        return this.ModatVosol;
    }


    private int ccGoroh;
    public void setCcGoroh(int ccGoroh){
        this.ccGoroh = ccGoroh;
    }
    public int getCcGoroh(){
        return this.ccGoroh;
    }


    private int ccMarkazSazmanForoshSakhtarForosh;
    public void setCcMarkazSazmanForoshSakhtarForosh(int ccMarkazSazmanForoshSakhtarForosh){
        this.ccMarkazSazmanForoshSakhtarForosh = ccMarkazSazmanForoshSakhtarForosh;
    }
    public int getCcMarkazSazmanForoshSakhtarForosh(){
        return this.ccMarkazSazmanForoshSakhtarForosh;
    }


    private String ccKalaCode;
    public void setCcKalaCode(String ccKalaCode){
        this.ccKalaCode = ccKalaCode;
    }
    public String getCcKalaCode(){
        return this.ccKalaCode;
    }


    private String NameGoroh;
    public void setNameGoroh(String NameGoroh){
        this.NameGoroh = NameGoroh;
    }
    public String getNameGoroh(){
        return this.NameGoroh;
    }


    private String NameMarkaz;
    public void setNameMarkaz(String NameMarkaz){
        this.NameMarkaz = NameMarkaz;
    }
    public String getNameMarkaz(){
        return this.NameMarkaz;
    }


    private int Darajeh;
    public void setDarajeh(int Darajeh){
        this.Darajeh = Darajeh;
    }
    public int getDarajeh(){
        return this.Darajeh;
    }


    private int ccBrand;
    public void setCcBrand(int ccBrand){
        this.ccBrand = ccBrand;
    }
    public int getCcBrand(){
        return this.ccBrand;
    }


    private int ccGorohKala;
    public int getCcGorohKala() {
        return ccGorohKala;
    }
    public void setCcGorohKala(int ccGorohKala) {
        this.ccGorohKala = ccGorohKala;
    }


    @NonNull
    @Override
    public String toString() {
        return "ModatVosolModel{" +
                "ccModatVosol=" + ccModatVosol +
                ", CodeNoe=" + CodeNoe +
                ", SharhModatVosol='" + SharhModatVosol + '\'' +
                ", FromDate='" + FromDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", Az=" + Az +
                ", Ta=" + Ta +
                ", ModatVosol=" + ModatVosol +
                ", ccGoroh=" + ccGoroh +
                ", ccMarkazSazmanForoshSakhtarForosh=" + ccMarkazSazmanForoshSakhtarForosh +
                ", ccKalaCode='" + ccKalaCode + '\'' +
                ", NameGoroh='" + NameGoroh + '\'' +
                ", NameMarkaz='" + NameMarkaz + '\'' +
                ", Darajeh=" + Darajeh +
                ", ccBrand=" + ccBrand +
                ", ccGorohKala=" + ccGorohKala +
                '}';
    }
}
