package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class AmargarMarkazSazmanForoshModel
{

    private static final String TABLE_NAME = "AmargarMarkazSazmanForosh";
    private static final String COLUMN_ccAmargarMarkazSazmanForosh = "ccAmargarMarkazSazmanForosh";
    private static final String COLUMN_ccAmargar = "ccAmargar";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_nameMarkazForosh = "nameMarkazForosh";
    private static final String COLUMN_ccSazmanForosh = "ccSazmanForosh";
    private static final String COLUMN_nameSazmanForosh = "nameSazmanForosh";
    private static final String COLUMN_ccMarkazSazmanForosh = "ccMarkazSazmanForosh";
    private static final String COLUMN_nameMarkazSazmanForosh = "nameMarkazSazmanForosh";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccAmargarMarkazSazmanForosh()
    {
        return COLUMN_ccAmargarMarkazSazmanForosh;
    }
    public static String COLUMN_ccAmargar()
    {
        return COLUMN_ccAmargar;
    }
    public static String COLUMN_ccMarkazForosh()
    {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_nameMarkazForosh()
    {
        return COLUMN_nameMarkazForosh;
    }
    public static String COLUMN_ccSazmanForosh()
    {
        return COLUMN_ccSazmanForosh;
    }
    public static String COLUMN_nameSazmanForosh()
    {
        return COLUMN_nameSazmanForosh;
    }
    public static String COLUMN_ccMarkazSazmanForosh()
    {
        return COLUMN_ccMarkazSazmanForosh;
    }
    public static String COLUMN_nameMarkazSazmanForosh()
    {
        return COLUMN_nameMarkazSazmanForosh;
    }



    @SerializedName("ccAmargarMarkazSazmanForosh")
    @Expose
    private int ccAmargarMarkazSazmanForosh;
    @SerializedName("ccAmargar")
    @Expose
    private int ccAmargar;
    @SerializedName("ccMarkazForosh")
    @Expose
    private int ccMarkazForosh;
    @SerializedName("NameMarkazForosh")
    @Expose
    private String nameMarkazForosh;
    @SerializedName("ccSazmanForosh")
    @Expose
    private int ccSazmanForosh;
    @SerializedName("NameSazmanForosh")
    @Expose
    private String nameSazmanForosh;
    @SerializedName("ccMarkazSazmanForosh")
    @Expose
    private int ccMarkazSazmanForosh;
    @SerializedName("NameMarkazSazmanForosh")
    @Expose
    private String nameMarkazSazmanForosh;
    @SerializedName("Id")
    @Expose
    private int id;


    public int getCcAmargarMarkazSazmanForosh()
    {
        return ccAmargarMarkazSazmanForosh;
    }

    public void setCcAmargarMarkazSazmanForosh(int ccAmargarMarkazSazmanForosh)
    {
        this.ccAmargarMarkazSazmanForosh = ccAmargarMarkazSazmanForosh;
    }

    public int getCcAmargar()
    {
        return ccAmargar;
    }

    public void setCcAmargar(int ccAmargar)
    {
        this.ccAmargar = ccAmargar;
    }

    public int getCcMarkazForosh()
    {
        return ccMarkazForosh;
    }

    public void setCcMarkazForosh(int ccMarkazForosh)
    {
        this.ccMarkazForosh = ccMarkazForosh;
    }

    public String getNameMarkazForosh()
    {
        return nameMarkazForosh;
    }

    public void setNameMarkazForosh(String nameMarkazForosh)
    {
        this.nameMarkazForosh = nameMarkazForosh;
    }

    public int getCcSazmanForosh()
    {
        return ccSazmanForosh;
    }

    public void setCcSazmanForosh(int ccSazmanForosh)
    {
        this.ccSazmanForosh = ccSazmanForosh;
    }

    public String getNameSazmanForosh()
    {
        return nameSazmanForosh;
    }

    public void setNameSazmanForosh(String nameSazmanForosh)
    {
        this.nameSazmanForosh = nameSazmanForosh;
    }

    public int getCcMarkazSazmanForosh()
    {
        return ccMarkazSazmanForosh;
    }

    public void setCcMarkazSazmanForosh(int ccMarkazSazmanForosh)
    {
        this.ccMarkazSazmanForosh = ccMarkazSazmanForosh;
    }

    public String getNameMarkazSazmanForosh()
    {
        return nameMarkazSazmanForosh;
    }

    public void setNameMarkazSazmanForosh(String nameMarkazSazmanForosh)
    {
        this.nameMarkazSazmanForosh = nameMarkazSazmanForosh;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "AmargarMarkazSazmanForoshModel{" +
                "ccAmargarMarkazSazmanForosh=" + ccAmargarMarkazSazmanForosh +
                ", ccAmargar=" + ccAmargar +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", nameMarkazForosh='" + nameMarkazForosh + '\'' +
                ", ccSazmanForosh=" + ccSazmanForosh +
                ", nameSazmanForosh='" + nameSazmanForosh + '\'' +
                ", ccMarkazSazmanForosh=" + ccMarkazSazmanForosh +
                ", nameMarkazSazmanForosh='" + nameMarkazSazmanForosh + '\'' +
                ", id=" + id +
                '}';
    }
}
