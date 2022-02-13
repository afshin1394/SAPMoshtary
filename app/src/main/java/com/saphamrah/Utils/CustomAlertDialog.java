package com.saphamrah.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sackcentury.shinebuttonlib.ShineButton;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Test.LocationAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CustomAlertDialog
{

    private Activity mcontext;
    private final int SHINE_BUTTON_CLICK_DELAY = 500;

    private AlertDialog editableTextShow;


    public CustomAlertDialog(Activity context)
    {
        mcontext = context;
    }



    /*public AlertDialog createLoading()
    {
        Typeface font = Typeface.createFromAsset(mcontext.getAssets(), mcontext.getResources().getString(R.string.FontPath));
        View view = ((LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dlg_progress, null, false);
        TextView textView = (TextView) view.findViewById(R.id.dlgProgress_textview);
        textView.setTypeface(font);
        AlertDialog d = new AlertDialog.Builder(mcontext).setCancelable(false).setView(view).create();
        return d;
    }*/


    private int getMessageIcon(int messageStatus)
    {
        if (messageStatus == Constants.FAILED_MESSAGE())
        {
            return R.drawable.ic_error;
        }
        else if (messageStatus == Constants.SUCCESS_MESSAGE())
        {
            return R.drawable.ic_success;
        }
        else if (messageStatus == Constants.INFO_MESSAGE())
        {
            return R.drawable.ic_warning;
        }
        else //if (messageStatus == Constants.NONE_MESSAGE())
        {
            return -1;
        }
    }


    public void showMessageAlert(final Activity context , final boolean closeActivity , String title , String message , int messageStatus , String btnText)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        View myview = context.getLayoutInflater().inflate(R.layout.alert_onebutton , null);
        LinearLayout layoutImageStatus = myview.findViewById(R.id.layImageStatus);
        final ShineButton imgStatusFailed = myview.findViewById(R.id.imgFailed);
        final ShineButton imgStatusSuccess = myview.findViewById(R.id.imgSuccess);
        final ShineButton imgStatusWarning = myview.findViewById(R.id.imgWarning);
        TextView lbl = myview.findViewById(R.id.lblMessage);
        Button btn = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(mcontext.getAssets(), mcontext.getResources().getString(R.string.fontPath));
        lbl.setTypeface(font);
        btn.setTypeface(font);
        lbl.setText(message);
        btn.setText(btnText);
        if (messageStatus == Constants.FAILED_MESSAGE())
        {
            imgStatusFailed.setVisibility(View.VISIBLE);
            imgStatusSuccess.setVisibility(View.GONE);
            imgStatusWarning.setVisibility(View.GONE);

            imgStatusFailed.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgStatusFailed.performClick();
                    imgStatusFailed.setPressed(true);
                    imgStatusFailed.invalidate();
                }
            } , SHINE_BUTTON_CLICK_DELAY);

        }
        else if (messageStatus == Constants.SUCCESS_MESSAGE())
        {
            imgStatusFailed.setVisibility(View.GONE);
            imgStatusSuccess.setVisibility(View.VISIBLE);
            imgStatusWarning.setVisibility(View.GONE);

            imgStatusSuccess.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgStatusSuccess.performClick();
                    imgStatusSuccess.setPressed(true);
                    imgStatusSuccess.invalidate();
                }
            } , SHINE_BUTTON_CLICK_DELAY);
        }
        else if (messageStatus == Constants.INFO_MESSAGE())
        {
            imgStatusFailed.setVisibility(View.GONE);
            imgStatusSuccess.setVisibility(View.GONE);
            imgStatusWarning.setVisibility(View.VISIBLE);

            imgStatusWarning.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgStatusWarning.performClick();
                    imgStatusWarning.setPressed(true);
                    imgStatusWarning.invalidate();
                }
            } , SHINE_BUTTON_CLICK_DELAY);
        }
        else
        {
            layoutImageStatus.setVisibility(View.GONE);
            imgStatusFailed.setVisibility(View.GONE);
            imgStatusSuccess.setVisibility(View.GONE);
            imgStatusWarning.setVisibility(View.GONE);
        }
        builder.setCancelable(false);
        builder.setView(myview);
        builder.create();

        if (!(context).isFinishing())
        {
            final AlertDialog show = builder.show();
            try
            {
                imgStatusFailed.setFixDialog(show);
                imgStatusSuccess.setFixDialog(show);
                imgStatusWarning.setFixDialog(show);

                imgStatusFailed.setClickable(false);
                imgStatusFailed.setFocusable(false);
                imgStatusFailed.setFocusableInTouchMode(false);

                imgStatusSuccess.setClickable(false);
                imgStatusSuccess.setFocusable(false);
                imgStatusSuccess.setFocusableInTouchMode(false);

                imgStatusWarning.setClickable(false);
                imgStatusWarning.setFocusable(false);
                imgStatusWarning.setFocusableInTouchMode(false);

                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "CustomAlertDialog", context.getClass().getSimpleName(), "showMessageAlert", "");
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                    if (closeActivity) {
                        context.finish();
                    }
                }
            });
        }
    }

    public void showMessageAlert(final Activity context , String title , String message , int messageStatus , String btnText , final CustomAlertDialogResponse customAlertDialogResponse)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        View myview = context.getLayoutInflater().inflate(R.layout.alert_onebutton , null);
        LinearLayout layoutImageStatus = myview.findViewById(R.id.layImageStatus);
        final ShineButton imgStatusFailed = myview.findViewById(R.id.imgFailed);
        final ShineButton imgStatusSuccess = myview.findViewById(R.id.imgSuccess);
        final ShineButton imgStatusWarning = myview.findViewById(R.id.imgWarning);
        TextView lbl = myview.findViewById(R.id.lblMessage);
        Button btn = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(mcontext.getAssets(), mcontext.getResources().getString(R.string.fontPath));
        lbl.setTypeface(font);
        btn.setTypeface(font);
        lbl.setText(message);
        btn.setText(btnText);
        if (messageStatus == Constants.FAILED_MESSAGE())
        {
            imgStatusFailed.setVisibility(View.VISIBLE);
            imgStatusSuccess.setVisibility(View.GONE);
            imgStatusWarning.setVisibility(View.GONE);

            imgStatusFailed.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgStatusFailed.performClick();
                    imgStatusFailed.setPressed(true);
                    imgStatusFailed.invalidate();
                }
            } , SHINE_BUTTON_CLICK_DELAY);

        }
        else if (messageStatus == Constants.SUCCESS_MESSAGE())
        {
            imgStatusFailed.setVisibility(View.GONE);
            imgStatusSuccess.setVisibility(View.VISIBLE);
            imgStatusWarning.setVisibility(View.GONE);

            imgStatusSuccess.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgStatusSuccess.performClick();
                    imgStatusSuccess.setPressed(true);
                    imgStatusSuccess.invalidate();
                }
            } , SHINE_BUTTON_CLICK_DELAY);
        }
        else if (messageStatus == Constants.INFO_MESSAGE())
        {
            imgStatusFailed.setVisibility(View.GONE);
            imgStatusSuccess.setVisibility(View.GONE);
            imgStatusWarning.setVisibility(View.VISIBLE);

            imgStatusWarning.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgStatusWarning.performClick();
                    imgStatusWarning.setPressed(true);
                    imgStatusWarning.invalidate();
                }
            } , SHINE_BUTTON_CLICK_DELAY);
        }
        else
        {
            layoutImageStatus.setVisibility(View.GONE);
            imgStatusFailed.setVisibility(View.GONE);
            imgStatusSuccess.setVisibility(View.GONE);
            imgStatusWarning.setVisibility(View.GONE);
        }
        builder.setCancelable(false);
        builder.setView(myview);
        builder.create();

        if (!(context).isFinishing())
        {
            final AlertDialog show = builder.show();
            try
            {
                imgStatusFailed.setFixDialog(show);
                imgStatusSuccess.setFixDialog(show);
                imgStatusWarning.setFixDialog(show);

                imgStatusFailed.setClickable(false);
                imgStatusFailed.setFocusable(false);
                imgStatusFailed.setFocusableInTouchMode(false);

                imgStatusSuccess.setClickable(false);
                imgStatusSuccess.setFocusable(false);
                imgStatusSuccess.setFocusableInTouchMode(false);

                imgStatusWarning.setClickable(false);
                imgStatusWarning.setFocusable(false);
                imgStatusWarning.setFocusableInTouchMode(false);

                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "CustomAlertDialog", context.getClass().getSimpleName(), "showMessageAlert", "");
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                    customAlertDialogResponse.setOnApplyClick();
                    customAlertDialogResponse.setOnCancelClick();
                }
            });
        }
    }

    public void showRptMojodiAnbarakHelpAlert(final Activity context)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        View myview = context.getLayoutInflater().inflate(R.layout.alert_mojodi_anbarak_help , null);
        TextView lblFirst = myview.findViewById(R.id.lblFirst);
        TextView lblTwo = myview.findViewById(R.id.lblTwo);
        TextView lblThree = myview.findViewById(R.id.lblThree);
        ImageView imgOne = myview.findViewById(R.id.imgOne);
        ImageView imgTwo = myview.findViewById(R.id.imgTwo);
        ImageView imgThree = myview.findViewById(R.id.imgThree);
        Button btn = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(mcontext.getAssets(), mcontext.getResources().getString(R.string.fontPath));
        lblFirst.setTypeface(font);
        lblTwo.setTypeface(font);
        lblThree.setTypeface(font);
        btn.setTypeface(font);
        lblFirst.setText(context.getResources().getString(R.string.mojodiAnbarRedColorHelp));
        lblTwo.setText(context.getResources().getString(R.string.mojodiAnbarYellowColorHelp));
        lblThree.setText(context.getResources().getString(R.string.mojodiAnbarGreenColorHelp));
        imgOne.setImageResource(R.drawable.red_circle);
        imgTwo.setImageResource(R.drawable.yellow_circle);
        imgThree.setImageResource(R.drawable.green_circle);
        btn.setText(context.getResources().getString(R.string.apply));
        builder.setCancelable(false);
        builder.setView(myview);
        builder.create();

        if (!(context).isFinishing())
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
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "CustomAlertDialog", context.getClass().getSimpleName(), "showRptMojodiAnbarakHelpAlert", "");
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                }
            });
        }
    }


    public void showLogMessageAlert(final Activity context, final boolean closeActivity, String title, String message, int messageStatus , String btnCancelText , String btnOKText , final CustomAlertDialogResponse customAlertDialogResponse)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        View myview = context.getLayoutInflater().inflate(R.layout.alert_twobutton , null);
        LinearLayout layoutImageStatus = myview.findViewById(R.id.layImageStatus);
        final ShineButton imgStatusFailed = myview.findViewById(R.id.imgFailed);
        final ShineButton imgStatusSuccess = myview.findViewById(R.id.imgSuccess);
        final ShineButton imgStatusWarning = myview.findViewById(R.id.imgWarning);
        TextView lblMessage = myview.findViewById(R.id.lblMessage);
        Button btnCancel = myview.findViewById(R.id.btnCancel);
        Button btnOK = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(mcontext.getAssets(), mcontext.getResources().getString(R.string.fontPath));
        lblMessage.setTypeface(font);
        btnCancel.setTypeface(font);
        btnOK.setTypeface(font);
        lblMessage.setText(message);
        btnCancel.setText(btnCancelText);
        btnOK.setText(btnOKText);
        if (messageStatus == Constants.FAILED_MESSAGE())
        {
            imgStatusFailed.setVisibility(View.VISIBLE);
            imgStatusSuccess.setVisibility(View.GONE);
            imgStatusWarning.setVisibility(View.GONE);

            imgStatusFailed.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgStatusFailed.performClick();
                    imgStatusFailed.setPressed(true);
                    imgStatusFailed.invalidate();
                }
            } , SHINE_BUTTON_CLICK_DELAY);

        }
        else if (messageStatus == Constants.SUCCESS_MESSAGE())
        {
            imgStatusFailed.setVisibility(View.GONE);
            imgStatusSuccess.setVisibility(View.VISIBLE);
            imgStatusWarning.setVisibility(View.GONE);

            imgStatusSuccess.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgStatusSuccess.performClick();
                    imgStatusSuccess.setPressed(true);
                    imgStatusSuccess.invalidate();
                }
            } , SHINE_BUTTON_CLICK_DELAY);
        }
        else if (messageStatus == Constants.INFO_MESSAGE())
        {
            imgStatusFailed.setVisibility(View.GONE);
            imgStatusSuccess.setVisibility(View.GONE);
            imgStatusWarning.setVisibility(View.VISIBLE);

            imgStatusWarning.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgStatusWarning.performClick();
                    imgStatusWarning.setPressed(true);
                    imgStatusWarning.invalidate();
                }
            } , SHINE_BUTTON_CLICK_DELAY);
        }
        else
        {
            layoutImageStatus.setVisibility(View.GONE);
            imgStatusFailed.setVisibility(View.GONE);
            imgStatusSuccess.setVisibility(View.GONE);
            imgStatusWarning.setVisibility(View.GONE);
        }
        builder.setCancelable(false);
        builder.setView(myview);
        builder.create();

        if (!(context).isFinishing())
        {
            final AlertDialog show = builder.show();
            try
            {
                imgStatusFailed.setFixDialog(show);
                imgStatusSuccess.setFixDialog(show);
                imgStatusWarning.setFixDialog(show);

                imgStatusFailed.setClickable(false);
                imgStatusFailed.setFocusable(false);
                imgStatusFailed.setFocusableInTouchMode(false);

                imgStatusSuccess.setClickable(false);
                imgStatusSuccess.setFocusable(false);
                imgStatusSuccess.setFocusableInTouchMode(false);

                imgStatusWarning.setClickable(false);
                imgStatusWarning.setFocusable(false);
                imgStatusWarning.setFocusableInTouchMode(false);

                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "CustomAlertDialog", context.getClass().getSimpleName(), "showLogMessageAlert", "");
            }
            btnCancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    show.dismiss();
                    customAlertDialogResponse.setOnCancelClick();
                }
            });

            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    show.dismiss();
                    customAlertDialogResponse.setOnApplyClick();
                }
            });

        }
    }

    public void showWeatherDetails(final Activity context, String iconName, String desc, double temperature, double maxTemp, double minTemp, double humidity, double windSpeed)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        View myview = context.getLayoutInflater().inflate(R.layout.alert_weather_details , null);
        ImageView imgStatus = myview.findViewById(R.id.imgWeatherIcon);
        TextView lblDesc = myview.findViewById(R.id.lblDesc);
        TextView lblTemperature = myview.findViewById(R.id.lblTemperature);
        TextView lblMaxTemperature = myview.findViewById(R.id.lblMaxTemperature);
        TextView lblMinTemperature = myview.findViewById(R.id.lblMinTemperature);
        TextView lblHumidity = myview.findViewById(R.id.lblHumidity);
        TextView lblWindSpeed = myview.findViewById(R.id.lblWindSpeed);
        Button btn = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(mcontext.getAssets(), mcontext.getResources().getString(R.string.fontPath));
        lblDesc.setTypeface(font);
        lblTemperature.setTypeface(font);
        lblMaxTemperature.setTypeface(font);
        lblMinTemperature.setTypeface(font);
        lblHumidity.setTypeface(font);
        lblWindSpeed.setTypeface(font);
        btn.setTypeface(font);
        try
        {
            imgStatus.setImageResource(context.getResources().getIdentifier("w" + iconName, "drawable", context.getPackageName()));
        }
        catch (Exception e)
        {
            imgStatus.setVisibility(View.GONE);
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.FAILED_MESSAGE(), e.toString(), "CustomAlertDialog", "", "showWeatherDetails", "");
        }
        lblDesc.setText(desc);
        lblTemperature.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.temperature) , (int)temperature));
        lblMaxTemperature.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.maxTemperature) , (int)maxTemp));
        lblMinTemperature.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.minTemperature) , (int)minTemp));
        lblHumidity.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.humidity) , (int)humidity));
        lblWindSpeed.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.windSpeed) , windSpeed));
        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();

        if (!(context).isFinishing())
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
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "CustomAlertDialog", context.getClass().getSimpleName(), "showWeatherDetails", "");
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                }
            });
        }
    }

    public void showDeviceLanguageMessage(final Activity context)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        View myview = context.getLayoutInflater().inflate(R.layout.alert_onebutton, null);
        final ShineButton imgStatusFailed = myview.findViewById(R.id.imgFailed);
        final ShineButton imgStatusSuccess = myview.findViewById(R.id.imgSuccess);
        final ShineButton imgStatusWarning = myview.findViewById(R.id.imgWarning);
        TextView lbl = myview.findViewById(R.id.lblMessage);
        Button btn = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(mcontext.getAssets(), mcontext.getResources().getString(R.string.fontPath));
        lbl.setTypeface(font);
        btn.setTypeface(font);
        lbl.setText(context.getResources().getString(R.string.errorDeviceLanguage));
        btn.setText(context.getResources().getString(R.string.setting));
        imgStatusFailed.setVisibility(View.GONE);
        imgStatusSuccess.setVisibility(View.GONE);
        imgStatusWarning.setVisibility(View.VISIBLE);
        imgStatusWarning.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgStatusWarning.performClick();
                imgStatusWarning.setPressed(true);
                imgStatusWarning.invalidate();
            }
        } , SHINE_BUTTON_CLICK_DELAY);
        builder.setCancelable(false);
        builder.setView(myview);
        builder.create();

        if (!(context).isFinishing())
        {
            final AlertDialog show = builder.show();
            try
            {
                imgStatusWarning.setFixDialog(show);

                imgStatusWarning.setClickable(false);
                imgStatusWarning.setFocusable(false);
                imgStatusWarning.setFocusableInTouchMode(false);

                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "CustomAlertDialog", context.getClass().getSimpleName(), "showDeviceLanguageMessage", "");
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                    context.startActivityForResult(intent , Constants.OPEN_LOCALE_SETTING());
                    show.dismiss();
                }
            });
        }
    }


    public void showToast(final Activity activity, String text , int messageType , int duration)
    {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast, null);
        Typeface font = Typeface.createFromAsset(activity.getAssets() , activity.getResources().getString(R.string.fontPath));
        ImageView img = view.findViewById(R.id.imgToast);
        TextView lbl = view.findViewById(R.id.lblToast);
        lbl.setTypeface(font);
        lbl.setText(text);
        int iconResId = getMessageIcon(messageType);
        if (iconResId == -1)
        {
            img.setVisibility(View.GONE);
        }
        else
        {
            img.setImageResource(iconResId);
        }

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 170);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }


    public void showList(Activity context , ArrayList<GPSDataModel> arrayListGpsData)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        View myview = context.getLayoutInflater().inflate(R.layout.alert_recyclerlist , null);
        RecyclerView recyclerView = myview.findViewById(R.id.recycler_view);
        Button btn = myview.findViewById(R.id.btnClose);
        Typeface font = Typeface.createFromAsset(mcontext.getAssets(), mcontext.getResources().getString(R.string.fontPath));
        btn.setTypeface(font);

        LocationAdapter adapter = new LocationAdapter(arrayListGpsData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();

        if (!(context).isFinishing())
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
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                }
            });
        }
    }


    public void showImage(Activity activity , byte[] image , boolean showDelete , final CustomAlertDialogResponse customAlertDialogResponse)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        View myview = activity.getLayoutInflater().inflate(R.layout.alert_show_image , null);
        ImageView imageView = myview.findViewById(R.id.imageview);
        LinearLayout layButtonSeperator = myview.findViewById(R.id.laySeprator);
        Button btnClose = myview.findViewById(R.id.btnClose);
        Button btnDelete = myview.findViewById(R.id.btnDelete);
        Typeface font = Typeface.createFromAsset(activity.getAssets() , activity.getResources().getString(R.string.fontPath));
        btnClose.setTypeface(font);
        btnDelete.setTypeface(font);
        final Bitmap bitmap = new PubFunc().new ImageUtils().convertByteArrayToBitmap(activity , image);
        imageView.setImageBitmap(bitmap);
        btnClose.setText(activity.getResources().getString(R.string.close));
        btnDelete.setText(activity.getResources().getString(R.string.delete));
        if (showDelete)
        {
            layButtonSeperator.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }
        else
        {
            layButtonSeperator.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }
        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();

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
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(activity,Constants.LOG_EXCEPTION(), exception.toString(), "CustomAlertDialog", activity.getClass().getSimpleName(), "showImage", "");
            }
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                    customAlertDialogResponse.setOnCancelClick();
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                    customAlertDialogResponse.setOnApplyClick();
                }
            });
            show.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    try
                    {
                        bitmap.recycle();
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            });
        }
    }


    public void showAboutAlert(Activity activity, String currentVersion, String newestVersion, String lastStableVersion, String newFeatures)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        View myview = activity.getLayoutInflater().inflate(R.layout.alert_about , null);
        TextView lblCurrentVersion = myview.findViewById(R.id.lblUserVersion);
        TextView lblNewestVersion = myview.findViewById(R.id.lblNewestVersion);
        TextView lblLastStableVersion = myview.findViewById(R.id.lblLastStableVersion);
        TextView lblNewFeaturesTitle = myview.findViewById(R.id.lblNewFeaturesTitle);
        TextView lblNewFeatures = myview.findViewById(R.id.lblNewFeatures);
        Button btnClose = myview.findViewById(R.id.btnClose);
        Typeface font = Typeface.createFromAsset(mcontext.getAssets(), mcontext.getResources().getString(R.string.fontPath));
        lblCurrentVersion.setTypeface(font);
        lblNewestVersion.setTypeface(font);
        lblLastStableVersion.setTypeface(font);
        lblNewFeaturesTitle.setTypeface(font);
        lblNewFeatures.setTypeface(font);
        btnClose.setTypeface(font);

        lblCurrentVersion.setText(activity.getResources().getString(R.string.currentVersion , currentVersion));
        lblNewestVersion.setText(activity.getResources().getString(R.string.newestVersion , newestVersion));
        lblLastStableVersion.setText(activity.getResources().getString(R.string.lastStableVersion , lastStableVersion));
        lblNewFeaturesTitle.setText(activity.getResources().getString(R.string.newFeaturesTitle));
        lblNewFeatures.setText(newFeatures);
        btnClose.setText(activity.getResources().getString(R.string.close));

        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();

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
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(activity,Constants.LOG_EXCEPTION(), exception.toString(), "CustomAlertDialog", activity.getClass().getSimpleName(), "showAboutAlert", "");
            }
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                }
            });
        }
    }


    public View createProgressBar(Activity activity)
    {
        View myview = activity.getLayoutInflater().inflate(R.layout.alert_progress_bar , null);
        Typeface font = Typeface.createFromAsset(activity.getAssets() , activity.getResources().getString(R.string.fontPath));
        TextView lblProgressPercentage = myview.findViewById(R.id.lblProgressPercentage);
        lblProgressPercentage.setTypeface(font);
        return myview;
    }



    public void showEditableTextAlert(final Activity context, String title, String message , String btnCancelText , String btnOKText , final ICustomEditableAlert customEditableAlert)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        View myview = context.getLayoutInflater().inflate(R.layout.alert_arzesh_afzoodeh , null);
        CustomTextInputLayout customTextInputLayout = myview.findViewById(R.id.cusTextInputModified);
        EditText modifiableEditText = myview.findViewById(R.id.edt_modifiable_value);
        TextView titleTextView = myview.findViewById(R.id.txt_title);
        Button btnCancel = myview.findViewById(R.id.btnCancel);
        Button btnOK = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(mcontext.getAssets(), mcontext.getResources().getString(R.string.fontPath));
        titleTextView.setTypeface(font);
        modifiableEditText.setTypeface(font);
        btnCancel.setTypeface(font);
        btnOK.setTypeface(font);
        titleTextView.setText(title);
        modifiableEditText.setText(message);
        btnCancel.setText(btnCancelText);
        btnOK.setText(btnOKText);

        builder.setCancelable(false);
        builder.setView(myview);
        builder.create();

        modifiableEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0) {
                    String englishNumerals = new BigDecimal(charSequence.toString()).toString();
                    customEditableAlert.onTextChange(customTextInputLayout,englishNumerals);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (!(context).isFinishing())
        {
             editableTextShow = builder.show();
            try
            {


                if (editableTextShow.getWindow() != null)
                {
                    editableTextShow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "CustomAlertDialog", context.getClass().getSimpleName(), "showLogMessageAlert", "");
            }
            btnCancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editableTextShow.dismiss();
                    customEditableAlert.setOnCancelClick();
                }
            });

            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    customEditableAlert.setOnApplyClick(customTextInputLayout,modifiableEditText.getText());
                }
            });

        }
    }
    public void  hideEditableTextAlert(){
        editableTextShow.dismiss();
    }



}
