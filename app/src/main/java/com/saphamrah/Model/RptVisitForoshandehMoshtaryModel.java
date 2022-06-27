package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class RptVisitForoshandehMoshtaryModel
{

    private static final String TABLE_NAME = "Rpt_VisitForoshandeh_Moshtary";
    private static final String COLUMN_Radif = "Radif";
    private static final String COLUMN_Olaviat = "Olaviat";
    private static final String COLUMN_CodeMoshtary = "CodeMoshtary";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_Telephone = "Telephone";
    private static final String COLUMN_RialKharid = "RialKharid";
    private static final String COLUMN_SaatVorodBeMaghazeh = "SaatVorodBeMaghazeh";
    private static final String COLUMN_SaatKhorojAzMaghazeh = "SaatKhorojAzMaghazeh";
    private static final String COLUMN_ZamanDarMaghazeh = "ZamanDarMaghazeh";
    private static final String COLUMN_VazeiatMorajeh = "VazeiatMorajeh";
    private static final String COLUMN_VazeiatDarkhast = "VazeiatDarkhast";
    private static final String COLUMN_DalilDarkhastManfi = "DalilDarkhastManfi";
    private static final String COLUMN_Tedad_AghlamFaktor = "Tedad_AghlamFaktor";
    private static final String COLUMN_IsMorajeh = "IsMorajeh";
    private static final String COLUMN_CodeNoeAdamDarkhast = "CodeNoeAdamDarkhast";

    private int ExtraProp_OpenView = 0;



    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_Radif() {
        return COLUMN_Radif;
    }
    public static String COLUMN_Olaviat() {
        return COLUMN_Olaviat;
    }
    public static String COLUMN_CodeMoshtary() {
        return COLUMN_CodeMoshtary;
    }
    public static String COLUMN_NameMoshtary() {
        return COLUMN_NameMoshtary;
    }
    public static String COLUMN_Telephone() {
        return COLUMN_Telephone;
    }
    public static String COLUMN_RialKharid() {
        return COLUMN_RialKharid;
    }
    public static String COLUMN_SaatVorodBeMaghazeh() {
        return COLUMN_SaatVorodBeMaghazeh;
    }
    public static String COLUMN_SaatKhorojAzMaghazeh() {
        return COLUMN_SaatKhorojAzMaghazeh;
    }
    public static String COLUMN_ZamanDarMaghazeh() {
        return COLUMN_ZamanDarMaghazeh;
    }
    public static String COLUMN_VazeiatMorajeh() {
        return COLUMN_VazeiatMorajeh;
    }
    public static String COLUMN_VazeiatDarkhast() {
        return COLUMN_VazeiatDarkhast;
    }
    public static String COLUMN_DalilDarkhastManfi() {
        return COLUMN_DalilDarkhastManfi;
    }
    public static String COLUMN_Tedad_AghlamFaktor() {
        return COLUMN_Tedad_AghlamFaktor;
    }
    public static String COLUMN_IsMorajeh() {
        return COLUMN_IsMorajeh;
    }
    public static String COLUMN_CodeNoeAdamDarkhast() {
         return COLUMN_CodeNoeAdamDarkhast;
    }





    private int Radif;
    public int getRadif() {
        return Radif;
    }
    public void setRadif(int radif) {
        Radif = radif;
    }


    private int CodeNoeAdamDarkhast;
    public int getCodeNoeAdamDarkhast() {
        return CodeNoeAdamDarkhast;
    }
    public void setcodeNoeAdamDarkhast(int CodeNoeAdamDarkhast) {
        this.CodeNoeAdamDarkhast = CodeNoeAdamDarkhast;
    }


    private int Olaviat;
    public int getOlaviat() {
        return Olaviat;
    }
    public void setOlaviat(int olaviat) {
        Olaviat = olaviat;
    }


    private String CodeMoshtary;
    public String getCodeMoshtary() {
        return CodeMoshtary;
    }
    public void setCodeMoshtary(String codeMoshtary) {
        CodeMoshtary = codeMoshtary;
    }


    private String NameMoshtary;
    public String getNameMoshtary() {
        return NameMoshtary;
    }
    public void setNameMoshtary(String nameMoshtary) {
        NameMoshtary = nameMoshtary;
    }


    private String Telephone;
    public String getTelephone() {
        return Telephone;
    }
    public void setTelephone(String telephone) {
        Telephone = telephone;
    }


    private double RialKharid;
    public double getRialKharid() {
        return RialKharid;
    }
    public void setRialKharid(double rialKharid) {
        RialKharid = rialKharid;
    }


    private String SaatVorodBeMaghazeh;
    public String getSaatVorodBeMaghazeh() {
        return SaatVorodBeMaghazeh;
    }
    public void setSaatVorodBeMaghazeh(String saatVorodBeMaghazeh) {
        SaatVorodBeMaghazeh = saatVorodBeMaghazeh;
    }


    private String SaatKhorojAzMaghazeh;
    public String getSaatKhorojAzMaghazeh() {
        return SaatKhorojAzMaghazeh;
    }
    public void setSaatKhorojAzMaghazeh(String saatKhorojAzMaghazeh) {
        SaatKhorojAzMaghazeh = saatKhorojAzMaghazeh;
    }


    private String ZamanDarMaghazeh;
    public String getZamanDarMaghazeh() {
        return ZamanDarMaghazeh;
    }
    public void setZamanDarMaghazeh(String zamanDarMaghazeh) {
        ZamanDarMaghazeh = zamanDarMaghazeh;
    }


    private String VazeiatMorajeh;
    public String getVazeiatMorajeh() {
        return VazeiatMorajeh;
    }
    public void setVazeiatMorajeh(String vazeiatMorajeh) {
        VazeiatMorajeh = vazeiatMorajeh;
    }


    private String VazeiatDarkhast;
    public String getVazeiatDarkhast() {
        return VazeiatDarkhast;
    }
    public void setVazeiatDarkhast(String vazeiatDarkhast) {
        VazeiatDarkhast = vazeiatDarkhast;
    }


    private String DalilDarkhastManfi;
    public String getDalilDarkhastManfi() {
        return DalilDarkhastManfi;
    }
    public void setDalilDarkhastManfi(String dalilDarkhastManfi) {
        DalilDarkhastManfi = dalilDarkhastManfi;
    }


    private int Tedad_AghlamFaktor;
    public int getTedad_AghlamFaktor() {
        return Tedad_AghlamFaktor;
    }
    public void setTedad_AghlamFaktor(int tedad_AghlamFaktor) {
        Tedad_AghlamFaktor = tedad_AghlamFaktor;
    }


    private int IsMorajeh;
    public int getIsMorajeh() {
        return IsMorajeh;
    }
    public void setIsMorajeh(int isMorajeh) {
        IsMorajeh = isMorajeh;
    }

    public int getExtraProp_OpenView() {
        return ExtraProp_OpenView;
    }

    public void setExtraProp_OpenView(int extraProp_OpenView) {
        ExtraProp_OpenView = extraProp_OpenView;
    }

    @NonNull
    @Override
    public String toString()
    {
        return  "Radif=" + Radif +
                ", Olaviat=" + Olaviat +
                ", CodeMoshtary='" + CodeMoshtary + '\'' +
                ", NameMoshtary='" + NameMoshtary + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", RialKharid=" + RialKharid +
                ", SaatVorodBeMaghazeh='" + SaatVorodBeMaghazeh + '\'' +
                ", SaatKhorojAzMaghazeh='" + SaatKhorojAzMaghazeh + '\'' +
                ", ZamanDarMaghazeh='" + ZamanDarMaghazeh + '\'' +
                ", VazeiatMorajeh='" + VazeiatMorajeh + '\'' +
                ", VazeiatDarkhast='" + VazeiatDarkhast + '\'' +
                ", DalilDarkhastManfi='" + DalilDarkhastManfi + '\'' +
                ", Tedad_AghlamFaktor=" + Tedad_AghlamFaktor +
                ", IsMorajeh=" + IsMorajeh +
                ", CodeNoeAdamDarkhast=" + CodeNoeAdamDarkhast;
    }


}
