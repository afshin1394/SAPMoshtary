package com.saphamrah.customer.utils.customViews;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.saphamrah.customer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



    public class SpinnerSimpleAdapter extends ArrayAdapter<String>
    {

        Activity activity;
        ArrayList<Integer> itemStatus;
        List<String> itemTitles;

        public SpinnerSimpleAdapter(Activity activity , List<String> itemTitles)
        {
            super(activity, 0, itemTitles);
            this.activity = activity;
            itemStatus = new ArrayList<>(Collections.nCopies(itemTitles.size() , 0));
            this.itemTitles = itemTitles;
        }

        @NonNull
        @Override
        public View getView(final int position, final View convertView, @NonNull ViewGroup parent)
        {
            View view = convertView;
            ViewHolder viewHolder;
            final Typeface font = Typeface.createFromAsset(getContext().getAssets() , getContext().getResources().getString(R.string.fontPath));
            if(view == null)
            {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(activity).inflate(R.layout.spinner_simple_item , parent , false);
                viewHolder.layRoot = (RelativeLayout)view.findViewById(R.id.layRoot);
                viewHolder.imgStatus = (ImageView) view.findViewById(R.id.imgStatus);
                viewHolder.lblTitle = (TextView) view.findViewById(R.id.lblTitle);

                view.setTag(viewHolder);
            }
            else
            {
                viewHolder= (ViewHolder) view.getTag();
            }
            viewHolder.lblTitle.setText(itemTitles.get(position));
            viewHolder.lblTitle.setTypeface(font);

            if (itemStatus.get(position) == 0)
            {
                viewHolder.imgStatus.setImageResource(R.drawable.circle_tick_gray);
            }
            else
            {
                viewHolder.imgStatus.setImageResource(R.drawable.ic_circle_tick_dark_red);
            }

            return view;
        }


        private static class ViewHolder
        {
            RelativeLayout layRoot;
            ImageView imgStatus;
            TextView lblTitle;
        }


        public void updateListStatus(int selectedIndex)
        {
            itemStatus = new ArrayList<>(Collections.nCopies(itemTitles.size() , 0));
            itemStatus.set(selectedIndex , 1);
            notifyDataSetChanged();
        }


        public void updateListStatus(ArrayList<Integer> selectedIndex)
        {
            itemStatus = new ArrayList<>(Collections.nCopies(itemTitles.size() , 0));
            for (Integer index : selectedIndex)
            {
                itemStatus.set(index, 1);
            }
            notifyDataSetChanged();
        }

    }

