package com.saphamrah.Model;

import com.google.gson.annotations.SerializedName;

public class NoePishnahadModel {

    public static final String TABLE_NAME = "NoePishnahad";


    @SerializedName("ccNoePishnehad")
    private int ccNoePishnahad;
    @SerializedName("NameNoePishnehad")
    private String NameNoePishnahad;
    @SerializedName("NoePishnehad")
    private int NoePishnahad;



    public String getCOLUMN_ccNoePishnahad() {
        return "ccNoePishnehad";
    }
    public String getCOLUMN_NameNoePishnahad() {
        return "NameNoePishnehad";
    }
    public String getCOLUMN_NoePishnahad() {
        return "NoePishnehad";
    }


    public int getCcNoePishnahad() {
        return ccNoePishnahad;
    }

    public void setCcNoePishnahad(int ccNoePishnahad) {
        this.ccNoePishnahad = ccNoePishnahad;
    }

    public String getNameNoePishnahad() {
        return NameNoePishnahad;
    }

    public void setNameNoePishnahad(String nameNoePishnahad) {
        this.NameNoePishnahad = nameNoePishnahad;
    }

    public int getNoePishnahad() {
        return NoePishnahad;
    }

    public void setNoePishnahad(int noePishnahad) {
        NoePishnahad = noePishnahad;
    }
}
