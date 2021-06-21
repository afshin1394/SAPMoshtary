package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KalaOlaviatGheymatModel {

    @SerializedName("ccKalaCode")
    @Expose
    private int cckalaCode;
    @SerializedName("Olaviat")
    @Expose
    private int Olaviat;
    @SerializedName("GheymatForosh")
    @Expose
    private double GheymatForosh;
    @SerializedName("Tedad")
    @Expose
    private int Tedad;
    @SerializedName("TarikhTolid")
    @Expose
    private String TarikhTolid;
    @SerializedName("Radif")
    @Expose
    private int Radif;

    public int getCckalaCode() {
        return cckalaCode;
    }

    public void setCckalaCode(int cckalaCode) {
        this.cckalaCode = cckalaCode;
    }

    public int getOlaviat() {
        return Olaviat;
    }

    public void setOlaviat(int olaviat) {
        Olaviat = olaviat;
    }

    public double getGheymatForosh() {
        return GheymatForosh;
    }

    public void setGheymatForosh(double gheymatForosh) {
        GheymatForosh = gheymatForosh;
    }

    public int getTedad() {
        return Tedad;
    }

    public void setTedad(int tedad) {
        Tedad = tedad;
    }

    public String getTarikhTolid() {
        return TarikhTolid;
    }

    public void setTarikhTolid(String tarikhTolid) {
        TarikhTolid = tarikhTolid;
    }

    public int getRadif() {
        return Radif;
    }

    public void setRadif(int radif) {
        Radif = radif;
    }

    public String getTABLE_NAME() {
        return "KalaOlaviatGheymat";
    }
    public String getCOLUMN_cckalaCode() {
        return "ccKalaCode";
    }
    public String getCOLUMN_Olaviat() {
        return "Olaviat";
    }
    public String getCOLUMN_GheymatForosh() {
        return "GheymatForosh";
    }
    public String getCOLUMN_Tedad() {
        return "Tedad";
    }
    public String getCOLUMN_TarikhTolid() {
        return "TarikhTolid";
    }
    public String getCOLUMN_Radif() {
        return "Radif";
    }
}
