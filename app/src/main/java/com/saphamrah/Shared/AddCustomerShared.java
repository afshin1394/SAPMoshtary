package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class AddCustomerShared
{

    private SharedPreferences sharedPreferences;
    private Context context;
    private final String JSON_DATA = "Np9QfVg6Y7UG8h";

    public AddCustomerShared(Context context)
    {
        this.context = context;
        final String SHARED_NAME = "nzpVR72bwJA&X";
        try
        {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n shared name : " + SHARED_NAME, "AddCustomerShared", "", "constructor", "");
        }
    }


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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "AddCustomerShared", "", "putString", "");
            return false;
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "AddCustomerShared", "", "getString", "");
            return defaultValue;
        }
    }

    public AddCustomerInfoModel getAddCustomerInfoModel(String key , String defaultValue)
    {
        String data = sharedPreferences.getString(key , defaultValue);
        if (data.equals(""))
        {
            return null;
        }
        else
        {
            try
            {
                JsonParser parser = new JsonParser();
                JsonElement jsonElement =  parser.parse(data);
                Gson gson = new Gson();
                return gson.fromJson(jsonElement , AddCustomerInfoModel.class);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "AddCustomerShared", "", "getAddCustomerInfoModel", "");
                return null;
            }
        }

    }


    public void removeAll()
    {
        Log.d("addcustomer" , "shared delete context : ");
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(JSON_DATA);
            editor.apply();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "AddCustomerShared", "", "removeAll", "");
        }
    }


    public String JSON_DATA()
    {
        return JSON_DATA;
    }


}
