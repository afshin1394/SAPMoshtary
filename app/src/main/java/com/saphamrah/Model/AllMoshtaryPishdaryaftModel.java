package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class AllMoshtaryPishdaryaftModel
{

    private static final String TABLE_NAME = "AllMoshtaryPishdaryaft";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_CodeMoshtary = "CodeMoshtary";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_Address = "Address";
    private static final String COLUMN_Telephone = "Telephone";
    private static final String COLUMN_ccMasir = "ccMasir";
    private static final String COLUMN_NameMasir = "NameMasir";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_Latitude = "Latitude";
    private static final String COLUMN_Longitude = "Longitude";
    private static final String COLUMN_Darajeh = "Darajeh";
    private static final String COLUMN_ccNoeMoshtary = "ccNoeMoshtary";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_CodeMoshtary() {
        return COLUMN_CodeMoshtary;
    }
    public static String COLUMN_NameMoshtary() {
        return COLUMN_NameMoshtary;
    }
    public static String COLUMN_Address() {
        return COLUMN_Address;
    }
    public static String COLUMN_Telephone() {
        return COLUMN_Telephone;
    }
    public static String COLUMN_ccMasir() {
        return COLUMN_ccMasir;
    }
    public static String COLUMN_NameMasir() {
        return COLUMN_NameMasir;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_Latitude() {
        return COLUMN_Latitude;
    }
    public static String COLUMN_Longitude() {
        return COLUMN_Longitude;
    }
    public static String COLUMN_Darajeh() {
        return COLUMN_Darajeh;
    }
    public static String COLUMN_ccNoeMoshtary() {
        return COLUMN_ccNoeMoshtary;
    }




    private int ccMoshtary;
    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }
    public int getCcMoshtary(){
        return this.ccMoshtary;
    }


    private String CodeMoshtary;
    public void setCodeMoshtary(String CodeMoshtary){
        this.CodeMoshtary = CodeMoshtary;
    }
    public String getCodeMoshtary(){
        return this.CodeMoshtary;
    }


    private String NameMoshtary;
    public void setNameMoshtary(String NameMoshtary){
        this.NameMoshtary = NameMoshtary;
    }
    public String getNameMoshtary(){
        return this.NameMoshtary;
    }


    private String Address;
    public void setAddress(String Address){
        this.Address = Address;
    }
    public String getAddress(){
        return this.Address;
    }


    private String Telephone;
    public void setTelephone(String Telephone){
        this.Telephone = Telephone;
    }
    public String getTelephone(){
        return this.Telephone;
    }


    private int ccMasir;
    public void setCcMasir(int ccMasir){
        this.ccMasir = ccMasir;
    }
    public int getCcMasir(){
        return this.ccMasir;
    }


    private String NameMasir;
    public void setNameMasir(String NameMasir){
        this.NameMasir = NameMasir;
    }
    public String getNameMasir(){
        return this.NameMasir;
    }


    private int ccForoshandeh;
    public void setCcForoshandeh(int ccForoshandeh){
        this.ccForoshandeh = ccForoshandeh;
    }
    public int getCcForoshandeh(){
        return this.ccForoshandeh;
    }


    private int Id;
    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }


    private float Latitude;
    public float getLatitude() {
        return Latitude;
    }
    public void setLatitude(float latitude) {
        Latitude = latitude;
    }


    private float Longitude;
    public float getLongitude() {
        return Longitude;
    }
    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    private int Darajeh;
    public int getDarajeh() {
        return Darajeh;
    }

    public void setDarajeh(int darajeh) {
        Darajeh = darajeh;
    }

    private int ccNoeMoshtary;

    public int getCcNoeMoshtary() {
        return ccNoeMoshtary;
    }

    public void setCcNoeMoshtary(int ccNoeMoshtary) {
        this.ccNoeMoshtary = ccNoeMoshtary;
    }

    @NonNull
    @Override
    public String toString() {
        return "AllMoshtaryForoshandehModel{" +
                "ccMoshtary=" + ccMoshtary +
                ", CodeMoshtary='" + CodeMoshtary + '\'' +
                ", NameMoshtary='" + NameMoshtary + '\'' +
                ", Address='" + Address + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", ccMasir=" + ccMasir +
                ", NameMasir='" + NameMasir + '\'' +
                ", ccForoshandeh=" + ccForoshandeh +
                ", Id=" + Id +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                ", Darajeh=" + Darajeh +
                ", ccNoeMoshtary=" + ccNoeMoshtary +
                '}';
    }
}
