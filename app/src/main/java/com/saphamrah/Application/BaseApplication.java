package com.saphamrah.Application;

import android.app.Activity;
import android.content.Context;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;

import androidx.multidex.MultiDexApplication;

import java.util.logging.Handler;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication instance = null;
    private static Context context;
    private static Activity currentActivity;
    public static final String TAG = BaseApplication.class.getSimpleName();



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * get current context anywhere in app
     * @return just use BaseApplication.getContext
     */
    public static Context getContext() {
        if (currentActivity != null) {
            return currentActivity;
        }
        return context;
    }

    public static Context getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }

    public static synchronized BaseApplication getInstance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }



}