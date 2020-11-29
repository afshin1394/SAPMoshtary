package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.PubFunc.PubFunc;


public class LastOlaviatMoshtaryShared
{

    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String CLASS_NAME = "LastOlaviatMoshtaryShared";


    public static final String OLAVIAT = "tcHhd5BTzG";
    public static final String TARIKH = "YXhKB4ZKDz";
    public static final String CCMOSHTARY = "uY7xNRDTCG";


    public LastOlaviatMoshtaryShared(Context context)
    {
        this.context = context;
        final String SHARED_NAME = "f8p6d4psJb";
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


    ////////////////////////////////////////// PUT //////////////////////////////////////////

    public boolean putString(String key , String value)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key , value);
            editor.apply();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, exception.toString() + "\n key : " + key + " , value : " + value, CLASS_NAME, "", "putString", "");
            return false;
        }
    }


    public boolean putInt(String key , int value)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key , value);
            editor.apply();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,LogPPCModel.LOG_EXCEPTION, exception.toString() + "\n key : " + key + " , value : " + value, CLASS_NAME, "", "putInt", "");
            return false;
        }
    }


    ////////////////////////////////////////// GET //////////////////////////////////////////


    public int getInt(String key , int defaultValue)
    {
        try
        {
            return sharedPreferences.getInt(key , defaultValue);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,LogPPCModel.LOG_EXCEPTION, e.toString() + "\n key : " + key, CLASS_NAME, "", "getInt", "");
            return defaultValue;
        }
    }

    public String getString(String key , String defaultValue)
    {
        try
        {
            return sharedPreferences.getString(key , defaultValue);
        }
        catch (Exception e)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,LogPPCModel.LOG_EXCEPTION, e.toString() + "\n key : " + key, CLASS_NAME, "", "getString", "");
            return defaultValue;
        }
    }



    public boolean removeAll()
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(OLAVIAT);
            editor.remove(TARIKH);
            editor.remove(CCMOSHTARY);
            editor.clear();
            editor.apply();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,LogPPCModel.LOG_EXCEPTION, e.toString(), CLASS_NAME, "", "removeAll", "");
            return false;
        }
    }



}
