package com.saphamrah.PubFunc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;

public class NetworkUtils
{

    private static final String CLASS_NAME = "NetworkUtils";


    public static final int WIFI = 1;
    public static final int CELLULAR = 2;
    public static final int NO_CONNECTION = -1;


    public boolean enableWifi(Context context)
    {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && !wifiManager.isWifiEnabled())
        {
            return wifiManager.setWifiEnabled(true);
        }
        return true;
    }

    public int checkInternetType(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= 23)
        {
            try
            {
                Network network = connectivityManager.getActiveNetwork();
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                if (networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                {
                    return CELLULAR;
                }
                else if (networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                {
                    return WIFI;
                }
                else
                {
                    return NO_CONNECTION;
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() , CLASS_NAME, "", "checkInternetType" , "");
                return NO_CONNECTION;
            }
        }
        else
        {
            try
            {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected())
                {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                    {
                        return WIFI;
                    }
                    else
                    {
                        return CELLULAR;
                    }
                }
                else
                {
                    return NO_CONNECTION;
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "checkInternetType" , "");
                return NO_CONNECTION;
            }
        }
    }

    public String getOperatorName(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        try
        {
            if (telephonyManager != null)
            {
                return telephonyManager.getNetworkOperatorName();
            }
            else
            {
                return "";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), CLASS_NAME, "", "getOperatorName", "");
            return "";
        }
    }

    public ServerIpModel getServerFromShared(Context context)
    {
        ServerIPShared serverIPShared = new ServerIPShared(context);
        ServerIpModel serverIpModel = new ServerIpModel();
        serverIpModel.setServerIp(serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
 , ""));
        serverIpModel.setPort(serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
 , ""));
        return serverIpModel;
    }

}
