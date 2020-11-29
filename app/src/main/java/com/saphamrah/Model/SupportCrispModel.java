package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportCrispModel
{



    @SerializedName("ccSupportCrisp")
    @Expose
    private int ccSupportCrisp;
    @SerializedName("ccSazmanForosh")
    @Expose
    private int ccSazmanForosh;
    @SerializedName("CrispID")
    @Expose
    private String crispID;



    public int getCcSupportCrisp() {
        return ccSupportCrisp;
    }

    public void setCcSupportCrisp(int ccSupportCrisp) {
        this.ccSupportCrisp = ccSupportCrisp;
    }

    public int getCcSazmanForosh() {
        return ccSazmanForosh;
    }

    public void setCcSazmanForosh(int ccSazmanForosh) {
        this.ccSazmanForosh = ccSazmanForosh;
    }

    public String getCrispID() {
        return crispID;
    }

    public void setCrispID(String crispID) {
        this.crispID = crispID;
    }



    public String TABLE_NAME = "SupportCrisp";
    public String getOLUMN_ccSupportCrisp = "ccSupportCrisp";
    public String getCOLUMN_ccSazmanForosh = "ccSazmanForosh";
    public String getCOLUMN_CrispID = "CrispID";

}
