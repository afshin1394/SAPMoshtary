package com.saphamrah.customer.utils.customViews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import com.saphamrah.customer.R;

public class CustomTextInputLayout extends TextInputLayout
{

    Typeface font = Typeface.createFromAsset(getContext().getAssets() , getContext().getString(R.string.fontPath));

    public CustomTextInputLayout(Context context)
    {
        super(context);
    }


    public CustomTextInputLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void addView(View child, int index, final ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child instanceof EditText)
        {
            // Typeface is set when adding an EditText, so we must set ours after

            setTypeface(font);
        }
    }


    @Override
    public void setError(@Nullable CharSequence errorText)
    {
        super.setError(errorText);
        Typeface font = Typeface.createFromAsset(getContext().getAssets() , getContext().getString(R.string.fontPath));
        this.setTypeface(font);
        this.setErrorTextColor(ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorRed)));
        if (errorText != null)
        {
            Animation animation = AnimationUtils.loadAnimation(getContext() , R.anim.shake);
            this.startAnimation(animation);
        }
    }

}
