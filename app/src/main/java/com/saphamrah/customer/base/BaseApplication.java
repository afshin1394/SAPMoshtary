package com.saphamrah.customer.base;

import android.app.Application;

public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }
}
