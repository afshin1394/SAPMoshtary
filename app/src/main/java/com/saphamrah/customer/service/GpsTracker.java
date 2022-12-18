package com.saphamrah.customer.service;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.saphamrah.customer.utils.Constants;


public class GpsTracker extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
{

    private LocationRequest mLocationRequest;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private int MIN_DISTANCE_CHANGE_FOR_UPDATES = Constants.MIN_DISTANCE_CHANGE_FOR_UPDATE();
    private int INTERVAL = Constants.INTERVAL_VALUE();
    private int FASTEST_INTERVAL = Constants.FASTEST_INTERVAL_VALUE();
    private int MAX_ACCURACY = Constants.MAX_ACCURACY_VALUE();
    /*private int ccForoshandeh;
    private int ccMamorPakhsh;
    private int ccMasir;*/


    public GpsTracker()
    {
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        buildGoogleApiClient();

        initializeNotification();
    }

    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (intent != null)
        {
            MIN_DISTANCE_CHANGE_FOR_UPDATES = intent.getIntExtra(Constants.DISTANCE() , Constants.MIN_DISTANCE_CHANGE_FOR_UPDATE());
            INTERVAL = intent.getIntExtra(Constants.INTERVAL() , Constants.INTERVAL_VALUE());
            FASTEST_INTERVAL = intent.getIntExtra(Constants.FASTEST_INTERVAL() , Constants.FASTEST_INTERVAL_VALUE());
            MAX_ACCURACY = intent.getIntExtra(Constants.ACCURACY() , Constants.MAX_ACCURACY_VALUE());
           /* ccForoshandeh = intent.getIntExtra("ccForoshandeh" , 0);
            ccMamorPakhsh = intent.getIntExtra("ccMamorPakhsh" , 0);
            ccMasir = intent.getIntExtra("ccMasir" , 0);*/
        }
        else
        {
            MIN_DISTANCE_CHANGE_FOR_UPDATES = Constants.MIN_DISTANCE_CHANGE_FOR_UPDATE();
            INTERVAL = Constants.INTERVAL_VALUE();
            FASTEST_INTERVAL = Constants.FASTEST_INTERVAL_VALUE();
            MAX_ACCURACY = Constants.MAX_ACCURACY_VALUE();
        }
        if (!googleApiClient.isConnected())
        {
            googleApiClient.connect();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        @SuppressLint("MissingPermission")
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location)
            {
                if (location != null)
                {
                    broadcastData(location);
                }
            }
        });
        startLocationUpdate();
    }


    @SuppressLint("MissingPermission")
    private void startLocationUpdate()
    {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
    }


    @Override
    public void onLocationChanged(Location location)
    {
        //Log.d("changed" , "location changed and accuracy : " + location.getAccuracy());
        if (location.getAccuracy() <= MAX_ACCURACY)
        {
            //Log.d("changed" , "location changed and broadcast data");
            broadcastData(location);
        }
        /*else
        {
            Log.d("changed" , "location changed and show test");
            //Toast.makeText(getApplicationContext() , String.format("accuracy = %1$s and not saved" , location.getAccuracy()) , Toast.LENGTH_SHORT).show();
        }*/
    }


    public void broadcastData(Location location)
    {
        try
        {
            Intent broadcastIntent = new Intent("com.sap.gpstracker");
            broadcastIntent.putExtra(Constants.LATITUDE()  , location.getLatitude());
            broadcastIntent.putExtra(Constants.LONGITUDE() , location.getLongitude());
            broadcastIntent.putExtra(Constants.SPEED()     , (float) (location.getSpeed()*3.6));
            broadcastIntent.putExtra(Constants.TIME()      , location.getTime());
            broadcastIntent.putExtra(Constants.ALTITUDE()  , location.getAltitude());
            broadcastIntent.putExtra(Constants.ACCURACY()  , location.getAccuracy());
            broadcastIntent.putExtra(Constants.BEARING()   , location.getBearing());
            broadcastIntent.putExtra(Constants.ELAPSED_REAL_TIME_NANOS() , location.getElapsedRealtimeNanos());
            broadcastIntent.putExtra(Constants.PROVIDER()  , location.getProvider());
            broadcastIntent.putExtra(Constants.DISTANCE()  , MIN_DISTANCE_CHANGE_FOR_UPDATES);
            /*broadcastIntent.putExtra("ccForoshandeh"  , ccForoshandeh);
            broadcastIntent.putExtra("ccMamorPakhsh"  , ccMamorPakhsh);
            broadcastIntent.putExtra("ccMasir"  , ccMasir);*/

            sendBroadcast(broadcastIntent);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            //Toast.makeText(GpsTracker.this, "Location has been disabled...", Toast.LENGTH_SHORT).show();
           /* PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(getApplicationContext(), Constants.LOG_EXCEPTION(), exception.toString(), "GpsTracker", "" , "broadcastData" ,"");*/
        }
    }


    @Override
    public void onConnectionSuspended(int i)
    {
        //Toast.makeText(getApplicationContext(), "location connection suspended" , Toast.LENGTH_SHORT).show();
       /* PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(getApplicationContext(), Constants.LOG_EXCEPTION(), "onConnectionSuspended and input parameter = " + i, "GpsTracker", "" , "onConnectionSuspended" ,"");*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        //Toast.makeText(getApplicationContext(), "location connection failed" , Toast.LENGTH_SHORT).show();
      /*  PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(getApplicationContext(), Constants.LOG_EXCEPTION(), connectionResult.getErrorMessage(), "GpsTracker", "" , "onConnectionFailed" ,"");*/
    }


    public void initializeNotification()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
          /*  try
            {
                *//**
                 * custom push-notification view
                 *//*
                RemoteViews contentViewLocation = new RemoteViews(getPackageName(), R.layout.custom_push_gps_tracker);
                contentViewLocation.setTextViewText(R.id.titleNotif, getString(R.string.gpsTrackerTitle));
                contentViewLocation.setTextViewText(R.id.textNotif,getString(R.string.gpsTrackerDescription));
                contentViewLocation.setImageViewResource(R.id.image, R.drawable.ic_launcher_icon);


                CharSequence name = getString(R.string.notifChannelName);
                String description = getString(R.string.notifChannelDesc);
                NotificationChannel channel = new NotificationChannel(GPS_CHANNEL_ID(), name,NotificationManager.IMPORTANCE_MIN);
                channel.setDescription(description);
                channel.setShowBadge(false);

                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                Notification.Builder notifi = new Notification.Builder(getApplicationContext(), GPS_CHANNEL_ID());
                notifi.setSmallIcon(R.drawable.ic_location);
                notifi.setCustomContentView(contentViewLocation);


                PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MapActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

                notifi.setContentIntent(contentIntent);
                //getting notification object from notification builder.
                Notification gpsNotification = notifi.build();

                int mNotificationId = 001;

                NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(mNotificationId, gpsNotification);

                //  starting foreground
                startForeground(1, gpsNotification);

            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }*/
        }
    }


}
