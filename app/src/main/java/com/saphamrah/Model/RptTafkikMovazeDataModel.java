package com.saphamrah.Model;

public class RptTafkikMovazeDataModel {

    private int ccMoshtary;
    private String NameMoshtary;
    private long ccDarkhastFaktor;
    private int ShomarehFaktor;

    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getShomarehFaktor() {
        return ShomarehFaktor;
    }

    public void setShomarehFaktor(int shomarehFaktor) {
        ShomarehFaktor = shomarehFaktor;
    }

    public void setNameMoshtary(String NameMoshtary){
        this.NameMoshtary = NameMoshtary;
    }
    public String getNameMoshtary(){
        return this.NameMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public String toStringCustom() {
        return
                 NameMoshtary + " " + ShomarehFaktor ;
    }
}
