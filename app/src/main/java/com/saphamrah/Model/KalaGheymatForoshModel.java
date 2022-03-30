package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class KalaGheymatForoshModel {
    public  final String TABLE_NAME = "KalaGheymatForosh";
    public  final String COLUMN_ccKalaGheymatForosh = "ccKalaGheymatForosh";
    public  final String COLUMN_ccKalaCode = "ccKalaCode";
    public  final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    public  final String COLUMN_ccSazmanForosh = "ccSazmanForosh";
    public  final String COLUMN_ccNoeMoshtary = "ccNoeMoshtary";
    public  final String COLUMN_ccNoeSenf = "ccNoeSenf";
    public  final String COLUMN_ccDarajeh = "ccDarajeh";
    public  final String COLUMN_ZaribAfzayeshGheymat = "ZaribAfzayeshGheymat";

    private int ccKalaGheymatForosh;
    private int ccKalaCode;
    private int ccMarkazForosh;
    private int ccSazmanForosh;
    private int ccNoeMoshtary;
    private int ccNoeSenf;
    private int ccDarajeh;
    private double ZaribAfzayeshGheymat;


    public int getCcKalaGheymatForosh() {
        return ccKalaGheymatForosh;
    }

    public void setCcKalaGheymatForosh(int ccKalaGheymatForosh) {
        this.ccKalaGheymatForosh = ccKalaGheymatForosh;
    }

    public int getCcKalaCode() {
        return ccKalaCode;
    }

    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }

    public int getCcMarkazForosh() {
        return ccMarkazForosh;
    }

    public void setCcMarkazForosh(int ccMarkazForosh) {
        this.ccMarkazForosh = ccMarkazForosh;
    }

    public int getCcSazmanForosh() {
        return ccSazmanForosh;
    }

    public void setCcSazmanForosh(int ccSazmanForosh) {
        this.ccSazmanForosh = ccSazmanForosh;
    }

    public int getCcNoeMoshtary() {
        return ccNoeMoshtary;
    }

    public void setCcNoeMoshtary(int ccNoeMoshtary) {
        this.ccNoeMoshtary = ccNoeMoshtary;
    }

    public int getCcNoeSenf() {
        return ccNoeSenf;
    }

    public void setCcNoeSenf(int ccNoeSenf) {
        this.ccNoeSenf = ccNoeSenf;
    }

    public int getCcDarajeh() {
        return ccDarajeh;
    }

    public void setCcDarajeh(int ccDarajeh) {
        this.ccDarajeh = ccDarajeh;
    }

    public double getZaribAfzayeshGheymat() {
        return ZaribAfzayeshGheymat;
    }

    public void setZaribAfzayeshGheymat(double zaribAfzayeshGheymat) {
        ZaribAfzayeshGheymat = zaribAfzayeshGheymat;
    }

    @NonNull
    @Override
    public String toString()
    {
        return  "ccKalaGheymatForosh=" + ccKalaGheymatForosh +
                "ccKalaCode=" + ccKalaCode +
                "ccMarkazForosh=" + ccMarkazForosh +
                "ccSazmanForosh=" + ccSazmanForosh +
                "ccNoeMoshtary=" + ccNoeMoshtary +
                "ccNoeSenf=" + ccNoeSenf +
                "ccDarajeh=" + ccDarajeh +
                ", ZaribAfzayeshGheymat=" + ZaribAfzayeshGheymat;
    }

}
