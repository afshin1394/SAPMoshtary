package com.saphamrah.WebService.ServiceResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVersionResult
{

    @SerializedName("StableVersion")
    @Expose
    private String stableVersion;
    @SerializedName("NewVersion")
    @Expose
    private String newVersion;
    @SerializedName("DateNewVersion")
    @Expose
    private String dateNewVersion;
    @SerializedName("Afrad")
    @Expose
    private String afrad;
    @SerializedName("URL")
    @Expose
    private String URL;
    @SerializedName("AzmayeshVersion")
    @Expose
    private String AzmayeshVersion;
    @SerializedName("ccMarkazSazmanSakhtarForosh")
    @Expose
    private String ccMarkazSazmanSakhtarForosh;
    @SerializedName("ccMarkazAnbar")
    @Expose
    private String ccMarkazAnbar;
    @SerializedName("URLAzmayeshVersion")
    @Expose
    private String URLAzmayeshVersion;
    @SerializedName("URLOSRM")
    @Expose
    private String URLOSRM;



    public String getStableVersion() {
        return stableVersion;
    }

    public void setStableVersion(String stableVersion) {
        this.stableVersion = stableVersion;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getDateNewVersion() {
        return dateNewVersion;
    }

    public void setDateNewVersion(String dateNewVersion) {
        this.dateNewVersion = dateNewVersion;
    }

    public String getAfrad() {
        return afrad;
    }

    public void setAfrad(String afrad) {
        this.afrad = afrad;
    }

    public String getURL()
    {
        return URL;
    }

    public void setURL(String URL)
    {
        this.URL = URL;
    }

    public String getAzmayeshVersion()
    {
        return AzmayeshVersion;
    }

    public void setAzmayeshVersion(String azmayeshVersion)
    {
        AzmayeshVersion = azmayeshVersion;
    }

    public String getCcMarkazSazmanSakhtarForosh()
    {
        return ccMarkazSazmanSakhtarForosh;
    }

    public void setCcMarkazSazmanSakhtarForosh(String ccMarkazSazmanSakhtarForosh)
    {
        this.ccMarkazSazmanSakhtarForosh = ccMarkazSazmanSakhtarForosh;
    }

    public String getCcMarkazAnbar()
    {
        return ccMarkazAnbar;
    }

    public void setCcMarkazAnbar(String ccMarkazAnbar)
    {
        this.ccMarkazAnbar = ccMarkazAnbar;
    }

    public String getURLAzmayeshVersion()
    {
        return URLAzmayeshVersion;
    }

    public void setURLAzmayeshVersion(String URLAzmayeshVersion)
    {
        this.URLAzmayeshVersion = URLAzmayeshVersion;
    }

    public String getURLOSRM()
    {
        return URLOSRM;
    }

    public void setURLOSRM(String URLOSRM)
    {
        this.URLOSRM = URLOSRM;
    }
}
