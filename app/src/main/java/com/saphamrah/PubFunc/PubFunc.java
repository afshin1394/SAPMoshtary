package com.saphamrah.PubFunc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.LogPPCDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.Model.DataTableModel;
import com.saphamrah.Model.EmailLogPPCModel;
import com.saphamrah.Model.ForoshandehAmoozeshiModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.AsyncSendMail;
import com.saphamrah.Network.AsyncTaskResponse;
import com.saphamrah.R;
import com.saphamrah.Shared.EmailLogPPCShared;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Shared.UserTypeShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetLoginInfoCallback;
import com.saphamrah.WebService.ServiceResponse.GetLoginInfoResult;

import java.io.ByteArrayOutputStream;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PubFunc
{

    public class FakeLocation
    {
        public boolean useFakeLocation(Context context)
        {
            if (Settings.Secure.getString(context.getContentResolver() , Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            {
                return isMockPermissionApps(context);
            }
            else
            {
                return true;
            }
        }

        private boolean isMockPermissionApps(Context context)
        {
            PackageManager pm = context.getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

            for (ApplicationInfo applicationInfo : packages)
            {
                try
                {
                    PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);

                    String[] requestedPermissions = packageInfo.requestedPermissions;
                    if (requestedPermissions != null) {
                        for (int i = 0; i < requestedPermissions.length; i++) {
                            if (requestedPermissions[i].equals("android.permission.ACCESS_MOCK_LOCATION") && !applicationInfo.packageName.equals(context.getPackageName()) && !(applicationInfo.sourceDir).substring(1,7).equals("system"))
                            {
                                return true;
                            }
                        }
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    Logger logger = new Logger();
                    logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "PubFunc.FakeLocation", "", "isMockPermissionApps", "");
                    return false;
                }
            }
            return false;
        }
    }


    public class LocationProvider extends Service implements LocationListener
    {
        private Context context;
        boolean isGPSEnabled = false;
        boolean isNetworkEnabled = false;
        boolean canGetLocation = false;

        Location location;
        double latitude = 0.0;
        double longitude = 0.0;
        double altitude = 0.0;
        float accurancy = 0.0f;
        float bearing = 0.0f;
        float speed = 0.0f;

        LocationManager locationManager;

        public LocationProvider(Context context)
        {
            this.context = context;
            latitude = 0.0;
            longitude = 0.0;
            getLocation();
        }


        public Location getLocation()
        {
            try
            {
                locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (isGPSEnabled || isNetworkEnabled)
                {
                    this.canGetLocation = true;
                    if (isGPSEnabled)
                    {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions((Activity)context , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 3, this);
                        if (locationManager != null)
                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null)
                            {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                altitude = location.getAltitude();
                                accurancy = location.getAccuracy();
                                bearing = location.getBearing();
                                speed = location.getSpeed();
                            }
                        }
                    }

                    if (isNetworkEnabled)
                    {
                        if (location == null)
                        {
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                            {
                                ActivityCompat.requestPermissions((Activity)context , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 3, this);
                            if (locationManager != null)
                            {
                                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                if (location != null){
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                    altitude = location.getAltitude();
                                    accurancy = location.getAccuracy();
                                    bearing = location.getBearing();
                                    speed = location.getSpeed();
                                }
                            }
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Logger logger = new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), "GPSTracker", "", "getLocation", "");
            }
            return location;
        }


        public double getLatitude()
        {
            //Log.d("location" , "lat : " + latitude + " provider : " + location.getProvider());
            if (location != null)
            {
                latitude = location.getLatitude();
            }
            return latitude;
        }

        public double getLongitude()
        {
            //Log.d("location" , "lng : " + longitude + " provider : " + location.getProvider());
            if (location != null)
            {
                longitude = location.getLongitude();
            }

            return longitude;
        }


        public double getAltitude()
        {
            if (location != null)
            {
                altitude = location.getAltitude();
            }
            return altitude;
        }


        public float getAccuracy()
        {
            if (location != null)
            {
                accurancy = location.getAccuracy();
            }
            return accurancy;
        }


        public float getBearing()
        {
            if (location != null)
            {
                bearing = location.getBearing();
            }
            return bearing;
        }

        public float getSpeed()
        {
            if (location != null)
            {
                speed = location.getSpeed()*3.6f;
            }
            return speed;
        }

        public boolean getHasAccess()
        {
            boolean hasAccess = false;
            if (Build.VERSION.SDK_INT >= 23)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    hasAccess = true;
                }
                else
                {
                    hasAccess = false;
                }
            }
            else
            {
                hasAccess = true;
            }
            return hasAccess;
        }

        public boolean canGetLocation(){
            return this.canGetLocation;
        }
        @Override
        public void onLocationChanged(Location location) {
            if (location != null)
            {
                this.location = location;
            }
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider)
        {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
        }

        @Override
        public IBinder onBind(Intent intent)
        {
            return null;
        }


    }



    public class GoogleLocationProvider
    {
        private FusedLocationProviderClient fusedLocationClient;
        private final ArrayList<Location> locations = new ArrayList<>();
        private boolean hasAccess;

        public GoogleLocationProvider(Context context)
        {
            hasAccess = false;
            if (Build.VERSION.SDK_INT >= 23)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    hasAccess = true;
                    getLocation(context);
                }
                else
                {
                    hasAccess = false;
                }
            }
            else
            {
                hasAccess = true;
                getLocation(context);
            }
        }


        private void getLocation(final Context context)
        {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
             fusedLocationClient.getLastLocation()
                    .addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null)
                            {
                                Log.d("Location" , "location != null and " + location.getLatitude() + " , " + location.getLongitude());
                                locations.clear();
                                locations.add(location);
                            }
                            else
                            {
                                Log.d("Location" , "location = null");
                                locations.clear();
                                LocationProvider locationProvider = new LocationProvider(context);
                                location = locationProvider.getLocation();
                                Log.d("Location" , "location == null and " + location.getLatitude() + " , " + location.getLongitude());
                                if (location != null)
                                {
                                    locations.add(location);
                                }
                            }
                        }
                    });
        }


        public double getLongitude()
        {
            if (locations.size() > 0)
            {
                Log.d("Location" , "long : " + locations.get(0).getLongitude());
                return locations.get(0).getLongitude();
            }
            else
            {
                return 0;
            }
        }


        public double getLatitude()
        {
            if (locations.size() > 0)
            {
                Log.d("Location" , "lat : " + locations.get(0).getLatitude());
                return locations.get(0).getLatitude();
            }
            else
            {
                return 0;
            }
        }


        public double getAltitude()
        {
            if (locations.size() > 0)
            {
                return locations.get(0).getAltitude();
            }
            else
            {
                return -1;
            }
        }

        public float getAccuracy()
        {
            if (locations.size() > 0)
            {
                return locations.get(0).getAccuracy();
            }
            else
            {
                return -1;
            }
        }

        public float getBearing()
        {
            if (locations.size() > 0)
            {
                return locations.get(0).getBearing();
            }
            else
            {
                return -1;
            }
        }

        public float getSpeed()
        {
            if (locations.size() > 0)
            {
                return locations.get(0).getSpeed();
            }
            else
            {
                return -1;
            }
        }

        public boolean getHasAccess()
        {
            return hasAccess;
        }

        public void setHasAccess(boolean hasAccess) {
            this.hasAccess = hasAccess;
        }
    }


    public class NetworkUtils
    {
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
                    Logger logger = new Logger();
                    logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() , "PubFunc.NetworkUtils", "", "checkInternetType" , "");
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
                    Logger logger = new Logger();
                    logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "PubFunc.NetworkUtils", "", "checkInternetType" , "");
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
                Logger logger = new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), "PubFunc.NetworkUtils", "", "getOperatorName", "");
                return "";
            }
        }

        public ServerIpModel getServerFromShared(Context context) {
            ServerIPShared serverIPShared = new ServerIPShared(context);
            Log.d("server", "ip : " + serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
                    , "").substring(0, 4));
            Log.d("server", "get : " + serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
                    , ""));
            ServerIpModel serverIpModel = new ServerIpModel();
            serverIpModel.setServerIp(serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
                    , ""));
            serverIpModel.setPort(serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
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
    }


    public class LoginInfo
    {
        public void callLoginInfoService(final Context context , String serverIP , String port , final GetLoginInfoCallback callback)
        {
            ServerIpModel serverIpModel=new PubFunc().new NetworkUtils().getServerFromShared(context);

            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetLoginInfoResult> call = apiServiceGet.getLoginInfo();
            call.enqueue(new Callback<GetLoginInfoResult>()
            {
                @Override
                public void onResponse(Call<GetLoginInfoResult> call, Response<GetLoginInfoResult> response)
                {
                    if (response.raw().body() != null)
                    {
                        Log.d("intercept" , "in on response PubFunc.LoginInfo.callLoginInfoService and response : " + response.raw().body().contentLength());
                        long contentLength = response.raw().body().contentLength();
                        Logger logger = new Logger();
                        logger.insertLogToDB(context,Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, "PubFunc.LoginInfo", "", "callLoginInfoService", "onResponse");
                    }
                    if (response.isSuccessful())
                    {
                        try
                        {
                            GetLoginInfoResult loginInfoResult = response.body();
                            String dateTime = loginInfoResult.getData().get(0).getServerDateTime();
                            SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
							SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_WITH_SPACE());																			  
                            Date serverDate = formatter.parse(dateTime);
                            Date deviceDate = Calendar.getInstance().getTime();
                            long diff = Math.abs(serverDate.getTime() - deviceDate.getTime()) / 1000;
                            Log.d("deviceTime" , deviceDate.toString());
                            Log.d("serverTime" , serverDate.toString());
                            Log.d("diffTime" , String.valueOf(diff));
                            if (diff > Constants.ALLOWABLE_SERVER_LOCAL_TIME_DIFF())
                            {
                                callback.onSuccess(false, sdf.format(serverDate), sdf.format(deviceDate), diff);
                            }
                            else
                            {
                                callback.onSuccess(true, sdf.format(serverDate), sdf.format(deviceDate), diff);
                            }
                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                            Logger logger = new Logger();
                            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "PubFunc.LoginInfo", "", "callLoginInfoService", "onResponse");
                            callback.onFailure(null);
                        }
                    }
                    else
                    {
                        callback.onFailure(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<GetLoginInfoResult> call, Throwable t)
                {
                    Log.d("fail" , "" + t.getMessage());
                    Logger logger = new Logger();
                    logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), t.toString(), "PubFunc.LoginInfo", "", "callLoginInfoService", "onFailure");
                    callback.onFailure(t.toString());
                }
            });
        }
    }


    public class DateUtils
    {
        /**
         *تاریخ امروز به شمسی و در قالب عددی
         * yyyymmdd
         * @param context
         * @return -1 if exception occurs
         */
        public int todayDate(Context context)
        {
            try
            {
                String today = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(Calendar.getInstance().getTime());
                String[] seperatedDate = today.split("-");
                int year = Integer.parseInt(seperatedDate[0]);
                int month = Integer.parseInt(seperatedDate[1]);
                int day = Integer.parseInt(seperatedDate[2]);

                DateConverter dateConverter = new DateConverter();
                dateConverter.gregorianToPersian(year , month , day);

                String persianYear = String.valueOf(dateConverter.getYear());
                String persianMonth = String.valueOf(dateConverter.getMonth());
                String persianDay = String.valueOf(dateConverter.getDay());

                persianMonth = (persianMonth.length() == 1) ? "0" + persianMonth : persianMonth;
                persianDay = (persianDay.length() == 1) ? "0" + persianDay : persianDay;

                int date = Integer.parseInt(persianYear + persianMonth + persianDay);
                return date;
            }
            catch (Exception e)
            {
                Logger logger = new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), "PubFunc.DateUtils", "", "todayDate", "");
                return -1;
            }
        }


        /**
         * تاریخ امروز به شمسی و با اسلش
         * yyyy/mm/dd
         * @param context
         * @return "" if exception occurs
         */
        public String todayDateWithSlash(Context context)
        {
            try
            {
                String today = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(Calendar.getInstance().getTime());
                String[] seperatedDate = today.split("-");
                int year = Integer.parseInt(seperatedDate[0]);
                int month = Integer.parseInt(seperatedDate[1]);
                int day = Integer.parseInt(seperatedDate[2]);

                DateConverter dateConverter = new DateConverter();
                dateConverter.gregorianToPersian(year , month , day);

                String persianYear = String.valueOf(dateConverter.getYear());
                String persianMonth = String.valueOf(dateConverter.getMonth());
                String persianDay = String.valueOf(dateConverter.getDay());

                persianMonth = (persianMonth.length() == 1) ? "0" + persianMonth : persianMonth;
                persianDay = (persianDay.length() == 1) ? "0" + persianDay : persianDay;

                return String.format("%1$s/%2$s/%3$s" , persianYear , persianMonth , persianDay);
            }
            catch (Exception e)
            {
                Logger logger = new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), "PubFunc.DateUtils", "", "todayDate", "");
                return "";
            }
        }


        /**
         * today persian date
         * @param context
         * @return [0] = year , [1] = month , [2] = day OR return null if exception occurs
         */
        public int[] splittedTodayPersianDate(Context context)
        {
            try
            {
                String today = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(Calendar.getInstance().getTime());
                String[] seperatedDate = today.split("-");
                int year = Integer.parseInt(seperatedDate[0]);
                int month = Integer.parseInt(seperatedDate[1]);
                int day = Integer.parseInt(seperatedDate[2]);

                DateConverter dateConverter = new DateConverter();
                dateConverter.gregorianToPersian(year , month , day);

                return new int[]{dateConverter.getYear() , dateConverter.getMonth() , dateConverter.getDay()};
            }
            catch (Exception e)
            {
                Logger logger = new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), "PubFunc.DateUtils", "", "splittedTodayPersianDate", "");
                return null;
            }
        }


        /**
         * convert gregorian date to persian date
         * @param date must be in this format : yyyy-MM-dd'T'HH:mm:ss
         * @return persian date time (format : yyyy/MM/dd - HH:mm:ss)
         */
        public String gregorianToPersianDateTime(Date date)
        {
            String gregorianDay = (String) DateFormat.format("dd" , date);
            String gregorianMonth = (String) DateFormat.format("MM" , date);
            String gregorianYear = (String) DateFormat.format("yyyy", date);
            String time = (String) DateFormat.format("HH:mm:ss" , date);

            int year = Integer.parseInt(gregorianYear);
            int month = Integer.parseInt(gregorianMonth);
            int day = Integer.parseInt(gregorianDay);

            DateConverter dateConverter = new DateConverter();
            dateConverter.gregorianToPersian(year , month , day);

            return String.format("%1$s/%2$s/%3$s - %4$s" , dateConverter.getYear() , dateConverter.getMonth() , dateConverter.getDay() , time);
        }


        /**
         * تاریخ و زمان الان به میلادی و در قالب
         * <br>
         * yyyy/MM/dd kk:mm:ss
         * @return
         */
        public String todayDateGregorian()
        {
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.NORMAL_DATE_TIME_FORMAT());
            return simpleDateFormat.format(today);
        }


        public Date todayGregorian()
        {
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            try
            {
                return simpleDateFormat.parse(simpleDateFormat.format(today));
            }
            catch (Exception e)
            {
                return new Date();
            }
        }


        /**
         *تاریخ الان بدون زمان و با اسلش به فرمت زیر
         * <br>
         * yyyy/MM/dd
         * @return
         */
        public String todayDateGregorianWithSlash()
        {
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH());
            return simpleDateFormat.format(today);
        }


        /**
         * تبدیل تاریخ شمسی به میلادی همراه با اسلش
         * <br>
         * yyyy/(m)m/(d)d
         * @param persianDate تاریخ شمسی با اسلش
         * @return تاریخ میلادی با اسلش
         */
        public String persianWithSlashToGregorianSlash(String persianDate)
        {
            String gregorianDate = "";
            String[] splittedDate = persianDate.split("/");
            int year = Integer.parseInt(splittedDate[0]);
            int month = Integer.parseInt(splittedDate[1]);
            int date = Integer.parseInt(splittedDate[2]);

            DateConverter dateConverter = new DateConverter();
            dateConverter.persianToGregorian(year , month , date);
            String gregorianYear = String.valueOf(dateConverter.getYear());
            String gregorianMonth = String.valueOf(dateConverter.getMonth());
            String gregorianDay = String.valueOf(dateConverter.getDay());
            gregorianDate = gregorianYear + "/" + gregorianMonth + "/" + gregorianDay;

            return gregorianDate;
        }


        /**
         * تبدیل تاریخ میلادی به شمسی همراه با اسلش
         * <br>
         * yyyy/(m)m/(d)d
         * @param gregorianDate تاریخ میلادی با اسلش
         * @return تاریخ شمسی با اسلش
         */
        public String gregorianWithSlashToPersianSlash(String gregorianDate)
        {
            String persianDate = "";
            String[] splittedDate = gregorianDate.split("/");
            int year = Integer.parseInt(splittedDate[0]);
            int month = Integer.parseInt(splittedDate[1]);
            int date = Integer.parseInt(splittedDate[2]);

            DateConverter dateConverter = new DateConverter();
            dateConverter.gregorianToPersian(year , month , date);
            String persianYear = String.valueOf(dateConverter.getYear());
            String persianMonth = String.valueOf(dateConverter.getMonth());
            String persianDay = String.valueOf(dateConverter.getDay());

            persianMonth = persianMonth.trim().length() == 1 ? "0" + persianMonth : persianMonth;
            persianDay = persianDay.trim().length() == 1 ? "0" + persianDay : persianDay;

            persianDate = persianYear + "/" + persianMonth + "/" + persianDay;

            return persianDate;
        }


        /**
         * زمان الان با فرمت
         * <br>
         * HH:mm:ss
         * @return
         */
        public String getCurrentTime()
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.TIME_FORMAT());
            return simpleDateFormat.format(new Date());
        }


        /**
         * تبدیل عدد ماه به نام ماه شمسی
         * @param context
         * @param monthId عدد ماه بین 1 تا 12
         * @return
         */
        public String getMonthName(Context context , int monthId)
        {
            switch (monthId)
            {
                case 1:
                    return context.getString(R.string.farvardin);
                case 2:
                    return context.getString(R.string.ordibehesht);
                case 3:
                    return context.getString(R.string.khordad);
                case 4:
                    return context.getString(R.string.tir);
                case 5:
                    return context.getString(R.string.mordad);
                case 6:
                    return context.getString(R.string.shahrivar);
                case 7:
                    return context.getString(R.string.mehr);
                case 8:
                    return context.getString(R.string.aban);
                case 9:
                    return context.getString(R.string.azar);
                case 10:
                    return context.getString(R.string.dey);
                case 11:
                    return context.getString(R.string.bahman);
                case 12:
                    return context.getString(R.string.esfand);
                default:
                    return String.valueOf(monthId);
            }
        }

        public long getDateDiffAsDay(Date dateStart , Date dateEnd)
        {
            long diff = dateEnd.getTime() - dateStart.getTime();
            return TimeUnit.MILLISECONDS.toDays(diff);
        }

        public Date addDay(Date date , int days)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH , days);
            return calendar.getTime();
        }

        public Date diffDateMinModatHozor(int minModatHozor){

            int milliSecondsMinModatHozor = minModatHozor * 60000;
            long diff = getCurrentDate().getTime() - milliSecondsMinModatHozor;
            return new Date(diff);
        }

        public Date getCurrentDate()
        {
            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            String date = sdf.format(new Date());
            try
            {
                currentDate = sdf.parse(date);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
            return currentDate;
        }

    }


    public class DateConverter
    {

        private int day, month, year;
        private int jYear, jMonth, jDay;
        private int gYear, gMonth, gDay;
        private int leap, march;

        /**
         * Calculates the Julian Day number (JG2JD) from Gregorian or Julian
         * <p>
         * calendar dates. This integer number corresponds to the noon of the date
         * <p>
         * (i.e. 12 hours of Universal Time). The procedure was tested to be good
         * <p>
         * since 1 March, -100100 (of both the calendars) up to a few millions
         * <p>
         * (10**6) years into the future. The algorithm is based on D.A. Hatcher,
         * <p>
         * Q.Jl.R.Astron.Soc. 25(1984), 53-55 slightly modified by me (K.M.
         * <p>
         * Borkowski, Post.Astron. 25(1987), 275-279).
         *
         * @param year  int
         * @param month int
         * @param day   int
         * @param J1G0  to be set to 1 for Julian and to 0 for Gregorian calendar
         * @return Julian Day number
         */
        private int JG2JD(int year, int month, int day, int J1G0) {
            int jd = (1461 * (year + 4800 + (month - 14) / 12)) / 4
                    + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12
                    - (3 * ((year + 4900 + (month - 14) / 12) / 100)) / 4 + day
                    - 32075;
            if (J1G0 == 0) {
                jd = jd - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752;
            }
            return jd;
        }

        /**
         * Calculates Gregorian and Julian calendar dates from the Julian Day number
         * <p>
         * (JD) for the period since JD=-34839655 (i.e. the year -100100 of both the
         * <p>
         * calendars) to some millions (10**6) years ahead of the present. The
         * <p>
         * algorithm is based on D.A. Hatcher, Q.Jl.R.Astron.Soc. 25(1984), 53-55
         * <p>
         * slightly modified by me (K.M. Borkowski, Post.Astron. 25(1987), 275-279).
         *
         * @param JD   Julian day number as int
         * @param J1G0 to be set to 1 for Julian and to 0 for Gregorian calendar
         */
        private void JD2JG(int JD, int J1G0) {
            int i, j;
            j = 4 * JD + 139361631;
            if (J1G0 == 0) {
                j = j + (4 * JD + 183187720) / 146097 * 3 / 4 * 4 - 3908;
            }
            i = (j % 1461) / 4 * 5 + 308;
            gDay = (i % 153) / 5 + 1;
            gMonth = ((i / 153) % 12) + 1;
            gYear = j / 1461 - 100100 + (8 - gMonth) / 6;
        }

        /**
         * Converts the Julian Day number to a date in the Jalali calendar
         *
         * @param JDN the Julian Day numbeJG2JDr
         */
        private void JD2Jal(int JDN) {
            JD2JG(JDN, 0);
            jYear = gYear - 621;
            JalCal(jYear);
            int JDN1F = JG2JD(gYear, 3, march, 0);
            int k     = JDN - JDN1F;
            if (k >= 0) {
                if (k <= 185) {
                    jMonth = 1 + k / 31;
                    jDay = (k % 31) + 1;
                    return;
                } else {
                    k = k - 186;
                }
            } else {
                jYear = jYear - 1;
                k = k + 179;
                if (leap == 1) {
                    k = k + 1;
                }
            }
            jMonth = 7 + k / 30;
            jDay = (k % 30) + 1;
        }

        /**
         * Converts a date of the Jalali calendar to the Julian Day Number
         *
         * @param jY Jalali year as int
         * @param jM Jalali month as int
         * @param jD Jalali day as int
         * @return Julian day number
         */
        private int Jal2JD(int jY, int jM, int jD) {
            JalCal(jY);
            return JG2JD(gYear, 3, march, 1) + (jM - 1) * 31 - jM / 7 * (jM - 7) + jD - 1;
        }

        /**
         * This procedure determines if the Jalali (Persian) year is leap (366-day
         * <p>
         * long) or is the common year (365 days), and finds the day in March
         * <p>
         * (Gregorian calendar) of the first day of the Jalali year (jYear)
         *
         * @param jY Jalali calendar year (-61 to 3177)
         */
        private void JalCal(int jY) {
            march = 0;
            leap = 0;
            int[] breaks = {-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210,
                    1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
            gYear = jY + 621;
            int leapJ = -14;
            int jp    = breaks[0];
            int jump  = 0;
            for (int j = 1; j <= 19; j++) {
                int jm = breaks[j];
                jump = jm - jp;
                if (jY < jm) {
                    int N = jY - jp;
                    leapJ = leapJ + N / 33 * 8 + (N % 33 + 3) / 4;
                    if ((jump % 33) == 4 && (jump - N) == 4) {
                        leapJ = leapJ + 1;
                    }
                    int leapG = (gYear / 4) - (gYear / 100 + 1) * 3 / 4 - 150;
                    march = 20 + leapJ - leapG;
                    if ((jump - N) < 6) {
                        N = N - jump + (jump + 4) / 33 * 33;
                    }
                    leap = ((((N + 1) % 33) - 1) % 4);
                    if (leap == -1) {
                        leap = 4;
                    }
                    break;
                }
                leapJ = leapJ + jump / 33 * 8 + (jump % 33) / 4;
                jp = jm;
            }
        }

        /**
         * Modified toString() method that represents date string
         *
         * @return Date as String
         */
        @SuppressLint("DefaultLocale")
        @Override
        public String toString() {
            return String.format("%04d-%02d-%02d", getYear(), getMonth(), getDay());
        }

        /**
         * Converts Gregorian date to Persian(Jalali) date
         *
         * @param year  int
         * @param month int
         * @param day   int
         */
        public void gregorianToPersian(int year, int month, int day) {
            int jd = JG2JD(year, month, day, 0);
            JD2Jal(jd);
            this.year = jYear;
            this.month = jMonth;
            this.day = jDay;
        }

        /**
         * Converts Persian(Jalali) date to Gregorian date
         *
         * @param year  int
         * @param month int
         * @param day   int
         */
        public void persianToGregorian(int year, int month, int day) {
            int jd = Jal2JD(year, month, day);
            JD2JG(jd, 0);
            this.year = gYear;
            this.month = gMonth;
            this.day = gDay;
        }

        /**
         * Get manipulated day
         *
         * @return Day as int
         */
        public int getDay() {
            return day;
        }

        /**
         * Get manipulated month
         *
         * @return Month as int
         */
        public int getMonth() {
            return month;
        }

        /**
         * Get manipulated year
         *
         * @return Year as int
         */
        public int getYear() {
            return year;
        }

    }


    public class DeviceInfo
    {

        /*
get mac address in android 10
*/
        public String getMacAddress(Context context) {
            try {
                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:",b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            } catch (Exception ex) {
            }
            return "02:00:00:00:00:00";
        }


        @SuppressLint("MissingPermission")
        public String getIMEI(Context context)
        {
            String iMEI="";
            Log.d("PubFunc","SDK:"+android.os.Build.VERSION.SDK_INT );
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            {
                //iMEI =  Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
                //TODO
                if (Authentication.getInstance().checkIfFileExists()) {
                    String stream=Authentication.getInstance().getIdentityCodeWithHashKey();
                    Log.i("STREAMM", "getIMEI: "+stream);
                    if (stream.length()>1) {
                        String[] strings = stream.split(",");
                        String identityCode = strings[0];
                        Log.i("STREAMM", "getIMEI: "+identityCode);
                        String hashKey = strings[1];
                        Log.i("STREAMM", "getIMEI: "+hashKey);
                        iMEI = Authentication.getInstance().encrypt(identityCode, hashKey);
                    }
                }
                Log.d("PubFunc", "Android UP 9 - iMei: " + iMEI);
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                try
                {
                    iMEI = telephonyManager.getDeviceId().trim();
                    Log.d("PubFunc","Android Down 9  - iMei: " + iMEI);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Logger logger = new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "PubFunc.DeviceInfo", "", "getIMEI", "");
                    return "";
                }
            }
            Log.d("PubFunc","Before Return Imei : "+ iMEI);
            return  iMEI;
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
                Logger logger = new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "PubFunc.DeviceInfo", "", "getAPILevel", "");
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
                Logger logger = new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "PubFunc.DeviceInfo", "", "copyToClipboard", "");
                return false;
            }
        }

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
                Logger logger = new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "DeviceInfo", "", "getCurrentVersion", "");
                return "";
            }
        }

    }


    public class UserType {
        public String checkUserType(Context context, List<ForoshandehAmoozeshiModel> foroshandehAmoozeshiModelList, String deviceIMEI) {
            String foroshandehAsliIMEI = ""; //if this parameter == "" then UserType = main and use deviceIMEI else UserType = test and use this IMEI
            for (ForoshandehAmoozeshiModel foroshandehAmoozeshi : foroshandehAmoozeshiModelList) {
                if (foroshandehAmoozeshi.getDeviceNumberForoshandehAmoozeshi().trim().equals(deviceIMEI)) {
                    foroshandehAsliIMEI = foroshandehAmoozeshi.getDeviceNumberForoshandehAsli();
                    break;
                }
            }

            String usingIMEI = "";
            int isTest = 0;
            if (foroshandehAsliIMEI.trim().length() != 0) {
                usingIMEI = foroshandehAsliIMEI;
                isTest = 1;
            } else {
                usingIMEI = deviceIMEI;
                isTest = 0;
            }

            //usingIMEI = deviceIMEI;
            //isTest = 0;

            UserTypeShared userTypeShared = new UserTypeShared(context);
            userTypeShared.putString(userTypeShared.USING_IMEI(), usingIMEI);
            userTypeShared.putInt(userTypeShared.USER_TYPE(), isTest);

            return foroshandehAsliIMEI;
        }
    }


    public class Logger
    {
        public boolean insertLogToDB(Context context,int logType, String logMessage, String logClass, String logActivity, String logFunctionParent, String logFunctionChild)
        {
            int ccAfrad;
            Log.d("insertLogToDB","imei:" + new DeviceInfo().getIMEI(context) +"-"+ logType +"-"+ logMessage +"-"+ new DeviceInfo().getAPILevel(context));
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(context);
            LogPPCDAO logPPCDAO = new LogPPCDAO(context);
            ccAfrad = foroshandehMamorPakhshDAO.getCCAfrad();
            LogPPCModel logModel = new LogPPCModel();
            logModel.setCcAfrad(ccAfrad);
            logModel.setIMEI(new DeviceInfo().getIMEI(context));
            logModel.setLogType(logType);
            logModel.setLogMessage(logMessage);
            logModel.setLogClass(logClass);
            logModel.setLogActivity(logActivity);
            logModel.setLogFunctionParent(logFunctionParent);
            logModel.setLogFunctionChild(logFunctionChild);
            logModel.setLogDate(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(Calendar.getInstance().getTime()));
            logModel.setExtraProp_IsOld(0);
            logModel.setAndroidAPI(new DeviceInfo().getAPILevel(context));
            return logPPCDAO.insert(logModel);
        }


        public boolean sendLogToServer(Context context, String logMessage, String logClass, String logActivity, String logFunctionParent, String logFunctionChild)
        {
            LogPPCModel logModel = new LogPPCModel();
            logModel.setCcAfrad(new ForoshandehMamorPakhshDAO(context).getCCAfrad());
            logModel.setIMEI(new DeviceInfo().getIMEI(context));
            logModel.setLogMessage(logMessage);
            logModel.setLogClass(logClass);
            logModel.setLogActivity(logActivity);
            logModel.setLogFunctionParent(logFunctionParent);
            logModel.setLogFunctionChild(logFunctionChild);
            logModel.setLogDate(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(Calendar.getInstance().getTime()));
            logModel.setExtraProp_IsOld(0);
            logModel.setAndroidAPI(new DeviceInfo().getAPILevel(context));
            return true;
        }


        public void sendLogToMail(Context context, String logMessage, String logClass, String logActivity, String logFunctionParent, String logFunctionChild, final AsyncTaskResponse callback)
        {
            EmailLogPPCShared emailLogPPCShared = new EmailLogPPCShared(context);
            String email = emailLogPPCShared.getString(emailLogPPCShared.EMAIL() , "");
            String pass = emailLogPPCShared.getString(emailLogPPCShared.PASSWORD() , "");

            Log.d("mail" , "email : " + email);
            Log.d("mail" , "encrypted pass : " + pass);

            if (!email.trim().equals("") && !pass.trim().equals(""))
            {
                Encryption encryption = new Encryption();
                String decryptedPass = encryption.decrypt(context , pass);

                Log.d("mail" , "decryptedPass pass : " + decryptedPass);

                if (!decryptedPass.trim().equals(""))
                {
                    SendMail sendMail = new SendMail();
                    String IMEI = new DeviceInfo().getIMEI(context);
                    int androidAPI = new DeviceInfo().getAPILevel(context);
                    String todayDate = new DateUtils().todayDateGregorian();

                    String emailBody = " Message : " + logMessage + "\n Class : " + logClass + "\n Activity : " + logActivity +
                            "\n FunctionParent : " + logFunctionParent + "\n Function Child : " + logFunctionChild +
                            "\n IMEI : " + IMEI + "\n date : " + todayDate + "\n android API : " + androidAPI;
                    sendMail.sendGmail(email, decryptedPass, "LOG FOR SAP APPLICATION", emailBody, EmailLogPPCModel.MAIL_RECEIVER, new AsyncTaskResponse() {
                        @Override
                        public void processFinished(Object object)
                        {
                            callback.processFinished(object);
                        }
                    });
                }
                else
                {
                    callback.processFinished(false);
                }
            }
            else
            {
                callback.processFinished(false);
            }
        }

    }


    public class Encryption
    {

        SecretKey generateKey(Context context)
        {
            try
            {
                final int KEY = 65464;
                return new SecretKeySpec((Constants.ENC_KEY() + KEY).getBytes() , "AES");
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                Logger logger = new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "PubFunc.Encryption", "", "generateKey", "");
                return null;
            }
        }

        public String encrypt(Context context, String plain)
        {
            try
            {
                SecretKey key = generateKey(context);
                if (key != null)
                {
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    byte[] cipherText = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
                    return Base64.encodeToString(cipherText, Base64.DEFAULT);
                }
                else
                {
                    return "";
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                Logger logger = new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "PubFunc.Encryption", "", "encrypt", "");
                return "";
            }
        }

        public String decrypt(Context context, String encoded)
        {
            try
            {
                SecretKey key = generateKey(context);
                if (key != null)
                {
                    byte[] encryted_bytes = Base64.decode(encoded , Base64.DEFAULT);
                    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
                    cipher.init(Cipher.DECRYPT_MODE, key);
                    byte[] decrypted = cipher.doFinal(encryted_bytes);
                    return new String(decrypted , StandardCharsets.UTF_8);
                }
                else
                {
                    return "";
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                Logger logger = new Logger();
                logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "PubFunc.Encryption", "", "decrypt", "");
                return "";
            }
        }
    }


    public class SendMail
    {
        public void sendGmail(String sender, String password, String subject, String body, String receiver, AsyncTaskResponse callback)
        {
            AsyncSendMail asyncSendMail = new AsyncSendMail(sender, password, subject, body, receiver);
            asyncSendMail.delegate = callback;
            asyncSendMail.execute();
        }
    }


    public class ImageUtils
    {
        public byte[] convertBitmapToByteArray(Context context , Bitmap bitmap , int quality)
        {
            try
            {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG , quality , stream);
                return stream.toByteArray();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                Logger logger = new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "imageUtils", "", "convertBitmapToByteArray", "");
                return new byte[]{};
            }
        }

        public Bitmap convertByteArrayToBitmap(Context context , byte[] bytes)
        {
            try
            {
                return BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                Logger logger = new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "imageUtils", "", "convertBitmapToByteArray", "");
                return null;
            }
        }

        public Bitmap getResizedBitmap(Bitmap image, int maxSize)
        {
            int width = image.getWidth();
            int height = image.getHeight();

            float bitmapRatio = (float)width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            return Bitmap.createScaledBitmap(image, width, height, true);
        }
    }


    public class NationalCodeUtil
    {

        private final String[] notNationalCode = new String[]{
                "0000000000",
                "1111111111",
                "2222222222",
                "3333333333",
                "4444444444",
                "5555555555",
                "6666666666",
                "7777777777",
                "8888888888",
                "9999999999"
        };

        public boolean checkNationalCode(String nationalCode)
        {
            if (nationalCode == null)
            {
                return false;
            }
            if (nationalCode.isEmpty())
            {
                return false;
            }
            if (nationalCode.length() != 10)
            {
                return false;
            }
            if (!nationalCode.matches("[0-9]+"))
            {
                return false;
            }

            for (String s : notNationalCode)
            {
                if (s.equalsIgnoreCase(nationalCode))
                {
                    return false;
                }
            }

            String nationalCodeWithoutControlDigit = nationalCode.substring(0, nationalCode.length() - 1);
            String controlDigit = nationalCode.substring(nationalCode.length() - 1);
            int sum = 0;
            int i = 10;
            for (char c : nationalCodeWithoutControlDigit.toCharArray())
            {
                int temp = Integer.parseInt("" + c) * i;
                i--;
                sum += temp;
            }
            int modBy11 = sum % 11;
            if (modBy11 < 2)
            {
                if (modBy11 == Integer.parseInt(controlDigit))
                {
                    return true;
                }
            }
            else if (11 - modBy11 == Integer.parseInt(controlDigit))
            {
                return true;
            }
            return false;
        }

        public boolean checkNationalEconomicalCode(String nationalCode)
        {
            if (nationalCode == null)
            {
                return false;
            }
            if (nationalCode.isEmpty())
            {
                return false;
            }
            if (nationalCode.length() != 11)
            {
                return false;
            }
            if (!nationalCode.matches("[0-9]+"))
            {
                return false;
            }
            String nationalCodeWithoutControlDigit = nationalCode.substring(0, nationalCode.length() - 1);
            String controlDigit = nationalCode.substring(nationalCode.length() - 1);
            String deci = nationalCode.substring(nationalCode.length() - 2, nationalCode.length() - 1);
            int decimal = Integer.parseInt(deci) + 2;
            int[] multiplier = new int[]{29, 27, 23, 19, 17, 29, 27, 23, 19, 17};
            int sum = 0;
            int i = 0;
            for (char c : nationalCodeWithoutControlDigit.toCharArray())
            {
                int temp = (Integer.parseInt("" + c) + decimal) * multiplier[i];
                i++;
                sum += temp;
            }
            int modBy11 = sum % 11;
            if (modBy11 == 10)
            {
                modBy11 = 0;
            }

            if (modBy11 == Integer.parseInt(controlDigit))
            {
                return true;
            }

            return false;
        }

    }


    public class LanguageUtil
    {
        public String convertFaNumberToEN(String strNumber)
        {
            String[] persianNumber = new String[]{"۰","۱","۲","۳","۴","۵","۶","۷","۸","۹","ي","ئ","یٰ","ة","ك","ؤ","ء","أ","ٱ","إ","اً","هٔ"};
            strNumber = strNumber.replace(persianNumber[0] , "0");
            strNumber = strNumber.replace(persianNumber[1] , "1");
            strNumber = strNumber.replace(persianNumber[2] , "2");
            strNumber = strNumber.replace(persianNumber[3] , "3");
            strNumber = strNumber.replace(persianNumber[4] , "4");
            strNumber = strNumber.replace(persianNumber[5] , "5");
            strNumber = strNumber.replace(persianNumber[6] , "6");
            strNumber = strNumber.replace(persianNumber[7] , "7");
            strNumber = strNumber.replace(persianNumber[8] , "8");
            strNumber = strNumber.replace(persianNumber[9] , "9");
            strNumber = strNumber.replace(persianNumber[10] , "ی");
            strNumber = strNumber.replace(persianNumber[11] , "ی");
            strNumber = strNumber.replace(persianNumber[12] , "ی");
            strNumber = strNumber.replace(persianNumber[13] , "ه");
            strNumber = strNumber.replace(persianNumber[14] , "ک");
            strNumber = strNumber.replace(persianNumber[15] , "و");
            strNumber = strNumber.replace(persianNumber[16] , "ی");
            strNumber = strNumber.replace(persianNumber[17] , "ا");
            strNumber = strNumber.replace(persianNumber[18] , "ا");
            strNumber = strNumber.replace(persianNumber[19] , "ا");
            strNumber = strNumber.replace(persianNumber[20] , "ا");
            strNumber = strNumber.replace(persianNumber[21] , "ه");
            return strNumber;
        }
    }




    public class RequestBottomBarConfig
    {
        private boolean showBarkhordAvalie;
        private boolean showMojoodiGiri;
        private boolean forceBarkhordAvalie;
        private boolean forceMojoodiGiri;
        private boolean multipleMojoodiGiri; //only one time can do this or more
        private boolean showInvoiceSettlement;

        public void getConfig(Context context)
        {
            ParameterChildDAO childParameterDAO = new ParameterChildDAO(context);
            ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccParameter(String.format("%1$s , %2$S", Constants.CC_BARKHORD_AVALIE_CONFIG(), Constants.CC_MOJOODI_GIRI_CONFIG()));
            Log.d("pubfunc","childParameterModels:"+childParameterModels.toString());
            for (ParameterChildModel model : childParameterModels)
            {
                if (model.getCcParameter() == Constants.CC_MOJOODI_GIRI_CONFIG())
                {
                    if (model.getName().trim().equalsIgnoreCase(Constants.SHOW()))
                    {
                        showMojoodiGiri = model.getValue().trim().equals("1");
                    }
                    else if (model.getName().trim().equalsIgnoreCase(Constants.FORCE()))
                    {
                        forceMojoodiGiri = model.getValue().trim().equals("1");
                    }
                    else if (model.getName().trim().equalsIgnoreCase(Constants.MULTIPLE()))
                    {
                        multipleMojoodiGiri = model.getValue().trim().equals("1");
                    }
                }
                else if (model.getCcParameter() == Constants.CC_BARKHORD_AVALIE_CONFIG())
                {
                    if (model.getName().trim().equalsIgnoreCase(Constants.SHOW()))
                    {
                        showBarkhordAvalie = model.getValue().trim().equals("1");
                    }
                    else if (model.getName().trim().equalsIgnoreCase(Constants.FORCE()))
                    {
                        forceBarkhordAvalie = model.getValue().trim().equals("1");
                    }
                }
            }
            Log.d("pubfunc","showMojoodiGiri:"+showMojoodiGiri+" , forceMojoodiGiri:"+forceMojoodiGiri+
                    " , multipleMojoodiGiri:"+multipleMojoodiGiri+
                    " ,showBarkhordAvalie"+showBarkhordAvalie + " ,forceBarkhordAvalie:"+forceBarkhordAvalie);
            SelectFaktorShared shared = new SelectFaktorShared(context);
            boolean haveMojoodiGiri = shared.getBoolean(shared.getHaveMojoodiGiri() , false);
            if (showMojoodiGiri)
            {
                Log.d("MojodigiriConfig" , "in if");
                if (!multipleMojoodiGiri && haveMojoodiGiri)
                {
                    Log.d("MojodigiriConfig" , "in second if");
                    showMojoodiGiri = false;
                }
            }

            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(context);
            ForoshandehMamorPakhshModel model = foroshandehMamorPakhshDAO.getIsSelect();
            int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(model);
            if (noeMasouliat == 2 || noeMasouliat == 4 || noeMasouliat == 5)
            {
                showInvoiceSettlement = true;
            }
        }


        public boolean getShowBarkhordAvalie() {
            return showBarkhordAvalie;
        }
        public boolean getShowMojoodiGiri() {
            return showMojoodiGiri;
        }
        public boolean getForceBarkhordAvalie() {
            return forceBarkhordAvalie;
        }
        public boolean getForceMojoodiGiri() {
            return forceMojoodiGiri;
        }
        public boolean getMultipleMojoodiGiri() {
            return multipleMojoodiGiri;
        }
        public boolean getShowInvoiceSettlement() {
            return showInvoiceSettlement;
        }


    }


    public class ConvertUnit
    {
        public int[] tedadToCartonBasteAdad(int tedad , int cartonUnitCount , int basteUnitCount , int adadUnitCount)
        {
            int cartonCount = tedad / cartonUnitCount;
            int remain = tedad % cartonUnitCount;
            int basteCount = remain / basteUnitCount;
            int adad = remain % basteUnitCount;
            return new int[]{cartonCount , basteCount , adad};
        }
    }


    public class VosolUtil
    {
        public int getCodeNoeCheck(int codeNoeVosol , ArrayList<ParameterChildModel> childParameterModels)
        {
            int codeNoeCheck = 0;
            for (ParameterChildModel model : childParameterModels)
            {
                if (model.getValue().equals(Constants.VALUE_SANAD_IRAN_CHECK()))
                {
                    codeNoeCheck = 200;
                }
                else if (model.getValue().equals(Constants.VALUE_SANAD_CHECK_BANKI()))
                {
                    codeNoeCheck = 100;
                }
                else if (model.getValue().equals(Constants.VALUE_SANAD_POS()))
                {
                    codeNoeCheck = 100;
                }
                else if (model.getValue().equals(Constants.VALUE_SANAD_RESID()))
                {
                    codeNoeCheck = 0;
                }
            }
            return codeNoeCheck;
        }

    }


    public class DAOUtil
    {
        public ArrayList<DataTableModel> cursorToDataTable(Context context , Cursor cursor)
        {
            ArrayList<DataTableModel> dataTableModels = new ArrayList<>();
            try
            {
                if (cursor != null)
                {
                    if (cursor.getCount() > 0)
                    {
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast())
                        {
                            DataTableModel dataTableModel = new DataTableModel();

                            if (cursor.getColumnCount() > 0)
                            {
                                if (cursor.getType(0)== Cursor.FIELD_TYPE_FLOAT)
                                {
                                    dataTableModel.setFiled1(cursor.getString(0)== null ? "0" : String.valueOf(cursor.getDouble(0)));
                                }
                                else
                                {
                                    dataTableModel.setFiled1(cursor.getString(0)== null ? "0" : cursor.getString(0));
                                }
                            }
                            if (cursor.getColumnCount()> 1)
                            {
                                if (cursor.getType(1)== Cursor.FIELD_TYPE_FLOAT)
                                {
                                    dataTableModel.setFiled2(cursor.getString(1)== null ? "0" : String.valueOf(cursor.getDouble(1)));
                                }
                                else
                                {
                                    dataTableModel.setFiled2(cursor.getString(1)== null ? "0" : cursor.getString(1));
                                }
                            }
                            if (cursor.getColumnCount()> 2)
                            {
                                if (cursor.getType(2)== Cursor.FIELD_TYPE_FLOAT)
                                {
                                    dataTableModel.setFiled3(cursor.getString(2)== null ? "0" : String.valueOf(cursor.getDouble(2)));
                                }
                                else
                                {
                                    dataTableModel.setFiled3(cursor.getString(2)== null ? "0" : cursor.getString(2));
                                }
                            }
                            if (cursor.getColumnCount()> 3)
                            {
                                if (cursor.getType(3)== Cursor.FIELD_TYPE_FLOAT)
                                {
                                    dataTableModel.setFiled4(cursor.getString(3)== null ? "0" : String.valueOf(cursor.getDouble(3)));
                                }
                                else
                                {
                                    dataTableModel.setFiled4(cursor.getString(3)== null ? "0" : cursor.getString(3));
                                }
                            }
                            if (cursor.getColumnCount()> 4)
                            {
                                if (cursor.getType(4)== Cursor.FIELD_TYPE_FLOAT)
                                {
                                    dataTableModel.setFiled5(cursor.getString(4)== null ? "0" : String.valueOf(cursor.getDouble(4)));
                                }
                                else
                                {
                                    dataTableModel.setFiled5(cursor.getString(4)== null ? "0" : cursor.getString(4));
                                }
                            }
                            if (cursor.getColumnCount()> 5)
                            {
                                if (cursor.getType(5)== Cursor.FIELD_TYPE_FLOAT)
                                {
                                    dataTableModel.setFiled6(cursor.getString(5)== null ? "0" : String.valueOf(cursor.getDouble(5)));
                                }
                                else
                                {
                                    dataTableModel.setFiled6(cursor.getString(5)== null ? "0" : cursor.getString(5));
                                }
                            }
                            if (cursor.getColumnCount()> 6)
                            {
                                if (cursor.getType(6)== Cursor.FIELD_TYPE_FLOAT)
                                {
                                    dataTableModel.setFiled7(cursor.getString(6)== null ? "0" : String.valueOf(cursor.getDouble(6)));
                                }
                                else
                                {
                                    dataTableModel.setFiled7(cursor.getString(6)== null ? "0" : cursor.getString(6));
                                }
                            }
                            if (cursor.getColumnCount()> 7)
                            {
                                if (cursor.getType(7)== Cursor.FIELD_TYPE_FLOAT)
                                {
                                    dataTableModel.setFiled8(cursor.getString(7)== null ? "0" : String.valueOf(cursor.getDouble(7)));
                                }
                                else
                                {
                                    dataTableModel.setFiled8(cursor.getString(7)== null ? "0" : cursor.getString(7));
                                }
                            }


                            dataTableModels.add(dataTableModel);
                            cursor.moveToNext();
                        }
                    }
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                Logger logger = new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "PubFunc.DAOUtil.", "", "cursorToDataTable", "");
            }
            return dataTableModels;
        }

        public String convertIntegerArrayToString(ArrayList<Integer> IntegerArray){
            String strInteger="";
            for (int i=0;i<IntegerArray.size();i++){
                if (i==0) {
                    strInteger += String.valueOf(IntegerArray.get(i));
                }else {
                    strInteger+="," + String.valueOf(IntegerArray.get(i));


                }
            }
            Log.i("PubFunc", "convertIntegerArrayToString: "+strInteger);
            return strInteger;

        }

    }

    public class FontUtils {
        //        static FontUtils instance = null;
//        // Factory method to provide the users with instances
//        public static FontUtils getInstance() {
//            if (instance == null)
//                instance = new FontUtils();
//
//            return instance;
//        }
        public FontUtils() {

        }

        public void setFont(ViewGroup group, Typeface font) {
            int count = group.getChildCount();
            View v;
            for (int i = 0; i < count; i++) {
                v = group.getChildAt(i);
                if (v instanceof TextView || v instanceof EditText || v instanceof Button) {
                    ((TextView) v).setTypeface(font);
                } else if (v instanceof ViewGroup)
                    setFont((ViewGroup) v, font);
            }
        }

    }

    public class ChartUtils{
        public float getHadafChartSumlimitLine(int tedadHadafMah ,float limitLine){

            float darsadlimitline= (float) (tedadHadafMah)*(0.8f);


            return darsadlimitline;

        }

        public String convertEnglishNumbersToPersian(String str){
            char[] arabicChars = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
            StringBuilder builder = new StringBuilder();
            for(int i =0;i<str.length();i++)
            {
                if(Character.isDigit(str.charAt(i)))
                {
                    builder.append(arabicChars[(int)(str.charAt(i))-48]);
                }
                else
                {
                    builder.append(str.charAt(i));
                }
            }

            return builder.toString();

        }
        //barChart with single barset groups for indicating percentageAnalysis
        public void drawPercentageBarChart(Context context, BarChart barChart , int tedadForoshMah, int tedadHadafForoshMah, int tedadForoshRooz, int tedadHadafForooshRooz, String name)
        {


            float darsadMah=((float) tedadForoshMah/(float) tedadHadafForoshMah)*100;
            float darsadRooz=((float) tedadForoshRooz/(float) tedadHadafForooshRooz)*100;
            Log.d("darsadMah", "drawPercentageBarChart: "+darsadMah);
            Log.d("darsadMah", "drawPercentageBarChart: "+darsadRooz);


            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
            //barChartCountFaktor.setOnChartValueSelectedListener(this);
            barChart.setDrawBarShadow(false);
            barChart.setDrawValueAboveBar(true);
            barChart.getDescription().setEnabled(false);
            barChart.setNoDataText(context.getResources().getString(R.string.errorGetDataChart));
            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            barChart.setMaxVisibleValueCount(60);
            // scaling can now only be done on x- and y-axis separately
            barChart.setPinchZoom(false);
            barChart.setDrawGridBackground(false);


            // chart.setDrawYLabels(false);


            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTypeface(font);
            xAxis.setDrawGridLines(false);
            xAxis.setTextSize(12f);

            xAxis.setAxisMinimum(0);
            xAxis.setGranularity(1f); // only intervals of 1 day
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value)
                {
                    if (value == 1)
                    {
                        return context.getString(R.string.untilToday);
                    }
                    else if (value == 2)
                    {
                        return context.getString(R.string.today);
                    }
                    else
                    {
                        return "";
                    }
                }
            });

            YAxis leftAxis =barChart.getAxisLeft();
            leftAxis.setTypeface(font);
            leftAxis.setLabelCount(5, false);
            //leftAxis.setValueFormatter(custom);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(20f);
            if (darsadMah<100 &&darsadRooz<100){
                leftAxis.setAxisMaximum(100f);
            }
            leftAxis.setAxisMinimum(0f);
//            leftAxis.setAxisMaximum(100f);// this replaces setStartAtZero(true)

            YAxis rightAxis = barChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setTypeface(font);
            rightAxis.setLabelCount(5, false);
            if (darsadMah<100 &&darsadRooz<100){
                rightAxis.setAxisMaximum(100f);
            }
//            rightAxis.setAxisMaximum(100f);
            //rightAxis.setValueFormatter(custom);
            rightAxis.setSpaceTop(20f);
            rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
            barChart.setFitBars(true);
            barChart.animateY(2500);

            Legend legend = barChart.getLegend();
            legend.setForm(Legend.LegendForm.EMPTY);
        /*legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextSize(0f);
        legend.setTypeface(font);*/
        /*legend.setFormSize(15f);
        legend.setXEntrySpace(4f);*/
            Log.i("nameofbrandMain", "drawPercentageBarChart: "+name);
            if (name.equals("جمع") ) {
                Log.i("insideLimitLine", "drawPercentageBarChart: insideLimitLine");
                float limitLineValue = 85f;
                LimitLine ll1 = new LimitLine(limitLineValue, "");
                ll1.setLineWidth(4f);
                ll1.enableDashedLine(10f, 10f, 0f);

                ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                ll1.setTextSize(18f);
                ll1.setTextColor(context.getResources().getColor(R.color.colorRed));



                barChart.getAxisLeft().removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
                barChart.getAxisLeft().addLimitLine(ll1);
            }

            ArrayList<BarEntry> values = new ArrayList<>();
            BarEntry todayEntry = new BarEntry(2f , darsadRooz);
            BarEntry untilTodayEntry = new BarEntry(1f , darsadMah);
            values.add(untilTodayEntry);
            values.add(todayEntry);

            BarDataSet datasetAmarForosh = new BarDataSet(values , "");
            datasetAmarForosh.setDrawIcons(false);
            datasetAmarForosh.setColor(context.getResources().getColor(R.color.colorForoshChart));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(datasetAmarForosh);

            BarData data = new BarData(dataSets);
//        data.setValueFormatter(new LargeValueFormatter());
            data.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    value=Float.valueOf(String.format("%.1f",value));

                    return   new PubFunc().new ChartUtils().convertEnglishNumbersToPersian(String.valueOf(value)) +"%";


                }
            });
            data.setValueTextSize(12f);
            data.setBarWidth(0.9f);
            barChart.setData(data);
            barChart.setScaleEnabled(false);

        }

        //barChart with single barset groups for indicating percentageAnalysis
        public void drawSingleBarBarChart(Context context, BarChart barChart , float firstBarValue, float secondBarValue, String titlename, ArrayList<String> xAxisLabels, Float limitLineValue , boolean isPercentage)
        {
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            if (firstBarValue==0 &&secondBarValue==0){
                setNoDataText(context,barChart,font);
                Log.i("inSetNoData", "drawPercentageBarChart: ");
            }else {
                if (!isPercentage){
                    firstBarValue=(int) firstBarValue;
                    secondBarValue=(int) secondBarValue;
                }





                //barChartCountFaktor.setOnChartValueSelectedListener(this);
                barChart.setDrawBarShadow(false);
                barChart.setDrawValueAboveBar(true);
                barChart.getDescription().setEnabled(false);
                barChart.setNoDataText(context.getResources().getString(R.string.errorGetDataChart));
                // if more than 60 entries are displayed in the chart, no values will be
                // drawn
                barChart.setMaxVisibleValueCount(60);
                // scaling can now only be done on x- and y-axis separately
                barChart.setPinchZoom(false);
                barChart.setDrawGridBackground(false);


                // chart.setDrawYLabels(false);


                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTypeface(font);
                xAxis.setDrawGridLines(false);
                xAxis.setTextSize(12f);

                xAxis.setAxisMinimum(0);
                xAxis.setGranularity(1f); // only intervals of 1 day



                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 1) {
                            return xAxisLabels.get(0);
                        } else if (value == 2) {
                            return xAxisLabels.get(1);
                        } else {
                            return "";
                        }
                    }
                });


                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setTypeface(font);
                leftAxis.setLabelCount(5, false);
                //leftAxis.setValueFormatter(custom);
                leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                leftAxis.setSpaceTop(20f);
                if (firstBarValue < 100f && secondBarValue < 100f) {
                    leftAxis.setAxisMaximum(100f);
                }
                leftAxis.setAxisMinimum(0f);
//            leftAxis.setAxisMaximum(100f);// this replaces setStartAtZero(true)

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawGridLines(false);
                rightAxis.setTypeface(font);
                rightAxis.setLabelCount(5, false);
                Log.i("darsadMahDarsadRooz", "drawPercentageBarChart: " + firstBarValue + " " + secondBarValue);
                if (firstBarValue < 100f && secondBarValue < 100f) {
                    rightAxis.setAxisMaximum(100f);
                }
//            rightAxis.setAxisMaximum(100f);
                //rightAxis.setValueFormatter(custom);
                rightAxis.setSpaceTop(20f);
                rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
                barChart.setFitBars(true);
                barChart.animateY(2500);

                Legend legend = barChart.getLegend();
                legend.setForm(Legend.LegendForm.EMPTY);

                Log.i("titlename", "drawPercentageBarChart: " + titlename);
                if (titlename.equals("جمع")) {
                    Log.i("insideLimitLine", "drawPercentageBarChart: insideLimitLine");
                    if (limitLineValue!=null) {
                        LimitLine limitLine = new LimitLine(limitLineValue, "");
                        limitLine.setLineWidth(2f);
                        limitLine.enableDashedLine(10f, 10f, 0f);

                        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                        limitLine.setTextSize(18f);
                        limitLine.setTextColor(context.getResources().getColor(R.color.colorRed));


                        barChart.getAxisLeft().removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
                        barChart.getAxisLeft().addLimitLine(limitLine);
                    }
                }

                ArrayList<BarEntry> values = new ArrayList<>();
                BarEntry todayEntry = new BarEntry(2f, firstBarValue);
                BarEntry untilTodayEntry = new BarEntry(1f,secondBarValue);
                values.add(untilTodayEntry);
                values.add(todayEntry);

                BarDataSet datasetAmarForosh = new BarDataSet(values, "");
                datasetAmarForosh.setDrawIcons(false);
                datasetAmarForosh.setColor(context.getResources().getColor(R.color.colorForoshChart));

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(datasetAmarForosh);


                BarData data = new BarData(dataSets);

//        data.setValueFormatter(new LargeValueFormatter());
                if (isPercentage) {
                    data.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            value = Float.valueOf(String.format("%.1f", value));

                            return new ChartUtils().convertEnglishNumbersToPersian(String.valueOf(value)) + "%";


                        }
                    });
                }else{
                    data.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            value = Float.valueOf(String.format("%.1f", value));

                            return new PubFunc().new ChartUtils().convertEnglishNumbersToPersian(String.valueOf(value)) ;


                        }
                    });
                }
                data.setValueTextSize(15f);
                data.setBarWidth(0.9f);
                data.setValueTypeface(font);
                barChart.setData(data);
                barChart.setTouchEnabled(false);
                barChart.setScaleEnabled(false);
            }
        }



        @RequiresApi(api = Build.VERSION_CODES.M)
        public void drawGroupBarBarChart(Context context, BarChart barChart, int firstSetFirstValue, int firstSetSecondValue, int secondSetFirstValue, int secondSetSecondValue, String titleName, ArrayList<String> xAxisLables, ArrayList<String> legendLabels, float limitLineValue) {
            float groupSpace = 0.6f;
            float barSpace = 0.0f; // x4 DataSet
            float barWidth = 0.2f; // x4 DataSet
            // (0.43 + 0.03) * 2 + 0.08 = 1.00 -> interval per "group"
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.fontPath));

            if (firstSetFirstValue == 0 && firstSetSecondValue == 0 && secondSetFirstValue == 0 && firstSetSecondValue == 0 && secondSetSecondValue == 0) {
                setNoDataText(context, barChart, font);
                Log.i("SetNoDataText", "drawBarChartNumerical: in set no Data");

            } else {

                barChart.setDrawBarShadow(false);
                barChart.setDrawValueAboveBar(true);
                barChart.getDescription().setEnabled(false);
                barChart.setNoDataText(context.getString(R.string.errorGetDataChart));
                // if more than 60 entries are displayed in the chart, no values will be
                // drawn
                barChart.setMaxVisibleValueCount(60);
                // scaling can now only be done on x- and y-axis separately
                barChart.setPinchZoom(false);
                barChart.setDrawGridBackground(false);

                Legend legend = barChart.getLegend();
                legend.setForm(Legend.LegendForm.SQUARE);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setDrawInside(false);
                legend.setTextSize(12f);
                legend.setTypeface(font);
                legend.setXEntrySpace(4f);
                legend.setFormSize(12f);


                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTypeface(font);
                //xAxis.setDrawGridLines(false);
                xAxis.setCenterAxisLabels(true);
                xAxis.setGranularity(1f); // only intervals of 1 day
                xAxis.setLabelCount(2);
                xAxis.setTextSize(13F);


                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0) {
                            return xAxisLables.get(0);
                        } else if (value == 1) {
                            return xAxisLables.get(1);
                        } else {
                            return "";
                        }
                    }
                });


                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setTypeface(font);
                leftAxis.setLabelCount(5, false);
                leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                leftAxis.setSpaceTop(20f);
                leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawGridLines(false);
                rightAxis.setTypeface(font);
                rightAxis.setLabelCount(5, false);
                rightAxis.setSpaceTop(20f);
                rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
                barChart.setFitBars(true);
                barChart.animateY(2500);


                ArrayList<BarEntry> values1 = new ArrayList<>();
                ArrayList<BarEntry> values2 = new ArrayList<>();

                values1.add(new BarEntry(0, firstSetFirstValue));
                values2.add(new BarEntry(0, firstSetSecondValue));
                values1.add(new BarEntry(1, secondSetFirstValue));
                values2.add(new BarEntry(1, secondSetSecondValue));

                BarDataSet set1, set2;

                if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
                    set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
                    set2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
                    for (int i = 0; i < barChart.getData().getDataSetCount(); i++)
                        Log.i("PubFunc", "drawBarChartNumerical: " + barChart.getData().getDataSetByIndex(i));

                    set1.setValues(values1);
                    set2.setValues(values2);
                    if (titleName.equals("جمع")) {
                        float limitLine = new PubFunc().new ChartUtils().getHadafChartSumlimitLine(firstSetSecondValue, limitLineValue);
                        LimitLine ll1 = new LimitLine(limitLine, "");
                        ll1.setLineWidth(2f);
                        ll1.enableDashedLine(10f, 10f, 0f);
//                    ll1.setLabel(PubFunc.ChartUtils.getInstance().convertEnglishNumbersToPersian(String.valueOf(limitLineValue)));
                        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                        ll1.setTextSize(18f);
                        ll1.setTextColor(context.getResources().getColor(R.color.colorRed));


                        barChart.getAxisLeft().removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
                        barChart.getAxisLeft().addLimitLine(ll1);
                    }
                    set1.setValueTextSize(15f);
//                set2.setValueTextSize(15f);
                    barChart.getData().notifyDataChanged();
                    barChart.notifyDataSetChanged();
                } else {
                    set1 = new BarDataSet(values1, legendLabels.get(0));
//                set1.setColor(itemView.getContext().getColor(R.color.colorForoshChart));
                    set2 = new BarDataSet(values2, legendLabels.get(1));
                    set2.setColor(context.getResources().getColor(R.color.colorMarjoeeChart));
                    set1.setValueTextSize(15f);
                    set2.setValueTextSize(15f);
                    if (titleName.equals("جمع")) {
                        float limitLine = new PubFunc().new ChartUtils().getHadafChartSumlimitLine(firstSetSecondValue, limitLineValue);
                        LimitLine ll1 = new LimitLine(limitLine, "");
                        ll1.setLineWidth(2f);
//                    ll1.setLabel(PubFunc.ChartUtils.getInstance().convertEnglishNumbersToPersian(String.valueOf(limitLineValue))+"%");
                        ll1.enableDashedLine(10f, 10f, 0f);
                        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                        ll1.setTextSize(18f);
                        ll1.setTextColor(context.getResources().getColor(R.color.colorRed));


                        barChart.getAxisLeft().removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
                        barChart.getAxisLeft().addLimitLine(ll1);
                    }
                    BarData data = new BarData(set1, set2);
                    data.setValueFormatter(new LargeValueFormatter());
                    data.setValueTypeface(font);

                    barChart.setData(data);
                }


                // specify the width each bar should have
                barChart.getBarData().setBarWidth(barWidth);

                // restrict the x-axis range
                barChart.getXAxis().setAxisMinimum(0);

                // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
                barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 2);
                barChart.groupBars(0, groupSpace, barSpace);
                barChart.setTouchEnabled(false);
                barChart.setScaleEnabled(false);
                barChart.invalidate();
            }
        }
        private void setNoDataText(Context context,BarChart barChart ,Typeface font){
            barChart.setNoDataText(context.getResources().getString(R.string.errorGetDataChart));
            barChart.setNoDataTextColor(Color.RED);
            barChart.setNoDataTextTypeface(font);
        }

        public ArrayList<Float> calculatePercentFromNumeric(int firstSetFirstValue,int firstSetSecondValue,int secondSetFirstValue,int secondSetSecondValue){
            ArrayList<Float> percentArray=new ArrayList<>();
            float firstPercentValue = ((float) firstSetFirstValue / (float) firstSetSecondValue) * 100f;
            float secondPercentValue = ((float) secondSetFirstValue / (float) secondSetSecondValue) * 100f;
            percentArray.add(firstPercentValue);
            percentArray.add(secondPercentValue);

            return  percentArray;

        }
    }



    public static class ConcurrencyUtils {

        private ConcurrencyUtils() {
        }

        static ConcurrencyUtils instance = null;

        // Factory method to provide the users with instances
        public static ConcurrencyUtils getInstance() {
            if (instance == null)
                instance = new ConcurrencyUtils();

            return instance;

        }


        public void runOnUiThread(ConcurrencyEvents concurrencyEvents) {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    concurrencyEvents.uiThreadIsReady();
                }
            };
            mainHandler.post(runnable);
        }
    }

    public interface ConcurrencyEvents {
        void uiThreadIsReady();
    }





}
