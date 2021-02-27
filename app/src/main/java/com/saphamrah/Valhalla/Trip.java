package com.saphamrah.Valhalla;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trip
{
    @SerializedName("locations")
    @Expose
    private List<Location> locations = null;
    @SerializedName("legs")
    @Expose
    private List<Leg> legs = null;
    @SerializedName("summary")
    @Expose
    private Summary_ summary;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("language")
    @Expose
    private String language;


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public Summary_ getSummary() {
        return summary;
    }

    public void setSummary(Summary_ summary) {
        this.summary = summary;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

}
