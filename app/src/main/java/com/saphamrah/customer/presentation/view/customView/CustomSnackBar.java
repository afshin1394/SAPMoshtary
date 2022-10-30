package com.saphamrah.customer.presentation.view.customView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.saphamrah.customer.R;

public class CustomSnackBar {
    public static Snackbar showSnack(Context context, View view, String message, Integer duration, String action) {

        // Create the Snackbar
        Snackbar snackbar = Snackbar.make(view, "", duration);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        // Hide the text
        /*val textView = layout.findViewById<TextView>(R.id.txt_snack)
        textView.visibility = View.INVISIBLE*/

        // Inflate our custom view
        LayoutInflater mInflater = LayoutInflater.from(view.getContext());

        View snackView = mInflater.inflate(R.layout.snackbar_view, null);
        TextView textViewTop = snackView.findViewById(R.id.txt_snack);
        textViewTop.setText(message);

        Button btnAction = snackView.findViewById(R.id.btn_snack);

        switch (action) {
            case "":
                btnAction.setVisibility(View.GONE);
                break;

            case "undo":
                btnAction.setText(context.getResources().getString(R.string.undo));
                break;

            case "dismiss":
                btnAction.setText(context.getResources().getString(R.string.dismiss));
                break;

            case "retry":
                btnAction.setText(context.getResources().getString(R.string.retry));
                break;
        }


        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (action) {

                    case "undo":
                        //TODO impl undo
                        break;

                    case "dismiss":
                        snackbar.dismiss();

                        break;

                    case "retry":
                        //TODO impl dismiss
                        break;

                }
            }
        });

        //If the view is not covering the whole snackbar layout, add this line
        layout.setPadding(0, 0, 0, 0);

        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);


        return snackbar;
    }

}
