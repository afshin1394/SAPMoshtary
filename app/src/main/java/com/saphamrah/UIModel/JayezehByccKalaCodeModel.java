package com.saphamrah.UIModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JayezehByccKalaCodeModel {


    private ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeModels;

    @SerializedName("ccJayezeh")
    @Expose
    private int ccJayezeh;
    @SerializedName("SharhJayezeh")
    @Expose
    private String sharhJayezeh;
    @SerializedName("NoeTedadRial")
    @Expose
    private int NoeTedadRial;
    @SerializedName("ccJayezehSatr")
    @Expose
    private int ccJayezehSatr;
    @SerializedName("Az")
    @Expose
    private Double Az;
    @SerializedName("Ta")
    @Expose
    private Double Ta;
    @SerializedName("TedadJayezeh")
    @Expose
    private int TedadJayezeh;
    @SerializedName("BeEza")
    @Expose
    private int BeEza;
    @SerializedName("CodeNoeBastehBandy")
    @Expose
    private int CodeNoeBastehBandy;


    public ArrayList<JayezehByccKalaCodeModel> getJayezehByccKalaCodeModels() {
        return jayezehByccKalaCodeModels;
    }

    public void setJayezehByccKalaCodeModels(ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeModels) {
        this.jayezehByccKalaCodeModels = jayezehByccKalaCodeModels;
    }

    public int getNoeTedadRial() {
        return NoeTedadRial;
    }

    public void setNoeTedadRial(int noeTedadRial) {
        NoeTedadRial = noeTedadRial;
    }

    public int getCodeNoeBastehBandy() {
        return CodeNoeBastehBandy;
    }

    public void setCodeNoeBastehBandy(int codeNoeBastehBandy) {
        CodeNoeBastehBandy = codeNoeBastehBandy;
    }

    public int getCcJayezeh() {
        return ccJayezeh;
    }

    public void setCcJayezeh(int ccJayezeh) {
        this.ccJayezeh = ccJayezeh;
    }

    public String getSharhJayezeh() {
        return sharhJayezeh;
    }

    public void setSharhJayezeh(String sharhJayezeh) {
        this.sharhJayezeh = sharhJayezeh;
    }

    public int getCcJayezehSatr() {
        return ccJayezehSatr;
    }

    public void setCcJayezehSatr(int ccJayezehSatr) {
        this.ccJayezehSatr = ccJayezehSatr;
    }

    public double getAz() {
        return Az;
    }

    public void setAz(double az) {
        Az = az;
    }

    public double getTa() {
        return Ta;
    }

    public void setTa(double ta) {
        Ta = ta;
    }

    public int getTedadJayezeh() {
        return TedadJayezeh;
    }

    public void setTedadJayezeh(int tedadJayezeh) {
        TedadJayezeh = tedadJayezeh;
    }

    public int getBeEza() {
        return BeEza;
    }

    public void setBeEza(int beEza) {
        BeEza = beEza;
    }

    public String getTABLE_NAME() {
        return "jayezeh";
    }

    public String getCOLUMN_ccJayezeh() {
        return "ccJayezeh";
    }

    public String getCOLUMN_SharhJayezeh() {
        return "SharhJayezeh";
    }

    public String getCOLUMN_ccJayezehSatr() {
        return "ccJayezehSatr";
    }

    public String getCOLUMN_Az() {
        return "Az";
    }

    public String getCOLUMN_Ta() {
        return "Ta";
    }

    public String getCOLUMN_TedadJayezeh() {
        return "TedadJayezeh";
    }

    public String getCOLUMN_BeEza() {
        return "BeEza";
    }

    public String getCOLUMN_CodeNoeBastehBandy() {
        return "CodeNoeBastehBandy";
    }

    public String getCOLUMN_NoeTedadRial() {
        return "NoeTedadRial";
    }


    // set Details for recycler 2

    private int ccJayezehDetails;
    private int tedadKalaDetails;
    private double mablaghForoshDetails;
    private int ccKalaCodeDetails;
    private Long ccDarkhastFaktorDetails;
    private double mablaghKol;

    public double getMablaghKol() {
        return mablaghKol;
    }

    public void setMablaghKol(double mablaghKol) {
        this.mablaghKol = mablaghKol;
    }

    public int getCcJayezehDetails() {
        return ccJayezehDetails;
    }

    public void setCcJayezehDetails(int ccJayezehDetails) {
        this.ccJayezehDetails = ccJayezehDetails;
    }

    public int getTedadKalaDetails() {
        return tedadKalaDetails;
    }

    public void setTedadKalaDetails(int tedadKalaDetails) {
        this.tedadKalaDetails = tedadKalaDetails;
    }

    public double getMablaghForoshDetails() {
        return mablaghForoshDetails;
    }

    public void setMablaghForoshDetails(double mablaghForoshDetails) {
        this.mablaghForoshDetails = mablaghForoshDetails;
    }

    public int getCcKalaCodeDetails() {
        return ccKalaCodeDetails;
    }

    public void setCcKalaCodeDetails(int ccKalaCodeDetails) {
        this.ccKalaCodeDetails = ccKalaCodeDetails;
    }

    public Long getCcDarkhastFaktorDetails() {
        return ccDarkhastFaktorDetails;
    }

    public void setCcDarkhastFaktorDetails(Long ccDarkhastFaktorDetails) {
        this.ccDarkhastFaktorDetails = ccDarkhastFaktorDetails;
    }
}
