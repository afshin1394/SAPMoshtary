package com.saphamrah.customer.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class GoogleLocationProvider {
    private FusedLocationProviderClient fusedLocationClient;
    private final ArrayList<Location> locations = new ArrayList<>();
    private boolean hasAccess;

    public GoogleLocationProvider(Context context) {
        hasAccess = false;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                hasAccess = true;
                getLocation(context);
            } else {
                hasAccess = false;
            }
        } else {
            hasAccess = true;
            getLocation(context);
        }
    }


    private void getLocation(final Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.d("Location", "location != null and " + location.getLatitude() + " , " + location.getLongitude());
                            locations.clear();
                            locations.add(location);
                        } else {
                            Log.d("Location", "location = null");
                            locations.clear();
                            LocationProvider locationProvider = new LocationProvider();
                            location = locationProvider.getLocation();
                            Log.d("Location", "location == null and " + location.getLatitude() + " , " + location.getLongitude());
                            if (location != null) {
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