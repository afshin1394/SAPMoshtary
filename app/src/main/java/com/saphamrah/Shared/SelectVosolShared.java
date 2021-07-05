package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class SelectVosolShared {

    private SharedPreferences sharedPreferences;
    private Context context;
    private String listVosolFaktorRoozByCodePosition = "f3435yhhg6jyh";
    private String listVosolFaktorRoozByRoutingPosition = "f3435yhhg6jyh";
    private String listVosolMandehDarPosition = "f343sggrgwwdwd";
    private String listVosolCcDarkhastFaktor = "f343sggrasdasd";



    public SelectVosolShared(Context context)
    {
        this.context = context;
        final String SHARED_NAME = "cosvolShare245";
        try
        {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n shared name : " + SHARED_NAME, "SelectVosolShared", "", "constructor", "");
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "SelectVosolShared", "", "putInt", "");
            return false;
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "SelectVosolShared", "", "getInt", "");
            return defaultValue;
        }
    }

    public boolean putLong(String key , long value)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(key , value);
            editor.apply();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "SelectVosolShared", "", "putInt", "");
            return false;
        }
    }



    public long getLong(String key , long defaultValue)
    {
        try
        {
            return sharedPreferences.getLong(key , defaultValue);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "SelectVosolShared", "", "getInt", "");
            return defaultValue;
        }
    }

    public void removePositions()
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(listVosolFaktorRoozByCodePosition);
            editor.remove(listVosolFaktorRoozByRoutingPosition);
            editor.remove(listVosolMandehDarPosition);
            editor.remove(listVosolCcDarkhastFaktor);
            editor.apply();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), "SelectVosolShared", "", "remove", "");
        }
    }

    public void removePosition(String key)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            editor.apply();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), "SelectVosolShared", "", "remove", "");
        }
    }

    public void removeCcDarkhastFaktor(String key)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            editor.apply();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), "SelectVosolShared", "", "remove", "");
        }
    }



    public String getListVosolFaktorRoozByCodePosition() {
        return listVosolFaktorRoozByCodePosition;
    }


    public String getListVosolFaktorRoozByRoutingPosition() {
        return listVosolFaktorRoozByRoutingPosition;
    }

    public String getListVosolMandehDarPosition() {
        return listVosolMandehDarPosition;
    }

    public String getListVosolCcDarkhastFaktor() {
        return listVosolCcDarkhastFaktor;
    }
}
