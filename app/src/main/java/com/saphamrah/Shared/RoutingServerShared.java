package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class RoutingServerShared
{

    private static final String CLASS_NAME = RoutingServerShared.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private Context context;
    public static final String IP = "YvZtC4UH";


    public RoutingServerShared(Context context)
    {
        this.context = context;
        final String SHARED_NAME = "QZgBKw9MpTu6AZ";
        try
        {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n shared name : " + SHARED_NAME, CLASS_NAME, "", "constructor", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, CLASS_NAME, "", "putString", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, CLASS_NAME, "", "getString", "");
            return defaultValue;
        }
    }

    public void removeAll()
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(IP);
            editor.apply();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "removeAll", "");
        }
    }


}
