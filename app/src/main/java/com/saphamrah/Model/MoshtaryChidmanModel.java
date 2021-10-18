package com.saphamrah.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.UIModel.CustomerDarkhastFaktorModel;
import com.saphamrah.Utils.Constants;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MoshtaryChidmanModel implements Parcelable
{




    private static final String TABLE_NAME = "MoshtaryChidman";
    private static final String COLUMN_ccMoshtaryChidman = "ccMoshtaryChidman";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccMasir = "ccMasir";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_Tarikh = "Tarikh";
    private static final String COLUMN_Description = "Description";
    private static final String COLUMN_Image = "Image";
    private static final String COLUMN_ExtraProp_IsSend = "ExtraProp_IsSend";


    public MoshtaryChidmanModel() {
    }

    protected MoshtaryChidmanModel(Parcel in) {
        ccMoshtaryChidman = in.readInt();
        ccMoshtary = in.readInt();
        ccDarkhastFaktor = in.readLong();
        ccMasir = in.readInt();
        Tarikh = in.readString();
        Description = in.readString();
        Image = in.createByteArray();
        extraProp_IsSend = in.readInt();

    }

    public static final Creator<MoshtaryChidmanModel> CREATOR = new Creator<MoshtaryChidmanModel>() {
        @Override
        public MoshtaryChidmanModel createFromParcel(Parcel in) {
            return new MoshtaryChidmanModel(in);
        }

        @Override
        public MoshtaryChidmanModel[] newArray(int size) {
            return new MoshtaryChidmanModel[size];
        }
    };

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtaryChidman() {
        return COLUMN_ccMoshtaryChidman;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccMasir() {
        return COLUMN_ccMasir;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_Tarikh() {
        return COLUMN_Tarikh;
    }
    public static String COLUMN_Description() {
        return COLUMN_Description;
    }
    public static String COLUMN_Image() {
        return COLUMN_Image;
    }
    public static String COLUMN_ExtraProp_IsSend() {
        return COLUMN_ExtraProp_IsSend;
    }





    private int ccMoshtaryChidman;
    public int getCcMoshtaryChidman() {
        return ccMoshtaryChidman;
    }
    public void setCcMoshtaryChidman(int ccMoshtaryChidman) {
        this.ccMoshtaryChidman = ccMoshtaryChidman;
    }


    private int ccMoshtary;
    public int getCcMoshtary() {
        return ccMoshtary;
    }
    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }


    private long ccDarkhastFaktor;

    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    private int ccMasir;
    public int getCcMasir() {
        return ccMasir;
    }
    public void setCcMasir(int ccMasir) {
        this.ccMasir = ccMasir;
    }


    private String Tarikh;
    public String getTarikh() {
        return Tarikh;
    }
    public void setTarikh(String tarikh) {
        Tarikh = tarikh;
    }

    private String Description;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    private byte[] Image;

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    private int extraProp_IsSend;


    public int getExtraProp_IsSend() {
        return extraProp_IsSend;
    }

    public void setExtraProp_IsSend(int extraProp_IsSend) {
        this.extraProp_IsSend = extraProp_IsSend;
    }

    @Override
    public String toString() {
        return "MoshtaryChidmanModel{" +
                "ccMoshtaryChidman=" + ccMoshtaryChidman +
                ", ccMoshtary=" + ccMoshtary +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccMasir=" + ccMasir +
                ", Tarikh='" + Tarikh + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Tarikh);
        parcel.writeInt(ccMasir);
        parcel.writeInt(ccMoshtaryChidman);
        parcel.writeString(Description);
        parcel.writeInt(ccMoshtary);
        parcel.writeLong(ccDarkhastFaktor);
        parcel.writeByteArray(Image);
        parcel.writeInt(extraProp_IsSend);
    }

    public JSONObject toJson()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {

            jsonObject.put("ccMoshtaryChidman" ,ccMoshtaryChidman);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("Description" , Description);
            jsonObject.put("Tarikh" , Tarikh);
            jsonObject.put("ccMasir" , ccMasir);
            jsonObject.put("Image", Base64.encodeToString(Image,Base64.DEFAULT));

            Log.d("DarkhastFaktorModel", "ccMoshtaryChidman : " + ccMoshtaryChidman + " ,ccMoshtary: " + ccMoshtary + " ,ccDarkhastFaktor: " + ccDarkhastFaktor + " ,Description: " + Description + " ,ccMasir: " + ccMasir + " ,tarikh: " + Tarikh + " ,image: " + Image);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("moshtaryChidmanModel", "toJson: error:"+e.getMessage());
        }

        return jsonObject;
    }

}
