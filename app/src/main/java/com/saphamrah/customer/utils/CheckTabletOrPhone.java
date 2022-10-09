package com.saphamrah.customer.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class CheckTabletOrPhone {

    private Context context;

    public CheckTabletOrPhone(Context context) {
        this.context = context;
    }

    public boolean isTablet() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;

        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        // 6.5inch device or bigger
        // smaller device
        return diagonalInches >= 6.5;

    }
}
