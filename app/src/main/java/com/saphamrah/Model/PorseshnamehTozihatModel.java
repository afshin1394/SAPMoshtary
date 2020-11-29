package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PorseshnamehTozihatModel
{

    private static final String TABLE_NAME = "PorseshnamehTozihat";
    private static final String COLUMN_ccPorseshnamehTozihat = "ccPorseshnamehTozihat";
    private static final String COLUMN_Sharh = "Sharh";

    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccPorseshnamehTozihat()
    {
        return COLUMN_ccPorseshnamehTozihat;
    }
    public static String COLUMN_Sharh()
    {
        return COLUMN_Sharh;
    }


    @SerializedName("ccPorseshnamehTozihat")
    @Expose
    private int ccPorseshnamehTozihat;
    @SerializedName("Sharh")
    @Expose
    private String sharh;
    @SerializedName("Id")
    @Expose
    private int id;


    public int getCcPorseshnamehTozihat()
    {
        return ccPorseshnamehTozihat;
    }
    public void setCcPorseshnamehTozihat(int ccPorseshnamehTozihat)
    {
        this.ccPorseshnamehTozihat = ccPorseshnamehTozihat;
    }

    public String getSharh()
    {
        return sharh;
    }
    public void setSharh(String sharh)
    {
        this.sharh = sharh;
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
