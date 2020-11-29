package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class MaxFaktorMandehDarModel
{
    private static final String TABLE_NAME = "MaxFaktorMandehDar";
    private static final String COLUMN_ccMaxFaktorMandehDar = "ccMaxFaktorMandehDar";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_ccGorohMoshtary = "ccGorohMoshtary";
    private static final String COLUMN_MaxTedad = "MaxTedad";
    private static final String COLUMN_MaxRooz = "MaxRooz";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMaxFaktorMandehDar() {
        return COLUMN_ccMaxFaktorMandehDar;
    }
    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_ccGorohMoshtary() {
        return COLUMN_ccGorohMoshtary;
    }
    public static String COLUMN_MaxTedad() {
        return COLUMN_MaxTedad;
    }
    public static String COLUMN_MaxRooz() {
        return COLUMN_MaxRooz;
    }




    private int ccMaxFaktorMandehDar;
    private int ccMarkazForosh;
    private int ccGorohMoshtary;
    private int MaxTedad;
    private int MaxRooz;

    public int getCcMaxFaktorMandehDar() {
        return ccMaxFaktorMandehDar;
    }
    public void setCcMaxFaktorMandehDar(int ccMaxFaktorMandehDar) {
        this.ccMaxFaktorMandehDar = ccMaxFaktorMandehDar;
    }
    public int getCcMarkazForosh() {
        return ccMarkazForosh;
    }
    public void setCcMarkazForosh(int ccMarkazForosh) {
        this.ccMarkazForosh = ccMarkazForosh;
    }
    public int getCcGorohMoshtary() {
        return ccGorohMoshtary;
    }
    public void setCcGorohMoshtary(int ccGorohMoshtary) {
        this.ccGorohMoshtary = ccGorohMoshtary;
    }
    public int getMaxTedad() {
        return MaxTedad;
    }
    public void setMaxTedad(int maxTedad) {
        MaxTedad = maxTedad;
    }
    public int getMaxRooz() {
        return MaxRooz;
    }
    public void setMaxRooz(int maxRooz) {
        MaxRooz = maxRooz;
    }


    @NonNull
    @Override
    public String toString() {
        return "MaxFaktorMandehDarModel{" +
                "ccMaxFaktorMandehDar=" + ccMaxFaktorMandehDar +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", ccGorohMoshtary=" + ccGorohMoshtary +
                ", MaxTedad=" + MaxTedad +
                ", MaxRooz=" + MaxRooz +
                '}';
    }
}
