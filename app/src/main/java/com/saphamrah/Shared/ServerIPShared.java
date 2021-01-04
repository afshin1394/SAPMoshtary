package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class ServerIPShared
{

    private SharedPreferences sharedPreferences;
    private Context context;

    //IP && PORT GET REQUESTS
    private final String IP_GET = "IP_GET";
    private final String PORT_GET = "PORT_GET";

    //IP && PORT POST REQUESTS
    private final String IP_POST = "IP_POST";
    private final String PORT_POST = "PORT_POST";


    //IP && PORT MULTI REQUESTS
    private final String IP_MULTI = "IP_MULTI";
    private final String PORT_MULTI = "PORT_MULTI";

    private final String DEVICE_IP = "6cBqwh1YxUH0O33l";
    private final String SERVER_TYPE ="serverType";



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
            editor.remove(IP_GET);
            editor.remove(IP_POST);
            editor.remove(IP_MULTI);
            editor.remove(PORT_GET);
            editor.remove(PORT_POST);
            editor.remove(PORT_MULTI);
            editor.remove(DEVICE_IP);
            editor.remove(SERVER_TYPE);
            editor.apply();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ServerIPShared", "", "removeAll", "");
        }
    }




    public String IP_GET_REQUEST() {
        return IP_GET;
    }

    public String PORT_GET_REQUEST() {
        return PORT_GET;
    }
	
    public String DEVICE_IP() {
        return DEVICE_IP;
    }


    public String IP_POST_REQUEST() {
        return IP_POST;
    }

    public String PORT_POST_REQUEST() {
        return PORT_POST;
    }

    public String IP_MULTI_REQUEST() {
        return IP_MULTI;
    }

    public String PORT_MULTI_REQUEST() {
        return PORT_MULTI;
    }
}
