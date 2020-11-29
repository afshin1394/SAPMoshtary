package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoshtaryParentModel
{

    private static final String TABLE_NAME = "Moshtary";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_NameTablo = "NameTablo";


    @SerializedName("Radif")
    @Expose
    private int Radif;
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("ccMoshtaryParent")
    @Expose
    private int ccMoshtaryParent;
    @SerializedName("NoeForoshandeh_First")
    @Expose
    private int NoeForoshandeh_First;

    public void setRadif(int Radif){
        this.Radif = Radif;
    }

    public int getRadif(){
        return this.Radif;
    }

    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcMoshtary(){
        return this.ccMoshtary;
    }

    public void setCcMoshtaryParent(int ccMoshtaryParent){
        this.ccMoshtaryParent = ccMoshtaryParent;
    }

    public int getCcMoshtaryParent(){
        return this.ccMoshtaryParent;
    }

    public void setNoeForoshandeh_First(int NoeForoshandeh_First){
        this.NoeForoshandeh_First = NoeForoshandeh_First;
    }

    public int getNoeForoshandeh_First(){
        return this.NoeForoshandeh_First;
    }



}
