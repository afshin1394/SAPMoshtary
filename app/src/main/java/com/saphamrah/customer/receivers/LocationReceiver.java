package com.saphamrah.customer.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;


import com.saphamrah.customer.data.GPSDataModel;
import com.saphamrah.customer.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LocationReceiver extends BroadcastReceiver
{


    @Override
    public void onReceive(Context context, Intent intent)
    {
        double latitude = intent.getDoubleExtra(Constants.LATITUDE() , 0.0);
        double longitude = intent.getDoubleExtra(Constants.LONGITUDE() , 0.0);
        float speed = intent.getFloatExtra(Constants.SPEED() , 0);
        long time = intent.getLongExtra(Constants.TIME() , 0);
        double altitude = intent.getDoubleExtra(Constants.ALTITUDE() , 0.0);
        float accuracy = intent.getFloatExtra(Constants.ACCURACY()  , 0);
        float bearing = intent.getFloatExtra(Constants.BEARING() , 0);
        long elapsedRealTimeNanos = intent.getLongExtra(Constants.ELAPSED_REAL_TIME_NANOS() , 0);
        String provider = intent.getStringExtra(Constants.PROVIDER());
        int minDistance = intent.getIntExtra(Constants.DISTANCE() , Constants.MIN_DISTANCE_CHANGE_FOR_UPDATE());
       /* int ccAfrad = intent.getIntExtra("ccAfrad" , 0);
        int ccForoshandeh = intent.getIntExtra("ccForoshandeh" , 0);
        int ccMamorPakhsh = intent.getIntExtra("ccMamorPakhsh" , 0);
        int ccMasir = intent.getIntExtra("ccMasir" , 0);*/


        /*Log.d("receiver" , "latitude : " + latitude);
        Log.d("receiver" , "longitude : " + longitude);
        Log.d("receiver" , "speed : " + speed);
        Log.d("receiver" , "time : " + time);
        Log.d("receiver" , "altitude : " + altitude);
        Log.d("receiver" , "accuracy : " + accuracy);
        Log.d("receiver" , "bearing : " + bearing);
        Log.d("receiver" , "elapsedRealTimeNanos : " + elapsedRealTimeNanos);
        Log.d("receiver" , "provider : " + provider);
        Log.d("receiver" , "distance : " + minDistance);*/

        GPSDataModel newGpsDataModel = new GPSDataModel();
        newGpsDataModel.setLatitude(latitude);
        newGpsDataModel.setLongitude(longitude);
        newGpsDataModel.setSpeed(speed);
        newGpsDataModel.setAltitude(altitude);
        newGpsDataModel.setAccurancy(accuracy);
        newGpsDataModel.setBearing(bearing);
        newGpsDataModel.setElapsedRealTimeNanos(elapsedRealTimeNanos);
        newGpsDataModel.setProvider(provider);
//        newGpsDataModel.setCcMamorPakhsh(ccMamorPakhsh);
        newGpsDataModel.setExtraProp_IsSend(0);
//        newGpsDataModel.setCcMasir(ccMasir);
//        newGpsDataModel.setCcForoshandeh(ccForoshandeh);
//        newGpsDataModel.setCcAfrad(ccAfrad);
        newGpsDataModel.setDistance(0);
        newGpsDataModel.setTarikh(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(Calendar.getInstance().getTime()));

        //getPreciseLocation(context, newGpsDataModel , minDistance);

//        checkDistance(context , newGpsDataModel , minDistance);
    }


    /*public void getPreciseLocation(final Context context, final GPSDataModel newGpsDataModel,final int minDistance)
    {
        APIServiceGet apiServiceGet = ApiClientNominatim.getClient().create(APIServiceGet.class);
        Call<LocationToAddressResult> call = apiServiceGet.reverseLocationToAddress(Constants.RESPONSE_TYPE(), String.valueOf(newGpsDataModel.getLatitude()) , String.valueOf(newGpsDataModel.getLongitude()) , Constants.ZOOM());
        call.enqueue(new Callback<LocationToAddressResult>() {
            @Override
            public void onResponse(Call<LocationToAddressResult> call, Response<LocationToAddressResult> response)
            {
                PubFunc.Logger logger = new PubFunc().new Logger();
                if (response.raw().body() != null)
                {
                    //Log.d("intercept" , "in on response LocationReceiver.getPreciseLocation and response : " + response.raw().body().contentLength());
                    long contentLength = response.raw().body().contentLength();
                    //logger.insertLogToDB(context,Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, "LocationReceiver", "", "getPreciseLocation", "onResponse");
                }
                if (response.isSuccessful())
                {
                    try
                    {
                        *//*Log.d("address" , "lat : " + response.body().getLat());
                        Log.d("address" , "lon : " + response.body().getLon());*//*

                        newGpsDataModel.setLatitude(Double.parseDouble(response.body().getLat()));
                        newGpsDataModel.setLongitude(Double.parseDouble(response.body().getLon()));

                        *//*Log.d("gpsModel" , "lat : " + newGpsDataModel.getLatitude());
                        Log.d("gpsModel" , "lon : " + newGpsDataModel.getLongitude());*//*

                        checkDistance(context , newGpsDataModel , minDistance);

                        *//*gpsDataPpcDAO.insert(newGpsDataModel);
                        Toast.makeText(context , "new api location saved and disatnce = " + distance , Toast.LENGTH_SHORT).show();
                        Log.d("newLoc" , "new api location saved and disatnce = " + distance);*//*
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        //logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "LocationReceiver", "", "getPreciseLocation", "onResponse");
                        checkDistance(context, newGpsDataModel , minDistance);
                        *//*gpsDataPpcDAO.insert(newGpsDataModel);
                        Toast.makeText(context , "new default location saved and disatnce = " + distance , Toast.LENGTH_SHORT).show();
                        Log.d("newLoc" , "new default location saved and disatnce = " + distance);*//*
                    }
                }
                else
                {
                    checkDistance(context, newGpsDataModel , minDistance);
                    *//*gpsDataPpcDAO.insert(newGpsDataModel);
                    Toast.makeText(context , "new default location saved and disatnce = " + distance , Toast.LENGTH_SHORT).show();
                    Log.d("newLoc" , "new default location saved and disatnce = " + distance);*//*
                }
            }

            @Override
            public void onFailure(Call<LocationToAddressResult> call, Throwable t)
            {
                //PubFunc.Logger logger = new PubFunc().new Logger();
                //logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), t.toString(), "LocationReceiver", "", "getPreciseLocation", "onFailure");
                //GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(context);
                //GPSDataModel lastGPSDataModel = gpsDataPpcDAO.getLastPoint();
                checkDistance(context, newGpsDataModel , minDistance);
                *//*gpsDataPpcDAO.insert(newGpsDataModel);
                Toast.makeText(context , "new default location saved and disatnce = " + distance , Toast.LENGTH_SHORT).show();
                Log.d("newLoc" , "new default location saved and disatnce = " + distance);*//*
            }
        });
    }*/


    /*public void checkDistance(Context context, GPSDataModel newGpsDataModel, int minDistance)
    {
        GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(context);
        GPSDataModel lastGPSDataModel = gpsDataPpcDAO.getLastPoint();
        if (lastGPSDataModel != null)
        {
            Location loc1 = new Location("");
            loc1.setLatitude(lastGPSDataModel.getLatitude());
            loc1.setLongitude(lastGPSDataModel.getLongitude());

            Location loc2 = new Location("");
            loc2.setLatitude(newGpsDataModel.getLatitude());
            loc2.setLongitude(newGpsDataModel.getLongitude());

            float distanceInMeters = loc1.distanceTo(loc2);

            newGpsDataModel.setDistance(distanceInMeters);

            if (distanceInMeters >= minDistance)
            {
                gpsDataPpcDAO.insert(newGpsDataModel);
                //Toast.makeText(context , "new default location saved and disatnce = " + distanceInMeters , Toast.LENGTH_SHORT).show();
            }
            *//*else
            {
                //Toast.makeText(context , "new location not saved and distance = " + distanceInMeters , Toast.LENGTH_SHORT).show();
                Log.d("newLoc" , "new location not saved and distance = " + distanceInMeters);
            }*//*
        }
        else
        {
            gpsDataPpcDAO.insert(newGpsDataModel);
            //Toast.makeText(context , "new location saved and last is null" , Toast.LENGTH_SHORT).show();
            //Log.d("newLoc" , "new location saved and last is null");
        }

    }*/



}
