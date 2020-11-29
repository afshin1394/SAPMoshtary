package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class PorseshnamehShomareshModel
{

    private static final String TABLE_NAME = "PorseshnamehShomaresh";
    private static final String COLUMN_ccPorseshnamehShomaresh = "ccPorseshnamehShomaresh";
    private static final String COLUMN_ccPorseshnameh = "ccPorseshnameh";
    private static final String COLUMN_ccKala = "ccKala";
    private static final String COLUMN_TedadShomaresh = "TedadShomaresh";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccPorseshnamehShomaresh()
    {
        return COLUMN_ccPorseshnamehShomaresh;
    }
    public static String COLUMN_ccPorseshnameh()
    {
        return COLUMN_ccPorseshnameh;
    }
    public static String COLUMN_ccKala()
    {
        return COLUMN_ccKala;
    }
    public static String COLUMN_TedadShomaresh()
    {
        return COLUMN_TedadShomaresh;
    }


    private int ccPorseshnamehShomaresh;
    private int ccPorseshnameh;
    private int ccKala;
    private int TedadShomaresh;


    public int getCcPorseshnamehShomaresh()
    {
        return ccPorseshnamehShomaresh;
    }
    public void setCcPorseshnamehShomaresh(int ccPorseshnamehShomaresh)
    {
        this.ccPorseshnamehShomaresh = ccPorseshnamehShomaresh;
    }

    public int getCcPorseshnameh()
    {
        return ccPorseshnameh;
    }
    public void setCcPorseshnameh(int ccPorseshnameh)
    {
        this.ccPorseshnameh = ccPorseshnameh;
    }

    public int getCcKala()
    {
        return ccKala;
    }
    public void setCcKala(int ccKala)
    {
        this.ccKala = ccKala;
    }

    public int getTedadShomaresh()
    {
        return TedadShomaresh;
    }
    public void setTedadShomaresh(int tedadShomaresh)
    {
        TedadShomaresh = tedadShomaresh;
    }


    public JSONObject toJsonObject()
    {
        try
        {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(COLUMN_ccPorseshnameh(), ccPorseshnameh);
            jsonObject.put(COLUMN_ccKala(), ccKala);
            jsonObject.put(COLUMN_TedadShomaresh(), TedadShomaresh);

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
        return "PorseshnamehShomareshDAO{" +
                "ccPorseshnamehShomaresh=" + ccPorseshnamehShomaresh +
                ", ccPorseshnameh=" + ccPorseshnameh +
                ", ccKala=" + ccKala +
                ", TedadShomaresh=" + TedadShomaresh +
                '}';
    }
}
