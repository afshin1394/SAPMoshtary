package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class MoshtaryJadidDarkhastModel
{


    private static final String TABLE_NAME = "MoshtaryJadidDarkhast";
    private static final String COLUMN_ccMoshtaryJadidDarkhast = "ccMoshtaryJadidDarkhast";
    private static final String COLUMN_ccNoeMoshtary = "ccNoeMoshtary";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_flag_ForMoshtaryJadid = "flag_ForMoshtaryJadid";
    private static final String COLUMN_Sath = "Sath";

    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtaryJadidDarkhast()
    {
        return COLUMN_ccMoshtaryJadidDarkhast;
    }
    public static String COLUMN_ccNoeMoshtary()
    {
        return COLUMN_ccNoeMoshtary;
    }
    public static String COLUMN_ccKalaCode()
    {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_flag_ForMoshtaryJadid()
    {
        return COLUMN_flag_ForMoshtaryJadid;
    }
    public static String COLUMN_Sath()
    {
        return COLUMN_Sath;
    }



    private int ccMoshtaryJadidDarkhast;
    private int ccNoeMoshtary;
    private int ccKalaCode;
    private int flag_ForMoshtaryJadid;
    private int Sath;

    public int getCcMoshtaryJadidDarkhast()
    {
        return ccMoshtaryJadidDarkhast;
    }
    public void setCcMoshtaryJadidDarkhast(int ccMoshtaryJadidDarkhast)
    {
        this.ccMoshtaryJadidDarkhast = ccMoshtaryJadidDarkhast;
    }

    public int getCcNoeMoshtary()
    {
        return ccNoeMoshtary;
    }
    public void setCcNoeMoshtary(int ccNoeMoshtary)
    {
        this.ccNoeMoshtary = ccNoeMoshtary;
    }

    public int getCcKalaCode()
    {
        return ccKalaCode;
    }
    public void setCcKalaCode(int ccKalaCode)
    {
        this.ccKalaCode = ccKalaCode;
    }

    public int getFlag_ForMoshtaryJadid()
    {
        return flag_ForMoshtaryJadid;
    }
    public void setFlag_ForMoshtaryJadid(int flag_ForMoshtaryJadid)
    {
        this.flag_ForMoshtaryJadid = flag_ForMoshtaryJadid;
    }

    public int getSath()
    {
        return Sath;
    }
    public void setSath(int sath)
    {
        Sath = sath;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "MoshtaryJadidDarkhastModel{" +
                "ccMoshtaryJadidDarkhast=" + ccMoshtaryJadidDarkhast +
                ", ccNoeMoshtary=" + ccNoeMoshtary +
                ", ccKalaCode=" + ccKalaCode +
                ", flag_ForMoshtaryJadid=" + flag_ForMoshtaryJadid +
                ", Sath=" + Sath +
                '}';
    }


}
