package com.saphamrah.customer.utils.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;

import com.saphamrah.customer.R;

import java.util.List;

public class CustomDropDownSpinner extends AppCompatSpinner {
    public CustomDropDownSpinner(Context context) {
        super(context);
    }

    public CustomDropDownSpinner(Context context, int mode) {
        super(context, mode);
    }

    public CustomDropDownSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initList(List<String> titles){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_title, titles);
        adapter.setDropDownViewResource(R.layout.custom_spinner_itemview);
    }

    public void initArray(String[] titles){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_title, titles);
        adapter.setDropDownViewResource(R.layout.custom_spinner_itemview);
    }

}
