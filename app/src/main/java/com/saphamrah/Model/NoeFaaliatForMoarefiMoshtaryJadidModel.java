package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class NoeFaaliatForMoarefiMoshtaryJadidModel
{

    private static final String TABLE_NAME = "NoeFaaliatForMoarefiMoshtaryJadid";
    private static final String COLUMN_ccNoeFaaliatForMoarefiMoshtaryJadid = "ccNoeFaaliatForMoarefiMoshtaryJadid";
    private static final String COLUMN_ccNoeMoshtary = "ccNoeMoshtary";
    private static final String COLUMN_ccSenfMoshtary = "ccSenfMoshtary";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccNoeFaaliatForMoarefiMoshtaryJadid()
    {
        return COLUMN_ccNoeFaaliatForMoarefiMoshtaryJadid;
    }
    public static String COLUMN_ccNoeMoshtary()
    {
        return COLUMN_ccNoeMoshtary;
    }
    public static String COLUMN_ccSenfMoshtary()
    {
        return COLUMN_ccSenfMoshtary;
    }



    private int ccNoeFaaliatForMoarefiMoshtaryJadid;
    private int ccNoeMoshtary;
    private int ccSenfMoshtary;


    public int getCcNoeFaaliatForMoarefiMoshtaryJadid()
    {
        return ccNoeFaaliatForMoarefiMoshtaryJadid;
    }
    public void setCcNoeFaaliatForMoarefiMoshtaryJadid(int ccNoeFaaliatForMoarefiMoshtaryJadid)
    {
        this.ccNoeFaaliatForMoarefiMoshtaryJadid = ccNoeFaaliatForMoarefiMoshtaryJadid;
    }


    public int getCcNoeMoshtary()
    {
        return ccNoeMoshtary;
    }
    public void setCcNoeMoshtary(int ccNoeMoshtary)
    {
        this.ccNoeMoshtary = ccNoeMoshtary;
    }


    public int getCcSenfMoshtary()
    {
        return ccSenfMoshtary;
    }
    public void setCcSenfMoshtary(int ccSenfMoshtary)
    {
        this.ccSenfMoshtary = ccSenfMoshtary;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "NoeFaaliatForMoarefiMoshtaryJadidModel{" +
                "ccNoeMoshtary=" + ccNoeMoshtary +
                ", ccSenfMoshtary=" + ccSenfMoshtary +
                '}';
    }


}
