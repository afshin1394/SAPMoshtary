package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class GetProgramShared
{

    private SharedPreferences sharedPreferences;
    private Context context;
    private final String PERSIAN_DATE_OF_GET_PROGRAM = "QBqwFB83rQyr";
    private final String GREGORIAN_DATE_OF_GET_PROGRAM = "kOPQzDujFyfs";
    private final String GREGORIAN_DATE_TIME_OF_GET_CONFIG = "J6WTpzw9JX"; // تاریخ و ساعت آخرین دریافت تنظیمات
    private final String SELECT_FOROSHANDEH = "J6WTpzdfd"; // فروشنده انتخاب شده جهت دریافت برنامه


    public GetProgramShared(Context context)
    {
        this.context = context;
        final String SHARED_NAME = "nexMmkk";
        try
        {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n shared name : " + SHARED_NAME, "GetProgramShared", "", "constructor", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "GetProgramShared", "", "putString", "");
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "GetProgramShared", "", "getString", "");
            return defaultValue;
        }
    }

    public void removeAll()
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(PERSIAN_DATE_OF_GET_PROGRAM);
            editor.remove(GREGORIAN_DATE_OF_GET_PROGRAM);
            editor.apply();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "GetProgramShared", "", "removeAll", "");
        }
    }






    public String PERSIAN_DATE_OF_GET_PROGRAM() {
        return PERSIAN_DATE_OF_GET_PROGRAM;
    }
    public String GREGORIAN_DATE_OF_GET_PROGRAM() {
        return GREGORIAN_DATE_OF_GET_PROGRAM;
    }
    public String GREGORIAN_DATE_TIME_OF_GET_CONFIG() {
        return GREGORIAN_DATE_TIME_OF_GET_CONFIG;
    }
    public String SELECT_FOROSHANDEH() {
        return SELECT_FOROSHANDEH;
    }


}
