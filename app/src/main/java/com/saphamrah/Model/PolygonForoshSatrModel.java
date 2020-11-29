package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PolygonForoshSatrModel
{

    private static final String TABLE_NAME = "PolygonForoshSatr";
    private static final String COLUMN_ccPolygonForosh = "ccPolygonForosh";
    private static final String COLUMN_ccPolygonForoshSatr = "ccPolygonForoshSatr";
    private static final String COLUMN_Lat_y = "Lat_y";
    private static final String COLUMN_Lng_x = "Lng_x";
    private static final String COLUMN_ccMasir = "ccMasir";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccPolygonForosh() {
        return COLUMN_ccPolygonForosh;
    }
    public static String COLUMN_ccPolygonForoshSatr() {
        return COLUMN_ccPolygonForoshSatr;
    }
    public static String COLUMN_Lat_y() {
        return COLUMN_Lat_y;
    }
    public static String COLUMN_Lng_x() {
        return COLUMN_Lng_x;
    }
    public static String COLUMN_ccMasir() {
        return COLUMN_ccMasir;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }



    @SerializedName("ccPolygonForosh")
    @Expose
    private int ccPolygonForosh;
    @SerializedName("ccPolygonForoshSatr")
    @Expose
    private int ccPolygonForoshSatr;
    @SerializedName("Lat_y")
    @Expose
    private double Lat_y;
    @SerializedName("Lng_x")
    @Expose
    private double Lng_x;
    @SerializedName("ccMasir")
    @Expose
    private int ccMasir;
    @SerializedName("ccForoshandeh")
    @Expose
    private int ccForoshandeh;
    @SerializedName("ccNoePolygon")
    @Expose
    private int ccNoePolygon;
    @SerializedName("Id")
    @Expose
    private int Id;

    public void setCcPolygonForosh(int ccPolygonForosh){
        this.ccPolygonForosh = ccPolygonForosh;
    }

    public int getCcPolygonForosh(){
        return this.ccPolygonForosh;
    }

    public void setCcPolygonForoshSatr(int ccPolygonForoshSatr){
        this.ccPolygonForoshSatr = ccPolygonForoshSatr;
    }

    public int getCcPolygonForoshSatr(){
        return this.ccPolygonForoshSatr;
    }

    public void setLat_y(double Lat_y){
        this.Lat_y = Lat_y;
    }

    public double getLat_y(){
        return this.Lat_y;
    }

    public void setLng_x(double Lng_x){
        this.Lng_x = Lng_x;
    }

    public double getLng_x(){
        return this.Lng_x;
    }

    public void setCcMasir(int ccMasir){
        this.ccMasir = ccMasir;
    }

    public int getCcMasir(){
        return this.ccMasir;
    }

    public void setCcForoshandeh(int ccForoshandeh){
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcForoshandeh(){
        return this.ccForoshandeh;
    }

    public void setCcNoePolygon(int ccNoePolygon){
        this.ccNoePolygon = ccNoePolygon;
    }

    public int getCcNoePolygon(){
        return this.ccNoePolygon;
    }

    public void setId(int Id){
        this.Id = Id;
    }

    public int getId(){
        return this.Id;
    }



    @Override
    public String toString()
    {
        return "ccPolygonForosh : " + ccPolygonForosh + " , ccPolygonForoshSatr : " + ccPolygonForoshSatr +
                " , Lat_y : " + Lat_y + " , Lng_x : " + Lng_x + " , ccMasir : " + ccMasir + " , ccForoshandeh : " + ccForoshandeh +
                " , ccNoePolygon : " + ccNoePolygon + " , Id : " + Id;
    }



}
