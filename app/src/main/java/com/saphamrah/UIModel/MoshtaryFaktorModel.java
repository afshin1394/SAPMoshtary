package com.saphamrah.UIModel;

import androidx.annotation.NonNull;

public class MoshtaryFaktorModel
{

    private static final String COLUMN_id = "id";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_CodeMoshtary = "CodeMoshtary";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_TedadFaktor = "TedadFaktor";
    private static final String COLUMN_MablaghKhalesFaktor = "MablaghKhalesFaktor";
    private static final String COLUMN_DarsadVisitMosbat = "DarsadVisitMosbat";


    public static String COLUMN_id() {
        return COLUMN_id;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_CodeMoshtary() {
        return COLUMN_CodeMoshtary;
    }
    public static String COLUMN_NameMoshtary() {
        return COLUMN_NameMoshtary;
    }
    public static String COLUMN_TedadFaktor() {
        return COLUMN_TedadFaktor;
    }
    public static String COLUMN_MablaghKhalesFaktor() {
        return COLUMN_MablaghKhalesFaktor;
    }
    public static String COLUMN_DarsadVisitMosbat() {
        return COLUMN_DarsadVisitMosbat;
    }


    private int id;
    private int ccMoshtary;
    private String CodeMoshtary;
    private String NameMoshtary;
    private int TedadFaktor;
    private double MablaghKhalesFaktor;
    private int DarsadVisitMosbat;


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getCcMoshtary()
    {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary)
    {
        this.ccMoshtary = ccMoshtary;
    }

    public String getCodeMoshtary()
    {
        return CodeMoshtary;
    }

    public void setCodeMoshtary(String codeMoshtary)
    {
        CodeMoshtary = codeMoshtary;
    }

    public String getNameMoshtary()
    {
        return NameMoshtary;
    }

    public void setNameMoshtary(String nameMoshtary)
    {
        NameMoshtary = nameMoshtary;
    }

    public int getTedadFaktor()
    {
        return TedadFaktor;
    }

    public void setTedadFaktor(int tedadFaktor)
    {
        TedadFaktor = tedadFaktor;
    }

    public double getMablaghKhalesFaktor()
    {
        return MablaghKhalesFaktor;
    }

    public void setMablaghKhalesFaktor(double mablaghKhalesFaktor)
    {
        MablaghKhalesFaktor = mablaghKhalesFaktor;
    }

    public int getDarsadVisitMosbat()
    {
        return DarsadVisitMosbat;
    }

    public void setDarsadVisitMosbat(int darsadVisitMosbat)
    {
        DarsadVisitMosbat = darsadVisitMosbat;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "MoshtaryFaktorModel{" +
                "id=" + id +
                ", ccMoshtary=" + ccMoshtary +
                ", CodeMoshtary='" + CodeMoshtary + '\'' +
                ", NameMoshtary='" + NameMoshtary + '\'' +
                ", TedadFaktor=" + TedadFaktor +
                ", MablaghKhalesFaktor=" + MablaghKhalesFaktor +
                ", DarsadVisitMosbat=" + DarsadVisitMosbat +
                '}';
    }
}
