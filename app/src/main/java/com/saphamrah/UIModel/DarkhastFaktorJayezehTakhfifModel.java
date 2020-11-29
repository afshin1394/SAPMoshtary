package com.saphamrah.UIModel;

import androidx.annotation.NonNull;

public class DarkhastFaktorJayezehTakhfifModel
{

    private static final int NOE_JAYEZEH = 1;
    private static final int NOE_TAKHFIF = 2;

    public static int NoeJayezeh()
    {
        return NOE_JAYEZEH;
    }
    public static int NoeTakhfif()
    {
        return NOE_TAKHFIF;
    }



    private static final String COLUMN_NoeJayezehTakhfif = "NoeJayezehTakhfif";
    private static final String COLUMN_ccDarkhastFaktorJayezehTakhfif = "ccDarkhastFaktorJayezehTakhfif";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccJayezehTakhfif = "ccJayezehTakhfif";
    private static final String COLUMN_ExtraProp_ccJayezehSatr = "ExtraProp_ccJayezehSatr";
    private static final String COLUMN_SharhJayezehTakhfif = "SharhJayezehTakhfif";
    private static final String COLUMN_CodeNoeJayezehTakhfif = "CodeNoeJayezehTakhfif";
    private static final String COLUMN_MablaghJayezehTakhfif = "MablaghJayezehTakhfif";
    private static final String COLUMN_ccKalaJayezeh = "ccKalaJayezeh";
    private static final String COLUMN_TedadJayezeh = "TedadJayezeh";


    public static String COLUMN_NoeJayezehTakhfif()
    {
        return COLUMN_NoeJayezehTakhfif;
    }
    public static String COLUMN_ccDarkhastFaktorJayezehTakhfif()
    {
        return COLUMN_ccDarkhastFaktorJayezehTakhfif;
    }
    public static String COLUMN_ccDarkhastFaktor()
    {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ccJayezehTakhfif()
    {
        return COLUMN_ccJayezehTakhfif;
    }
    public static String COLUMN_ExtraProp_ccJayezehSatr()
    {
        return COLUMN_ExtraProp_ccJayezehSatr;
    }
    public static String COLUMN_SharhJayezehTakhfif()
    {
        return COLUMN_SharhJayezehTakhfif;
    }
    public static String COLUMN_CodeNoeJayezehTakhfif()
    {
        return COLUMN_CodeNoeJayezehTakhfif;
    }
    public static String COLUMN_MablaghJayezehTakhfif()
    {
        return COLUMN_MablaghJayezehTakhfif;
    }
    public static String COLUMN_ccKalaJayezeh()
    {
        return COLUMN_ccKalaJayezeh;
    }
    public static String COLUMN_TedadJayezeh()
    {
        return COLUMN_TedadJayezeh;
    }





    //value of this field = 1,2 => if NoeJayezehTakhfif == 2 => DarkhastFaktorTakhfif , if NoeJayezehTakhfif == 1 DarkhastFaktorJayezeh
    private int NoeJayezehTakhfif;
    private int ccDarkhastFaktorJayezehTakhfif;
    private long ccDarkhastFaktor;
    private int ccJayezehTakhfif;
    private int ExtraProp_ccJayezehSatr;
    private String SharhJayezehTakhfif;
    private int CodeNoeJayezehTakhfif;
    private float MablaghJayezehTakhfif;
    private int ccKalaJayezeh;
    private int TedadJayezeh;


    public int getNoeJayezehTakhfif()
    {
        return NoeJayezehTakhfif;
    }
    public void setNoeJayezehTakhfif(int noeJayezehTakhfif)
    {
        NoeJayezehTakhfif = noeJayezehTakhfif;
    }


    public int getCcDarkhastFaktorJayezehTakhfif()
    {
        return ccDarkhastFaktorJayezehTakhfif;
    }
    public void setCcDarkhastFaktorJayezehTakhfif(int ccDarkhastFaktorJayezehTakhfif)
    {
        this.ccDarkhastFaktorJayezehTakhfif = ccDarkhastFaktorJayezehTakhfif;
    }


    public long getCcDarkhastFaktor()
    {
        return ccDarkhastFaktor;
    }
    public void setCcDarkhastFaktor(long ccDarkhastFaktor)
    {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }


    public int getCcJayezehTakhfif()
    {
        return ccJayezehTakhfif;
    }
    public void setCcJayezehTakhfif(int ccJayezehTakhfif)
    {
        this.ccJayezehTakhfif = ccJayezehTakhfif;
    }


    public int getExtraProp_ccJayezehSatr()
    {
        return ExtraProp_ccJayezehSatr;
    }
    public void setExtraProp_ccJayezehSatr(int extraProp_ccJayezehSatr)
    {
        ExtraProp_ccJayezehSatr = extraProp_ccJayezehSatr;
    }

    public String getSharhJayezehTakhfif()
    {
        return SharhJayezehTakhfif;
    }
    public void setSharhJayezehTakhfif(String sharhJayezehTakhfif)
    {
        SharhJayezehTakhfif = sharhJayezehTakhfif;
    }


    public int getCodeNoeJayezehTakhfif()
    {
        return CodeNoeJayezehTakhfif;
    }
    public void setCodeNoeJayezehTakhfif(int codeNoeJayezehTakhfif)
    {
        CodeNoeJayezehTakhfif = codeNoeJayezehTakhfif;
    }


    public float getMablaghJayezehTakhfif()
    {
        return MablaghJayezehTakhfif;
    }
    public void setMablaghJayezehTakhfif(float mablaghJayezehTakhfif)
    {
        MablaghJayezehTakhfif = mablaghJayezehTakhfif;
    }


    public int getCcKalaJayezeh()
    {
        return ccKalaJayezeh;
    }
    public void setCcKalaJayezeh(int ccKalaJayezeh)
    {
        this.ccKalaJayezeh = ccKalaJayezeh;
    }


    public int getTedadJayezeh()
    {
        return TedadJayezeh;
    }
    public void setTedadJayezeh(int tedadJayezeh)
    {
        TedadJayezeh = tedadJayezeh;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "DarkhastFaktorJayezehTakhfifModel{" +
                "NoeJayezehTakhfif=" + NoeJayezehTakhfif +
                ", ccDarkhastFaktorJayezehTakhfif=" + ccDarkhastFaktorJayezehTakhfif +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccJayezehTakhfif=" + ccJayezehTakhfif +
                ", ExtraProp_ccJayezehSatr=" + ExtraProp_ccJayezehSatr +
                ", SharhJayezehTakhfif='" + SharhJayezehTakhfif + '\'' +
                ", CodeNoeJayezehTakhfif=" + CodeNoeJayezehTakhfif +
                ", MablaghJayezehTakhfif=" + MablaghJayezehTakhfif +
                ", ccKalaJayezeh=" + ccKalaJayezeh +
                ", TedadJayezeh=" + TedadJayezeh +
                '}';
    }
}
