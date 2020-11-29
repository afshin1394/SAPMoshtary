package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class WeatherShared
{

    private SharedPreferences sharedPreferences;
    private Context context;
    private final String MAIN_ID = "vR6G4HQ";
    private final String DESC = "gTbx6Sx4";
    private final String ICON = "Ct8gZS5";
    private final String TEMPERATURE = "JLykaVE9";
    private final String MAX_TEMPERATURE = "NAWf33tD";
    private final String MIN_TEMPERATURE = "QL4MuGea";
    private final String HUMIDITY = "Z67fYvXp";
    private final String WIND_SPEED = "2FjX44BN";

    public WeatherShared(Context context)
    {
        this.context = context;
        final String SHARED_NAME = "dybVrSF9yxWG7eLa";
        try
        {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n shared name : " + SHARED_NAME, "WeatherShared", "", "constructor", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "WeatherShared", "", "putString", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "WeatherShared", "", "getString", "");
            return defaultValue;
        }
    }

    public void removeAll()
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(MAIN_ID);
            editor.remove(DESC);
            editor.remove(ICON);
            editor.remove(TEMPERATURE);
            editor.remove(MAX_TEMPERATURE);
            editor.remove(MIN_TEMPERATURE);
            editor.remove(HUMIDITY);
            editor.remove(WIND_SPEED);
            editor.apply();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "WeatherShared", "", "removeAll", "");
        }
    }



    public String DESC() {
        return DESC;
    }
    public String MAIN_ID() {
        return MAIN_ID;
    }
    public String ICON() {
        return ICON;
    }
    public String TEMPERATURE() {
        return TEMPERATURE;
    }
    public String MAX_TEMPERATURE() {
        return MAX_TEMPERATURE;
    }
    public String MIN_TEMPERATURE() {
        return MIN_TEMPERATURE;
    }
    public String HUMIDITY() {
        return HUMIDITY;
    }
    public String WIND_SPEED() {
        return WIND_SPEED;
    }


}
