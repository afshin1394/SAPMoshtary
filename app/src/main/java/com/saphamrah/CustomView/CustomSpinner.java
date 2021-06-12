package com.saphamrah.CustomView;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.saphamrah.Adapter.SpinnerSimpleAdapter;
import com.saphamrah.R;
import com.saphamrah.Utils.CustomSpinnerResponse;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinner
{


    private int selectedIndex;

    public void showSpinner(Activity activity , List<String> itemTitles , final CustomSpinnerResponse response)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View myview = activity.getLayoutInflater().inflate(R.layout.spinner_simple , null);
        Typeface font = Typeface.createFromAsset(activity.getAssets() , activity.getResources().getString(R.string.fontPath));
        selectedIndex = 0;
        ListView listView = myview.findViewById(R.id.lstview);
        TextView lblEmptyItem = myview.findViewById(R.id.lblEmptyItem);
        Button btnApply = myview.findViewById(R.id.btnApply);
        Button btnCancel = myview.findViewById(R.id.btnCancel);
        lblEmptyItem.setTypeface(font);
        btnApply.setTypeface(font);
        btnCancel.setTypeface(font);
        btnApply.setText(activity.getResources().getString(R.string.apply));
        btnCancel.setText(activity.getResources().getString(R.string.close));
        final SpinnerSimpleAdapter adapter = new SpinnerSimpleAdapter(activity , itemTitles);
        listView.setAdapter(adapter);
        listView.setEmptyView(lblEmptyItem);
        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                selectedIndex = position;
                adapter.updateListStatus(selectedIndex);
            }
        });

        if (!(activity).isFinishing())
        {
            final AlertDialog show = builder.show();
            try
            {
                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }

            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    show.dismiss();
                    response.onApplySingleSelection(selectedIndex);
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                }
            });

        }
    }


    public void showMultiSelectSpinner(Activity activity , List<String> itemTitles , final CustomSpinnerResponse response)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View myview = activity.getLayoutInflater().inflate(R.layout.spinner_simple , null);
        Typeface font = Typeface.createFromAsset(activity.getAssets() , activity.getResources().getString(R.string.fontPath));
        final ArrayList<Integer> selectedItems = new ArrayList<>();
        ListView listView = myview.findViewById(R.id.lstview);
        TextView lblEmptyItem = myview.findViewById(R.id.lblEmptyItem);
        Button btnApply = myview.findViewById(R.id.btnApply);
        Button btnCancel = myview.findViewById(R.id.btnCancel);
        lblEmptyItem.setTypeface(font);
        btnApply.setTypeface(font);
        btnCancel.setTypeface(font);
        btnApply.setText(activity.getResources().getString(R.string.apply));
        btnCancel.setText(activity.getResources().getString(R.string.close));
        final SpinnerSimpleAdapter adapter = new SpinnerSimpleAdapter(activity , itemTitles);
        listView.setAdapter(adapter);
        listView.setEmptyView(lblEmptyItem);
        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                int index = selectedItems.indexOf(position);
                if (index == -1)
                {
                    selectedItems.add(position);
                }
                else
                {
                    selectedItems.remove(index);
                }
                adapter.updateListStatus(selectedItems);
            }
        });

        if (!(activity).isFinishing())
        {
            final AlertDialog show = builder.show();
            try
            {
                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }

            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    show.dismiss();
                    response.onApplyMultiSelection(selectedItems);
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                }
            });

        }
    }

    public void showSpinnerSingleButton(Activity activity , List<String> itemTitles , final CustomSpinnerResponse response)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View myview = activity.getLayoutInflater().inflate(R.layout.spinner_simple_single_button , null);
        Typeface font = Typeface.createFromAsset(activity.getAssets() , activity.getResources().getString(R.string.fontPath));
        selectedIndex = 0;
        ListView listView = myview.findViewById(R.id.lstview);
        TextView lblEmptyItem = myview.findViewById(R.id.lblEmptyItem);
        Button btnApply = myview.findViewById(R.id.btnApply);

        lblEmptyItem.setTypeface(font);
        btnApply.setTypeface(font);

        btnApply.setText(activity.getResources().getString(R.string.apply));

        final SpinnerSimpleAdapter adapter = new SpinnerSimpleAdapter(activity , itemTitles);
        listView.setAdapter(adapter);
        listView.setEmptyView(lblEmptyItem);
        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                selectedIndex = position;
                adapter.updateListStatus(selectedIndex);
            }
        });

        if (!(activity).isFinishing())
        {
            final AlertDialog show = builder.show();
            try
            {
                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }

            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    show.dismiss();
                    response.onApplySingleSelection(selectedIndex);
                }
            });

        }
    }


}
