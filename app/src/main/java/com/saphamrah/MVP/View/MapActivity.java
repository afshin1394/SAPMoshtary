package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.saphamrah.BuildConfig;
import com.saphamrah.DAO.GPSDataPpcDAO;
import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Receiver.LocationReceiver;
import com.saphamrah.Service.GpsTracker;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;


public class MapActivity extends AppCompatActivity
{

    MapView map = null;
    Intent intent;
    //HashMap<Integer , Marker> hashMapMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Button btnDeleteAll = findViewById(R.id.btnDeleteAll);
        Button btnShow = findViewById(R.id.btnShow);
        Button btnShowAllLocations = findViewById(R.id.btnShowAllLocation);
        Button btnRestart = findViewById(R.id.btnRestart);
        Button btnSetShowDistance = findViewById(R.id.btnSetShowDistance);
        Button btnSetSpeedAltitudeFilter = findViewById(R.id.btnSetFilters);
        Button btnSetAccuracy = findViewById(R.id.btnSetAccuracy);
        final TextView lblAllDataCount = findViewById(R.id.lblAllDataCount);
        final TextView lblFilterDataCount = findViewById(R.id.lblFilterDataCount);
        final EditText txtTimer = findViewById(R.id.txtTimer);
        final EditText txtDistance = findViewById(R.id.txtDistance);
        final EditText txtSpeed = findViewById(R.id.txtSpeed);
        final EditText txtAltitude = findViewById(R.id.txtAltitude);
        final EditText txtAccuracy = findViewById(R.id.txtAccuracy);

        Configuration.getInstance().load(MapActivity.this, PreferenceManager.getDefaultSharedPreferences(MapActivity.this));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        map = findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay( new GpsMyLocationProvider(MapActivity.this) , map);
        mLocationOverlay.enableMyLocation();
        map.getOverlays().add(mLocationOverlay);

        PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider(MapActivity.this);
        IMapController mapController = new MapController(map);
        mapController.setCenter(new GeoPoint(googleLocationProvider.getLatitude() , googleLocationProvider.getLongitude()));
        mapController.setZoom(19.0);


        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(MapActivity.this);
                gpsDataPpcDAO.deleteAll();
            }
        });


        btnSetShowDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                map.getOverlays().clear();

                GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(MapActivity.this);
                ArrayList<GPSDataModel> arrayListGpsData = new ArrayList<>();
                arrayListGpsData = gpsDataPpcDAO.getAll();
                List<GeoPoint> pointsList = new ArrayList<>();

                GeoPoint geoPoint = new GeoPoint(arrayListGpsData.get(0).getLatitude() , arrayListGpsData.get(0).getLongitude());
                pointsList.add(geoPoint);

                for (int i=1 ; i<arrayListGpsData.size() ; i++)
                {
                    GPSDataModel currentGPSDataModel = new GPSDataModel();
                    GPSDataModel beforeGPSDataModel = new GPSDataModel();
                    //GPSDataModel nextGPSDataModel = new GPSDataModel();
                    currentGPSDataModel = arrayListGpsData.get(i);
                    beforeGPSDataModel = arrayListGpsData.get(i - 1);

                    Location loc1 = new Location("");
                    loc1.setLatitude(currentGPSDataModel.getLatitude());
                    loc1.setLongitude(currentGPSDataModel.getLongitude());

                    Location loc2 = new Location("");
                    loc2.setLatitude(beforeGPSDataModel.getLatitude());
                    loc2.setLongitude(beforeGPSDataModel.getLongitude());

                    float distance = loc1.distanceTo(loc2);

                    if (distance <= Integer.parseInt(txtDistance.getText().toString()))
                    {
                        geoPoint = new GeoPoint(currentGPSDataModel.getLatitude() , currentGPSDataModel.getLongitude());
                        pointsList.add(geoPoint);

                        Marker marker = new Marker(map);
                        marker.setPosition(geoPoint);
                        map.getOverlays().add(marker);

                    }

                }

                Polyline line = new Polyline();
                line.setPoints(pointsList);
                line.setGeodesic(true);
                map.getOverlayManager().add(line);

                lblAllDataCount.setText("data count : " + arrayListGpsData.size());
                lblFilterDataCount.setText("filter data count : " + pointsList.size());
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(MapActivity.this);
                    ArrayList<GPSDataModel> arrayListGpsData = new ArrayList<>();
                    arrayListGpsData = gpsDataPpcDAO.getAll();
                    List<GeoPoint> pointsList = new ArrayList<>();

                    GeoPoint geoPoint = new GeoPoint(arrayListGpsData.get(0).getLatitude() , arrayListGpsData.get(0).getLongitude());
                    pointsList.add(geoPoint);

                    for (int i=1 ; i<arrayListGpsData.size() ; i++)
                    {
                        GPSDataModel currentGPSDataModel = new GPSDataModel();
                        GPSDataModel beforeGPSDataModel = new GPSDataModel();
                        GPSDataModel nextGPSDataModel = new GPSDataModel();
                        currentGPSDataModel = arrayListGpsData.get(i);
                        beforeGPSDataModel = arrayListGpsData.get(i - 1);


                        geoPoint = new GeoPoint(currentGPSDataModel.getLatitude() , currentGPSDataModel.getLongitude());
                        pointsList.add(geoPoint);

                        String title = "id : " + currentGPSDataModel.getCcGpsData_PPC() + "\n" + "lat : " + currentGPSDataModel.getLatitude() + "\n"
                                + "lng : " + currentGPSDataModel.getLongitude() + "\n" + "alt : " + currentGPSDataModel.getAltitude() + "\n"
                                + "speed : " + currentGPSDataModel.getSpeed() + "\n" + "accuracy : " + currentGPSDataModel.getAccurancy() + "\n"
                                + "provider : " + currentGPSDataModel.getProvider();

                        Marker marker = new Marker(map);
                        marker.setPosition(geoPoint);
                        marker.setTitle(title);
                        map.getOverlays().add(marker);
                        //hashMapMarkers.put(currentGPSDataModel.getCcGpsData_PPC() , marker);

                    /*Location loc1 = new Location("");
                    loc1.setLatitude(currentGPSDataModel.getLatitude());
                    loc1.setLongitude(currentGPSDataModel.getLongitude());

                    Location loc2 = new Location("");
                    loc2.setLatitude(beforeGPSDataModel.getLatitude());
                    loc2.setLongitude(beforeGPSDataModel.getLongitude());

                    float distance = loc1.distanceTo(loc2);

                    if (distance <= 20)
                    {
                        geoPoint = new GeoPoint(currentGPSDataModel.getLatitude() , currentGPSDataModel.getLongitude());
                        pointsList.add(geoPoint);

                        Marker marker = new Marker(map);
                        marker.setPosition(geoPoint);
                        map.getOverlays().add(marker);

                    }*/

                    }

                    Polyline line = new Polyline();
                    line.setPoints(pointsList);
                    line.setGeodesic(true);
                    map.getOverlayManager().add(line);

                    lblAllDataCount.setText("data count : " + arrayListGpsData.size());
                }
                catch (Exception e)
                {e.printStackTrace();}
            }
        });


        btnShowAllLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(MapActivity.this);
                ArrayList<GPSDataModel> arrayListGpsData = new ArrayList<>();
                arrayListGpsData = gpsDataPpcDAO.getAll();
                /*String allLocation = "";
                for (GPSDataModel gpsDataModel : arrayListGpsData)
                {
                    allLocation = allLocation + "\n" + "lat : " + gpsDataModel.getLatitude() + " , lon : " + gpsDataModel.getLongitude();
                }*/
                CustomAlertDialog customAlertDialog = new CustomAlertDialog(MapActivity.this);
                customAlertDialog.showList(MapActivity.this, arrayListGpsData);
            }
        });


        //intent = new Intent(MapActivity.this, GPSService.class);
        intent = new Intent(MapActivity.this, GpsTracker.class);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    stopService(intent);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                intent.putExtra(Constants.DISTANCE() , Integer.parseInt(txtDistance.getText().toString()));
                intent.putExtra(Constants.INTERVAL() , Integer.parseInt(txtTimer.getText().toString()));
                intent.putExtra(Constants.FASTEST_INTERVAL() , Constants.FASTEST_INTERVAL_VALUE());
                intent.putExtra(Constants.ACCURACY() , Constants.MAX_ACCURACY_VALUE());

                try
                {
                    if (Build.VERSION.SDK_INT >= 26)
                    {
                        LocationReceiver locationReceiver = new LocationReceiver();
                        IntentFilter filter = new IntentFilter("com.sap.gpstracker");
                        registerReceiver(locationReceiver , filter);
                        startForegroundService(intent);
                        Toast.makeText(MapActivity.this, "Tracking Started..", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        startService(intent);
                        Toast.makeText(MapActivity.this, "Tracking Started..", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Log.d("error" , "error run service");
                    e.printStackTrace();
                }
            }
        });


        btnSetSpeedAltitudeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                map.getOverlays().clear();

                int speed = Integer.parseInt(txtSpeed.getText().toString());
                int altitude = Integer.parseInt(txtAltitude.getText().toString());
                int validDistance = Integer.parseInt(txtDistance.getText().toString());
                GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(MapActivity.this);
                ArrayList<GPSDataModel> arrayListGpsData = new ArrayList<>();
                arrayListGpsData = gpsDataPpcDAO.getAll();
                List<GeoPoint> pointsList = new ArrayList<>();

                GeoPoint geoPoint = new GeoPoint(arrayListGpsData.get(0).getLatitude() , arrayListGpsData.get(0).getLongitude());
                pointsList.add(geoPoint);

                for (int i=1 ; i<arrayListGpsData.size()-1 ; i++)
                {
                    GPSDataModel currentGPSDataModel = new GPSDataModel();
                    GPSDataModel beforeGPSDataModel = new GPSDataModel();
                    currentGPSDataModel = arrayListGpsData.get(i);
                    beforeGPSDataModel = arrayListGpsData.get(i-1);

                    if (currentGPSDataModel.getStatus() == 1)
                    {
                        Location loc1 = new Location("");
                        loc1.setLatitude(currentGPSDataModel.getLatitude());
                        loc1.setLongitude(currentGPSDataModel.getLongitude());

                        Location loc2 = new Location("");
                        loc2.setLatitude(beforeGPSDataModel.getLatitude());
                        loc2.setLongitude(beforeGPSDataModel.getLongitude());

                        float distance = loc1.distanceTo(loc2);

                        int validGeoPoint = 1;
                        if (currentGPSDataModel.getSpeed() != beforeGPSDataModel.getSpeed())
                        {
                            if (currentGPSDataModel.getSpeed() > speed && currentGPSDataModel.getAltitude() > 0 && distance <= validDistance)
                            {
                                geoPoint = new GeoPoint(currentGPSDataModel.getLatitude() , currentGPSDataModel.getLongitude());
                                pointsList.add(geoPoint);

                                String title = "id : " + currentGPSDataModel.getCcGpsData_PPC() + "\n" + "lat : " + currentGPSDataModel.getLatitude() + "\n"
                                        + "lng : " + currentGPSDataModel.getLongitude() + "\n" + "alt : " + currentGPSDataModel.getAltitude() + "\n"
                                        + "speed : " + currentGPSDataModel.getSpeed() + "\n" + "distance : " + distance;

                                Marker marker = new Marker(map);
                                marker.setPosition(geoPoint);
                                marker.setTitle(title);
                                map.getOverlays().add(marker);
                            }
                        }
                        else
                        {
                            if (currentGPSDataModel.getSpeed() > 0 && currentGPSDataModel.getAltitude() > 0 && distance <= validDistance)
                            {
                                geoPoint = new GeoPoint(currentGPSDataModel.getLatitude() , currentGPSDataModel.getLongitude());
                                pointsList.add(geoPoint);

                                String title = "id : " + currentGPSDataModel.getCcGpsData_PPC() + "\n" + "lat : " + currentGPSDataModel.getLatitude() + "\n"
                                        + "lng : " + currentGPSDataModel.getLongitude() + "\n" + "alt : " + currentGPSDataModel.getAltitude() + "\n"
                                        + "speed : " + currentGPSDataModel.getSpeed() + "\n" + "distance : " + distance;

                                Marker marker = new Marker(map);
                                marker.setPosition(geoPoint);
                                marker.setTitle(title);
                                map.getOverlays().add(marker);
                            }
                        }
                    }
                }

                Polyline line = new Polyline();
                line.setPoints(pointsList);
                line.setGeodesic(true);
                map.getOverlayManager().add(line);


                lblAllDataCount.setText(String.format("data count : %1$s" , arrayListGpsData.size()));
                lblFilterDataCount.setText(String.format("filtered data count : %1$s" , pointsList.size()));
            }
        });


        btnSetAccuracy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                map.getOverlays().clear();

                GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(MapActivity.this);
                ArrayList<GPSDataModel> arrayListGpsData = new ArrayList<>();
                arrayListGpsData = gpsDataPpcDAO.getAll();
                List<GeoPoint> pointsList = new ArrayList<>();
                int validAccuracy = 0;
                try
                {
                    validAccuracy = Integer.parseInt(txtAccuracy.getText().toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(MapActivity.this , "invalid accuray" , Toast.LENGTH_SHORT).show();

                }

                for (GPSDataModel gpsDataModel : arrayListGpsData)
                {
                    if (gpsDataModel.getAccurancy() <= validAccuracy)
                    {
                        GeoPoint geoPoint = new GeoPoint(gpsDataModel.getLatitude() , gpsDataModel.getLongitude());
                        pointsList.add(geoPoint);

                        String title = "id : " + gpsDataModel.getCcGpsData_PPC() + "\n" + "lat : " + gpsDataModel.getLatitude() + "\n"
                                + "lng : " + gpsDataModel.getLongitude() + "\n" + "alt : " + gpsDataModel.getAltitude() + "\n"
                                + "speed : " + gpsDataModel.getSpeed() + "\n" + "accuracy : " + gpsDataModel.getAccurancy();

                        Marker marker = new Marker(map);
                        marker.setPosition(geoPoint);
                        marker.setTitle(title);
                        map.getOverlays().add(marker);
                    }
                }

                Polyline line = new Polyline();
                line.setPoints(pointsList);
                line.setGeodesic(true);
                map.getOverlayManager().add(line);


                lblAllDataCount.setText(String.format("data count : %1$s" , arrayListGpsData.size()));
                lblFilterDataCount.setText(String.format("filtered data count : %1$s" , pointsList.size()));

            }
        });


    }


    public void onResume(){
        super.onResume();
        map.onResume();
    }

    public void onPause(){
        super.onPause();
        map.onPause();
    }


}
