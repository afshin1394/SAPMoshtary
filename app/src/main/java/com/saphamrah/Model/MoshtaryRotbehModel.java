package com.saphamrah.Model;

import java.util.Date;

public class MoshtaryRotbehModel
{

    private static final String TABLE_NAME = "MoshtaryRotbeh";
    private static final String COLUMN_ccMoshtaryRotbeh = "ccMoshtaryRotbeh";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccBrand = "ccBrand";
    private static final String COLUMN_Darajeh = "Darajeh";
    private static final String COLUMN_FromDate = "FromDate";
    private static final String COLUMN_EndDate = "EndDate";
    private static final String COLUMN_NameBrand = "NameBrand";
    private static final String COLUMN_DarsadAfzayeshEtebar = "DarsadAfzayeshEtebar";
    private static final String COLUMN_NameDarajeh = "NameDarajeh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtaryRotbeh() {
        return COLUMN_ccMoshtaryRotbeh;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccBrand() {
        return COLUMN_ccBrand;
    }
    public static String COLUMN_Darajeh() {
        return COLUMN_Darajeh;
    }
    public static String COLUMN_FromDate() {
        return COLUMN_FromDate;
    }
    public static String COLUMN_EndDate() {
        return COLUMN_EndDate;
    }
    public static String COLUMN_NameBrand() {
        return COLUMN_NameBrand;
    }
    public static String COLUMN_DarsadAfzayeshEtebar() {
        return COLUMN_DarsadAfzayeshEtebar;
    }
    public static String COLUMN_NameDarajeh() {
        return COLUMN_NameDarajeh;
    }


    private int ccMoshtaryRotbeh;
    private int ccMoshtary;
    private int ccBrand;
    private int Darajeh;
    private Date FromDate;
    private Date EndDate;
    private String NameBrand;
    private float DarsadAfzayeshEtebar;
    private String NameDarajeh;


    public int getCcMoshtaryRotbeh() {
        return ccMoshtaryRotbeh;
    }
    public void setCcMoshtaryRotbeh(int ccMoshtaryRotbeh) {
        this.ccMoshtaryRotbeh = ccMoshtaryRotbeh;
    }
    public int getCcMoshtary() {
        return ccMoshtary;
    }
    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }
    public int getCcBrand() {
        return ccBrand;
    }
    public void setCcBrand(int ccBrand) {
        this.ccBrand = ccBrand;
    }
    public int getDarajeh() {
        return Darajeh;
    }
    public void setDarajeh(int darajeh) {
        Darajeh = darajeh;
    }
    public Date getFromDate() {
        return FromDate;
    }
    public void setFromDate(Date fromDate) {
        FromDate = fromDate;
    }
    public Date getEndDate() {
        return EndDate;
    }
    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }
    public String getNameBrand() {
        return NameBrand;
    }
    public void setNameBrand(String nameBrand) {
        NameBrand = nameBrand;
    }
    public float getDarsadAfzayeshEtebar() {
        return DarsadAfzayeshEtebar;
    }
    public void setDarsadAfzayeshEtebar(float darsadAfzayeshEtebar) {
        DarsadAfzayeshEtebar = darsadAfzayeshEtebar;
    }
    public String getNameDarajeh() {
        return NameDarajeh;
    }
    public void setNameDarajeh(String nameDarajeh) {
        NameDarajeh = nameDarajeh;
    }


}
