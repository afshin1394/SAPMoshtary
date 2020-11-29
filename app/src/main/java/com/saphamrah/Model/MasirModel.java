package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasirModel
{


    private static final String TABLE_NAME = "Masir";
    private static final String COLUMN_ccMasir = "ccMasir";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_NameMasir = "NameMasir";
    private static final String COLUMN_ToorVisit = "ToorVisit";
    private static final String COLUMN_ExtraProp_TarikhMasir = "ExtraProp_TarikhMasir"; // tarikhe daryaft barname
    private static final String COLUMN_ccMasirRoozVisit = "ccMasirRoozVisit";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMasir() {
        return COLUMN_ccMasir;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_NameMasir() {
        return COLUMN_NameMasir;
    }
    public static String COLUMN_ToorVisit() {
        return COLUMN_ToorVisit;
    }
    public static String COLUMN_ExtraProp_TarikhMasir() {
        return COLUMN_ExtraProp_TarikhMasir;
    }
    public static String COLUMN_ccMasirRoozVisit() {
        return COLUMN_ccMasirRoozVisit;
    }



    @SerializedName("ccForoshandeh")
    @Expose
    private int ccForoshandeh;
    @SerializedName("ccMasir")
    @Expose
    private int ccMasir;
    @SerializedName("NameMasir")
    @Expose
    private String NameMasir;
    @SerializedName("ToorVisit")
    @Expose
    private int ToorVisit;
    @SerializedName("ccMasirRoozVisit")
    @Expose
    private int ccMasirRoozVisit;
    private String tarikhMasir;

    public void setCcForoshandeh(int ccForoshandeh){
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcForoshandeh(){
        return this.ccForoshandeh;
    }

    public void setCcMasir(int ccMasir){
        this.ccMasir = ccMasir;
    }

    public int getCcMasir(){
        return this.ccMasir;
    }

    public void setNameMasir(String NameMasir){
        this.NameMasir = NameMasir;
    }

    public String getNameMasir(){
        return this.NameMasir;
    }

    public void setToorVisit(int ToorVisit){
        this.ToorVisit = ToorVisit;
    }

    public int getToorVisit(){
        return this.ToorVisit;
    }

    public void setCcMasirRoozVisit(int ccMasirRoozVisit){
        this.ccMasirRoozVisit = ccMasirRoozVisit;
    }

    public int getCcMasirRoozVisit(){
        return this.ccMasirRoozVisit;
    }

    public String getTarikhMasir() {
        return tarikhMasir;
    }

    public void setTarikhMasir(String tarikhMasir) {
        this.tarikhMasir = tarikhMasir;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "ccForoshandeh : " + ccForoshandeh + " , ccMasir : " + ccMasir + " , NameMasir : " + NameMasir +
                " , ToorVisit : " + ToorVisit + " , ccMasirRoozVisit : " + ccMasirRoozVisit + " , tarikhMasir : " + tarikhMasir;
    }




}
