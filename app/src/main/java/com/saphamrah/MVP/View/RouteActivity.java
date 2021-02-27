package com.saphamrah.MVP.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;
import com.saphamrah.Adapter.RoutingDirectionAdapter;
import com.saphamrah.BaseMVP.RouteMVP;
import com.saphamrah.BuildConfig;
import com.saphamrah.MVP.Presenter.RoutePresenter;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.RoutingUtils;
import com.saphamrah.Utils.StateMaintainer;
import com.saphamrah.Valhalla.Maneuver;
import com.saphamrah.Valhalla.OptimizedRouteResult;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RouteActivity extends AppCompatActivity implements RouteMVP.RequiredViewOps, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
{

    RouteMVP.PresenterOps mPresenter;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(getSupportFragmentManager() , TAG , RouteActivity.this);

    private OptimizedRouteResult result;
    private ArrayList<LatLng> latLngPointOfPolyline;
    private CustomAlertDialog customAlertDialog;
    private RoutingDirectionAdapter adapter;
    private Marker currentMarker;
    private GoogleApiClient googleApiClient;
    private static final int INTERVAL = 1000;
    private static final int FASTEST_INTERVAL = 1000;
    private int currentManeuver = 0;
	private boolean autoRotateAndCurrentLocation;											 

    private MapController mapController;
    private MapView map;
    private RecyclerView recyclerView;
    private CardView cardViewRoutingDirection;
    private TextView lblCurrentSpeed;
    private TextView lblCurrentDirection;
    private ImageView imgCurrentDirection;
	private ImageView imgCompass;							 


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startMVPOps();

        customAlertDialog = new CustomAlertDialog(RouteActivity.this);
        final SlidingUpPanelLayout slidingUpPanelLayoutBottom = findViewById(R.id.sliding_layout_bottom);
        recyclerView = findViewById(R.id.recyclerViewDirections);
        cardViewRoutingDirection = findViewById(R.id.cardviewRoutingDirection);
        lblCurrentDirection = findViewById(R.id.lblRoutingInstruction);
        lblCurrentSpeed = findViewById(R.id.lblcurrentSpeed);
        imgCurrentDirection = findViewById(R.id.imgRoutingDirection);
        map = findViewById(R.id.map);
        Configuration.getInstance().load(RouteActivity.this, PreferenceManager.getDefaultSharedPreferences(RouteActivity.this));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        mapController = new MapController(map);
        mapController.setZoom(20.0);
        currentMarker = new Marker(map);
        currentMarker.setIcon(getResources().getDrawable(R.drawable.ic_user_marker));
        currentMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView)
            {
                return false;
            }
        });
        lblCurrentDirection.setGravity(Gravity.CENTER);
        imgCompass = findViewById(R.id.imgCompass);
        final FloatingActionButton fabMyLocation = findViewById(R.id.fabMyLocation);
        final FloatingActionButton fabRoute = findViewById(R.id.fabRoute);
        fabMyLocation.setVisibility(View.GONE);
        autoRotateAndCurrentLocation = true;															

        googleApiClient = new GoogleApiClient.Builder(this).addOnConnectionFailedListener(this).addConnectionCallbacks(this).addApi(LocationServices.API).build();
        googleApiClient.connect();

        Intent getIntent = getIntent();
        String routingResponse = getIntent.getStringExtra("routing");
        
		onGetRoute(routingResponse);

		showStartDirection();

        mPresenter.getTripInfo(result.getId(), (int) result.getTrip().getSummary().getTime(), result.getTrip().getSummary().getLength());

        adapter = new RoutingDirectionAdapter(RouteActivity.this, result.getTrip().getLegs().get(0).getManeuvers());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RouteActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        slidingUpPanelLayoutBottom.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener()
        {
            @Override
            public void onPanelSlide(View panel, float slideOffset)
            {}

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState)
            {
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED)
                {
                    showCurrentDirection();
                }
                else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED)
                {
                    showAllDirection();
                }
            }
        });

        slidingUpPanelLayoutBottom.setFadeOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                slidingUpPanelLayoutBottom.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });


        map.getOverlays().add(new MapEventsOverlay(new MapEventsReceiver()
        {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p)
            {
                autoRotateAndCurrentLocation = false;
                fabMyLocation.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p)
            {
                return false;
            }
        }));

        map.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                autoRotateAndCurrentLocation = false;
                fabMyLocation.setVisibility(View.VISIBLE);
                imgCompass.setRotation(map.getMapOrientation());
                return false;
            }
        });


        fabMyLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                autoRotateAndCurrentLocation = true;
                rotateToNextCheckPoint();
                mapController.setCenter(currentMarker.getPosition());
                mapController.setZoom(20.0);
                fabMyLocation.setVisibility(View.GONE);
            }
        });


        fabRoute.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Route" , "click button");
                route();
            }
        });


        imgCompass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                map.setMapOrientation(0);
                imgCompass.setRotation(0);
            }
        });
    }

	@SuppressLint("MissingPermission")
    private void route()
    {
        Log.d("Route" , "in route method");
        LocationServices.getFusedLocationProviderClient(RouteActivity.this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>()
        {
            @Override
            public void onSuccess(Location location)
            {
                if (location == null)
                {
                    Log.d("Route" , "current location is null");
                    customAlertDialog.showToast(RouteActivity.this, getString(R.string.errorGetCurrentLocation), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
                else
                {
                    com.saphamrah.Valhalla.Location startLocation = new com.saphamrah.Valhalla.Location();
                    startLocation.setLat(location.getLatitude());
                    startLocation.setLon(location.getLongitude());
                    Log.d("Route" , "current location : " + location.getLatitude() + " , " + location.getLongitude());
                    mPresenter.route(startLocation, result.getTrip().getLocations().get(1), result.getId());
                }
            }
        });
    }
	
    @Override
    public Context getAppContext()
    {
        return RouteActivity.this;
    }

    @Override
    public void showTripTime(int hour, int min, String timeInDistance)
    {
        TextView lblTimeInDestination = findViewById(R.id.lblTimeInDestination);
        TextView lblTripTime = findViewById(R.id.lblTripTime);

        String tripTime = (hour == 0) ? String.format("%1$s %2$s", min, getString(R.string.min)) : String.format("%1$s %2$s %3$s %4$s %5$s", hour, getString(R.string.hour), getString(R.string.and), min, getString(R.string.min));

        lblTimeInDestination.setText(timeInDistance);
        lblTripTime.setText(tripTime);
    }

    @Override
    public void showTripDistance(String distance, int resIdUnit)
    {
        TextView lblTripDistance = findViewById(R.id.lblTripDistance);
        lblTripDistance.setText(String.format("%1$s %2$s", distance, getString(resIdUnit)));
    }


    @Override
    public void showCustomerName(String customerName)
    {
        TextView lblCustomerName = findViewById(R.id.lblCustomerName);
        lblCustomerName.setText(customerName);
    }

    @Override
    public void hideCustomerName()
    {
        TextView lblCustomerName = findViewById(R.id.lblCustomerName);
        lblCustomerName.setText("");
    }

	@Override
    public void onGetRoute(String routeResult)
    {
        map.getOverlays().clear();
        Gson gson = new Gson();
        result = gson.fromJson(routeResult , OptimizedRouteResult.class);
        showSourceMarker(result.getTrip().getLocations().get(0).getLat() , result.getTrip().getLocations().get(0).getLon());
        showDestinationMarker(result.getTrip().getLocations().get(1).getLat() , result.getTrip().getLocations().get(1).getLon());
        drawPolyline(result.getTrip().getLegs().get(0).getShape());
        RotationGestureOverlay rotationGestureOverlay = new RotationGestureOverlay(map);
        rotationGestureOverlay.setEnabled(true);
        map.getOverlays().add(rotationGestureOverlay);
        mapController.setCenter(currentMarker.getPosition());
        mapController.setZoom(20.0);
        autoRotateAndCurrentLocation = true;
        currentManeuver = 0;
        rotateToNextCheckPoint();
    }
	
    @Override				 
    public void showToast(int resId, int messageType, int duration)
    {

    }

    private void showStartDirection()
    {
        cardViewRoutingDirection.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        String identifier = RoutingUtils.drawableIdentifier + result.getTrip().getLegs().get(0).getManeuvers().get(0).getType();
        imgCurrentDirection.setImageResource(getResources().getIdentifier(identifier, null, getPackageName()));
        lblCurrentDirection.setText(result.getTrip().getLegs().get(0).getManeuvers().get(0).getInstruction());
    }																												

    private void showCurrentDirection()
    {
        cardViewRoutingDirection.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void showAllDirection()
    {
        List<Maneuver> currentToEndManeuvers = result.getTrip().getLegs().get(0).getManeuvers().subList(currentManeuver , result.getTrip().getLegs().get(0).getManeuvers().size());
        adapter = new RoutingDirectionAdapter(RouteActivity.this, currentToEndManeuvers);
        recyclerView.setAdapter(adapter);
        cardViewRoutingDirection.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    private void showSourceMarker(double lat , double lng)
    {
        Marker marker = new Marker(map);
        marker.setPosition(new GeoPoint(lat , lng));
        marker.setIcon(getResources().getDrawable(R.drawable.ic_start_routing));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView)
            {
                return false;
            }
        });
        map.getOverlays().add(marker);
        map.invalidate();
    }


    private void showDestinationMarker(double lat , double lng)
    {
        Marker marker = new Marker(map);
        marker.setPosition(new GeoPoint(lat , lng));
        marker.setIcon(getResources().getDrawable(R.drawable.ic_end_routing));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView)
            {
                return false;
            }
        });
        map.getOverlays().add(marker);
        map.invalidate();
    }


    private void drawPolyline(String shape)
    {
        try
        {
            ArrayList<GeoPoint> pointOfPolyline = RoutingUtils.decode(shape , false);
            latLngPointOfPolyline = RoutingUtils.decodeToLatLng(shape , false);
            Polyline polyline = new Polyline();
            polyline.setPoints(pointOfPolyline);
            polyline.setColor(getResources().getColor(R.color.colorPolyline));
            polyline.setWidth(15.0F);
            map.getOverlays().add(polyline);
            map.invalidate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            customAlertDialog.showToast(RouteActivity.this, getString(R.string.errorDrawPolyline), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }			 
    }


    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        @SuppressLint("MissingPermission")
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location)
            {
                if (location != null)
                {
                    Log.d("route" , "onConnected : " + location.getLatitude() + " , " + location.getLongitude());
                    mapController.setCenter(new GeoPoint(location.getLatitude() , location.getLongitude()));
                    showCurrentLocationMarker(location);
                }
            }
        });
        startLocationUpdate();
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdate()
    {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        customAlertDialog.showMessageAlert(RouteActivity.this, false, "", getString(R.string.errorGetLocation), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }


    @Override
    public void onLocationChanged(Location location)
    {
        if (location != null)
        {
            Log.d("route" , "onUpdate : " + location.getLatitude() + " , " + location.getLongitude());
            showCurrentLocationMarker(location);
            lblCurrentSpeed.setText(String.format("%.2f", location.getSpeed()*(3.6)));
            LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
            findManeuverOfNewLocation(latLng);
            if (autoRotateAndCurrentLocation)
            {
                rotateToNextCheckPoint();
                mapController.setCenter(currentMarker.getPosition());
                mapController.setZoom(20.0);
            }
        }
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        googleApiClient.disconnect();
    }


    private void showCurrentLocationMarker(Location location)
    {
        map.getOverlays().remove(currentMarker);
        currentMarker.setPosition(new GeoPoint(location.getLatitude(), location.getLongitude()));
        map.getOverlays().add(currentMarker);
        map.invalidate();
    }


    private void findManeuverOfNewLocation(LatLng location)
    {
        try
        {
            //Log.d("route" , "start finding maneuver : " + System.currentTimeMillis());
            int maneuverSize = result.getTrip().getLegs().get(0).getManeuvers().size();
            for (int i=0 ; i<maneuverSize; i++)
            {
                Maneuver maneuver = result.getTrip().getLegs().get(0).getManeuvers().get(i);
                //Log.d("route" , "maneuver : " + maneuver.toString());
                List<LatLng> latLngs = latLngPointOfPolyline.subList(maneuver.getBeginShapeIndex() , maneuver.getEndShapeIndex()+1);
                if (PolyUtil.isLocationOnPath(location, latLngs, false, 2D))
                {
                    currentManeuver = i;
                    Location sourceLocation = new Location("source");
                    sourceLocation.setLatitude(latLngs.get(0).latitude);
                    sourceLocation.setLongitude(latLngs.get(0).longitude);

                    Location destinationLocation = new Location("destination");
                    destinationLocation.setLatitude(latLngs.get(latLngs.size()-1).latitude);
                    destinationLocation.setLongitude(latLngs.get(latLngs.size()-1).longitude);

                    Location currentLocation = new Location("current");
                    currentLocation.setLatitude(location.latitude);
                    currentLocation.setLongitude(location.longitude);

                    //float sourceToCurrent = sourceLocation.distanceTo(currentLocation);
                    float currentToDestination = currentLocation.distanceTo(destinationLocation);
                    float sourceToDestination = sourceLocation.distanceTo(destinationLocation);

                    float percentToDestination = (currentToDestination * 100) / sourceToDestination;

                    /*Log.d("route" , "currentToDestination : " + currentToDestination);
                    Log.d("route" , "sourceToDestination : " + sourceToDestination);
                    Log.d("route" , "percentToDestination : " + percentToDestination);*/

                    if (percentToDestination < 10)
                    {
						String identifier = RoutingUtils.drawableIdentifier + result.getTrip().getLegs().get(0).getManeuvers().get(i+1).getType();
                        imgCurrentDirection.setImageResource(getResources().getIdentifier(identifier, null, getPackageName()));												
                        lblCurrentDirection.setText(result.getTrip().getLegs().get(0).getManeuvers().get(i+1).getVerbalPreTransitionInstruction());
                    }
                    else
                    {
                        if (maneuver.getVerbalPostTransitionInstruction() != null)
                        {
							imgCurrentDirection.setImageDrawable(null);																													   
                            lblCurrentDirection.setText(maneuver.getVerbalPostTransitionInstruction());
                        }
                        else
                        {
							String identifier = RoutingUtils.drawableIdentifier + maneuver.getType();
                            imgCurrentDirection.setImageResource(getResources().getIdentifier(identifier, null, getPackageName()));									   
                            lblCurrentDirection.setText(maneuver.getVerbalPreTransitionInstruction());
                        }
                    }
                    break;
                }
            }
            //Log.d("route" , "end finding maneuver : " + System.currentTimeMillis());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "", "RouteActivity", "findManeuverOfNewLocation", "");
        }
    }

    public void rotateToNextCheckPoint()
    {
        LatLng nextPoint = latLngPointOfPolyline.get(result.getTrip().getLegs().get(0).getManeuvers().get(currentManeuver).getEndShapeIndex());
        LatLng currPoint = latLngPointOfPolyline.get(result.getTrip().getLegs().get(0).getManeuvers().get(currentManeuver).getBeginShapeIndex());

        double lat1 = Math.toRadians(currPoint.latitude);
        double lon1 = Math.toRadians(currPoint.longitude);
        double lat2 = Math.toRadians(nextPoint.latitude);
        double lon2 = Math.toRadians(nextPoint.longitude);

        double cos1 = Math.cos(lat1);
        double cos2 = Math.cos(lat2);
        double sin1 = Math.sin(lat1);
        double sin2 = Math.sin(lat2);

        double delta = lon2 - lon1;
        double deltaCos = Math.cos(delta);
        double deltaSin = Math.sin(delta);

        double x = (cos1 * sin2) - (sin1 * cos2 * deltaCos);
        double y = deltaSin * cos2;
        double z = Math.toDegrees(Math.atan((-y / x)));
        if (x < 0)
        {
            z += 180;
        }
        double z2 = (z + 180) % 360 - 180;
        z2 = -Math.toRadians(z2);

        double angleRad = z2 - (Math.PI * 2 * Math.floor(z2 / (2 * Math.PI)));
        double angle = Math.toDegrees(angleRad);
        map.setMapOrientation(-(float) angle);
        imgCompass.setRotation(-(float) angle);
    }

	
    private void startMVPOps()
    {
        try
        {
            if ( stateMaintainer.firstTimeIn() )
            {
                initialize(this);
            }
            else
            {
                reinitialize(this);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "startMVPOps", "");
        }
    }

    private void initialize(RouteMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RoutePresenter(view);
            stateMaintainer.put(RouteMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "initialize", "");
        }
    }

    private void reinitialize(RouteMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RouteMVP.PresenterOps.class.getSimpleName());
            if ( mPresenter == null )
            {
                initialize( view );
            }
            else
            {
                mPresenter.onConfigurationChanged(view);
            }
        }
        catch (Exception exception)
        {
            if (mPresenter != null)
            {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "reinitialize", "");
            }
        }
    }



}
