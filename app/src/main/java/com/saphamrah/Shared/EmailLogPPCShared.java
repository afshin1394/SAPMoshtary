package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class EmailLogPPCShared
{

    private SharedPreferences sharedPreferences;
    private Context context;
    private final String ID = "NoeoibNw";
    private final String EMAIL = "vhIjVtPwt";
    private final String PASSWORD = "xS6o833E0";

    public EmailLogPPCShared(Context context)
    {
        this.context = context;
        final String SHARED_NAME = "b84mE1kIrmtSlJCQQD5e";
        try
        {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n shared name : " + SHARED_NAME, "EmailLogPPCShared", "", "constructor", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "EmailLogPPCShared", "", "putInt", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "EmailLogPPCShared", "", "putString", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "EmailLogPPCShared", "", "getInt", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "EmailLogPPCShared", "", "getString", "");
            return defaultValue;
        }
    }

    public void removeAll()
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ID);
            editor.remove(EMAIL);
            editor.remove(PASSWORD);
            editor.apply();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "EmailLogPPCShared", "", "removeAll", "");
        }
    }


    public String ID() {
        return ID;
    }

    public String EMAIL() {
        return EMAIL;
    }

    public String PASSWORD() {
        return PASSWORD;
    }

}
