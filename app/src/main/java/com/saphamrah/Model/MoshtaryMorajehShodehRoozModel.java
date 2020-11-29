package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class MoshtaryMorajehShodehRoozModel
{

    private static final String TABLE_NAME = "MoshtaryMorajehShodeh_Rooz";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_NoeMorajeh = "NoeMorajeh";
    private static final String COLUMN_EtebarEzafehShodeh = "EtebarEzafehShodeh";
    private static final String COLUMN_MablaghMandehFaktor = "MablaghMandehFaktor";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_NoeMorajeh() {
        return COLUMN_NoeMorajeh;
    }
    public static String COLUMN_EtebarEzafehShodeh() {
        return COLUMN_EtebarEzafehShodeh;
    }
    public static String COLUMN_MablaghMandehFaktor() {
        return COLUMN_MablaghMandehFaktor;
    }



    private int ccForoshandeh;
    private int ccMoshtary;
    private int NoeMorajeh;
    private int EtebarEzafehShodeh;
    private double MablaghMandehFaktor;


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

    public int getNoeMorajeh() {
        return NoeMorajeh;
    }

    public void setNoeMorajeh(int noeMorajeh) {
        NoeMorajeh = noeMorajeh;
    }

    public int getEtebarEzafehShodeh() {
        return EtebarEzafehShodeh;
    }

    public void setEtebarEzafehShodeh(int etebarEzafehShodeh) {
        EtebarEzafehShodeh = etebarEzafehShodeh;
    }

    public double getMablaghMandehFaktor() {
        return MablaghMandehFaktor;
    }

    public void setMablaghMandehFaktor(double mablaghMandehFaktor) {
        MablaghMandehFaktor = mablaghMandehFaktor;
    }


    @NonNull
    @Override
    public String toString() {
        return "ccForoshandeh : " + ccForoshandeh + " , ccMoshtary : " + ccMoshtary + " , NoeMorajeh : " + NoeMorajeh +
                " , EtebarEzafehShodeh : " + EtebarEzafehShodeh + " , MablaghMandehFaktor : " + MablaghMandehFaktor;
    }



}
