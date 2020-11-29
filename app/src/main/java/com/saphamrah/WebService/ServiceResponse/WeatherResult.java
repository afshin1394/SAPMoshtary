package com.saphamrah.WebService.ServiceResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Model.WeatherDataModel;
import com.saphamrah.Model.WeatherModel;
import com.saphamrah.Model.WindModel;

import java.util.List;

public class WeatherResult
{

    /*@SerializedName("coord")
    @Expose
    private Coord coord;*/
    @SerializedName("weather")
    @Expose
    private List<WeatherModel> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private WeatherDataModel weatherData;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("wind")
    @Expose
    private WindModel wind;
    /*@SerializedName("clouds")
    @Expose
    private Clouds clouds;*/
    @SerializedName("dt")
    @Expose
    private Integer dt;
    /*@SerializedName("sys")
    @Expose
    private Sys sys;*/
    @SerializedName("timezone")
    @Expose
    private Integer timezone;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private Integer cod;

    /*public Coord getCoord() {
        return coord;
    }
    public void setCoord(Coord coord) {
        this.coord = coord;
    }*/


    public List<WeatherModel> getWeather() {
        return weather;
    }
    public void setWeather(List<WeatherModel> weather) {
        this.weather = weather;
    }


    public String getBase() {
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }


    public WeatherDataModel getWeatherData() {
        return weatherData;
    }
    public void setWeatherData(WeatherDataModel weatherData) {
        this.weatherData = weatherData;
    }


    public Integer getVisibility() {
        return visibility;
    }
    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }


    public WindModel getWind() {
        return wind;
    }
    public void setWind(WindModel wind) {
        this.wind = wind;
    }


    /*public Clouds getClouds() {
        return clouds;
    }
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }*/


    public Integer getDt() {
        return dt;
    }
    public void setDt(Integer dt) {
        this.dt = dt;
    }


    /*public Sys getSys() {
        return sys;
    }
    public void setSys(Sys sys) {
        this.sys = sys;
    }*/


    public Integer getTimezone() {
        return timezone;
    }
    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public Integer getCod() {
        return cod;
    }
    public void setCod(Integer cod) {
        this.cod = cod;
    }

}
