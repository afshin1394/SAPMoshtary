package com.saphamrah.MVP.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.BaseMVP.RptCustomersListByDistanceMVP;
import com.saphamrah.BuildConfig;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.RptCustomersListByDistancePresenter;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
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
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RptCustomersListByDistanceActivity extends AppCompatActivity implements RptCustomersListByDistanceMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptCustomersListByDistanceActivity.this);
    private RptCustomersListByDistanceMVP.PresenterOps mPresenter;
    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;
    private ArrayList<String> arrayListRadiusItems;
    private ArrayList<String> arrayListRadiusItemsTitle;
    private MapView map;
    private GeoPoint currentLocation;
    private Polygon circle;
    private Marker markerCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_customers_list_by_distance);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabShowSelectRadius);
        map = findViewById(R.id.mapView);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        customAlertDialog = new CustomAlertDialog(RptCustomersListByDistanceActivity.this);
        customLoadingDialog = new CustomLoadingDialog();
        arrayListRadiusItems = new ArrayList<>();
        arrayListRadiusItemsTitle = new ArrayList<>();

        circle = new Polygon(map);
        markerCurrentLocation = new Marker(map);

        PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider();
        currentLocation = new GeoPoint(locationProvider.getLatitude() , locationProvider.getLongitude());


        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        IMapController mapController = new MapController(map);
        mapController.setCenter(currentLocation);
        mapController.setZoom(19.0);


        startMVPOps();

        mPresenter.getRadiusConfig();

        fabRefresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                showSpinnerSelectRadius();
            }
        });



    }


    @Override
    public Context getAppContext()
    {
        return RptCustomersListByDistanceActivity.this;
    }

    @Override
    public void onGetRadiusConfig(ArrayList<String> arrayListRadiusItems)
    {
        this.arrayListRadiusItems.clear();
        this.arrayListRadiusItems.addAll(arrayListRadiusItems);
        arrayListRadiusItemsTitle.clear();
        for (String item : arrayListRadiusItems)
        {
            arrayListRadiusItemsTitle.add(String.format("%1$s %2$s %3$s", getResources().getString(R.string.until), item, getResources().getString(R.string.meter)));
        }
        showSpinnerSelectRadius();
    }

    @Override
    public void onFailedGetConfig()
    {
        customAlertDialog.showMessageAlert(RptCustomersListByDistanceActivity.this, true, "", getResources().getString(R.string.errorGetData), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
    }

    @Override
    public void onGetCustomerList(final ArrayList<ListMoshtarianModel> customerList , String radius)
    {
        map.getOverlays().clear();

        showCurrentLocationMarker();
        showCircle(radius);

        ArrayList<OverlayItem> items = new ArrayList<>();
        for (int i=0; i<customerList.size(); i++)
        {
            items.add(new OverlayItem(customerList.get(i).getNameMoshtary(), "", new GeoPoint(customerList.get(i).getLatitudeY(), customerList.get(i).getLongitudeX()))); // Lat/Lon decimal degrees
        }

        //the overlay
        ItemizedIconOverlay<OverlayItem> itemItemizedIconOverlay = new ItemizedIconOverlay<>(items, getResources().getDrawable(R.drawable.ic_customer_location), new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item)
            {
                Log.d("customer" , "index : " + index);
                InfoWindow.closeAllInfoWindowsOn(map);
                String text = String.format("%1$s - %2$s \n %3$s : %4$s \n %5$s : %6$s \n %7$s : %8$s \n %9$s : %10$s",
                        customerList.get(index).getCodeMoshtaryOld(), customerList.get(index).getNameMoshtary(),
                        getResources().getString(R.string.tabloName), customerList.get(index).getNameTablo(),
                        getResources().getString(R.string.customerType), customerList.get(index).getNameNoeMoshtary(),
                        getResources().getString(R.string.address), customerList.get(index).getAddress(),
                        getResources().getString(R.string.telephone), customerList.get(index).getTelephone());
                customAlertDialog.showMessageAlert(RptCustomersListByDistanceActivity.this, false, "", text, Constants.NONE_MESSAGE(), getResources().getString(R.string.apply));
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        }, RptCustomersListByDistanceActivity.this);
        map.getOverlays().add(itemItemizedIconOverlay);
        map.invalidate();

        closeLoadingAlert();
    }

    @Override
    public void closeLoadingAlert()
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptCustomersListByDistanceActivity", "closeLoadingDialog", "");
            }
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RptCustomersListByDistanceActivity.this, getResources().getString(resId), messageType, duration);
    }


    private void showCurrentLocationMarker()
    {
        map.getOverlays().remove(markerCurrentLocation);
        markerCurrentLocation.setPosition(currentLocation);
        markerCurrentLocation.setIcon(getResources().getDrawable(R.drawable.ic_user_marker));
        markerCurrentLocation.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(markerCurrentLocation);
    }

    private void showCircle(String radius)
    {
        map.getOverlays().remove(circle);
        circle = new Polygon(map);
        circle.setPoints(Polygon.pointsAsCircle(currentLocation , Double.parseDouble(radius)*1000));
        circle.setFillColor(Color.parseColor("#302196F3"));
        circle.setStrokeColor(Color.BLUE);
        circle.setStrokeWidth(2);
        map.getOverlays().add(circle);
    }

    private void showSpinnerSelectRadius()
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(RptCustomersListByDistanceActivity.this, arrayListRadiusItemsTitle, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                String selectedItem = arrayListRadiusItems.get(selectedIndex);
                alertDialogLoading = customLoadingDialog.showLoadingDialog(RptCustomersListByDistanceActivity.this);
                mPresenter.getCustomerList(selectedItem , String.valueOf(currentLocation.getLatitude()), String.valueOf(currentLocation.getLongitude()));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptCustomersListByDistanceActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptCustomersListByDistanceMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RptCustomersListByDistancePresenter(view);
            stateMaintainer.put(RptCustomersListByDistanceMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptCustomersListByDistanceActivity", "initialize", "");
        }
    }


    private void reinitialize(RptCustomersListByDistanceMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptCustomersListByDistanceMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptCustomersListByDistanceActivity", "reinitialize", "");
            }
        }
    }


}
