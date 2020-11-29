package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TafkikJozeModel
{

    private static final String TABLE_NAME = "TafkikJoze";
    private static final String COLUMN_ccTafkikJoze = "ccTafkikJoze";
    private static final String COLUMN_ShomarehTafkikJoze = "ShomarehTafkikJoze";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccTafkikJoze() {
        return COLUMN_ccTafkikJoze;
    }
    public static String COLUMN_ShomarehTafkikJoze() {
        return COLUMN_ShomarehTafkikJoze;
    }


    @SerializedName("ccTafkikJoze")
    @Expose
    private long ccTafkikJoze;
    @SerializedName("ShomarehTafkikJoze")
    @Expose
    private int ShomarehTafkikJoze;


    public long getCcTafkikJoze()
    {
        return ccTafkikJoze;
    }
    public void setCcTafkikJoze(long ccTafkikJoze)
    {
        this.ccTafkikJoze = ccTafkikJoze;
    }

    public int getShomarehTafkikJoze()
    {
        return ShomarehTafkikJoze;
    }
    public void setShomarehTafkikJoze(int shomarehTafkikJoze)
    {
        ShomarehTafkikJoze = shomarehTafkikJoze;
    }
}
