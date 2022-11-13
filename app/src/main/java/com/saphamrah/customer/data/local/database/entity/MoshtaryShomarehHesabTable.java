package com.saphamrah.customer.data.local.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MoshtaryShomarehHesabTable {

    @PrimaryKey
    private int ccMoshtaryShomarehHesab;

    private int ccMoshtary;
    private int ccBank;
    private int ccNoeHesab;
    private int ccShomareHesab;
    private int codeShobeh;
    private int shartBardashAzHesab;

    private String bankTitle;
    private String noeHesabTitle;
    private String shomarehHesab;
    private String shobehTitle;
    private String sahebHesabTitle;

    public MoshtaryShomarehHesabTable(int ccMoshtaryShomarehHesab, int ccMoshtary, int ccBank, int ccNoeHesab, int ccShomareHesab, int codeShobeh, int shartBardashAzHesab, String bankTitle, String noeHesabTitle, String shomarehHesab, String shobehTitle, String sahebHesabTitle) {
        this.ccMoshtaryShomarehHesab = ccMoshtaryShomarehHesab;
        this.ccMoshtary = ccMoshtary;
        this.ccBank = ccBank;
        this.ccNoeHesab = ccNoeHesab;
        this.ccShomareHesab = ccShomareHesab;
        this.codeShobeh = codeShobeh;
        this.shartBardashAzHesab = shartBardashAzHesab;
        this.bankTitle = bankTitle;
        this.noeHesabTitle = noeHesabTitle;
        this.shomarehHesab = shomarehHesab;
        this.shobehTitle = shobehTitle;
        this.sahebHesabTitle = sahebHesabTitle;
    }

    public int getCcMoshtaryShomarehHesab() {
        return ccMoshtaryShomarehHesab;
    }

    public void setCcMoshtaryShomarehHesab(int ccMoshtaryShomarehHesab) {
        this.ccMoshtaryShomarehHesab = ccMoshtaryShomarehHesab;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcBank() {
        return ccBank;
    }

    public void setCcBank(int ccBank) {
        this.ccBank = ccBank;
    }

    public int getCcNoeHesab() {
        return ccNoeHesab;
    }

    public void setCcNoeHesab(int ccNoeHesab) {
        this.ccNoeHesab = ccNoeHesab;
    }

    public int getCcShomareHesab() {
        return ccShomareHesab;
    }

    public void setCcShomareHesab(int ccShomareHesab) {
        this.ccShomareHesab = ccShomareHesab;
    }

    public int getCodeShobeh() {
        return codeShobeh;
    }

    public void setCodeShobeh(int codeShobeh) {
        this.codeShobeh = codeShobeh;
    }

    public int getShartBardashAzHesab() {
        return shartBardashAzHesab;
    }

    public void setShartBardashAzHesab(int shartBardashAzHesab) {
        this.shartBardashAzHesab = shartBardashAzHesab;
    }

    public String getBankTitle() {
        return bankTitle;
    }

    public void setBankTitle(String bankTitle) {
        this.bankTitle = bankTitle;
    }

    public String getNoeHesabTitle() {
        return noeHesabTitle;
    }

    public void setNoeHesabTitle(String noeHesabTitle) {
        this.noeHesabTitle = noeHesabTitle;
    }

    public String getShomarehHesab() {
        return shomarehHesab;
    }

    public void setShomarehHesab(String shomarehHesab) {
        this.shomarehHesab = shomarehHesab;
    }

    public String getShobehTitle() {
        return shobehTitle;
    }

    public void setShobehTitle(String shobehTitle) {
        this.shobehTitle = shobehTitle;
    }

    public String getSahebHesabTitle() {
        return sahebHesabTitle;
    }

    public void setSahebHesabTitle(String sahebHesabTitle) {
        this.sahebHesabTitle = sahebHesabTitle;
    }
}
