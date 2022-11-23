package com.saphamrah.customer.data.local;

public class SaleInfoModel {
    private String noeSenf;
    private String noeFaaliat;
    private String darajeMoshtary;
    private String noeVosol;
    private int modateVosol;
    private String sazmanForosh;

    public SaleInfoModel(String noeSenf, String noeFaaliat, String darajeMoshtary, String noeVosol, int modateVosol, String sazmanForosh) {
        this.noeSenf = noeSenf;
        this.noeFaaliat = noeFaaliat;
        this.darajeMoshtary = darajeMoshtary;
        this.noeVosol = noeVosol;
        this.modateVosol = modateVosol;
        this.sazmanForosh = sazmanForosh;
    }

    public String getNoeSenf() {
        return noeSenf;
    }

    public void setNoeSenf(String noeSenf) {
        this.noeSenf = noeSenf;
    }

    public String getNoeFaaliat() {
        return noeFaaliat;
    }

    public void setNoeFaaliat(String noeFaaliat) {
        this.noeFaaliat = noeFaaliat;
    }

    public String getDarajeMoshtary() {
        return darajeMoshtary;
    }

    public void setDarajeMoshtary(String darajeMoshtary) {
        this.darajeMoshtary = darajeMoshtary;
    }

    public String getNoeVosol() {
        return noeVosol;
    }

    public void setNoeVosol(String noeVosol) {
        this.noeVosol = noeVosol;
    }

    public int getModateVosol() {
        return modateVosol;
    }

    public void setModateVosol(int modateVosol) {
        this.modateVosol = modateVosol;
    }

    public String getSazmanForosh() {
        return sazmanForosh;
    }

    public void setSazmanForosh(String sazmanForosh) {
        this.sazmanForosh = sazmanForosh;
    }
}
