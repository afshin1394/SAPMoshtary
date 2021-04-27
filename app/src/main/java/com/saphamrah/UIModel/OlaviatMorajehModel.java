package com.saphamrah.UIModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaviatMorajehModel {

    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("NoeMorajeh")
    @Expose
    private int NoeMorajeh;
    @SerializedName("Olaviat")
    @Expose
    private int Olaviat;


    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public int getNoeMorajeh() {
        return NoeMorajeh;
    }

    public void setNoeMorajeh(int noeMorajeh) {
        NoeMorajeh = noeMorajeh;
    }

    public int getOlaviat() {
        return Olaviat;
    }

    public void setOlaviat(int olaviat) {
        Olaviat = olaviat;
    }

    public String getCOLUMN_ccMoshtary() {
        return "ccMoshtary";
    }
    public String getCOLUMN_NoeMorajeh() {
        return "NoeMorajeh";
    }
    public String getCOLUMN_Olaviat() {
        return "Olaviat";
    }

}
