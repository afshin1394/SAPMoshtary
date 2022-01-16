package com.saphamrah.UIModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KalaFilterUiModel {
    @SerializedName("ccGoroh")
    @Expose
    private int ccGoroh;
    @SerializedName("NameGoroh")
    @Expose
    private String NameGoroh;

    public int getCcGoroh() {
        return ccGoroh;
    }

    public void setCcGoroh(int ccGoroh) {
        this.ccGoroh = ccGoroh;
    }

    public String getNameGoroh() {
        return NameGoroh;
    }

    public void setNameGoroh(String nameGoroh) {
        NameGoroh = nameGoroh;
    }

    public String getCOLUMN_ccGoroh() {
        return "ccGoroh";
    }
    public String getCOLUMN_NameGoroh() {
        return "NameGoroh";
    }

}
