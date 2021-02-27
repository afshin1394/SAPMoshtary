package com.saphamrah.MVP.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.saphamrah.Adapter.GetProgramItemsStatusAdapter;
import com.saphamrah.BaseMVP.CustomerListMapMVP;
import com.saphamrah.BuildConfig;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.CustomerListMapPresenter;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;

import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import it.sephiroth.android.library.viewrevealanimator.ViewRevealAnimator;
import me.anwarshahriar.calligrapher.Calligrapher;

public class CustomersListMapActivity extends AppCompatActivity implements CustomerListMapMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private CustomerListMapMVP.PresenterOps mPresenter;
    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;
    private ArrayList<String> arrayListRadiusItems;
    private ArrayList<String> arrayListRadiusItemsTitle;
    private MapView map;
    private GeoPoint currentLocation;
    private Polygon circle;
    private Marker markerCurrentLocation;
    private BottomSheetBehavior bottomSheetBehavior;



    private List<Integer> getCustomerInfoItemsStatus;
    private ViewRevealAnimator viewRevealAnimator;
    private RecyclerView recyclerViewGetProgramItems;
    private ProgressBar progressBar;
    private TextView lblPassedItemCounter;
    private TextView lblProgressPercentage;
    private ShineButton imgGetProgramResultFailed;
    private ShineButton imgGetProgramResultSuccess;
    private TextView lblGetProgramResult;
    private Button btnGetProgramResultClose;
    private GetProgramItemsStatusAdapter alertAdapter;
    private AlertDialog show;
    private int getCustomerInfoItemCount;
    private int selectedccMasir;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list_map);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabShowSelectRadius);
        map = findViewById(R.id.mapView);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        customAlertDialog = new CustomAlertDialog(CustomersListMapActivity.this);
        customLoadingDialog = new CustomLoadingDialog();
        arrayListRadiusItems = new ArrayList<>();
        arrayListRadiusItemsTitle = new ArrayList<>();

        circle = new Polygon(map);
        markerCurrentLocation = new Marker(map);
        markerCurrentLocation.setInfoWindow(null);

        PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider();
        currentLocation = new GeoPoint(locationProvider.getLatitude() , locationProvider.getLongitude());
        //BottomSheet
        LinearLayout lnrlayBottomsheet = findViewById(R.id.linBottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(lnrlayBottomsheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);



        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        IMapController mapController = new MapController(map);
        mapController.setCenter(currentLocation);
        mapController.setZoom(19.0);


        mPresenter = new CustomerListMapPresenter(this);

        getCustomerInfoItemCount = getResources().getStringArray(R.array.getCustomerInfo).length;
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
        return CustomersListMapActivity.this;
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
        customAlertDialog.showMessageAlert(CustomersListMapActivity.this, true, "", getResources().getString(R.string.errorGetData), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
    }

    @Override
    public void onGetCustomerList(final ArrayList<ListMoshtarianModel> customerList , String radius)
    {
        map.getOverlays().clear();

        showCurrentLocationMarker();
        showCircle(radius);

//        ArrayList<OverlayItem> items = new ArrayList<>();
        for (int i=0; i<customerList.size(); i++)
        {
           Marker marker=new Marker(map);
           marker.setId(String.valueOf(i));
           marker.setIcon(getResources().getDrawable(R.drawable.ic_customer_location));
           marker.setTitle(customerList.get(i).getNameMoshtary());
           marker.setPosition(new GeoPoint(customerList.get(i).getLatitudeY(), customerList.get(i).getLongitudeX()));
           marker.setSubDescription(customerList.get(i).getNameMoshtary());
           marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
               @Override
               public boolean onMarkerClick(Marker marker, MapView mapView) {
//                   InfoWindow.closeAllInfoWindowsOn(map);
                   showBottomSheet(customerList.get(Integer.valueOf(marker.getId())));

                   return false;
               }
           });
            map.getOverlays().add(marker);
            map.invalidate();
        }



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
        customAlertDialog.showToast(CustomersListMapActivity.this, getResources().getString(resId), messageType, duration);
    }

    @Override
    public void onSuccessfullyGetNewItemOfInfo(int itemIndex)
    {
        getCustomerInfoItemsStatus.set(itemIndex , 1);
        Log.i("alertAdapter", "onSuccessfullyGetNewItemOfInfo: "+alertAdapter.getItemCount());
        alertAdapter.notifyDataSetChanged();
        Log.i("alertAdapter", "onSuccessfullyGetNewItemOfInfo: "+alertAdapter.getItemCount());

        recyclerViewGetProgramItems.smoothScrollToPosition(itemIndex + 1);
        Log.i("alertAdapter", "onSuccessfullyGetNewItemOfInfo: "+alertAdapter.getItemCount());

        int percentage = (itemIndex*100)/getCustomerInfoItemCount;
        Log.i("alertAdapter", "onSuccessfullyGetNewItemOfInfo: "+alertAdapter.getItemCount());

        progressBar.setProgress(percentage);
        Log.i("alertAdapter", "onSuccessfullyGetNewItemOfInfo: "+alertAdapter.getItemCount());

        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , percentage));
        Log.i("alertAdapter", "onSuccessfullyGetNewItemOfInfo: "+alertAdapter.getItemCount());

        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , String.valueOf(itemIndex + 1) , String.valueOf(getCustomerInfoItemCount)));
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
        circle.setOnClickListener(null);
        circle.setInfoWindow(null);
        circle.setFillColor(Color.parseColor("#302196F3"));
        circle.setStrokeColor(Color.BLUE);
        circle.setStrokeWidth(2);
        map.getOverlays().add(circle);

    }

    private void showSpinnerSelectRadius()
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(CustomersListMapActivity.this, arrayListRadiusItemsTitle, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                String selectedItem = arrayListRadiusItems.get(selectedIndex);
                alertDialogLoading = customLoadingDialog.showLoadingDialog(CustomersListMapActivity.this);
                mPresenter.getCustomerList(selectedItem , String.valueOf(currentLocation.getLatitude()), String.valueOf(currentLocation.getLongitude()));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }
    public void showBottomSheet(ListMoshtarianModel listMoshtarianModel)
    {

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
        {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        TextView txtNameMoshtary = findViewById(R.id.txtNameMoshtary);
        TextView txtCodeMoshtary = findViewById(R.id.txtCodeMoshtary);
        TextView txtTablooMoshtary = findViewById(R.id.txtTabloMoshtary);
        TextView txtAddressMoshtary = findViewById(R.id.txtAddressMoshtary);
        TextView txtTelephonMoshtary = findViewById(R.id.txtTelephoneMoshtary);

        AppCompatButton buttonsAddToCurrentMoshtary = findViewById(R.id.btnRefreshCustomerInfo);


        txtNameMoshtary.setText(listMoshtarianModel.getNameMoshtary());
        txtCodeMoshtary.setText(listMoshtarianModel.getCodeMoshtaryOld());
        txtTablooMoshtary.setText(listMoshtarianModel.getNameTablo());
        txtAddressMoshtary.setText(listMoshtarianModel.getAddress());
        txtTelephonMoshtary.setText(listMoshtarianModel.getTelephone());

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        buttonsAddToCurrentMoshtary.setOnClickListener(view -> {
            getCustomerInfoItemsStatus=initializeItemsStatus();
            showItemStatusList();
            mPresenter.checkSelectedCustomerForGetInfo(listMoshtarianModel);
        });
    }


    @Override
    public void onFailedGetCustomerInfo(int itemIndex, String error)
    {

        getCustomerInfoItemsStatus.set(itemIndex , -1);
        alertAdapter.notifyDataSetChanged();
        imgGetProgramResultFailed.setVisibility(View.VISIBLE);
        imgGetProgramResultSuccess.setVisibility(View.GONE);
        viewRevealAnimator.showNext();
        lblGetProgramResult.setText(getResources().getString(R.string.getProgramError , getItemName(itemIndex)));
        btnGetProgramResultClose.setBackground(getResources().getDrawable(R.drawable.altdlg_btn_bg_failed));
        imgGetProgramResultFailed.postDelayed(() -> {
            imgGetProgramResultFailed.performClick();
            imgGetProgramResultFailed.setPressed(true);
            imgGetProgramResultFailed.invalidate();
        }, 800);
    }

    @Override
    public void onCompleteGetCustomerInfo()
    {
        recyclerViewGetProgramItems.smoothScrollToPosition(getCustomerInfoItemCount);
        progressBar.setProgress(100);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , 100));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , String.valueOf(getCustomerInfoItemCount) , String.valueOf(getCustomerInfoItemCount)));
        imgGetProgramResultFailed.setVisibility(View.GONE);
        imgGetProgramResultSuccess.setVisibility(View.VISIBLE);
        viewRevealAnimator.showNext();
        lblGetProgramResult.setText(getResources().getString(R.string.successfullyDoneOps));
        btnGetProgramResultClose.setBackground(getResources().getDrawable(R.drawable.altdlg_btn_bg_success));
        imgGetProgramResultSuccess.postDelayed(() -> {
            imgGetProgramResultSuccess.performClick();
            imgGetProgramResultSuccess.setPressed(true);
            imgGetProgramResultSuccess.invalidate();
        }, 800);
    }

    private String getItemName(int itemIndex)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(getResources().getStringArray(R.array.getCustomerInfo)[itemIndex]);
            return jsonObject.getString("name");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomersListActivity", "getItemName", "");
            return "";
        }
    }
    public void showItemStatusList()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CustomersListMapActivity.this);
        View myview = getLayoutInflater().inflate(R.layout.alert_recyclerlist_without_btn , null);
        Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        viewRevealAnimator = myview.findViewById(R.id.animator);
        recyclerViewGetProgramItems = (RecyclerView)myview.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) myview.findViewById(R.id.progress);
        lblPassedItemCounter = (TextView) myview.findViewById(R.id.lblItemCounter);
        lblProgressPercentage = (TextView) myview.findViewById(R.id.lblProgressPercentage);
        imgGetProgramResultFailed = (ShineButton) myview.findViewById(R.id.shineBtnGetProgramResultFailed);
        imgGetProgramResultSuccess = (ShineButton) myview.findViewById(R.id.shineBtnGetProgramResultSuccess);
        lblGetProgramResult = (TextView) myview.findViewById(R.id.lblStatus);
        btnGetProgramResultClose = (Button) myview.findViewById(R.id.btnApply);

        List<String> itemsTitle = Arrays.asList(getResources().getStringArray(R.array.getCustomerInfo));
        alertAdapter = new GetProgramItemsStatusAdapter(CustomersListMapActivity.this , getCustomerInfoItemsStatus , itemsTitle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CustomersListMapActivity.this);
        recyclerViewGetProgramItems.setLayoutManager(mLayoutManager);
        recyclerViewGetProgramItems.setItemAnimator(new DefaultItemAnimator());
        recyclerViewGetProgramItems.addItemDecoration(new DividerItemDecoration(CustomersListMapActivity.this, DividerItemDecoration.VERTICAL));
        recyclerViewGetProgramItems.setAdapter(alertAdapter);
        progressBar.setProgress(0);
        lblGetProgramResult.setTypeface(font);
        btnGetProgramResultClose.setTypeface(font);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , 0));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , "0" , String.valueOf(getCustomerInfoItemCount)));

        builder.setCancelable(false);
        builder.setView(myview);
        builder.create();

        btnGetProgramResultClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (show.getWindow() != null)
                {
                    try
                    {
                        show.dismiss();
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            }
        });

        if (!(CustomersListMapActivity.this).isFinishing())
        {
            show = builder.show();
            imgGetProgramResultFailed.setFixDialog(show);
            imgGetProgramResultSuccess.setFixDialog(show);

            imgGetProgramResultFailed.setClickable(false);
            imgGetProgramResultFailed.setFocusable(false);
            imgGetProgramResultFailed.setFocusableInTouchMode(false);

            imgGetProgramResultSuccess.setClickable(false);
            imgGetProgramResultSuccess.setFocusable(false);
            imgGetProgramResultSuccess.setFocusableInTouchMode(false);

            try
            {
                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
    private List<Integer> initializeItemsStatus()
    {
        return new ArrayList<>(Collections.nCopies(getCustomerInfoItemCount , 0));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        new PubFunc().new LocationProvider().stopLocationProvider();
    }

}
