package com.saphamrah.Model;


import android.util.Base64;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdamDarkhastModel
{

    private static final String TABLE_NAME = "AdamDarkhast";
    private static final String COLUMN_ccAdamDarkhast = "ccAdamDarkhast";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccElatAdamDarkhast = "ccElatAdamDarkhast";
    private static final String COLUMN_TarikhAdamDarkhast = "TarikhAdamDarkhast";
    private static final String COLUMN_Latitude = "Latitude";
    private static final String COLUMN_Longitude = "Longitude";
    private static final String COLUMN_IsSentToServer = "IsSentToServer";
    private static final String COLUMN_AdamDarkhastImage = "AdamDarkhastImage";
    private static final String COLUMN_CodeMoshtaryTekrari = "CodeMoshtaryTekrari";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccAdamDarkhast() {
        return COLUMN_ccAdamDarkhast;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccElatAdamDarkhast() {
        return COLUMN_ccElatAdamDarkhast;
    }
    public static String COLUMN_TarikhAdamDarkhast() {
        return COLUMN_TarikhAdamDarkhast;
    }
    public static String COLUMN_Latitude() {
        return COLUMN_Latitude;
    }
    public static String COLUMN_Longitude() {
        return COLUMN_Longitude;
    }
    public static String COLUMN_IsSentToServer() {
        return COLUMN_IsSentToServer;
    }
    public static String COLUMN_AdamDarkhastImage() {
        return COLUMN_AdamDarkhastImage;
    }
    public static String COLUMN_CodeMoshtaryTekrari() {
        return COLUMN_CodeMoshtaryTekrari;
    }



    private int ccAdamDarkhast;
    private int ccForoshandeh;
    private int ccMoshtary;
    private int ccElatAdamDarkhast;
    private Date dateAdamDarkhast;
    private float Latitude;
    private float Longitude;
    private boolean IsSentToServer;
    private byte[] AdamDarkhastImage;
    private String CodeMoshtaryTekrari;



    public int getCcAdamDarkhast() {
        return ccAdamDarkhast;
    }
    public void setCcAdamDarkhast(int ccAdamDarkhast) {
        this.ccAdamDarkhast = ccAdamDarkhast;
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
    public int getCcElatAdamDarkhast() {
        return ccElatAdamDarkhast;
    }
    public void setCcElatAdamDarkhast(int ccElatAdamDarkhast) {
        this.ccElatAdamDarkhast = ccElatAdamDarkhast;
    }
    public Date getDateAdamDarkhast() {
        return dateAdamDarkhast;
    }
    public void setDateAdamDarkhast(Date dateAdamDarkhast) {
        this.dateAdamDarkhast = dateAdamDarkhast;
    }
    public float getLatitude() {
        return Latitude;
    }
    public void setLatitude(float latitude) {
        Latitude = latitude;
    }
    public float getLongitude() {
        return Longitude;
    }
    public void setLongitude(float longitude) {
        Longitude = longitude;
    }
    public boolean getIsSentToServer() {
        return IsSentToServer;
    }
    public void setIsSentToServer(boolean sentToServer) {
        IsSentToServer = sentToServer;
    }
    public byte[] getAdamDarkhastImage() {
        return AdamDarkhastImage;
    }
    public void setAdamDarkhastImage(byte[] adamDarkhastImage) {
        AdamDarkhastImage = adamDarkhastImage;
    }
    public String getCodeMoshtaryTekrari() {
        return CodeMoshtaryTekrari;
    }
    public void setCodeMoshtaryTekrari(String codeMoshtaryTekrari) {
        CodeMoshtaryTekrari = codeMoshtaryTekrari;
    }




    public String toJsonString(int ccMarkazPakhsh , int ccMarkazForosh)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ccAdamDarkhast" , ccAdamDarkhast);
        jsonObject.addProperty("ccGorohForosh" , 0);
        jsonObject.addProperty("ccMarkazPakhsh" , ccMarkazPakhsh);
        jsonObject.addProperty("ccForoshandeh" , ccForoshandeh);
        jsonObject.addProperty("ccMoshtary" , ccMoshtary);
        jsonObject.addProperty("ccElatAdamDarkhast" , ccElatAdamDarkhast);
        jsonObject.addProperty("TarikhAdamDarkhast" , new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(dateAdamDarkhast));
        jsonObject.addProperty("Latitude" , Latitude);
        jsonObject.addProperty("Longitude" , Longitude);
        jsonObject.addProperty("ccMarkazForosh" , ccMarkazForosh);
        jsonObject.addProperty("CodeMoshtaryTekrari" , CodeMoshtaryTekrari);
        if (AdamDarkhastImage != null)
        {
            String image = Base64.encodeToString(AdamDarkhastImage , Base64.DEFAULT);
            jsonObject.addProperty("Image" , image);
        }
        return jsonObject.toString();
    }




}
