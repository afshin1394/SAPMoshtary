package com.saphamrah.Model;


import android.util.Base64;

import com.google.gson.JsonObject;

import org.json.JSONObject;

public class MoshtaryPhotoPPCModel
{

    private static final String TABLE_NAME = "MoshtaryPhotoPPC";
    private static final String COLUMN_ccMoshtaryPhoto = "ccMoshtaryPhoto";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccNoePhoto = "ccNoePhoto";
    private static final String COLUMN_txtNoePhoto = "txtNoePhoto";
    private static final String COLUMN_ImageMadrak = "ImageMadrak";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtaryPhoto() {
        return COLUMN_ccMoshtaryPhoto;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccNoePhoto() {
        return COLUMN_ccNoePhoto;
    }
    public static String COLUMN_txtNoePhoto() {
        return COLUMN_txtNoePhoto;
    }
    public static String COLUMN_ImageMadrak() {
        return COLUMN_ImageMadrak;
    }




    private Integer ccMoshtaryPhoto;
    private Integer ccMoshtary;
    private Integer ccNoePhoto;
    private String txtNoePhoto;
    private byte[] ImageMadrak;

    public Integer getCcMoshtaryPhoto() {
        return ccMoshtaryPhoto;
    }
    public void setCcMoshtaryPhoto(Integer ccMoshtaryPhoto) {
        this.ccMoshtaryPhoto = ccMoshtaryPhoto;
    }
    public Integer getCcMoshtary() {
        return ccMoshtary;
    }
    public void setCcMoshtary(Integer ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }
    public Integer getCcNoePhoto() {
        return ccNoePhoto;
    }
    public void setCcNoePhoto(Integer ccNoePhoto) {
        this.ccNoePhoto = ccNoePhoto;
    }
    public String getTxtNoePhoto() {
        return txtNoePhoto;
    }
    public void setTxtNoePhoto(String txtNoePhoto) {
        this.txtNoePhoto = txtNoePhoto;
    }
    public byte[] getImageMadrak() {
        return ImageMadrak;
    }
    public void setImageMadrak(byte[] imageMadrak) {
        ImageMadrak = imageMadrak;
    }


    public JSONObject toJsonObject()
    {
        /*JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccMoshtaryPhoto" , ccMoshtaryPhoto);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("ccNoePhoto" , ccNoePhoto);
            jsonObject.put("txtNoePhoto" , txtNoePhoto);
            jsonObject.put("ImageMadrak" , Base64.encodeToString(ImageMadrak , Base64.NO_WRAP));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;*/

        /*ccNoePhoto INT, ImageMadrak VARBINARY(MAX))*/
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccNoePhoto" , ccNoePhoto);
            jsonObject.put("ImageMadrak" , Base64.encodeToString(ImageMadrak , Base64.NO_WRAP));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }


    public String toJsonString()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(COLUMN_ccMoshtaryPhoto() , ccMoshtaryPhoto);
        jsonObject.addProperty(COLUMN_ccMoshtary() , ccMoshtary);
        jsonObject.addProperty(COLUMN_ccNoePhoto() , ccNoePhoto);
        jsonObject.addProperty(COLUMN_ImageMadrak() , Base64.encodeToString(ImageMadrak , Base64.NO_WRAP));
        return jsonObject.toString();
    }

}
