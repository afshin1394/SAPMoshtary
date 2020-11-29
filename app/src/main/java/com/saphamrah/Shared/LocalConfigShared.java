package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.PubFunc.PubFunc;

public class LocalConfigShared
{

    private static final String CLASS_NAME = "LocalConfigShared";
    private SharedPreferences sharedPreferences;
    private Context context;

    public static final String GALLERY_VERSION = "Uw5pnTCk3T";
    public static final String JAYEZEH_TAKHFIF_VERSION = "XKAer7rrfm";

    public LocalConfigShared(Context context)
    {
        final String SHARED_NAME = "FvqUvvHA2v";
        this.context = context;
        try
        {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, exception.toString() + "\n shared name : " + SHARED_NAME, CLASS_NAME, "", "constructor", "");
        }
    }

    public void putInt(String key , int value)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key , value);
            editor.apply();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, exception.toString() + "\n key : " + key + " , value : " + value, CLASS_NAME, "", "putInt", "");
        }
    }

    public int getInt(String key , int defaultValue)
    {
        try
        {
            return sharedPreferences.getInt(key , defaultValue);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,LogPPCModel.LOG_EXCEPTION, exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, CLASS_NAME, "", "getInt", "");
            return defaultValue;
        }
    }


    public void remove(String key)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }


}
