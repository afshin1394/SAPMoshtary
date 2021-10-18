package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RptJashnvarehForoshModel {


    private static final String TABLE_NAME = "Rpt_JashnvarehForosh";
    private static final String Column_Radif = "Radif";
    private static final String Column_ccJashnvarehForosh = "ccJashnvarehForosh";
    private static final String Column_ccJashnvarehForoshSatr = "ccJashnvarehForoshSatr";
    private static final String Column_SharhJashnvareh = "SharhJashnvareh";
    private static final String Column_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String Column_ccMoshtary = "ccMoshtary";
    private static final String Column_CodeMoshtary = "CodeMoshtary";
    private static final String Column_NameMoshtary = "NameMoshtary";
    private static final String Column_ccMarkazSazmanForosh = "ccMarkazSazmanForosh";
    private static final String Column_NameMarkaz = "NameMarkaz";
    private static final String Column_NameSazmanForosh = "NameSazmanForosh";
    private static final String Column_NoeMohasebeh = "NoeMohasebeh";
    private static final String Column_MabnaMohasebeh = "MabnaMohasebeh";
    private static final String Column_SharhMabnaMohasebeh = "SharhMabnaMohasebeh";
    private static final String Column_txtNoeMohasebeh = "txtNoeMohasebeh";
    private static final String Column_MohasebehBarAsase = "MohasebehBarAsase";
    private static final String Column_AzTarikhJashnvareh = "AzTarikhJashnvareh";
    private static final String Column_TaTarikhJashnvareh = "TaTarikhJashnvareh";
    private static final String Column_AzTarikhJashnvarehSatr = "AzTarikhJashnvarehSatr";
    private static final String Column_TaTarikhJashnvarehSatr = "TaTarikhJashnvarehSatr";
    private static final String Column_txtCodeNoeBastehBandy = "txtCodeNoeBastehBandy";
    private static final String Column_txtCodeNoeBastehBandyBeEza = "txtCodeNoeBastehBandyBeEza";
    private static final String Column_Az = "Az";
    private static final String Column_Ta = "Ta";
    private static final String Column_BeEza = "BeEza";
    private static final String Column_EmtiazJashnvareh = "EmtiazJashnvareh";
    private static final String Column_RialYekEmtiazJashnvareh = "RialYekEmtiazJashnvareh";
    private static final String Column_EmtiazMoshtary = "EmtiazMoshtary";
    private static final String Column_RialEmtiazMoshtary = "RialEmtiazMoshtary";
    private static final String Column_Doreh = "Doreh";
    private static final String Column_TarikhMohasebehEmtiaz = "TarikhMohasebehEmtiaz";


    public static String TableName() {
        return TABLE_NAME;
    }

    public static String Column_Radif() {
        return Column_Radif;
    }

    public static String Column_ccJashnvarehForosh() {
        return Column_ccJashnvarehForosh;
    }

    public static String Column_ccJashnvarehForoshSatr() {
        return Column_ccJashnvarehForoshSatr;
    }

    public static String Column_SharhJashnvareh() {
        return Column_SharhJashnvareh;
    }

    public static String Column_ccDarkhastFaktor() {
        return Column_ccDarkhastFaktor;
    }

    public static String Column_ccMoshtary() {
        return Column_ccMoshtary;
    }

    public static String Column_CodeMoshtary() {
        return Column_CodeMoshtary;
    }

    public static String Column_NameMoshtary() {
        return Column_NameMoshtary;
    }

    public static String Column_ccMarkazSazmanForosh() {
        return Column_ccMarkazSazmanForosh;
    }

    public static String Column_NameMarkaz() {
        return Column_NameMarkaz;
    }

    public static String Column_NameSazmanForosh() {
        return Column_NameSazmanForosh;
    }

    public static String Column_NoeMohasebeh() {
        return Column_NoeMohasebeh;
    }

    public static String Column_MabnaMohasebeh() {
        return Column_MabnaMohasebeh;
    }

    public static String Column_SharhMabnaMohasebeh() {
        return Column_SharhMabnaMohasebeh;
    }

    public static String Column_txtNoeMohasebeh() {
        return Column_txtNoeMohasebeh;
    }

    public static String Column_MohasebehBarAsase() {
        return Column_MohasebehBarAsase;
    }

    public static String Column_AzTarikhJashnvareh() {
        return Column_AzTarikhJashnvareh;
    }

    public static String Column_TaTarikhJashnvareh() {
        return Column_TaTarikhJashnvareh;
    }

    public static String Column_AzTarikhJashnvarehSatr() {
        return Column_AzTarikhJashnvarehSatr;
    }

    public static String Column_TaTarikhJashnvarehSatr() {
        return Column_TaTarikhJashnvarehSatr;
    }

    public static String Column_txtCodeNoeBastehBandy() {
        return Column_txtCodeNoeBastehBandy;
    }

    public static String Column_txtCodeNoeBastehBandyBeEza() {
        return Column_txtCodeNoeBastehBandyBeEza;
    }

    public static String Column_Az() {
        return Column_Az;
    }

    public static String Column_Ta() {
        return Column_Ta;
    }

    public static String Column_BeEza() {
        return Column_BeEza;
    }

    public static String Column_EmtiazJashnvareh() {
        return Column_EmtiazJashnvareh;
    }

    public static String Column_RialYekEmtiazJashnvareh() {
        return Column_RialYekEmtiazJashnvareh;
    }

    public static String Column_EmtiazMoshtary() {
        return Column_EmtiazMoshtary;
    }

    public static String Column_RialEmtiazMoshtary() {
        return Column_RialEmtiazMoshtary;
    }

    public static String Column_Doreh() {
        return Column_Doreh;
    }

    public static String Column_TarikhMohasebehEmtiaz() {
        return Column_TarikhMohasebehEmtiaz;
    }

    @SerializedName("Radif")
    @Expose
    private int radif;


    @SerializedName("ccJashnvarehForosh")
    @Expose
    private int ccJashnvarehForosh;


    @SerializedName("ccJashnvarehForoshSatr")
    @Expose
    private int ccJashnvarehForoshSatr;

    @SerializedName("SharhJashnvareh")
    @Expose
    private String SharhJashnvareh;

    @SerializedName("ccDarkhastFaktor")
    @Expose
    private int ccDarkhastFaktor;

    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;


    @SerializedName("CodeMoshtary")
    @Expose
    private String codeMoshtary;

    @SerializedName("NameMoshtary")
    @Expose
    private String nameMoshtary;

    @SerializedName("ccMarkazSazmanForosh")
    @Expose
    private int ccMarkazSazmanForosh;

    @SerializedName("NameMarkaz")
    @Expose
    private String nameMarkaz;

    @SerializedName("NameSazmanForosh")
    @Expose
    private String nameSazmanForosh;


    @SerializedName("noeMohasebeh")
    @Expose
    private int noeMohasebeh;


    @SerializedName("MabnaMohasebeh")
    @Expose
    private int mabnaMohasebeh;


    @SerializedName("SharhMabnaMohasebeh")
    @Expose
    private String sharhMabnaMohasebeh;


    @SerializedName("txtNoeMohasebeh")
    @Expose
    private String txtNoeMohasebeh;


    @SerializedName("MohasebehBarAsase")
    @Expose
    private String mohasebehBarAsase;


    @SerializedName("AzTarikhJashnvareh")
    @Expose
    private String azTarikhJashnvareh;
    @SerializedName("TaTarikhJashnvareh")
    @Expose
    private String taTarikhJashnvareh;


    @SerializedName("AzTarikhJashnvarehSatr")
    @Expose
    private String azTarikhJashnvarehSatr;


    @SerializedName("TaTarikhJashnvarehSatr")
    @Expose
    private String taTarikhJashnvarehSatr;

    @SerializedName("txtCodeNoeBastehBandy")
    @Expose
    private String txtCodeNoeBastehBandy;


    @SerializedName("txtCodeNoeBastehBandyBeEza")
    @Expose
    private String txtCodeNoeBastehBandyBeEza;

    @SerializedName("Az")
    @Expose
    private double az;

    @SerializedName("Ta")
    @Expose
    private double ta;

    @SerializedName("BeEza")
    @Expose
    private double beEza;

    @SerializedName("EmtiazJashnvareh")
    @Expose
    private int emtiazJashnvareh;

    @SerializedName("RialYekEmtiazJashnvareh")
    @Expose
    private double rialYekEmtiazJashnvareh;

    @SerializedName("EmtiazMoshtary")
    @Expose
    private int emtiazMoshtary;

    @SerializedName("RialEmtiazMoshtary")
    @Expose
    private double rialEmtiazMoshtary;

    @SerializedName("Doreh")
    @Expose
    private int doreh;

    @SerializedName("tarikhMohasebehEmtiaz")
    @Expose
    private String tarikhMohasebehEmtiaz;


    /**
     * for view
     */
    private boolean isExpanded = false;

    /**
     * forView
     */
    private ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();


    public int getRadif() {
        return radif;
    }

    public void setRadif(int radif) {
        this.radif = radif;
    }

    public int getCcJashnvarehForosh() {
        return ccJashnvarehForosh;
    }

    public void setCcJashnvarehForosh(int ccJashnvarehForosh) {
        this.ccJashnvarehForosh = ccJashnvarehForosh;
    }

    public int getCcJashnvarehForoshSatr() {
        return ccJashnvarehForoshSatr;
    }

    public void setCcJashnvarehForoshSatr(int ccJashnvarehForoshSatr) {
        this.ccJashnvarehForoshSatr = ccJashnvarehForoshSatr;
    }

    public String getSharhJashnvareh() {
        return SharhJashnvareh;
    }

    public void setSharhJashnvareh(String sharhJashnvareh) {
        SharhJashnvareh = sharhJashnvareh;
    }

    public int getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(int ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getCodeMoshtary() {
        return codeMoshtary;
    }

    public void setCodeMoshtary(String codeMoshtary) {
        this.codeMoshtary = codeMoshtary;
    }

    public String getNameMoshtary() {
        return nameMoshtary;
    }

    public void setNameMoshtary(String nameMoshtary) {
        this.nameMoshtary = nameMoshtary;
    }

    public int getCcMarkazSazmanForosh() {
        return ccMarkazSazmanForosh;
    }

    public void setCcMarkazSazmanForosh(int ccMarkazSazmanForosh) {
        this.ccMarkazSazmanForosh = ccMarkazSazmanForosh;
    }

    public String getNameMarkaz() {
        return nameMarkaz;
    }

    public void setNameMarkaz(String nameMarkaz) {
        this.nameMarkaz = nameMarkaz;
    }

    public String getNameSazmanForosh() {
        return nameSazmanForosh;
    }

    public void setNameSazmanForosh(String nameSazmanForosh) {
        this.nameSazmanForosh = nameSazmanForosh;
    }

    public int getNoeMohasebeh() {
        return noeMohasebeh;
    }

    public void setNoeMohasebeh(int noeMohasebeh) {
        this.noeMohasebeh = noeMohasebeh;
    }

    public int getMabnaMohasebeh() {
        return mabnaMohasebeh;
    }

    public void setMabnaMohasebeh(int mabnaMohasebeh) {
        this.mabnaMohasebeh = mabnaMohasebeh;
    }

    public String getSharhMabnaMohasebeh() {
        return sharhMabnaMohasebeh;
    }

    public void setSharhMabnaMohasebeh(String sharhMabnaMohasebeh) {
        this.sharhMabnaMohasebeh = sharhMabnaMohasebeh;
    }

    public String getTxtNoeMohasebeh() {
        return txtNoeMohasebeh;
    }

    public void setTxtNoeMohasebeh(String txtNoeMohasebeh) {
        this.txtNoeMohasebeh = txtNoeMohasebeh;
    }

    public String getMohasebehBarAsase() {
        return mohasebehBarAsase;
    }

    public void setMohasebehBarAsase(String mohasebehBarAsase) {
        this.mohasebehBarAsase = mohasebehBarAsase;
    }

    public String getAzTarikhJashnvareh() {
        return azTarikhJashnvareh;
    }

    public void setAzTarikhJashnvareh(String azTarikhJashnvareh) {
        this.azTarikhJashnvareh = azTarikhJashnvareh;
    }

    public String getTaTarikhJashnvareh() {
        return taTarikhJashnvareh;
    }

    public void setTaTarikhJashnvareh(String taTarikhJashnvareh) {
        this.taTarikhJashnvareh = taTarikhJashnvareh;
    }

    public String getAzTarikhJashnvarehSatr() {
        return azTarikhJashnvarehSatr;
    }

    public void setAzTarikhJashnvarehSatr(String azTarikhJashnvarehSatr) {
        this.azTarikhJashnvarehSatr = azTarikhJashnvarehSatr;
    }

    public String getTaTarikhJashnvarehSatr() {
        return taTarikhJashnvarehSatr;
    }

    public void setTaTarikhJashnvarehSatr(String taTarikhJashnvarehSatr) {
        this.taTarikhJashnvarehSatr = taTarikhJashnvarehSatr;
    }

    public String getTxtCodeNoeBastehBandy() {
        return txtCodeNoeBastehBandy;
    }

    public void setTxtCodeNoeBastehBandy(String txtCodeNoeBastehBandy) {
        this.txtCodeNoeBastehBandy = txtCodeNoeBastehBandy;
    }

    public String getTxtCodeNoeBastehBandyBeEza() {
        return txtCodeNoeBastehBandyBeEza;
    }

    public void setTxtCodeNoeBastehBandyBeEza(String txtCodeNoeBastehBandyBeEza) {
        this.txtCodeNoeBastehBandyBeEza = txtCodeNoeBastehBandyBeEza;
    }

    public double getAz() {
        return az;
    }

    public void setAz(double az) {
        this.az = az;
    }

    public double getTa() {
        return ta;
    }

    public void setTa(double ta) {
        this.ta = ta;
    }

    public double getBeEza() {
        return beEza;
    }

    public void setBeEza(double beEza) {
        this.beEza = beEza;
    }

    public int getEmtiazJashnvareh() {
        return emtiazJashnvareh;
    }

    public void setEmtiazJashnvareh(int emtiazJashnvareh) {
        this.emtiazJashnvareh = emtiazJashnvareh;
    }

    public double getRialYekEmtiazJashnvareh() {
        return rialYekEmtiazJashnvareh;
    }

    public void setRialYekEmtiazJashnvareh(double rialYekEmtiazJashnvareh) {
        this.rialYekEmtiazJashnvareh = rialYekEmtiazJashnvareh;
    }

    public int getEmtiazMoshtary() {
        return emtiazMoshtary;
    }

    public void setEmtiazMoshtary(int emtiazMoshtary) {
        this.emtiazMoshtary = emtiazMoshtary;
    }

    public double getRialEmtiazMoshtary() {
        return rialEmtiazMoshtary;
    }

    public void setRialEmtiazMoshtary(double rialEmtiazMoshtary) {
        this.rialEmtiazMoshtary = rialEmtiazMoshtary;
    }

    public int getDoreh() {
        return doreh;
    }

    public void setDoreh(int doreh) {
        this.doreh = doreh;
    }

    public String getTarikhMohasebehEmtiaz() {
        return tarikhMohasebehEmtiaz;
    }

    public void setTarikhMohasebehEmtiaz(String tarikhMohasebehEmtiaz) {
        this.tarikhMohasebehEmtiaz = tarikhMohasebehEmtiaz;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public ArrayList<RptJashnvarehForoshModel> getRptJashnvarehForoshModels() {
        return rptJashnvarehForoshModels;
    }

    public void setRptJashnvarehForoshModels(ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
        this.rptJashnvarehForoshModels = rptJashnvarehForoshModels;
    }
}



