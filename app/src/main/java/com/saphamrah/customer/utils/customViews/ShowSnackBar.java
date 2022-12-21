package com.saphamrah.customer.utils.customViews;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.saphamrah.customer.R;


public class ShowSnackBar {

    public static Snackbar showSnack(View view, String message, Integer duration) {

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

        //If the view is not covering the whole snackbar layout, add this line
        layout.setPadding(0, 0, 0, 0);

        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);

        return snackbar;
    }
}
