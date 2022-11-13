package com.saphamrah.customer.data.local.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MoshtarySazmanForoshTable {

    @PrimaryKey
    private int ccMoshtarySazmanForosh;

    private int ccMoshtary;
    private  int ccMarkazForosh;
    private int ccSazmanForosh;
    private int ccMarkazForoshSazmanForosh;
    private int ccDarajeh;
    private int ccNoeMoshtary;
    private int ccSenfMoshtary;

    private String markazForoshTitle;
    private String sazmanForoshTitle;
    private String darajehTitle;
    private String noeMoshtaryTitle;
    private String senfMoshtaryTitle;

    public MoshtarySazmanForoshTable(int ccMoshtarySazmanForosh, int ccMoshtary, int ccMarkazForosh, int ccSazmanForosh, int ccMarkazForoshSazmanForosh, int ccDarajeh, int ccNoeMoshtary, int ccSenfMoshtary, String markazForoshTitle, String sazmanForoshTitle, String darajehTitle, String noeMoshtaryTitle, String senfMoshtaryTitle) {
        this.ccMoshtarySazmanForosh = ccMoshtarySazmanForosh;
        this.ccMoshtary = ccMoshtary;
        this.ccMarkazForosh = ccMarkazForosh;
        this.ccSazmanForosh = ccSazmanForosh;
        this.ccMarkazForoshSazmanForosh = ccMarkazForoshSazmanForosh;
        this.ccDarajeh = ccDarajeh;
        this.ccNoeMoshtary = ccNoeMoshtary;
        this.ccSenfMoshtary = ccSenfMoshtary;
        this.markazForoshTitle = markazForoshTitle;
        this.sazmanForoshTitle = sazmanForoshTitle;
        this.darajehTitle = darajehTitle;
        this.noeMoshtaryTitle = noeMoshtaryTitle;
        this.senfMoshtaryTitle = senfMoshtaryTitle;
    }

    public int getCcMoshtarySazmanForosh() {
        return ccMoshtarySazmanForosh;
    }

    public void setCcMoshtarySazmanForosh(int ccMoshtarySazmanForosh) {
        this.ccMoshtarySazmanForosh = ccMoshtarySazmanForosh;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
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

    public int getCcMarkazForoshSazmanForosh() {
        return ccMarkazForoshSazmanForosh;
    }

    public void setCcMarkazForoshSazmanForosh(int ccMarkazForoshSazmanForosh) {
        this.ccMarkazForoshSazmanForosh = ccMarkazForoshSazmanForosh;
    }

    public int getCcDarajeh() {
        return ccDarajeh;
    }

    public void setCcDarajeh(int ccDarajeh) {
        this.ccDarajeh = ccDarajeh;
    }

    public int getCcNoeMoshtary() {
        return ccNoeMoshtary;
    }

    public void setCcNoeMoshtary(int ccNoeMoshtary) {
        this.ccNoeMoshtary = ccNoeMoshtary;
    }

    public int getCcSenfMoshtary() {
        return ccSenfMoshtary;
    }

    public void setCcSenfMoshtary(int ccSenfMoshtary) {
        this.ccSenfMoshtary = ccSenfMoshtary;
    }

    public String getMarkazForoshTitle() {
        return markazForoshTitle;
    }

    public void setMarkazForoshTitle(String markazForoshTitle) {
        this.markazForoshTitle = markazForoshTitle;
    }

    public String getSazmanForoshTitle() {
        return sazmanForoshTitle;
    }

    public void setSazmanForoshTitle(String sazmanForoshTitle) {
        this.sazmanForoshTitle = sazmanForoshTitle;
    }

    public String getDarajehTitle() {
        return darajehTitle;
    }

    public void setDarajehTitle(String darajehTitle) {
        this.darajehTitle = darajehTitle;
    }

    public String getNoeMoshtaryTitle() {
        return noeMoshtaryTitle;
    }

    public void setNoeMoshtaryTitle(String noeMoshtaryTitle) {
        this.noeMoshtaryTitle = noeMoshtaryTitle;
    }

    public String getSenfMoshtaryTitle() {
        return senfMoshtaryTitle;
    }

    public void setSenfMoshtaryTitle(String senfMoshtaryTitle) {
        this.senfMoshtaryTitle = senfMoshtaryTitle;
    }
}
