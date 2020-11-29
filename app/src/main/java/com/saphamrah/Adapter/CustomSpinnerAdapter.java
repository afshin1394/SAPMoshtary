package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.saphamrah.R;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends ArrayAdapter<String>
{
    Typeface font = Typeface.createFromAsset(getContext().getAssets(), getContext().getResources().getString(R.string.fontPath));

    public CustomSpinnerAdapter(Context context, int resource, ArrayList<String> items) {
        super(context, resource, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(font);
        return view;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(font);
        return view;
    }

}
