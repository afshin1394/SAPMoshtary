package com.saphamrah.customer.utils.customViews;

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

import com.saphamrah.customer.R;

import java.util.List;

public class CustomSpinner {
    private int selectedIndex;
    public void showSpinnerSingleButton(Activity activity , List<String> itemTitles,boolean cancelable, final CustomSpinnerResponse response)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View myview = activity.getLayoutInflater().inflate(R.layout.spinner_simple_single_button , null);
        Typeface font = Typeface.createFromAsset(activity.getAssets() , activity.getResources().getString(R.string.fontPath));
        selectedIndex = -1;
        ListView listView = myview.findViewById(R.id.lstview);
        TextView lblEmptyItem = myview.findViewById(R.id.lblEmptyItem);
        Button btnApply = myview.findViewById(R.id.btnApply);

        lblEmptyItem.setTypeface(font);
        btnApply.setTypeface(font);

        btnApply.setText(activity.getResources().getString(R.string.apply));

        final SpinnerSimpleAdapter adapter = new SpinnerSimpleAdapter(activity , itemTitles);
        listView.setAdapter(adapter);
        listView.setEmptyView(lblEmptyItem);
        builder.setCancelable(cancelable);
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
                    if (selectedIndex != -1) {
                        show.dismiss();
                        response.onSingleSelect(selectedIndex);
                    }
                }
            });

        }
    }
}
