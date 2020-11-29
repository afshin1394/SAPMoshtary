package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class RptMarjoeeKalaModel
{

    private static final String TABLE_NAME = "Rpt_Marjoee";
    private static final String COLUMN_Radif = "Radif";
    private static final String COLUMN_ccElamMarjoee = "ccElamMarjoee";
    private static final String COLUMN_ccElamMarjoeeSatr = "ccElamMarjoeeSatr";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ShomarehFaktor = "ShomarehFaktor";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_CodeMoshtary = "CodeMoshtary";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_CodeKala = "CodeKala";
    private static final String COLUMN_NameKala = "NameKala";
    private static final String COLUMN_Tedad3 = "Tedad3";
    private static final String COLUMN_ShomarehBach = "ShomarehBach";
    private static final String COLUMN_TarikhTolid = "TarikhTolid";
    private static final String COLUMN_TarikhEngheza = "TarikhEngheza";
    private static final String COLUMN_Fee = "Fee";
    private static final String COLUMN_ccTaminkonandeh = "ccTaminkonandeh";
    private static final String COLUMN_GheymatMasrafkonandeh = "GheymatMasrafkonandeh";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_Radif()
    {
        return COLUMN_Radif;
    }
    public static String COLUMN_ccElamMarjoee()
    {
        return COLUMN_ccElamMarjoee;
    }
    public static String COLUMN_ccElamMarjoeeSatr()
    {
        return COLUMN_ccElamMarjoeeSatr;
    }
    public static String COLUMN_ccDarkhastFaktor()
    {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ShomarehFaktor()
    {
        return COLUMN_ShomarehFaktor;
    }
    public static String COLUMN_ccMoshtary()
    {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_CodeMoshtary()
    {
        return COLUMN_CodeMoshtary;
    }
    public static String COLUMN_NameMoshtary()
    {
        return COLUMN_NameMoshtary;
    }
    public static String COLUMN_CodeKala()
    {
        return COLUMN_CodeKala;
    }
    public static String COLUMN_NameKala()
    {
        return COLUMN_NameKala;
    }
    public static String COLUMN_Tedad3()
    {
        return COLUMN_Tedad3;
    }
    public static String COLUMN_ShomarehBach()
    {
        return COLUMN_ShomarehBach;
    }
    public static String COLUMN_TarikhTolid()
    {
        return COLUMN_TarikhTolid;
    }
    public static String COLUMN_TarikhEngheza()
    {
        return COLUMN_TarikhEngheza;
    }
    public static String COLUMN_Fee()
    {
        return COLUMN_Fee;
    }
    public static String COLUMN_ccTaminkonandeh()
    {
        return COLUMN_ccTaminkonandeh;
    }
    public static String COLUMN_GheymatMasrafkonandeh()
    {
        return COLUMN_GheymatMasrafkonandeh;
    }




    private int Radif;
    private long ccElamMarjoee;
    private long ccElamMarjoeeSatr;
    private long ccDarkhastFaktor;
    private String ShomarehFaktor;
    private int ccMoshtary;
    private String CodeMoshtary;
    private String NameMoshtary;
    private String CodeKala;
    private String NameKala;
    private int Tedad3;
    private String ShomarehBach;
    private String TarikhTolid;
    private String TarikhEngheza;
    private long Fee;
    private int ccTaminkonandeh;
    private long GheymatMasrafkonandeh;


    public int getRadif()
    {
        return Radif;
    }

    public void setRadif(int radif)
    {
        Radif = radif;
    }

    public long getCcElamMarjoee()
    {
        return ccElamMarjoee;
    }

    public void setCcElamMarjoee(long ccElamMarjoee)
    {
        this.ccElamMarjoee = ccElamMarjoee;
    }

    public long getCcElamMarjoeeSatr()
    {
        return ccElamMarjoeeSatr;
    }

    public void setCcElamMarjoeeSatr(long ccElamMarjoeeSatr)
    {
        this.ccElamMarjoeeSatr = ccElamMarjoeeSatr;
    }

    public long getCcDarkhastFaktor()
    {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor)
    {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public String getShomarehFaktor()
    {
        return ShomarehFaktor;
    }

    public void setShomarehFaktor(String shomarehFaktor)
    {
        ShomarehFaktor = shomarehFaktor;
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

    public String getCodeKala()
    {
        return CodeKala;
    }

    public void setCodeKala(String codeKala)
    {
        CodeKala = codeKala;
    }

    public String getNameKala()
    {
        return NameKala;
    }

    public void setNameKala(String nameKala)
    {
        NameKala = nameKala;
    }

    public int getTedad3()
    {
        return Tedad3;
    }

    public void setTedad3(int tedad3)
    {
        Tedad3 = tedad3;
    }

    public String getShomarehBach()
    {
        return ShomarehBach;
    }

    public void setShomarehBach(String shomarehBach)
    {
        ShomarehBach = shomarehBach;
    }

    public String getTarikhTolid()
    {
        return TarikhTolid;
    }

    public void setTarikhTolid(String tarikhTolid)
    {
        TarikhTolid = tarikhTolid;
    }

    public String getTarikhEngheza()
    {
        return TarikhEngheza;
    }

    public void setTarikhEngheza(String tarikhEngheza)
    {
        TarikhEngheza = tarikhEngheza;
    }

    public long getFee()
    {
        return Fee;
    }

    public void setFee(long fee)
    {
        Fee = fee;
    }

    public int getCcTaminkonandeh()
    {
        return ccTaminkonandeh;
    }

    public void setCcTaminkonandeh(int ccTaminkonandeh)
    {
        this.ccTaminkonandeh = ccTaminkonandeh;
    }

    public long getGheymatMasrafkonandeh()
    {
        return GheymatMasrafkonandeh;
    }

    public void setGheymatMasrafkonandeh(long gheymatMasrafkonandeh)
    {
        GheymatMasrafkonandeh = gheymatMasrafkonandeh;
    }





    @NonNull
    @Override
    public String toString()
    {
        return "RptMarjoeeKalaModel{" +
                "Radif=" + Radif +
                ", ccElamMarjoee=" + ccElamMarjoee +
                ", ccElamMarjoeeSatr=" + ccElamMarjoeeSatr +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ShomarehFaktor='" + ShomarehFaktor + '\'' +
                ", ccMoshtary=" + ccMoshtary +
                ", CodeMoshtary='" + CodeMoshtary + '\'' +
                ", NameMoshtary='" + NameMoshtary + '\'' +
                ", CodeKala='" + CodeKala + '\'' +
                ", NameKala='" + NameKala + '\'' +
                ", Tedad3=" + Tedad3 +
                ", ShomarehBach='" + ShomarehBach + '\'' +
                ", TarikhTolid='" + TarikhTolid + '\'' +
                ", TarikhEngheza='" + TarikhEngheza + '\'' +
                ", Fee=" + Fee +
                ", ccTaminkonandeh=" + ccTaminkonandeh +
                ", GheymatMasrafkonandeh=" + GheymatMasrafkonandeh +
                '}';
    }



}
