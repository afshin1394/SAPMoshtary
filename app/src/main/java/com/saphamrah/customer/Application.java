package com.saphamrah.customer;

import com.saphamrah.customer.listeners.SmsListener;

public class Application extends android.app.Application {
    private SmsListener smsListener;
    private static Application sInstance;

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

    public static Application getInstance() {
        return sInstance;
    }
}
