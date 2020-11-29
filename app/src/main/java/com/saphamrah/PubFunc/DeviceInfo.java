package com.saphamrah.PubFunc;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.saphamrah.Utils.Constants;

public class DeviceInfo
{

    private static final String CLASS_NAME = "DeviceInfo";


    @SuppressLint("MissingPermission")
    public String getIMEI(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        try
        {
            return telephonyManager.getDeviceId().trim();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), CLASS_NAME, "", "getIMEI", "");
            return "";
        }
    }


    public int getAPILevel(Context context)
    {
        try
        {
            return Build.VERSION.SDK_INT;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "getAPILevel", "");
            return -1;
        }
    }

    public boolean copyToClipboard(Context context , String tag , String value)
    {
        try
        {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(tag , value);
            clipboard.setPrimaryClip(clip);
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "copyToClipboard", "");
            return false;
        }
    }


    /**
     * نام نسخه (Version Name) نصب شده بر روی دستگاه کاربر را بر می گرداند
     * فرمت نام گذاری نسخه به شکل زیر می باشد
     * <br>
     * w.x.y.z
     * w :
     * x :
     * y :
     * z :
     * @param context
     * @return
     */
    public String getCurrentVersion(Context context)
    {
        try
        {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), CLASS_NAME, "", "getCurrentVersion", "");
            return "";
        }
    }

}
