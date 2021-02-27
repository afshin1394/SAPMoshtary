package com.saphamrah.MVP.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.github.clans.fab.FloatingActionButton;
import com.saphamrah.BaseMVP.BanksInfoMVP;
import com.saphamrah.BuildConfig;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.BanksInfoPresenter;
import com.saphamrah.Model.BankLocation;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.text.DecimalFormat;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class BanksInfoActivity extends AppCompatActivity implements BanksInfoMVP.RequiredViewOps
{

    private BanksInfoMVP.PresenterOps mPresenter;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(getSupportFragmentManager() , TAG , BanksInfoActivity.this);

    private EditText edttxtBanks;
    private MapView map;
    private ArrayList<BankModel> bankModels;
    private ArrayList<String> bankTitles;
    private CustomSpinner customSpinner;
    private CustomAlertDialog customAlertDialog;

    private MapController mapController;
    private Marker myLocationMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banks_info);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imgviewBack = findViewById(R.id.imgBack);
        edttxtBanks = findViewById(R.id.txtSelectBank);
        map = findViewById(R.id.map);
        FloatingActionButton fabMyLocation = findViewById(R.id.fabMyLocation);

        double[] location = getCurrentLocation();
        Configuration.getInstance().load(BanksInfoActivity.this, PreferenceManager.getDefaultSharedPreferences(BanksInfoActivity.this));
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        mapController = new MapController(map);
        mapController.setCenter(new GeoPoint(location[0] , location[1]));
        mapController.setZoom(12.0);
        showCurrentLocation();

        startMVPOps();

        customSpinner = new CustomSpinner();
        customAlertDialog = new CustomAlertDialog(BanksInfoActivity.this);
        bankModels = new ArrayList<>();
        bankTitles = new ArrayList<>();
        mPresenter.getListOfAllBanks();

        edttxtBanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (bankTitles == null || bankTitles.size() == 0)
                {
                    mPresenter.getListOfAllBanks();
                }
                else
                {
                    showSpinner(bankTitles);
                }
            }
        });

        edttxtBanks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtBanks , hasFocus);
                if (hasFocus)
                {
                    if (bankTitles == null || bankTitles.size() == 0)
                    {
                        mPresenter.getListOfAllBanks();
                    }
                    else
                    {
                        showSpinner(bankTitles);
                    }
                }
            }
        });


        fabMyLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showCurrentLocation();
            }
        });


        imgviewBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BanksInfoActivity.this.finish();
            }
        });


    }

    @Override
    public Context getAppContext()
    {
        return BanksInfoActivity.this;
    }

    @Override
    public void showBanks(final ArrayList<BankModel> bankModels)
    {
        this.bankModels = bankModels;
        for (BankModel bank : bankModels)
        {
            bankTitles.add(bank.getNameBank());
        }
        showSpinner(bankTitles);
    }

    @Override
    public void showBranchOfBank(final ArrayList<BankLocation> bankLocations)
    {
        try
        {
            final DecimalFormat formatter = new DecimalFormat("#,###,###");
            ArrayList<OverlayItem> items = new ArrayList<>();
            for (int i=0; i<bankLocations.size(); i++)
            {
                items.add(new OverlayItem(bankLocations.get(i).getAddress(), "", new GeoPoint(Double.parseDouble(bankLocations.get(i).getLatBank()), Double.parseDouble(bankLocations.get(i).getLngBank())))); // Lat/Lon decimal degrees
            }

            //the overlay
            ItemizedIconOverlay<OverlayItem> itemItemizedIconOverlay = new ItemizedIconOverlay<>(items, getResources().getDrawable(R.drawable.ic_customer_location), new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                @Override
                public boolean onItemSingleTapUp(int index, OverlayItem item)
                {
                    InfoWindow.closeAllInfoWindowsOn(map);
                    String text = String.format("%1$s %2$s \n %3$s \n %4$s \n %5$s : %6$s %7$s", getString(R.string.bank), bankLocations.get(index).getNameBank(),
                            bankLocations.get(index).getAddress(), bankLocations.get(index).getPhone(), getString(R.string.distance), formatter.format(bankLocations.get(index).getDistance()), getString(R.string.meter));
                    customAlertDialog.showMessageAlert(BanksInfoActivity.this, false, "", text, Constants.NONE_MESSAGE(), getResources().getString(R.string.apply));
                    return false;
                }

                @Override
                public boolean onItemLongPress(int index, OverlayItem item) {
                    return false;
                }
            }, BanksInfoActivity.this);
            map.getOverlays().add(itemItemizedIconOverlay);
            map.invalidate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "", TAG, "showBranchOfBank", "");
        }
    }

    @Override
    public void showError(int resId)
    {
        customAlertDialog.showMessageAlert(BanksInfoActivity.this, false, "", getString(resId), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    private void showSpinner(final ArrayList<String> bankTitles)
    {
        customSpinner.showSpinner(BanksInfoActivity.this, bankTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                map.getOverlays().clear();
                showCurrentLocation();
                edttxtBanks.setText(bankModels.get(selectedIndex).getNameBank());
                double[] location = getCurrentLocation();
                mPresenter.getBranchOfBank(String.valueOf(bankModels.get(selectedIndex).getCcBank()), String.valueOf(location[0]), String.valueOf(location[1]));
            }
            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    /**
     * دریافت موقعیت مکانی
     * @return double[] -> double[0]=Latitude , double[1]=Longitude
     */
    private double[] getCurrentLocation()
    {
        PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider();
        return new double[]{locationProvider.getLatitude() , locationProvider.getLongitude()};
    }

    private void showCurrentLocation()
    {
        try
        {
            if (myLocationMarker != null)
            {
                map.getOverlays().remove(myLocationMarker);
            }
            double[] location = getCurrentLocation();
            GeoPoint myLocation = new GeoPoint(location[0], location[1]);
            myLocationMarker = new Marker(map);
            myLocationMarker.setPosition(myLocation);
            myLocationMarker.setTitle(getResources().getString(R.string.yourLocation));
            myLocationMarker.setIcon(getResources().getDrawable(R.drawable.ic_user_marker));
            myLocationMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener()
            {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView)
                {
                    return false;
                }
            });
            mapController.setCenter(myLocation);
            map.getOverlays().add(myLocationMarker);
            map.invalidate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void changeDrawableLeftTint(EditText editText , boolean hasFocus)
    {
        if (hasFocus)
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(BanksInfoActivity.this, R.drawable.ic_arrow_down);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorTextPrimary));
                editText.setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable , null , null , null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
        else
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(BanksInfoActivity.this, R.drawable.ic_arrow_down);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorTextPrimary));
                editText.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_arrow_down) , null , null , null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
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
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "startMVPOps", "");
        }
    }

    private void initialize(BanksInfoMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new BanksInfoPresenter(view);
            stateMaintainer.put(BanksInfoMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "initialize", "");
        }
    }

    private void reinitialize(BanksInfoMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(BanksInfoMVP.PresenterOps.class.getSimpleName());
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
