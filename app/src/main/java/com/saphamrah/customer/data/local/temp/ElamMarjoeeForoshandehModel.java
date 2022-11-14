package com.saphamrah.customer.data.local.temp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElamMarjoeeForoshandehModel {


    @SerializedName("ccElamMarjoeeSatr")
    @Expose
    private int ccElamMarjoeeSatr;
    @SerializedName("ccElamMarjoee")
    @Expose
    private int ccElamMarjoee;
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("ccDarkhastFaktor")
    @Expose
    private String ccDarkhastFaktor;
    @SerializedName("ccKala")
    @Expose
    private int ccKala;
    @SerializedName("ccKalaCode")
    @Expose
    private int ccKalaCode;
    @SerializedName("CodeKala")
    @Expose
    private String CodeKala;
    @SerializedName("NameKala")
    @Expose
    private String NameKala;
    @SerializedName("ShomarehBach")
    @Expose
    private String ShomarehBach;
    @SerializedName("TarikhTolid")
    @Expose
    private String TarikhTolid;
    @SerializedName("TarikhTolidShamsi")
    @Expose
    private String TarikhTolidShamsi;
    @SerializedName("TarikhEngheza")
    @Expose
    private String TarikhEngheza;
    @SerializedName("ccElatMarjoeeKala")
    @Expose
    private int ccElatMarjoeeKala;
    @SerializedName("NameElatMarjoeeKala")
    @Expose
    private String NameElatMarjoeeKala;
    @SerializedName("Tedad3")
    @Expose
    private int Tedad3;
    @SerializedName("ccTaminKonandeh")
    @Expose
    private int ccTaminKonandeh;
    @SerializedName("NameMoshtary")
    @Expose
    private String NameMoshtary;
    @SerializedName("FullNameForoshandeh")
    @Expose
    private String FullNameForoshandeh;
    @SerializedName("ShomarehFaktor")
    @Expose
    private String ShomarehFaktor;
    @SerializedName("ccAnbar")
    @Expose
    private int ccAnbar;
    @SerializedName("ExtraProp_TedadNahaeeMarjoee")
    @Expose
    private int ExtraProp_TedadNahaeeMarjoee;
    @SerializedName("GheymatKharid")
    @Expose
    private float GheymatKharid;
    @SerializedName("GheymatForosh")
    @Expose
    private float GheymatForosh;
    @SerializedName("GheymatForoshKhales")
    @Expose
    private float GheymatForoshKhales;
    @SerializedName("GheymatMasrafKonandeh")
    @Expose
    private float GheymatMasrafKonandeh;
    @SerializedName("ccAnbarGhesmat")
    @Expose
    private int ccAnbarGhesmat;
    @SerializedName("GheymatForoshAsli")
    @Expose
    private float GheymatForoshAsli;
    @SerializedName("ccForoshandeh")
    @Expose
    private int ccForoshandeh;

    public ElamMarjoeeForoshandehModel(int ccElamMarjoeeSatr, String codeKala, String nameKala, String shomarehBach, String tarikhTolid, String tarikhEngheza, int tedad3, float gheymatForosh) {
        this.ccElamMarjoeeSatr = ccElamMarjoeeSatr;
        CodeKala = codeKala;
        NameKala = nameKala;
        ShomarehBach = shomarehBach;
        TarikhTolid = tarikhTolid;
        TarikhEngheza = tarikhEngheza;
        Tedad3 = tedad3;
        GheymatForosh = gheymatForosh;
    }

    public int getCcElamMarjoeeSatr() {
        return ccElamMarjoeeSatr;
    }

    public void setCcElamMarjoeeSatr(int ccElamMarjoeeSatr) {
        this.ccElamMarjoeeSatr = ccElamMarjoeeSatr;
    }

    public int getCcElamMarjoee() {
        return ccElamMarjoee;
    }

    public void setCcElamMarjoee(int ccElamMarjoee) {
        this.ccElamMarjoee = ccElamMarjoee;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(String ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getCcKala() {
        return ccKala;
    }

    public void setCcKala(int ccKala) {
        this.ccKala = ccKala;
    }

    public int getCcKalaCode() {
        return ccKalaCode;
    }

    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }

    public String getCodeKala() {
        return CodeKala;
    }

    public void setCodeKala(String codeKala) {
        CodeKala = codeKala;
    }

    public String getNameKala() {
        return NameKala;
    }

    public void setNameKala(String nameKala) {
        NameKala = nameKala;
    }

    public String getShomarehBach() {
        return ShomarehBach;
    }

    public void setShomarehBach(String shomarehBach) {
        ShomarehBach = shomarehBach;
    }

    public String getTarikhTolid() {
        return TarikhTolid;
    }

    public void setTarikhTolid(String tarikhTolid) {
        TarikhTolid = tarikhTolid;
    }

    public String getTarikhTolidShamsi() {
        return TarikhTolidShamsi;
    }

    public void setTarikhTolidShamsi(String tarikhTolidShamsi) {
        TarikhTolidShamsi = tarikhTolidShamsi;
    }

    public String getTarikhEngheza() {
        return TarikhEngheza;
    }

    public void setTarikhEngheza(String tarikhEngheza) {
        TarikhEngheza = tarikhEngheza;
    }

    public int getCcElatMarjoeeKala() {
        return ccElatMarjoeeKala;
    }

    public void setCcElatMarjoeeKala(int ccElatMarjoeeKala) {
        this.ccElatMarjoeeKala = ccElatMarjoeeKala;
    }

    public String getNameElatMarjoeeKala() {
        return NameElatMarjoeeKala;
    }

    public void setNameElatMarjoeeKala(String nameElatMarjoeeKala) {
        NameElatMarjoeeKala = nameElatMarjoeeKala;
    }

    public int getTedad3() {
        return Tedad3;
    }

    public void setTedad3(int tedad3) {
        Tedad3 = tedad3;
    }

    public int getCcTaminKonandeh() {
        return ccTaminKonandeh;
    }

    public void setCcTaminKonandeh(int ccTaminKonandeh) {
        this.ccTaminKonandeh = ccTaminKonandeh;
    }

    public String getNameMoshtary() {
        return NameMoshtary;
    }

    public void setNameMoshtary(String nameMoshtary) {
        NameMoshtary = nameMoshtary;
    }

    public String getFullNameForoshandeh() {
        return FullNameForoshandeh;
    }

    public void setFullNameForoshandeh(String fullNameForoshandeh) {
        FullNameForoshandeh = fullNameForoshandeh;
    }

    public String getShomarehFaktor() {
        return ShomarehFaktor;
    }

    public void setShomarehFaktor(String shomarehFaktor) {
        ShomarehFaktor = shomarehFaktor;
    }

    public int getCcAnbar() {
        return ccAnbar;
    }

    public void setCcAnbar(int ccAnbar) {
        this.ccAnbar = ccAnbar;
    }

    public int getExtraProp_TedadNahaeeMarjoee() {
        return ExtraProp_TedadNahaeeMarjoee;
    }

    public void setExtraProp_TedadNahaeeMarjoee(int extraProp_TedadNahaeeMarjoee) {
        ExtraProp_TedadNahaeeMarjoee = extraProp_TedadNahaeeMarjoee;
    }

    public float getGheymatKharid() {
        return GheymatKharid;
    }

    public void setGheymatKharid(float gheymatKharid) {
        GheymatKharid = gheymatKharid;
    }

    public float getGheymatForosh() {
        return GheymatForosh;
    }

    public void setGheymatForosh(float gheymatForosh) {
        GheymatForosh = gheymatForosh;
    }

    public float getGheymatForoshKhales() {
        return GheymatForoshKhales;
    }

    public void setGheymatForoshKhales(float gheymatForoshKhales) {
        GheymatForoshKhales = gheymatForoshKhales;
    }

    public float getGheymatMasrafKonandeh() {
        return GheymatMasrafKonandeh;
    }

    public void setGheymatMasrafKonandeh(float gheymatMasrafKonandeh) {
        GheymatMasrafKonandeh = gheymatMasrafKonandeh;
    }

    public int getCcAnbarGhesmat() {
        return ccAnbarGhesmat;
    }

    public void setCcAnbarGhesmat(int ccAnbarGhesmat) {
        this.ccAnbarGhesmat = ccAnbarGhesmat;
    }

    public float getGheymatForoshAsli() {
        return GheymatForoshAsli;
    }

    public void setGheymatForoshAsli(float gheymatForoshAsli) {
        GheymatForoshAsli = gheymatForoshAsli;
    }

    public int getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public String getTABLE_NAME() {
        return "ElamMarjoeeForoshandeh";
    }




    /**
     * add for adapter
     */
    @SerializedName("ExtraProp_TedadMarjoee")
    @Expose
    private int ExtraProp_TedadMarjoee;

    public int getExtraProp_TedadMarjoee() {
        return ExtraProp_TedadMarjoee;
    }

    public void setExtraProp_TedadMarjoee(int extraProp_TedadMarjoee) {
        ExtraProp_TedadMarjoee = extraProp_TedadMarjoee;
    }
}
