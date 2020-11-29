package com.saphamrah.Model;

import android.nfc.tech.IsoDep;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

public class MojoodiGiriModel
{

    private static final String TABLE_NAME = "MojoodiGiri";
    private static final String COLUMN_ccMojoodiGiri = "ccMojoodiGiri";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_Tarikh = "Tarikh";
    private static final String COLUMN_TedadMojoodiGiri = "TedadMojoodiGiri";
    private static final String COLUMN_ToorVisit = "ToorVisit";
    private static final String COLUMN_TedadPishnahady = "TedadPishnahady";
    private static final String COLUMN_SaatVorod = "SaatVorod";
    private static final String COLUMN_SaatKhoroj = "SaatKhoroj";
    private static final String COLUMN_Latitude = "Latitude";
    private static final String COLUMN_Longitude = "Longitude";
    private static final String COLUMN_InsertInPPC = "InsertInPPC";
    private static final String COLUMN_IsOld = "IsOld";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMojoodiGiri() {
        return COLUMN_ccMojoodiGiri;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_Tarikh() {
        return COLUMN_Tarikh;
    }
    public static String COLUMN_TedadMojoodiGiri() {
        return COLUMN_TedadMojoodiGiri;
    }
    public static String COLUMN_ToorVisit() {
        return COLUMN_ToorVisit;
    }
    public static String COLUMN_TedadPishnahady() {
        return COLUMN_TedadPishnahady;
    }
    public static String COLUMN_SaatVorod() {
        return COLUMN_SaatVorod;
    }
    public static String COLUMN_SaatKhoroj() {
        return COLUMN_SaatKhoroj;
    }
    public static String COLUMN_Latitude() {
        return COLUMN_Latitude;
    }
    public static String COLUMN_Longitude() {
        return COLUMN_Longitude;
    }
    public static String COLUMN_InsertInPPC() {
        return COLUMN_InsertInPPC;
    }
    public static String COLUMN_IsOld() {
        return COLUMN_IsOld;
    }


    private int ccMojoodiGiri;
    private int ccForoshandeh;
    private int ccMoshtary;
    private int ccKalaCode;
    private String Tarikh;
    private Float TedadMojoodiGiri;
    private int ToorVisit;
    private Float TedadPishnahady;
    private String SaatVorod;
    private String SaatKhoroj;
    private Float Latitude;
    private Float Longitude;
    private Boolean InsertInPPC;
    private Boolean IsOld;



    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getCOLUMN_ccMojoodiGiri() {
        return COLUMN_ccMojoodiGiri;
    }

    public static String getCOLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }

    public static String getCOLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }

    public static String getCOLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }

    public static String getCOLUMN_Tarikh() {
        return COLUMN_Tarikh;
    }

    public static String getCOLUMN_TedadMojoodiGiri() {
        return COLUMN_TedadMojoodiGiri;
    }

    public static String getCOLUMN_ToorVisit() {
        return COLUMN_ToorVisit;
    }

    public static String getCOLUMN_TedadPishnahady() {
        return COLUMN_TedadPishnahady;
    }

    public static String getCOLUMN_SaatVorod() {
        return COLUMN_SaatVorod;
    }

    public static String getCOLUMN_SaatKhoroj() {
        return COLUMN_SaatKhoroj;
    }

    public static String getCOLUMN_Latitude() {
        return COLUMN_Latitude;
    }

    public static String getCOLUMN_Longitude() {
        return COLUMN_Longitude;
    }

    public static String getCOLUMN_InsertInPPC() {
        return COLUMN_InsertInPPC;
    }

    public static String getCOLUMN_IsOld() {
        return COLUMN_IsOld;
    }

    public int getCcMojoodiGiri() {
        return ccMojoodiGiri;
    }

    public void setCcMojoodiGiri(int ccMojoodiGiri) {
        this.ccMojoodiGiri = ccMojoodiGiri;
    }

    public int getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcKalaCode() {
        return ccKalaCode;
    }

    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }

    public String getTarikh() {
        return Tarikh;
    }

    public void setTarikh(String tarikh) {
        Tarikh = tarikh;
    }

    public Float getTedadMojoodiGiri() {
        return TedadMojoodiGiri;
    }

    public void setTedadMojoodiGiri(Float tedadMojoodiGiri) {
        TedadMojoodiGiri = tedadMojoodiGiri;
    }

    public int getToorVisit() {
        return ToorVisit;
    }

    public void setToorVisit(int toorVisit) {
        ToorVisit = toorVisit;
    }

    public Float getTedadPishnahady() {
        return TedadPishnahady;
    }

    public void setTedadPishnahady(Float tedadPishnahady) {
        TedadPishnahady = tedadPishnahady;
    }

    public String getSaatVorod() {
        return SaatVorod;
    }

    public void setSaatVorod(String saatVorod) {
        SaatVorod = saatVorod;
    }

    public String getSaatKhoroj() {
        return SaatKhoroj;
    }

    public void setSaatKhoroj(String saatKhoroj) {
        SaatKhoroj = saatKhoroj;
    }

    public Float getLatitude() {
        return Latitude;
    }

    public void setLatitude(Float latitude) {
        Latitude = latitude;
    }

    public Float getLongitude() {
        return Longitude;
    }

    public void setLongitude(Float longitude) {
        Longitude = longitude;
    }

    public Boolean getInsertInPPC() {
        return InsertInPPC;
    }

    public void setInsertInPPC(Boolean insertInPPC) {
        InsertInPPC = insertInPPC;
    }

    public Boolean getOld() {
        return IsOld;
    }

    public void setOld(Boolean old) {
        IsOld = old;
    }


    public String toJsonString()
    {
        String tarikh = this.Tarikh == null ? "" : this.Tarikh;
        String saatVorod = this.SaatVorod == null ? "" : this.SaatVorod;
        String saatKhoroj = this.SaatKhoroj == null ? "" : this.SaatKhoroj;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(COLUMN_ccMojoodiGiri() , ccMojoodiGiri);
        jsonObject.addProperty(COLUMN_ccForoshandeh() , ccForoshandeh);
        jsonObject.addProperty(COLUMN_ccMoshtary() , ccMoshtary);
        jsonObject.addProperty(COLUMN_ccKalaCode() , ccKalaCode);
        jsonObject.addProperty(COLUMN_Tarikh() , tarikh);
        jsonObject.addProperty(COLUMN_TedadMojoodiGiri() , TedadMojoodiGiri == -1 ? 0 : TedadMojoodiGiri);
        jsonObject.addProperty(COLUMN_ToorVisit() , ToorVisit);
        jsonObject.addProperty(COLUMN_TedadPishnahady() , TedadPishnahady);
        jsonObject.addProperty(COLUMN_SaatVorod() , saatVorod);
        jsonObject.addProperty(COLUMN_SaatKhoroj() , saatKhoroj);
        jsonObject.addProperty(COLUMN_Latitude() , Latitude);
        jsonObject.addProperty(COLUMN_Longitude() , Longitude);
        jsonObject.addProperty(COLUMN_InsertInPPC() , InsertInPPC);
        jsonObject.addProperty(COLUMN_IsOld() , IsOld);
        return jsonObject.toString();
    }



    @NonNull
    @Override
    public String toString() {
        return "ccMojoodiGiri : " + ccMojoodiGiri + " , ccForoshandeh : " + ccForoshandeh + " , ccMoshtary : " + ccMoshtary +
                " , ccKalaCode : " + ccKalaCode + " , Tarikh : " + Tarikh + " , TedadMojoodiGiri : " + TedadMojoodiGiri +
                " , ToorVisit : " + ToorVisit + " , TedadPishnahady : " + TedadPishnahady + " , SaatVorod : " + SaatVorod +
                " , SaatKhoroj : " + SaatKhoroj + " , Latitude : " + Latitude + " , Longitude : " +
                " , InsertInPPC : " + InsertInPPC + " , IsOld : " + IsOld;
    }


}
