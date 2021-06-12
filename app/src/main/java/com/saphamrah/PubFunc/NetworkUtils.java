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

    public ServerIpModel postServerFromShared(Context context) {
        ServerIPShared serverIPShared = new ServerIPShared(context);
        Log.d("server", "ip : " + serverIPShared.getString(serverIPShared.IP_POST_REQUEST()
                , "").substring(0, 4));
        Log.d("server", "post : " + serverIPShared.getString(serverIPShared.PORT_POST_REQUEST()
                , ""));
        ServerIpModel serverIpModel = new ServerIpModel();
        serverIpModel.setServerIp(serverIPShared.getString(serverIPShared.IP_POST_REQUEST()
                , ""));
        serverIpModel.setPort(serverIPShared.getString(serverIPShared.PORT_POST_REQUEST()
                , ""));
        return serverIpModel;
    }

    public ServerIpModel multiServerFromShared(Context context){
        ServerIPShared serverIPShared = new ServerIPShared(context);
        Log.d("server", "ip : " + serverIPShared.getString(serverIPShared.IP_MULTI_REQUEST()
                , "").substring(0, 4));
        Log.d("server", "multi : " + serverIPShared.getString(serverIPShared.PORT_MULTI_REQUEST()
                , ""));
        ServerIpModel serverIpModel = new ServerIpModel();
        serverIpModel.setServerIp(serverIPShared.getString(serverIPShared.IP_MULTI_REQUEST()
                , ""));
        serverIpModel.setPort(serverIPShared.getString(serverIPShared.PORT_MULTI_REQUEST()
                , ""));
        return serverIpModel;
    }

    /**
     * Method is used for checking network availability.
     * creay By pouria hemati
     * @param context
     * @return isNetAvailable: boolean true for Internet availability, false
     * otherwise
     */
    public static boolean isNetworkAvailable(Context context) {

        boolean isNetAvailable = false;
        if (context != null) {

            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (mConnectivityManager != null) {

                boolean mobileNetwork = false;
                boolean wifiNetwork = false;

                boolean mobileNetworkConnected = false;
                boolean wifiNetworkConnected = false;

                final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (mobileInfo != null) {
                    mobileNetwork = mobileInfo.isAvailable();
                }

                if (wifiInfo != null) {
                    wifiNetwork = wifiInfo.isAvailable();
                }

                if (wifiNetwork || mobileNetwork) {

                    if (mobileInfo != null) {

                        mobileNetworkConnected = mobileInfo.isConnectedOrConnecting();
                    }
                    wifiNetworkConnected = wifiInfo.isConnectedOrConnecting();
                }
                isNetAvailable = (mobileNetworkConnected || wifiNetworkConnected);
            }
        }
        return isNetAvailable;
    }

}
