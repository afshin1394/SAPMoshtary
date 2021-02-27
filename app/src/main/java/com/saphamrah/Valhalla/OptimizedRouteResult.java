package com.saphamrah.Valhalla;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptimizedRouteResult
{
    @SerializedName("trip")
    @Expose
    private Trip trip;
    @SerializedName("id")
    @Expose
    private String id;


    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }


    public Trip getTrip() {
        return trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }


}
