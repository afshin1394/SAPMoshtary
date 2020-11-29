package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwghatModel
{

    @SerializedName("azan_sobh")
    @Expose
    private String azanSobh;
    @SerializedName("tolu_aftab")
    @Expose
    private String toluAftab;
    @SerializedName("azan_zohr")
    @Expose
    private String azanZohr;
    @SerializedName("ghorub_aftab")
    @Expose
    private String ghorubAftab;
    @SerializedName("azan_maghreb")
    @Expose
    private String azanMaghreb;
    @SerializedName("nimeshab")
    @Expose
    private String nimeshab;
    @SerializedName("month")
    @Expose
    private Integer month;
    @SerializedName("day")
    @Expose
    private Integer day;

    public String getAzanSobh() {
        return azanSobh;
    }

    public void setAzanSobh(String azanSobh) {
        this.azanSobh = azanSobh;
    }

    public String getToluAftab() {
        return toluAftab;
    }

    public void setToluAftab(String toluAftab) {
        this.toluAftab = toluAftab;
    }

    public String getAzanZohr() {
        return azanZohr;
    }

    public void setAzanZohr(String azanZohr) {
        this.azanZohr = azanZohr;
    }

    public String getGhorubAftab() {
        return ghorubAftab;
    }

    public void setGhorubAftab(String ghorubAftab) {
        this.ghorubAftab = ghorubAftab;
    }

    public String getAzanMaghreb() {
        return azanMaghreb;
    }

    public void setAzanMaghreb(String azanMaghreb) {
        this.azanMaghreb = azanMaghreb;
    }

    public String getNimeshab() {
        return nimeshab;
    }

    public void setNimeshab(String nimeshab) {
        this.nimeshab = nimeshab;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

}
