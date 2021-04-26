package com.saphamrah.PubFunc;

import android.os.Handler;
import android.os.Looper;

public class ConcurrencyUtils {
    private ConcurrencyUtils() {
    }

    static ConcurrencyUtils instance = null;

    // Factory method to provide the users with instances
    public static ConcurrencyUtils getInstance() {
        if (instance == null)
            instance = new ConcurrencyUtils();

        return instance;

    }


    public void runOnUiThread(ConcurrencyEvents concurrencyEvents) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                concurrencyEvents.uiThreadIsReady();
            }
        };
        mainHandler.post(runnable);
    }

    public interface ConcurrencyEvents {
        void uiThreadIsReady();
    }
}




