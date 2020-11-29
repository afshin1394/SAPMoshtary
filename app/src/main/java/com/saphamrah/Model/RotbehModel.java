package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RotbehModel
{

    private static final String TABLE_NAME = "Rotbeh";
    private static final String COLUMN_ccRotbeh = "ccRotbeh";
    private static final String COLUMN_NameRotbeh = "NameRotbeh";
    private static final String COLUMN_ViewInPPC_ForMoshtaryJadid = "ViewInPPC_ForMoshtaryJadid";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccRotbeh()
    {
        return COLUMN_ccRotbeh;
    }
    public static String COLUMN_NameRotbeh()
    {
        return COLUMN_NameRotbeh;
    }
    public static String COLUMN_ViewInPPC_ForMoshtaryJadid()
    {
        return COLUMN_ViewInPPC_ForMoshtaryJadid;
    }



    @SerializedName("ccRotbeh")
    @Expose
    private int ccRotbeh;
    @SerializedName("NameRotbeh")
    @Expose
    private String nameRotbeh;
    @SerializedName("ViewInPPC_ForMoshtaryJadid")
    @Expose
    private int viewInPPCForMoshtaryJadid;
    @SerializedName("Id")
    @Expose
    private int id;


    public int getCcRotbeh()
    {
        return ccRotbeh;
    }
    public void setCcRotbeh(int ccRotbeh)
    {
        this.ccRotbeh = ccRotbeh;
    }

    public String getNameRotbeh()
    {
        return nameRotbeh;
    }
    public void setNameRotbeh(String nameRotbeh)
    {
        this.nameRotbeh = nameRotbeh;
    }

    public int getViewInPPCForMoshtaryJadid()
    {
        return viewInPPCForMoshtaryJadid;
    }
    public void setViewInPPCForMoshtaryJadid(int viewInPPCForMoshtaryJadid)
    {
        this.viewInPPCForMoshtaryJadid = viewInPPCForMoshtaryJadid;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
}
