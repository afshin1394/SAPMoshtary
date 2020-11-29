package com.saphamrah.UIModel;


import java.util.Arrays;

public class CustomerAdamDarkhastModel
{

    //AdamDarkhast
    private int ccAdamDarkhast;
    private int ccForoshandeh;
    private int ccMoshtary;
    private int ccElatAdamDarkhast;
    private String TarikhAdamDarkhast;
    private float Latitude;
    private float Longitude;
    private boolean IsSentToServer;
    private byte[] AdamDarkhastImage;
    private String CodeMoshtaryTekrari;
    //Moshtary
    private String fullNameMoshtary;
    private String codeMoshtary;
    //elatAdamDarkhast
    private String nameElatAdamDarkhast;


    public int getCcAdamDarkhast() {
        return ccAdamDarkhast;
    }

    public void setCcAdamDarkhast(int ccAdamDarkhast) {
        this.ccAdamDarkhast = ccAdamDarkhast;
    }

    public int getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcElatAdamDarkhast() {
        return ccElatAdamDarkhast;
    }

    public void setCcElatAdamDarkhast(int ccElatAdamDarkhast) {
        this.ccElatAdamDarkhast = ccElatAdamDarkhast;
    }

    public String getTarikhAdamDarkhast() {
        return TarikhAdamDarkhast;
    }

    public void setTarikhAdamDarkhast(String tarikhAdamDarkhast) {
        TarikhAdamDarkhast = tarikhAdamDarkhast;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public boolean isSentToServer() {
        return IsSentToServer;
    }

    public void setSentToServer(boolean sentToServer) {
        IsSentToServer = sentToServer;
    }

    public byte[] getAdamDarkhastImage() {
        return AdamDarkhastImage;
    }

    public void setAdamDarkhastImage(byte[] adamDarkhastImage) {
        AdamDarkhastImage = adamDarkhastImage;
    }

    public String getCodeMoshtaryTekrari() {
        return CodeMoshtaryTekrari;
    }

    public void setCodeMoshtaryTekrari(String codeMoshtaryTekrari) {
        CodeMoshtaryTekrari = codeMoshtaryTekrari;
    }

    public String getFullNameMoshtary() {
        return fullNameMoshtary;
    }

    public void setFullNameMoshtary(String fullNameMoshtary) {
        this.fullNameMoshtary = fullNameMoshtary;
    }

    public String getCodeMoshtary() {
        return codeMoshtary;
    }

    public void setCodeMoshtary(String codeMoshtary) {
        this.codeMoshtary = codeMoshtary;
    }

    public String getNameElatAdamDarkhast() {
        return nameElatAdamDarkhast;
    }

    public void setNameElatAdamDarkhast(String nameElatAdamDarkhast) {
        this.nameElatAdamDarkhast = nameElatAdamDarkhast;
    }


    @Override
    public String toString() {
        return "CustomerAdamDarkhastModel{" +
                "ccAdamDarkhast=" + ccAdamDarkhast +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccMoshtary=" + ccMoshtary +
                ", ccElatAdamDarkhast=" + ccElatAdamDarkhast +
                ", TarikhAdamDarkhast='" + TarikhAdamDarkhast + '\'' +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                ", IsSentToServer=" + IsSentToServer +
                ", AdamDarkhastImage=" + Arrays.toString(AdamDarkhastImage) +
                ", CodeMoshtaryTekrari='" + CodeMoshtaryTekrari + '\'' +
                ", fullNameMoshtary='" + fullNameMoshtary + '\'' +
                ", codeMoshtary='" + codeMoshtary + '\'' +
                ", nameElatAdamDarkhast='" + nameElatAdamDarkhast + '\'' +
                '}';
    }
}
