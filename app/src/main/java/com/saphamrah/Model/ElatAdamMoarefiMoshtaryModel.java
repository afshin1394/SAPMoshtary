package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElatAdamMoarefiMoshtaryModel
{

    private static final String TABLE_NAME = "ElatAdamMoarefiMoshtary";
    private static final String COLUMN_ccElatAdamMoarefiMoshtary = "ccElatAdamMoarefiMoshtary";
    private static final String COLUMN_NameElatAdamMoarefiMoshtary = "NameElatAdamMoarefiMoshtary";
    private static final String COLUMN_ViewInPPC_Foroshandeh = "ViewInPPC_Foroshandeh";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccElatAdamMoarefiMoshtary()
    {
        return COLUMN_ccElatAdamMoarefiMoshtary;
    }
    public static String COLUMN_NameElatAdamMoarefiMoshtary()
    {
        return COLUMN_NameElatAdamMoarefiMoshtary;
    }
    public static String COLUMN_ViewInPPC_Foroshandeh()
    {
        return COLUMN_ViewInPPC_Foroshandeh;
    }


    @SerializedName("ccElatAdamMoarefiMoshtary")
    @Expose
    private int ccElatAdamMoarefiMoshtary;
    @SerializedName("NameElatAdamMoarefiMoshtary")
    @Expose
    private String nameElatAdamMoarefiMoshtary;
    @SerializedName("ViewInPPC_Amargar")
    @Expose
    private int viewInPPCAmargar;
    @SerializedName("ViewInPPC_Foroshandeh")
    @Expose
    private int viewInPPCForoshandeh;
    @SerializedName("MoshtaryFaal")
    @Expose
    private int moshtaryFaal;
    @SerializedName("MoshtaryGheyreFaal")
    @Expose
    private int moshtaryGheyreFaal;
    @SerializedName("Id")
    @Expose
    private int id;


    public int getCcElatAdamMoarefiMoshtary()
    {
        return ccElatAdamMoarefiMoshtary;
    }
    public void setCcElatAdamMoarefiMoshtary(int ccElatAdamMoarefiMoshtary)
    {
        this.ccElatAdamMoarefiMoshtary = ccElatAdamMoarefiMoshtary;
    }

    public String getNameElatAdamMoarefiMoshtary()
    {
        return nameElatAdamMoarefiMoshtary;
    }
    public void setNameElatAdamMoarefiMoshtary(String nameElatAdamMoarefiMoshtary)
    {
        this.nameElatAdamMoarefiMoshtary = nameElatAdamMoarefiMoshtary;
    }

    public int getViewInPPCAmargar()
    {
        return viewInPPCAmargar;
    }
    public void setViewInPPCAmargar(int viewInPPCAmargar)
    {
        this.viewInPPCAmargar = viewInPPCAmargar;
    }

    public int getViewInPPCForoshandeh()
    {
        return viewInPPCForoshandeh;
    }
    public void setViewInPPCForoshandeh(int viewInPPCForoshandeh)
    {
        this.viewInPPCForoshandeh = viewInPPCForoshandeh;
    }

    public int getMoshtaryFaal()
    {
        return moshtaryFaal;
    }
    public void setMoshtaryFaal(int moshtaryFaal)
    {
        this.moshtaryFaal = moshtaryFaal;
    }

    public int getMoshtaryGheyreFaal()
    {
        return moshtaryGheyreFaal;
    }
    public void setMoshtaryGheyreFaal(int moshtaryGheyreFaal)
    {
        this.moshtaryGheyreFaal = moshtaryGheyreFaal;
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
