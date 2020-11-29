package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class ServerIPShared
{

    private SharedPreferences sharedPreferences;
    private Context context;
    private final String IP = "NoeoibNw";
    private final String PORT = "vhIjVtPwt";
    private final String DEVICE_IP = "6cBqwh1YxUH0O33l";

    public ServerIPShared(Context context)
    {
        this.context = context;
        final String SHARED_NAME = "DG0Y65ky5n7w8zxNfP";
        try
        {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n shared name : " + SHARED_NAME, "ServerIPShared", "", "constructor", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "ServerIPShared", "", "putInt", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "ServerIPShared", "", "putString", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "ServerIPShared", "", "getInt", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "ServerIPShared", "", "getString", "");
            return defaultValue;
        }
    }

    public void remove(String key)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
	
    public void removeAll()
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(IP);
            editor.remove(PORT);
			editor.remove(DEVICE_IP);						 
            editor.apply();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ServerIPShared", "", "removeAll", "");
        }
    }


    public String IP() {
        return IP;
    }

    public String PORT() {
        return PORT;
    }
	
    public String DEVICE_IP() {
        return DEVICE_IP;
    }
}
