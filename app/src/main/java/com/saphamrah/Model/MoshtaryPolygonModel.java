package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoshtaryPolygonModel
{

    private static final String TABLE_NAME = "MoshtaryPolygon";
    private static final String COLUMN_ccPolygonForosh = "ccPolygonForosh";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_CanVisitKharejAzMahal = "CanVisitKharejAzMahal";
    private static final String COLUMN_ccMasir = "ccMasir";
    private static final String COLUMN_ToorVisit = "ToorVisit";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccPolygonForosh() {
        return COLUMN_ccPolygonForosh;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_CanVisitKharejAzMahal() {
        return COLUMN_CanVisitKharejAzMahal;
    }
    public static String COLUMN_ccMasir() {
        return COLUMN_ccMasir;
    }
    public static String COLUMN_ToorVisit() {
        return COLUMN_ToorVisit;
    }



    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("ccMasir")
    @Expose
    private int ccMasir;
    @SerializedName("ccPolygonForosh")
    @Expose
    private int ccPolygonForosh;
    @SerializedName("CanVisitKharejAzMahal")
    @Expose
    private int CanVisitKharejAzMahal;
    @SerializedName("ToorVisit")
    @Expose
    private int ToorVisit;
    @SerializedName("Id")
    @Expose
    private int Id;



    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcMoshtary(){
        return this.ccMoshtary;
    }

    public void setCcMasir(int ccMasir){
        this.ccMasir = ccMasir;
    }

    public int getCcMasir(){
        return this.ccMasir;
    }

    public void setCcPolygonForosh(int ccPolygonForosh){
        this.ccPolygonForosh = ccPolygonForosh;
    }

    public int getCcPolygonForosh(){
        return this.ccPolygonForosh;
    }

    public void setCanVisitKharejAzMahal(int CanVisitKharejAzMahal){
        this.CanVisitKharejAzMahal = CanVisitKharejAzMahal;
    }

    public int getCanVisitKharejAzMahal(){
        return this.CanVisitKharejAzMahal;
    }

    public void setToorVisit(int ToorVisit){
        this.ToorVisit = ToorVisit;
    }

    public int getToorVisit(){
        return this.ToorVisit;
    }

    public void setId(int Id){
        this.Id = Id;
    }

    public int getId(){
        return this.Id;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "ccMoshtary : " + ccMoshtary + " , ccMasir : " + ccMasir + " , ccPolygonForosh : " + ccPolygonForosh +
                " , CanVisitKharejAzMahal : " + CanVisitKharejAzMahal + " , ToorVisit : " + ToorVisit + " , Id : " + Id;
    }


}
