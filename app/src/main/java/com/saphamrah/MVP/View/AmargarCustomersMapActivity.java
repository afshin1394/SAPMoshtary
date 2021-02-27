package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.AmargarCustomersMapMVP;
import com.saphamrah.BuildConfig;
import com.saphamrah.MVP.Presenter.AmargarCustomersMapPresenter;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;
import com.saphamrah.Valhalla.Location;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class AmargarCustomersMapActivity extends AppCompatActivity implements AmargarCustomersMapMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , this);
    private AmargarCustomersMapMVP.PresenterOps mPresenter;

    private MapView map = null;
    private CustomAlertDialog customAlertDialog;
    private Location currentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amargar_customers_map);


        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        map = findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        currentLocation = new Location();
        customAlertDialog = new CustomAlertDialog(this);

        startMVPOps();


        PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();
        IMapController mapController = new MapController(map);
        mapController.setCenter(new GeoPoint(googleLocationProvider.getLatitude() , googleLocationProvider.getLongitude()));
        mapController.setZoom(18.0);

        mPresenter.getCustomers();
    }


    @Override
    public Context getAppContext()
    {
        return this;
    }

    @Override
    public void showCustomers(final List<ListMoshtarianModel> listMoshtarianModels)
    {
        ArrayList<OverlayItem> items = new ArrayList<>();
        for (int i=0; i<listMoshtarianModels.size(); i++)
        {
            items.add(new OverlayItem(listMoshtarianModels.get(i).getNameMoshtary(), "", new GeoPoint(listMoshtarianModels.get(i).getLatitudeY(), listMoshtarianModels.get(i).getLongitudeX()))); // Lat/Lon decimal degrees
        }

        //the overlay
        ItemizedIconOverlay<OverlayItem> itemItemizedIconOverlay = new ItemizedIconOverlay<>(items, getResources().getDrawable(R.drawable.ic_customer_location), new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item)
            {
                String text = String.format("%1$s - %2$s \n %3$s", listMoshtarianModels.get(index).getCodeMoshtaryOld(),
                        listMoshtarianModels.get(index).getNameMoshtary(),
                        listMoshtarianModels.get(index).getAddress());
                customAlertDialog.showMessageAlert(AmargarCustomersMapActivity.this, false, "", text, Constants.NONE_MESSAGE(), getResources().getString(R.string.apply));
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        }, this);
        map.getOverlays().add(itemItemizedIconOverlay);

        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay( new GpsMyLocationProvider(BaseApplication.getContext()) , map);
        mLocationOverlay.enableMyLocation();
        map.getOverlays().add(mLocationOverlay);
    }

    @Override
    public void showErrorNotFoundCustomer()
    {
        customAlertDialog.showMessageAlert(this, true, "", getString(R.string.errorNotFoundMoshtary), Constants.FAILED_MESSAGE(), getString(R.string.apply));
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
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "startMVPOps", "");
        }
    }

    private void initialize(AmargarCustomersMapMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new AmargarCustomersMapPresenter(view);
            stateMaintainer.put(AmargarCustomersMapMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "initialize", "");
        }
    }

    private void reinitialize(AmargarCustomersMapMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(AmargarCustomersMapMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "reinitialize", "");
            }
        }
    }


}
