package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class PorseshnamehTablighatModel
{

    private static final String TABLE_NAME = "Porseshnameh_Tablighat";
    private static final String COLUMN_ccPorseshnameh_Tablighat = "ccPorseshnameh_Tablighat";
    private static final String COLUMN_ccPorseshnameh = "ccPorseshnameh";
    private static final String COLUMN_ccNoeTablighat = "ccNoeTablighat";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccPorseshnameh_Tablighat()
    {
        return COLUMN_ccPorseshnameh_Tablighat;
    }
    public static String COLUMN_ccPorseshnameh()
    {
        return COLUMN_ccPorseshnameh;
    }
    public static String COLUMN_ccNoeTablighat()
    {
        return COLUMN_ccNoeTablighat;
    }



    private int ccPorseshnamehTablighat;
    private int ccPorseshnameh;
    private int ccNoeTablighat;

    public int getCcPorseshnamehTablighat()
    {
        return ccPorseshnamehTablighat;
    }

    public void setCcPorseshnamehTablighat(int ccPorseshnamehTablighat)
    {
        this.ccPorseshnamehTablighat = ccPorseshnamehTablighat;
    }

    public int getCcPorseshnameh()
    {
        return ccPorseshnameh;
    }

    public void setCcPorseshnameh(int ccPorseshnameh)
    {
        this.ccPorseshnameh = ccPorseshnameh;
    }

    public int getCcNoeTablighat()
    {
        return ccNoeTablighat;
    }

    public void setCcNoeTablighat(int ccNoeTablighat)
    {
        this.ccNoeTablighat = ccNoeTablighat;
    }


    public JSONObject toJsonObject()
    {
        try
        {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(COLUMN_ccPorseshnameh(), ccPorseshnameh);
            jsonObject.put(COLUMN_ccNoeTablighat(), ccNoeTablighat);

            return jsonObject;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    @NonNull
    @Override
    public String toString()
    {
        return "PorseshnamehTablighatModel{" +
                "ccPorseshnamehTablighat=" + ccPorseshnamehTablighat +
                ", ccPorseshnameh=" + ccPorseshnameh +
                ", ccNoeTablighat=" + ccNoeTablighat +
                '}';
    }
}
