package com.saphamrah.Model;

import android.util.Base64;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class TizerModel {

    @SerializedName("ccTizer")
    @Expose
    private int ccTizer;
    @SerializedName("NameTizer")
    @Expose
    private String NameTizer;
    @SerializedName("NameTizer_Farsi")
    @Expose
    private String NameTizer_Farsi;
    @SerializedName("NameFolder")
    @Expose
    private String NameFolder;
    @SerializedName("Image")
    @Expose
    private byte[] Image;

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public int getCcTizer() {
        return ccTizer;
    }

    public void setCcTizer(int ccTizer) {
        this.ccTizer = ccTizer;
    }

    public String getNameTizer() {
        return NameTizer;
    }

    public void setNameTizer(String nameTizer) {
        NameTizer = nameTizer;
    }

    public String getNameTizer_Farsi() {
        return NameTizer_Farsi;
    }

    public void setNameTizer_Farsi(String nameTizer_Farsi) {
        NameTizer_Farsi = nameTizer_Farsi;
    }

    public String getNameFolder() {
        return NameFolder;
    }

    public void setNameFolder(String nameFolder) {
        NameFolder = nameFolder;
    }


    public String getTABLE_NAME() {
        return "Tizer";
    }

    public String getCOLUMN_ccTizer() {
        return "ccTizer";
    }

    public String getCOLUMN_NameTizer() {
        return "NameTizer";
    }

    public String getCOLUMN_NameTizer_Farsi() {
        return "NameTizer_Farsi";
    }

    public String getCOLUMN_NameFolder() {
        return "NameFolder";
    }

    public String getCOLUMN_Image() {
        return "Image";
    }

    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        String encodedImage = Base64.encodeToString(Image, Base64.NO_WRAP);
        try
        {
            jsonObject.put("ccTizer" , ccTizer);
            jsonObject.put("NameTizer" , NameTizer);
            jsonObject.put("NameFolder" , NameFolder);
            jsonObject.put("Image" , encodedImage);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }

    public String toJsonString()
    {

        String ImageEndcode = Base64.encodeToString(Image , Base64.DEFAULT);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(getCOLUMN_ccTizer() , ccTizer);
        jsonObject.addProperty(getCOLUMN_NameFolder() , NameFolder);
        jsonObject.addProperty(getCOLUMN_NameTizer() , NameTizer);
        jsonObject.addProperty(getCOLUMN_Image() , ImageEndcode);

        return jsonObject.toString();
    }

}
