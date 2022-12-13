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
    private Context context;
    public PersianRangeSlider(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public PersianRangeSlider(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PersianRangeSlider(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setStepSize(1000);
        this.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return  String.format("%1$s %2$s", ((int) value),"ريال");
            }
        });
        this.setLabelBehavior(LabelFormatter.LABEL_FLOATING);
    }
}
