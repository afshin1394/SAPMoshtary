package com.saphamrah.Valhalla;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location
{

    public Location()
    {

    }

    public Location(double lat, double lon)
    {
        this.lat = lat;
        this.lon = lon;
    }


    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("original_index")
    @Expose
    private Integer originalIndex;
    @SerializedName("side_of_street")
    @Expose
    private String sideOfStreet;

    public Integer getOriginalIndex() {
        return originalIndex;
    }

    public void setOriginalIndex(Integer originalIndex) {
        this.originalIndex = originalIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getSideOfStreet() {
        return sideOfStreet;
    }

    public void setSideOfStreet(String sideOfStreet) {
        this.sideOfStreet = sideOfStreet;
    }

}
