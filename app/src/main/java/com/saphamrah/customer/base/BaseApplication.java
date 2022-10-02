package com.saphamrah.customer.base;

import android.app.Application;

import com.saphamrah.customer.listeners.SmsListener;

public class BaseApplication extends Application {
    private SmsListener smsListener;
    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public SmsListener getSmsListener() {
        return smsListener;
    }

    public void setSmsListener(SmsListener smsListener) {
        this.smsListener = smsListener;
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }
}
