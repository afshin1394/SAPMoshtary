package com.saphamrah.customer.utils.customViews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.saphamrah.customer.R;



public class PersianRangeSlider extends RangeSlider {
    public PersianRangeSlider(@NonNull Context context) {
        super(context);

    }

    public PersianRangeSlider(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public PersianRangeSlider(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);

    }



    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setStepSize(1000f);
        this.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return  String.format("%1$s %2$s", ((int) value),"ريال");
            }
        });
    }
}
