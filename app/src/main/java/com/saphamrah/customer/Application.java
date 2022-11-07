package com.saphamrah.customer;

import android.app.Activity;
import android.content.Context;

import com.saphamrah.customer.listeners.SmsListener;

public class Application extends android.app.Application {
    private SmsListener smsListener;
    private static Application instance;
    private static Context context;
    private static Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = this;
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

    public SmsListener getSmsListener() {
        return smsListener;
    }

    public void setSmsListener(SmsListener smsListener) {
        this.smsListener = smsListener;
    }

    public static Context getCurrentActivity() {
        return currentActivity;
    }
    public static Activity getActivity() {
        return currentActivity;
    }
    public static void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }

    public static synchronized Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }
}
