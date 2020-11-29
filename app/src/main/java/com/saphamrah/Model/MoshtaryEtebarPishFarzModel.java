package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoshtaryEtebarPishFarzModel
{


    private static final String TABLE_NAME = "MoshtaryEtebarPishFarz";
    private static final String COLUMN_ccMoshtaryEtebarPishFarz = "ccMoshtaryEtebarPishFarz";
    private static final String COLUMN_ccNoeMoshtary = "ccNoeMoshtary";
    private static final String COLUMN_SaghfEtebarRiali = "SaghfEtebarRiali";
    private static final String COLUMN_SaghfEtebarAsnad = "SaghfEtebarAsnad";
    private static final String COLUMN_SaghfEtebarTedadi = "SaghfEtebarTedadi";
    private static final String COLUMN_SaghfEtebarModat = "SaghfEtebarModat";
    private static final String COLUMN_RialTazamin = "RialTazamin";
    private static final String COLUMN_RialAsnadShakhsi = "RialAsnadShakhsi";
    private static final String COLUMN_TedadAsnadShakhsi = "TedadAsnadShakhsi";
    private static final String COLUMN_ModatAsnadShakhsi = "ModatAsnadShakhsi";
    private static final String COLUMN_RialAsnadMoshtary = "RialAsnadMoshtary";
    private static final String COLUMN_TedadAsnadMoshtary = "TedadAsnadMoshtary";
    private static final String COLUMN_ModatAsnadMoshtary = "ModatAsnadMoshtary";
    private static final String COLUMN_RialMoavagh = "RialMoavagh";
    private static final String COLUMN_TedadMoavagh = "TedadMoavagh";
    private static final String COLUMN_ModatMoavagh = "ModatMoavagh";
    private static final String COLUMN_RialBargashty = "RialBargashty";
    private static final String COLUMN_TedadBargashty = "TedadBargashty";
    private static final String COLUMN_ModatBargashty = "ModatBargashty";
    private static final String COLUMN_ModatVosol = "ModatVosol";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtaryEtebarPishFarz()
    {
        return COLUMN_ccMoshtaryEtebarPishFarz;
    }
    public static String COLUMN_ccNoeMoshtary()
    {
        return COLUMN_ccNoeMoshtary;
    }
    public static String COLUMN_SaghfEtebarRiali()
    {
        return COLUMN_SaghfEtebarRiali;
    }
    public static String COLUMN_SaghfEtebarAsnad()
    {
        return COLUMN_SaghfEtebarAsnad;
    }
    public static String COLUMN_SaghfEtebarTedadi()
    {
        return COLUMN_SaghfEtebarTedadi;
    }
    public static String COLUMN_SaghfEtebarModat()
    {
        return COLUMN_SaghfEtebarModat;
    }
    public static String COLUMN_RialTazamin()
    {
        return COLUMN_RialTazamin;
    }
    public static String COLUMN_RialAsnadShakhsi()
    {
        return COLUMN_RialAsnadShakhsi;
    }
    public static String COLUMN_TedadAsnadShakhsi()
    {
        return COLUMN_TedadAsnadShakhsi;
    }
    public static String COLUMN_ModatAsnadShakhsi()
    {
        return COLUMN_ModatAsnadShakhsi;
    }
    public static String COLUMN_RialAsnadMoshtary()
    {
        return COLUMN_RialAsnadMoshtary;
    }
    public static String COLUMN_TedadAsnadMoshtary()
    {
        return COLUMN_TedadAsnadMoshtary;
    }
    public static String COLUMN_ModatAsnadMoshtary()
    {
        return COLUMN_ModatAsnadMoshtary;
    }
    public static String COLUMN_RialMoavagh()
    {
        return COLUMN_RialMoavagh;
    }
    public static String COLUMN_TedadMoavagh()
    {
        return COLUMN_TedadMoavagh;
    }
    public static String COLUMN_ModatMoavagh()
    {
        return COLUMN_ModatMoavagh;
    }
    public static String COLUMN_RialBargashty()
    {
        return COLUMN_RialBargashty;
    }
    public static String COLUMN_TedadBargashty()
    {
        return COLUMN_TedadBargashty;
    }
    public static String COLUMN_ModatBargashty()
    {
        return COLUMN_ModatBargashty;
    }
    public static String COLUMN_ModatVosol()
    {
        return COLUMN_ModatVosol;
    }



    @SerializedName("ccMoshtaryEtebarPishFarz")
    @Expose
    private int ccMoshtaryEtebarPishFarz;
    @SerializedName("ccNoeMoshtary")
    @Expose
    private int ccNoeMoshtary;
    @SerializedName("SaghfEtebarRiali")
    @Expose
    private long saghfEtebarRiali;
    @SerializedName("SaghfEtebarAsnad")
    @Expose
    private long saghfEtebarAsnad;
    @SerializedName("SaghfEtebarTedadi")
    @Expose
    private int saghfEtebarTedadi;
    @SerializedName("SaghfEtebarModat")
    @Expose
    private int saghfEtebarModat;
    @SerializedName("RialTazamin")
    @Expose
    private long rialTazamin;
    @SerializedName("RialAsnadShakhsi")
    @Expose
    private long rialAsnadShakhsi;
    @SerializedName("TedadAsnadShakhsi")
    @Expose
    private int tedadAsnadShakhsi;
    @SerializedName("ModatAsnadShakhsi")
    @Expose
    private int modatAsnadShakhsi;
    @SerializedName("RialAsnadMoshtary")
    @Expose
    private long rialAsnadMoshtary;
    @SerializedName("TedadAsnadMoshtary")
    @Expose
    private int tedadAsnadMoshtary;
    @SerializedName("ModatAsnadMoshtary")
    @Expose
    private int modatAsnadMoshtary;
    @SerializedName("RialMoavagh")
    @Expose
    private long rialMoavagh;
    @SerializedName("TedadMoavagh")
    @Expose
    private int tedadMoavagh;
    @SerializedName("ModatMoavagh")
    @Expose
    private int modatMoavagh;
    @SerializedName("RialBargashty")
    @Expose
    private long rialBargashty;
    @SerializedName("TedadBargashty")
    @Expose
    private int tedadBargashty;
    @SerializedName("ModatBargashty")
    @Expose
    private int modatBargashty;
    @SerializedName("ModatVosol")
    @Expose
    private int modatVosol;


    public int getCcMoshtaryEtebarPishFarz()
    {
        return ccMoshtaryEtebarPishFarz;
    }
    public void setCcMoshtaryEtebarPishFarz(int ccMoshtaryEtebarPishFarz)
    {
        this.ccMoshtaryEtebarPishFarz = ccMoshtaryEtebarPishFarz;
    }


    public int getCcNoeMoshtary()
    {
        return ccNoeMoshtary;
    }
    public void setCcNoeMoshtary(int ccNoeMoshtary)
    {
        this.ccNoeMoshtary = ccNoeMoshtary;
    }


    public long getSaghfEtebarRiali()
    {
        return saghfEtebarRiali;
    }
    public void setSaghfEtebarRiali(long saghfEtebarRiali)
    {
        this.saghfEtebarRiali = saghfEtebarRiali;
    }


    public long getSaghfEtebarAsnad()
{
    return saghfEtebarAsnad;
}
    public void setSaghfEtebarAsnad(long saghfEtebarAsnad)
    {
        this.saghfEtebarAsnad = saghfEtebarAsnad;
    }

    public int getSaghfEtebarTedadi()
    {
        return saghfEtebarTedadi;
    }
    public void setSaghfEtebarTedadi(int saghfEtebarTedadi)
    {
        this.saghfEtebarTedadi = saghfEtebarTedadi;
    }

    public int getSaghfEtebarModat()
    {
        return saghfEtebarModat;
    }
    public void setSaghfEtebarModat(int saghfEtebarModat)
    {
        this.saghfEtebarModat = saghfEtebarModat;
    }


    public long getRialTazamin()
    {
        return rialTazamin;
    }
    public void setRialTazamin(long rialTazamin)
    {
        this.rialTazamin = rialTazamin;
    }


    public long getRialAsnadShakhsi()
    {
        return rialAsnadShakhsi;
    }
    public void setRialAsnadShakhsi(long rialAsnadShakhsi)
    {
        this.rialAsnadShakhsi = rialAsnadShakhsi;
    }


    public int getTedadAsnadShakhsi()
    {
        return tedadAsnadShakhsi;
    }
    public void setTedadAsnadShakhsi(int tedadAsnadShakhsi)
    {
        this.tedadAsnadShakhsi = tedadAsnadShakhsi;
    }


    public int getModatAsnadShakhsi()
    {
        return modatAsnadShakhsi;
    }
    public void setModatAsnadShakhsi(int modatAsnadShakhsi)
    {
        this.modatAsnadShakhsi = modatAsnadShakhsi;
    }


    public long getRialAsnadMoshtary()
    {
        return rialAsnadMoshtary;
    }
    public void setRialAsnadMoshtary(long rialAsnadMoshtary)
    {
        this.rialAsnadMoshtary = rialAsnadMoshtary;
    }


    public int getTedadAsnadMoshtary()
    {
        return tedadAsnadMoshtary;
    }
    public void setTedadAsnadMoshtary(int tedadAsnadMoshtary)
    {
        this.tedadAsnadMoshtary = tedadAsnadMoshtary;
    }


    public int getModatAsnadMoshtary()
    {
        return modatAsnadMoshtary;
    }
    public void setModatAsnadMoshtary(int modatAsnadMoshtary)
    {
        this.modatAsnadMoshtary = modatAsnadMoshtary;
    }


    public long getRialMoavagh()
    {
        return rialMoavagh;
    }
    public void setRialMoavagh(long rialMoavagh)
    {
        this.rialMoavagh = rialMoavagh;
    }


    public int getTedadMoavagh()
    {
        return tedadMoavagh;
    }
    public void setTedadMoavagh(int tedadMoavagh)
    {
        this.tedadMoavagh = tedadMoavagh;
    }


    public int getModatMoavagh()
    {
        return modatMoavagh;
    }
    public void setModatMoavagh(int modatMoavagh)
    {
        this.modatMoavagh = modatMoavagh;
    }


    public long getRialBargashty()
    {
        return rialBargashty;
    }
    public void setRialBargashty(long rialBargashty)
    {
        this.rialBargashty = rialBargashty;
    }


    public int getTedadBargashty()
    {
        return tedadBargashty;
    }
    public void setTedadBargashty(int tedadBargashty)
    {
        this.tedadBargashty = tedadBargashty;
    }


    public int getModatBargashty()
    {
        return modatBargashty;
    }
    public void setModatBargashty(int modatBargashty)
    {
        this.modatBargashty = modatBargashty;
    }


    public int getModatVosol()
    {
        return modatVosol;
    }
    public void setModatVosol(int modatVosol)
    {
        this.modatVosol = modatVosol;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "MoshtaryEtebarPishFarzModel{" +
                "ccMoshtaryEtebarPishFarz=" + ccMoshtaryEtebarPishFarz +
                ", ccNoeMoshtary=" + ccNoeMoshtary +
                ", saghfEtebarRiali=" + saghfEtebarRiali +
                ", saghfEtebarAsnad=" + saghfEtebarAsnad +
                ", saghfEtebarTedadi=" + saghfEtebarTedadi +
                ", saghfEtebarModat=" + saghfEtebarModat +
                ", rialTazamin=" + rialTazamin +
                ", rialAsnadShakhsi=" + rialAsnadShakhsi +
                ", tedadAsnadShakhsi=" + tedadAsnadShakhsi +
                ", modatAsnadShakhsi=" + modatAsnadShakhsi +
                ", rialAsnadMoshtary=" + rialAsnadMoshtary +
                ", tedadAsnadMoshtary=" + tedadAsnadMoshtary +
                ", modatAsnadMoshtary=" + modatAsnadMoshtary +
                ", rialMoavagh=" + rialMoavagh +
                ", tedadMoavagh=" + tedadMoavagh +
                ", modatMoavagh=" + modatMoavagh +
                ", rialBargashty=" + rialBargashty +
                ", tedadBargashty=" + tedadBargashty +
                ", modatBargashty=" + modatBargashty +
                ", modatVosol=" + modatVosol +
                '}';
    }



}
