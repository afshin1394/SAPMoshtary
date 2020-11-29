package com.saphamrah.Model;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class CompanyModel
{

    private static final String TABLE_NAME = "Company";
    private static final String COLUMN_ccCompany = "ccCompany";
    private static final String COLUMN_NameCompany = "NameCompany";
    private static final String COLUMN_LogoPhoto = "LogoPhoto";
    private static final String COLUMN_LogoPhotoPrint = "LogoPhotoPrint";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccCompany() {
        return COLUMN_ccCompany;
    }
    public static String COLUMN_NameCompany() { return COLUMN_NameCompany; }
    public static String COLUMN_LogoPhoto() {
        return COLUMN_LogoPhoto;
    }
    public static String COLUMN_LogoPhotoPrint() {
        return COLUMN_LogoPhotoPrint;
    }





    private int ccCompany;
    private String NameCompany;
    private byte[] LogoPhoto;
    private byte[] LogoPhotoPrint;


    public int getCcCompany() {
        return ccCompany;
    }
    public void setCcCompany(int ccCompany) {
        this.ccCompany = ccCompany;
    }

    public String getNameCompany() {
        return NameCompany;
    }
    public void setNameCompany(String nameCompany) {
        NameCompany = nameCompany;
    }

    public byte[] getLogoPhoto() {
        return LogoPhoto;
    }
    public void setLogoPhoto(byte[] logoPhoto) {
        LogoPhoto = logoPhoto;
    }

    public byte[] getLogoPhotoPrint() {
        return LogoPhotoPrint;
    }
    public void setLogoPhotoPrint(byte[] logoPhotoPrint) {
        LogoPhotoPrint = logoPhotoPrint;
    }




    @NonNull
    @Override
    public String toString() {
        return "CompanyModel{" +
                "ccCompany=" + ccCompany +
                ", NameCompany='" + NameCompany + '\'' +
                ", LogoPhoto=" + Arrays.toString(LogoPhoto) +
                ", LogoPhotoPrint=" + Arrays.toString(LogoPhotoPrint) +
                '}';
    }
}
