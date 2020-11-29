package com.saphamrah.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.whiteelephant.gifplayer.GifView;

public class CustomLoadingDialog
{

    private final String CLASS_NAME = "CustomLoadingDialog";


    public AlertDialog showLoadingDialog(final Activity mcontext)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        AlertDialog alertDialog = null;
        View myview = mcontext.getLayoutInflater().inflate(R.layout.alert_loading , null);
        TextView lbl = myview.findViewById(R.id.lblPleaseWait);
        GifView gifView = myview.findViewById(R.id.gif);
        Typeface font = Typeface.createFromAsset(mcontext.getAssets(), mcontext.getResources().getString(R.string.fontPath));
        lbl.setTypeface(font);
        builder.setCancelable(false);
        builder.setView(myview);
        builder.create();
        if (!(mcontext).isFinishing())
        {
            try
            {
                alertDialog = builder.show();
                gifView.start();
                if (alertDialog.getWindow() != null)
                {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(mcontext, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "showLoadingDialog", "");
            }
        }
        return alertDialog;
    }


}
