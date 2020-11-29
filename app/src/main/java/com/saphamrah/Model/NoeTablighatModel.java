package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoeTablighatModel
{

    private static final String TABLE_NAME = "NoeTablighat";
    private static final String COLUMN_ccNoeTablighat = "ccNoeTablighat";
    private static final String COLUMN_NameNoeTablighat = "NameNoeTablighat";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccNoeTablighat()
    {
        return COLUMN_ccNoeTablighat;
    }
    public static String COLUMN_NameNoeTablighat()
    {
        return COLUMN_NameNoeTablighat;
    }


    @SerializedName("ccNoeTablighat")
    @Expose
    private int ccNoeTablighat;
    @SerializedName("NameNoeTablighat")
    @Expose
    private String nameNoeTablighat;
    @SerializedName("Id")
    @Expose
    private int id;


    public int getCcNoeTablighat()
    {
        return ccNoeTablighat;
    }
    public void setCcNoeTablighat(int ccNoeTablighat)
    {
        this.ccNoeTablighat = ccNoeTablighat;
    }

    public String getNameNoeTablighat()
    {
        return nameNoeTablighat;
    }
    public void setNameNoeTablighat(String nameNoeTablighat)
    {
        this.nameNoeTablighat = nameNoeTablighat;
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
