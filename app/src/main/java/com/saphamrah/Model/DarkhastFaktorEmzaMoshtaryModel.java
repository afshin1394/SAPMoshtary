package com.saphamrah.Model;

import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.saphamrah.PubFunc.PubFunc;

import org.json.JSONObject;

import java.util.Arrays;

public class DarkhastFaktorEmzaMoshtaryModel
{

    private static final String TABLE_NAME = "DarkhastFaktor_EmzaMoshtary";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_EmzaImage = "EmzaImage";
    private static final String COLUMN_DarkhastFaktorImage = "DarkhastFaktorImage";
    private static final String COLUMN_ReceiptImage = "ReceiptImage";
    private static final String COLUMN_Have_ReceiptImage = "Have_ReceiptImage";
    private static final String COLUMN_Have_FaktorImage = "Have_FaktorImage";
    private static final String ExtraProp_IsSend_ReceiptImage = "ExtraProp_IsSend_ReceiptImage";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_EmzaImage() {
        return COLUMN_EmzaImage;
    }
    public static String COLUMN_DarkhastFaktorImage() {
        return COLUMN_DarkhastFaktorImage;
    }
    public static String COLUMN_ReceiptImage() {
        return COLUMN_ReceiptImage;
    }
    public static String COLUMN_Have_ReceiptImage() {
        return COLUMN_Have_ReceiptImage;
    }
    public static String COLUMN_Have_FaktorImage() {
        return COLUMN_Have_FaktorImage;
    }
    public static String COLUMN_ExtraProp_IsSend_ReceiptImage() {
        return ExtraProp_IsSend_ReceiptImage;
    }




    private long ccDarkhastFaktor;
    private int ccMoshtary;
    private byte[] EmzaImage;
    private byte[] DarkhastFaktorImage;
    private byte[] ReceiptImage;
    private int Have_FaktorImage;
    private int Have_ReceiptImage;
    private String ExtraProp_UniqueID;
    private int extraProp_IsSend_ReceiptImage;


    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }
    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }
    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public byte[] getEmzaImage() {
        return EmzaImage;
    }
    public void setEmzaImage(byte[] emzaImage) {
        EmzaImage = emzaImage;
    }

    public byte[] getDarkhastFaktorImage() {
        return DarkhastFaktorImage;
    }
    public void setDarkhastFaktorImage(byte[] darkhastFaktorImage) {
        DarkhastFaktorImage = darkhastFaktorImage;
    }

    public int getHave_FaktorImage() {
        return Have_FaktorImage;
    }
    public void setHave_FaktorImage(int have_FaktorImage) {
        Have_FaktorImage = have_FaktorImage;
    }

    public byte[] getReceiptImage() {
        return ReceiptImage;
    }

    public void setReceiptImage(byte[] receiptImage) {
        ReceiptImage = receiptImage;
    }

    public int getHave_ReceiptImage() {
        return Have_ReceiptImage;
    }

    public void setHave_ReceiptImage(int have_ReceiptImage) {
        Have_ReceiptImage = have_ReceiptImage;
    }

    public String getExtraProp_UniqueID() {
        return ExtraProp_UniqueID;
    }

    public void setExtraProp_UniqueID(String extraProp_UniqueID) {
        ExtraProp_UniqueID = extraProp_UniqueID;
    }

    public int getExtraProp_IsSend_ReceiptImage() {
        return extraProp_IsSend_ReceiptImage;
    }

    public void setExtraProp_IsSend_ReceiptImage(int extraProp_IsSend_ReceiptImage) {
        this.extraProp_IsSend_ReceiptImage = extraProp_IsSend_ReceiptImage;
    }

    public String toJsonString()
    {
        String darkhastFaktorImage = Base64.encodeToString(DarkhastFaktorImage , Base64.DEFAULT);
        String emzaImage = Base64.encodeToString(EmzaImage , Base64.DEFAULT);
        String receiptImage = Base64.encodeToString(ReceiptImage , Base64.DEFAULT);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(COLUMN_ccDarkhastFaktor() , ccDarkhastFaktor);
        jsonObject.addProperty(COLUMN_ccMoshtary() , ccMoshtary);
        jsonObject.addProperty(COLUMN_EmzaImage() , emzaImage);
        jsonObject.addProperty(COLUMN_ReceiptImage() , receiptImage);
        jsonObject.addProperty(COLUMN_DarkhastFaktorImage() , darkhastFaktorImage);
        jsonObject.addProperty(COLUMN_Have_FaktorImage() , Have_FaktorImage);
        jsonObject.addProperty(COLUMN_Have_ReceiptImage() , Have_ReceiptImage);
        return jsonObject.toString();
    }
    public JSONObject toJsonObjectReceiptImage()
    {
        JSONObject jsonObject = new JSONObject();
        String encodedImage = Base64.encodeToString(DarkhastFaktorImage, Base64.NO_WRAP);
        String receiptImage="";
        if(ReceiptImage!=null)
            receiptImage = Base64.encodeToString(ReceiptImage, Base64.NO_WRAP);
        try
        {
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("receiptImage" , receiptImage);

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }


    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        String encodedImage = Base64.encodeToString(DarkhastFaktorImage, Base64.NO_WRAP);

        try
        {
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("Image" , encodedImage);
            jsonObject.put("Noe" , 1);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public String toString() {
        return "DarkhastFaktorEmzaMoshtaryModel{" +
                "ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccMoshtary=" + ccMoshtary +
                ", EmzaImage=" + Arrays.toString(EmzaImage) +
                ", DarkhastFaktorImage=" + Arrays.toString(DarkhastFaktorImage) +
                ", ReceiptImage=" + Arrays.toString(ReceiptImage) +
                ", Have_FaktorImage=" + Have_FaktorImage +
                ", Have_ReceiptImage=" + Have_ReceiptImage +
                ", ExtraProp_UniqueID='" + ExtraProp_UniqueID + '\'' +
                ", extraProp_IsSend_ReceiptImage=" + extraProp_IsSend_ReceiptImage +
                '}';
    }
}
