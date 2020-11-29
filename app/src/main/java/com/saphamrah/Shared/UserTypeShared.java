package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class UserTypeShared
{
    private SharedPreferences sharedPreferences;
    private Context context;
    private final int USER_TYPE = 453458;
    private final int USING_IMEI = 568732;

    public UserTypeShared(Context context)
    {
        final String SHARED_NAME = "UjJjciShJrzrlGm";
        this.context = context;
        try
        {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n shared name : " + SHARED_NAME, "UserTypeShared", "", "constructor", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "UserTypeShared", "", "putInt", "");
        }
    }


    public void putString(String key , String value)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key , value);
            editor.apply();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "UserTypeShared", "", "putString", "");
        }
    }

    public int getInt(String key , int defaultValue)
    {
        try
        {
            return sharedPreferences.getInt(key , defaultValue); // 0-Main , 1-Test
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "UserTypeShared", "", "getInt", "");
            return defaultValue;
        }
    }


    public String getString(String key , String defaultValue)
    {
        try
        {
            return sharedPreferences.getString(key , defaultValue);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "UserTypeShared", "", "getString", "");
            return defaultValue;
        }
    }

    public String USER_TYPE()
    {
        return String.valueOf(USER_TYPE);
    }

    public String USING_IMEI()
    {
        return String.valueOf(USING_IMEI);
    }

}
