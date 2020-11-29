package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class GPSDataMashinModel
{


    private static final String TABLE_NAME = "GPSData_Mashin";
    private static final String COLUMN_ccGPSData_Mashin = "ccGPSData_Mashin";
    private static final String COLUMN_ccMashin = "ccMashin";
    private static final String COLUMN_ccAfradMamorPakhsh = "ccAfradMamorPakhsh";
    private static final String COLUMN_ccMamorPakhsh = "ccMamorPakhsh";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_Longitude_x = "Longitude_x";
    private static final String COLUMN_Latitude_y = "Latitude_y";
    private static final String COLUMN_ZamaneSabt = "ZamaneSabt";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccGPSData_Mashin()
    {
        return COLUMN_ccGPSData_Mashin;
    }
    public static String COLUMN_ccMashin()
    {
        return COLUMN_ccMashin;
    }
    public static String COLUMN_ccAfradMamorPakhsh()
    {
        return COLUMN_ccAfradMamorPakhsh;
    }
    public static String COLUMN_ccMamorPakhsh()
    {
        return COLUMN_ccMamorPakhsh;
    }
    public static String COLUMN_ccDarkhastFaktor()
    {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ccMoshtary()
    {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_Longitude_x()
    {
        return COLUMN_Longitude_x;
    }
    public static String COLUMN_Latitude_y()
    {
        return COLUMN_Latitude_y;
    }
    public static String COLUMN_ZamaneSabt()
    {
        return COLUMN_ZamaneSabt;
    }




    private int ccGPSDataMashin;
    private int ccMashin;
    private int ccAfradMamorPakhsh;
    private int ccMamorPakhsh;
    private long ccDarkhastFaktor;
    private int ccMoshtary;
    private float Longitude_x;
    private float Latitude_y;
    private String ZamaneSabt;

    public int getCcGPSDataMashin()
    {
        return ccGPSDataMashin;
    }

    public void setCcGPSDataMashin(int ccGPSDataMashin)
    {
        this.ccGPSDataMashin = ccGPSDataMashin;
    }

    public int getCcMashin()
    {
        return ccMashin;
    }

    public void setCcMashin(int ccMashin)
    {
        this.ccMashin = ccMashin;
    }

    public int getCcAfradMamorPakhsh()
    {
        return ccAfradMamorPakhsh;
    }

    public void setCcAfradMamorPakhsh(int ccAfradMamorPakhsh)
    {
        this.ccAfradMamorPakhsh = ccAfradMamorPakhsh;
    }

    public int getCcMamorPakhsh()
    {
        return ccMamorPakhsh;
    }

    public void setCcMamorPakhsh(int ccMamorPakhsh)
    {
        this.ccMamorPakhsh = ccMamorPakhsh;
    }

    public long getCcDarkhastFaktor()
    {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor)
    {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getCcMoshtary()
    {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary)
    {
        this.ccMoshtary = ccMoshtary;
    }

    public float getLongitude_x()
    {
        return Longitude_x;
    }

    public void setLongitude_x(float longitude_x)
    {
        Longitude_x = longitude_x;
    }

    public float getLatitude_y()
    {
        return Latitude_y;
    }

    public void setLatitude_y(float latitude_y)
    {
        Latitude_y = latitude_y;
    }

    public String getZamaneSabt()
    {
        return ZamaneSabt;
    }

    public void setZamaneSabt(String zamaneSabt)
    {
        ZamaneSabt = zamaneSabt;
    }


    public String toJsonString()
    {
        try
        {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("ccGPSData_Mashin" , ccGPSDataMashin);
            jsonObject.put("ccMashin" , ccMashin);
            jsonObject.put("ccAfradMamorPakhsh" , ccAfradMamorPakhsh);
            jsonObject.put("ccMamorPakhsh" , ccMamorPakhsh);
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("Longitude_x" , Longitude_x);
            jsonObject.put("Latitude_y" , Latitude_y);
            jsonObject.put("ZamaneSabt" , ZamaneSabt);

            return jsonObject.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }


    @NonNull
    @Override
    public String toString()
    {
        return "GPSDataMashinModel{" +
                "ccGPSDataMashin=" + ccGPSDataMashin +
                ", ccMashin=" + ccMashin +
                ", ccAfradMamorPakhsh=" + ccAfradMamorPakhsh +
                ", ccMamorPakhsh=" + ccMamorPakhsh +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccMoshtary=" + ccMoshtary +
                ", Longitude_x=" + Longitude_x +
                ", Latitude_y=" + Latitude_y +
                ", ZamaneSabt='" + ZamaneSabt + '\'' +
                '}';
    }


}
