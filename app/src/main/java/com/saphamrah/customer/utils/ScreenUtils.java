package com.saphamrah.customer.utils;

import android.view.View;

public class ScreenUtils {
    public static float[] getViewLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        float[] result = new float[2];
        result[0] = location[0];
        result[1] = location[1];
        return result;
    }
}
