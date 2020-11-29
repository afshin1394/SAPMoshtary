package com.saphamrah.MVP.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.BaseMVP.ForoshandehRouteMapMVP;
import com.saphamrah.BuildConfig;
import com.saphamrah.MVP.Presenter.ForoshandehRouteMapPresenter;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerAddressModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class ForoshandehRouteMapActivity extends AppCompatActivity implements ForoshandehRouteMapMVP.RequiredViewOps
{

    private MapView map = null;
    private Polyline polyline;
    private FloatingActionMenu fabMenu;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , ForoshandehRouteMapActivity.this);
    private ForoshandehRouteMapMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;
    private final int LOCATION_PERMISSION = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foroshandeh_route_map);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        customAlertDialog = new CustomAlertDialog(ForoshandehRouteMapActivity.this);
        customLoadingDialog = new CustomLoadingDialog();

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);
        map = findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        polyline = new Polyline();
        fabMenu.setVisibility(View.GONE);

        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay( new GpsMyLocationProvider(this) , map);
        mLocationOverlay.enableMyLocation();
        map.getOverlays().add(mLocationOverlay);

        startMVPOps();

        if (Build.VERSION.SDK_INT >= 23)
        {
            ArrayList<String> permissions = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(ForoshandehRouteMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(ForoshandehRouteMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (permissions.size() > 0)
            {
                ActivityCompat.requestPermissions(ForoshandehRouteMapActivity.this, permissions.toArray(new String[permissions.size()]), LOCATION_PERMISSION);
            }
            else
            {
                showData();
            }
        }
        else
        {
            showData();
        }


        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                showLoading();
                mPresenter.updateGPSData();
            }
        });


    }

    private void showData()
    {
        PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider(ForoshandehRouteMapActivity.this);
        IMapController mapController = new MapController(map);
        Log.d("location" , "map lat : " + googleLocationProvider.getLatitude() + " long : " + googleLocationProvider.getLongitude());
        mapController.setCenter(new GeoPoint(googleLocationProvider.getLatitude() , googleLocationProvider.getLongitude()));
        mapController.setZoom(19.0);

        mPresenter.getCustomerInfo();
        mPresenter.getGpsDatas();

        fabMenu.setVisibility(View.VISIBLE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                showData();
            }
            else
            {
                customAlertDialog.showMessageAlert(ForoshandehRouteMapActivity.this, true, "", getResources().getString(R.string.errorAccessToLocation), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
            }
        }
    }


    @Override
    public Context getAppContext()
    {
        return ForoshandehRouteMapActivity.this;
    }

    @Override
    public void onGetCustomerInfo(final ArrayList<CustomerAddressModel> customerAddressModels)
    {
        ArrayList<OverlayItem> items = new ArrayList<>();
        for (int i=0; i<customerAddressModels.size(); i++)
        {
            items.add(new OverlayItem(customerAddressModels.get(i).getMoshtaryModel().getNameMoshtary(), "", new GeoPoint(customerAddressModels.get(i).getMoshtaryAddressModels().get(0).getLatitude_y(), customerAddressModels.get(i).getMoshtaryAddressModels().get(0).getLongitude_x()))); // Lat/Lon decimal degrees
        }

        //the overlay
        ItemizedIconOverlay<OverlayItem> itemItemizedIconOverlay = new ItemizedIconOverlay<>(items, getResources().getDrawable(R.drawable.ic_customer_location), new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item)
            {
                String text = String.format("%1$s - %2$s \n %3$s : %4$s \n %5$s", customerAddressModels.get(index).getMoshtaryModel().getCodeMoshtary(),
                        customerAddressModels.get(index).getMoshtaryModel().getNameMoshtary(), getResources().getString(R.string.olaviatMasir),
                        customerAddressModels.get(index).getMoshtaryModel().getOlaviat(), customerAddressModels.get(index).getMoshtaryAddressModels().get(0).getAddress());
                customAlertDialog.showMessageAlert(ForoshandehRouteMapActivity.this, false, "", text, Constants.NONE_MESSAGE(), getResources().getString(R.string.apply));
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        }, ForoshandehRouteMapActivity.this);
        map.getOverlays().add(itemItemizedIconOverlay);
    }

    @Override
    public void onGetGpsDatas(ArrayList<GeoPoint> geoPoints)
    {
        MyLocationNewOverlay myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ForoshandehRouteMapActivity.this),map);
        myLocationNewOverlay.enableMyLocation();
        map.getOverlays().add(myLocationNewOverlay);

        Marker startMarker = new Marker(map);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_start_location));
        startMarker.setPosition(geoPoints.get(0));
        map.getOverlays().add(startMarker);

        try
        {
            map.getOverlays().remove(polyline);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "", "ForoshandehRouteMapActivity", "onGetGpsDatas", "");
        }
        polyline = new Polyline();
        polyline.setPoints(geoPoints);
        map.getOverlayManager().add(polyline);
        map.invalidate();
    }


    private void showLoading()
    {
        alertDialogLoading = customLoadingDialog.showLoadingDialog(ForoshandehRouteMapActivity.this);
    }

    @Override
    public void closeLoading()
    {
        if (alertDialogLoading != null)
        {
            try
            {
                alertDialogLoading.dismiss();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "ForoshandehRouteMapActivity", "closeLoadingDialog", "");
            }
        }
        else
        {
            Log.d("routemap", "alert dialog was null");
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(ForoshandehRouteMapActivity.this, getResources().getString(resId), messageType, duration);
    }

    public void startMVPOps()
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "ForoshandehRouteMapActivity", "startMVPOps", "");
        }
    }


    private void initialize(ForoshandehRouteMapMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new ForoshandehRouteMapPresenter(view);
            stateMaintainer.put(ForoshandehRouteMapMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "ForoshandehRouteMapActivity", "initialize", "");
        }
    }


    private void reinitialize(ForoshandehRouteMapMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(ForoshandehRouteMapMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "ForoshandehRouteMapActivity", "reinitialize", "");
            }
        }
    }





}
