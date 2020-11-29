package com.saphamrah.CustomView;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

public class CustomProgressBar
{

    private static final String CLASS_NAME = "CustomProgressBar";
    private AlertDialog show;
    private TextView lblProgressPercentage;
    private ProgressBar progressBar;

    public void showProgressBar(Activity activity)
    {
        View view = activity.getLayoutInflater().inflate(R.layout.alert_progress_bar , null);
        Typeface font = Typeface.createFromAsset(activity.getAssets() , activity.getResources().getString(R.string.fontPath));
        lblProgressPercentage = view.findViewById(R.id.lblProgressPercentage);
        progressBar = view.findViewById(R.id.progress);
        lblProgressPercentage.setTypeface(font);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setView(view);
        builder.create();
        if (!(activity).isFinishing())
        {
            show = builder.show();
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
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(activity, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activity.getClass().getSimpleName(), "showProgressBar", "");
            }
        }
    }


    public void updateProgress(int progress)
    {
        try
        {
            lblProgressPercentage.setText(String.valueOf(progress));
            progressBar.setProgress(progress);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void closeProgress()
    {
        try
        {
            show.dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



}
