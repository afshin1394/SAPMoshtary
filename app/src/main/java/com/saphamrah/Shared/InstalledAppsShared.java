package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class InstalledAppsShared {
    private SharedPreferences sharedPreferences;
    private Context context;
    public static final String INSTALLED_APPLICATIONS = "2installoapplicant1";

    public InstalledAppsShared(Context context) {
        this.context = context;
        final String SHARED_NAME = "installingo23s";
        try {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n shared name : " + SHARED_NAME, "InstalledAppsShared", "", "constructor", "");
        }
    }

    public boolean putString(String key, String value) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, exception.toString() + "\n key : " + key + " , value : " + value, InstalledAppsShared.class.getSimpleName(), "", "putString", "");
            return false;
        }
    }


    public String getString(String key, String defaultValue) {
        try {
            return sharedPreferences.getString(key, defaultValue);
        } catch (Exception e) {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, e.toString() + "\n key : " + key, InstalledAppsShared.class.getSimpleName(), "", "getString", "");
            return defaultValue;
        }
    }

    public boolean removeAll() {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(INSTALLED_APPLICATIONS);
            editor.clear();
            editor.apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, e.toString(), InstalledAppsShared.class.getSimpleName(), "", "removeAll", "");
            return false;
        }
    }
}
