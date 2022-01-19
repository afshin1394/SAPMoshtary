package com.saphamrah.Model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerDarkhastFaktorModel;

import org.json.JSONObject;

public class TafkikJozeModel
{

    private static final String TABLE_NAME = "TafkikJoze";
    private static final String COLUMN_ccTafkikJoze = "ccTafkikJoze";
    private static final String COLUMN_ShomarehTafkikJoze = "ShomarehTafkikJoze";
    private static final String COLUMN_CodeVazeiat = "CodeVazeiat";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccTafkikJoze() {
        return COLUMN_ccTafkikJoze;
    }
    public static String COLUMN_ShomarehTafkikJoze() {
        return COLUMN_ShomarehTafkikJoze;
    }
    public static String COLUMN_CodeVazeiat() {
        return COLUMN_CodeVazeiat;
    }


    @SerializedName("ccTafkikJoze")
    @Expose
    private long ccTafkikJoze;
    @SerializedName("ShomarehTafkikJoze")
    @Expose
    private int ShomarehTafkikJoze;
    @SerializedName("CodeVazeiat")
    @Expose
    private int CodeVazeiat;


    public long getCcTafkikJoze()
    {
        return ccTafkikJoze;
    }
    public void setCcTafkikJoze(long ccTafkikJoze)
    {
        this.ccTafkikJoze = ccTafkikJoze;
    }

    public int getShomarehTafkikJoze()
    {
        return ShomarehTafkikJoze;
    }
    public void setShomarehTafkikJoze(int shomarehTafkikJoze)
    {
        ShomarehTafkikJoze = shomarehTafkikJoze;
    }

    public int getCodeVazeiat() {
        return CodeVazeiat;
    }

    public void setCodeVazeiat(int codeVazeiat) {
        CodeVazeiat = codeVazeiat;
    }

    public JSONObject toJson()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {


            jsonObject.put("ccTafkikJoze" , ccTafkikJoze);
            jsonObject.put("ShomarehTafkikJoze" , ShomarehTafkikJoze);
            jsonObject.put("CodeVazeiat" , CodeVazeiat);

            Log.d("TafkikJozeModel", "odat tafkik Joze : " +jsonObject.toString());

        }

        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(BaseApplication.getContext(), LogPPCModel.LOG_EXCEPTION, e.getMessage(), "TafkikJozeModel" , "" , "" , "toJson");
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return "TafkikJozeModel{" +
                "ccTafkikJoze=" + ccTafkikJoze +
                ", ShomarehTafkikJoze=" + ShomarehTafkikJoze +
                ", CodeVazeiat=" + CodeVazeiat +
                '}';
    }
}
