package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.saphamrah.Adapter.TreasuryMapFaktorAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.TreasuryListMapMVP;
import com.saphamrah.BuildConfig;
import com.saphamrah.MVP.Presenter.TreasuryListMapPresenter;
import com.saphamrah.MVP.View.marjoee.DarkhastFaktorMarjoeeActivity;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.RoutingServerShared;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;
import com.saphamrah.Valhalla.SourcesToTargetsFailedResult;
import com.saphamrah.WebService.APIServiceValhalla;
import com.saphamrah.WebService.ApiClientGlobal;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TreasuryListMapActivity extends AppCompatActivity implements TreasuryListMapMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , TreasuryListMapActivity.this);
    private TreasuryListMapMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private FloatingActionButton fabRouteActivity;
    private TextView lblSortText;
    private MapView map;
    private BottomSheetBehavior bottomSheetBehavior;

    private IMapController mapController;
    private final int OPEN_INVOICE_SETTLEMENT = 100;
    private int sortType;
    private Marker myLocationMarker;
    private Polyline polyline;
    private String routingResponse;
    private int noeMasouliat;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasury_list_map);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imgBack = findViewById(R.id.imgBack);
        FloatingActionButton fabMyLocation = findViewById(R.id.fabMyLocation);
        fabRouteActivity = findViewById(R.id.fabRouteActivity);
        lblSortText = findViewById(R.id.lblSortTitle);
        map = findViewById(R.id.mapView);
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabSortByRouting = findViewById(R.id.fabSortByRouting);
        FloatingActionButton fabSortByCustomerCode = findViewById(R.id.fabSortByCustomerCode);
        LinearLayout lnrlayBottomsheet = findViewById(R.id.lnrlayRoot);
        bottomSheetBehavior = BottomSheetBehavior.from(lnrlayBottomsheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        customLoadingDialog = new CustomLoadingDialog();
        fabRouteActivity.setVisibility(View.GONE);

        customAlertDialog = new CustomAlertDialog(TreasuryListMapActivity.this);
        alertDialog = customLoadingDialog.showLoadingDialog(TreasuryListMapActivity.this);
        startMVPOps();

        double[] location = getCurrentLocation();
        Configuration.getInstance().load(TreasuryListMapActivity.this, PreferenceManager.getDefaultSharedPreferences(TreasuryListMapActivity.this));
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        mapController = new MapController(map);
        mapController.setCenter(new GeoPoint(location[0] , location[1]));
        mapController.setZoom(12.0);
        showCurrentLocation(location[0] , location[1]);

        mPresenter.getSortList();

        fabSortByCustomerCode.setOnClickListener(v -> {
              if (sortType == Constants.SORT_TREASURY_BY_CUSTOMER_CODE){
                customAlertDialog.showToast(TreasuryListMapActivity.this , getResources().getString(R.string.isSelectedSort) ,Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
            } else {
                  alertDialog = customLoadingDialog.showLoadingDialog(TreasuryListMapActivity.this);
                  fabMenu.close(true);
                  removePolyline();
                  map.getOverlays().clear();
                  map.invalidate();
                  mPresenter.getCustomers(Constants.SORT_TREASURY_BY_CUSTOMER_CODE);
              }
        });


        fabSortByRouting.setOnClickListener(v -> {
            if (sortType == Constants.SORT_TREASURY_BY_ROUTING){
                customAlertDialog.showToast(TreasuryListMapActivity.this , getResources().getString(R.string.isSelectedSort) ,Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
            } else {
                alertDialog = customLoadingDialog.showLoadingDialog(TreasuryListMapActivity.this);
                fabMenu.close(true);
                removePolyline();
                map.getOverlays().clear();
                map.invalidate();
                mPresenter.getCustomers(Constants.SORT_TREASURY_BY_ROUTING);
            }


        });

        map.getOverlays().add(new MapEventsOverlay(new MapEventsReceiver()
        {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p)
            {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p)
            {
                return false;
            }
        }));


        fabMyLocation.setOnClickListener(v -> {
            double[] location1 = getCurrentLocation();
            if (location1.length > 0)
            {
                showCurrentLocation(location1[0] , location1[1]);
            }
        });

        fabRouteActivity.setOnClickListener(v -> {
            if (routingResponse == null || routingResponse.length() == 0)
            {
                showAlertDialog(R.string.errorFirstRouting, false, Constants.FAILED_MESSAGE());
            }
            else
            {
                Intent intent = new Intent(TreasuryListMapActivity.this , RouteActivity.class);
                intent.putExtra("routing" , routingResponse);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                TreasuryListMapActivity.this.finish();
            }
        });

        imgBack.setOnClickListener(v ->
                TreasuryListMapActivity.this.finish());
    }

    public void route()
    {
        PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider();
        Location destinationLocation = new Location("destination");
        destinationLocation.setLatitude(35.754232);
        destinationLocation.setLongitude(51.215811);

        JsonArray jsonArrayLocations = new JsonArray();
        jsonArrayLocations.add(locationToJson(locationProvider.getLocation()));
        jsonArrayLocations.add(locationToJson(destinationLocation));

        JsonObject jsonObjectOptions = new JsonObject();
        jsonObjectOptions.addProperty("units" , "kilometer");

        JsonObject jsonObjectAllData = new JsonObject();
        jsonObjectAllData.add("locations" , jsonArrayLocations);
        jsonObjectAllData.addProperty("costing" , "auto");
        jsonObjectAllData.add("directions_options" , jsonObjectOptions);
        jsonObjectAllData.addProperty("id" , "");


        RoutingServerShared routingServerShared = new RoutingServerShared(BaseApplication.getContext());
        String urlOsrm = routingServerShared.getString(RoutingServerShared.IP,"http://91.92.125.244:8002");
        Log.d("urlOsrm",urlOsrm.substring(0, 11));
        if (urlOsrm.length() > 0)
        {
            APIServiceValhalla apiServiceValhalla = ApiClientGlobal.getInstance().getClientServiceValhalla(urlOsrm);
            Call<Object> call = apiServiceValhalla.getOptimizedRoute(jsonObjectAllData.toString());
            call.enqueue(new Callback<Object>()
            {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response)
                {
                    try
                    {
                        Gson gson = new Gson();
                        if (response.isSuccessful())
                        {
                            String jsonObjectResponse = gson.toJson(response.body());
                            routingResponse = jsonObjectResponse;

                        }
                        else
                        {
                            SourcesToTargetsFailedResult responseError = gson.fromJson(gson.toJson(response), SourcesToTargetsFailedResult.class);

                            customAlertDialog.showToast(TreasuryListMapActivity.this, responseError.getError(), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                        }
                    }
                    catch (Exception exception)
                    {
                        customAlertDialog.showToast(TreasuryListMapActivity.this, exception.toString(), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t)
                {
                    customAlertDialog.showToast(TreasuryListMapActivity.this, t.getMessage(), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
            });
        }


    }

    private JsonObject locationToJson(Location location)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lat" , location.getLatitude());
        jsonObject.addProperty("lon" , location.getLongitude());
        return jsonObject;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.checkFakeLocationAndDateTime();
    }



    /**
     * get sort in first time
     * @param sortList
     */
    @Override
    public void onGetSortList(int sortList) {
        mPresenter.getCustomers(sortList);
        sortType = sortList;
        mPresenter.getNoeMasouliat();
    }

    @Override
    public void onErrorSendRasGiri(String error) {
        customAlertDialog.showMessageAlert(TreasuryListMapActivity.this, false, "", error, Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showLoadingDialog() {
        if (customLoadingDialog!=null)
            alertDialog = customLoadingDialog.showLoadingDialog(TreasuryListMapActivity.this);
        else {
            customAlertDialog = new CustomAlertDialog(TreasuryListMapActivity.this);
            alertDialog = customLoadingDialog.showLoadingDialog(TreasuryListMapActivity.this);
        }
    }

    @Override
    public void onOpenInvoiceSettlement(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel)
    {
        Log.d("treasury", "ccDarkhastFaktor : " + darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor());
        Intent intent = new Intent(TreasuryListMapActivity.this, InvoiceSettlementActivity.class);
        intent.putExtra("ccMoshtary", darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());
        intent.putExtra("ccDarkhastFaktor", darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor());
        intent.putExtra("sourceActivity", "TreasuryListActivity");
        startActivityForResult(intent, OPEN_INVOICE_SETTLEMENT);
    }

    @Override
    public Context getAppContext() {
        return TreasuryListMapActivity.this;
    }

	@Override
    public void onGetNoeMasouliat(int noeMasouliat)
    {
        this.noeMasouliat = noeMasouliat;
    }
	
    @Override						 
    public void showCurrentLocation(double lat, double lng)
    {
        if (myLocationMarker != null)
        {
            map.getOverlays().remove(myLocationMarker);
        }
        myLocationMarker = new Marker(map);
        myLocationMarker.setPosition(new GeoPoint(lat , lng));
        myLocationMarker.setIcon(getResources().getDrawable(R.drawable.ic_user_marker));
        myLocationMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView)
            {
                return false;
            }
        });
        map.getOverlays().add(myLocationMarker);
        map.invalidate();
    }

    @Override
    public void showRoutingInfo()
    {
        lblSortText.setText(getString(R.string.sortWithRouting));
        sortType = Constants.SORT_TREASURY_BY_ROUTING;
    }

    @Override
    public void showCustomerCodeInfo()
    {
        lblSortText.setText(getString(R.string.sortWithCustomerCode));
        sortType = Constants.SORT_TREASURY_BY_CUSTOMER_CODE;
    }

    @Override
    public void showFirstPriority(final DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel, MoshtaryAddressModel moshtaryAddressModel) {
        Marker marker = new Marker(map);
        marker.setIcon(getResources().getDrawable(R.drawable.ic_priority_one));
        marker.setPosition(new GeoPoint(moshtaryAddressModel.getLatitude_y(), moshtaryAddressModel.getLongitude_x()));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                mapController.animateTo(new GeoPoint(moshtaryAddressModel.getLatitude_y(), moshtaryAddressModel.getLongitude_x()));
                //showBottomSheet(darkhastFaktorMoshtaryForoshandeModel , true, "1");
                mPresenter.getCustomerFaktors(darkhastFaktorMoshtaryForoshandeModel, moshtaryAddressModel, "1");
                return false;
            }
        });
        map.getOverlays().add(marker);
        map.invalidate();
    }

    @Override
    public void showSecondPriority(final DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel, MoshtaryAddressModel moshtaryAddressModel) {
        Marker marker = new Marker(map);
        marker.setIcon(getResources().getDrawable(R.drawable.ic_priority_two));
        marker.setPosition(new GeoPoint(moshtaryAddressModel.getLatitude_y(), moshtaryAddressModel.getLongitude_x()));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                mapController.animateTo(new GeoPoint(moshtaryAddressModel.getLatitude_y(), moshtaryAddressModel.getLongitude_x()));
                //showBottomSheet(darkhastFaktorMoshtaryForoshandeModel , true, "2");
                mPresenter.getCustomerFaktors(darkhastFaktorMoshtaryForoshandeModel, moshtaryAddressModel, "2");
                return false;
            }
        });
        map.getOverlays().add(marker);
        map.invalidate();
    }

    @Override
    public void showThirdPriority(final DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel, MoshtaryAddressModel moshtaryAddressModel) {
        Marker marker = new Marker(map);
        marker.setIcon(getResources().getDrawable(R.drawable.ic_priority_three));
        marker.setPosition(new GeoPoint(moshtaryAddressModel.getLatitude_y(), moshtaryAddressModel.getLongitude_x()));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                mapController.animateTo(new GeoPoint(moshtaryAddressModel.getLatitude_y(), moshtaryAddressModel.getLongitude_x()));
                //showBottomSheet(darkhastFaktorMoshtaryForoshandeModel , true, "3");
                mPresenter.getCustomerFaktors(darkhastFaktorMoshtaryForoshandeModel, moshtaryAddressModel, "3");
                return false;
            }
        });
        map.getOverlays().add(marker);
        map.invalidate();
    }


    /**
     * نمایش درخواست فاکتورهایی که ارسال نشده اند و اولویتی بالاتر از 3 دارند.
     * @param darkhastFaktorMoshtaryForoshandeModels لیست درخواست فاکتورهایی که ارسال نشده اند.
     */
    @Override
    public void onGetTodayTreasuryList(final ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels, ArrayList<MoshtaryAddressModel> moshtaryAddressModelsNew) {


        ArrayList<OverlayItem> items = new ArrayList<>();
        MoshtaryAddressModel moshtaryAddressModel = new MoshtaryAddressModel();
        for (int i = 0; i < darkhastFaktorMoshtaryForoshandeModels.size(); i++) {
            items.add(new OverlayItem(darkhastFaktorMoshtaryForoshandeModels.get(i).getFullNameMoshtary(), "", new GeoPoint(moshtaryAddressModelsNew.get(i).getLatitude_y(), moshtaryAddressModelsNew.get(i).getLongitude_x()))); // Lat/Lon decimal degrees
//            for (int j = 0; j < moshtaryAddressModelsNew.size(); j++) {
//                if(darkhastFaktorMoshtaryForoshandeModels.get(i).getCcMoshtary()==moshtaryAddressModelsNew.get(j).getCcMoshtary())
//                    moshtaryAddressModel = moshtaryAddressModelsNew.get(j);
//            }

        }

        ItemizedIconOverlay<OverlayItem> itemItemizedIconOverlay = new ItemizedIconOverlay<>(items, getResources().getDrawable(R.drawable.ic_red_star), new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                mapController.animateTo(new GeoPoint(moshtaryAddressModelsNew.get(index).getLatitude_y(), moshtaryAddressModelsNew.get(index).getLongitude_x()));
                // به ایندکس 4 عدد اضافه میکنیم، سه تا برای اولویت های یک تا 3، یکی هم برای اینکه اندیس ها از صفر شروع می شوند.
                //showBottomSheet(darkhastFaktorMoshtaryForoshandeModels.get(index) , true, String.valueOf(index + 4));
                mPresenter.getCustomerFaktors(darkhastFaktorMoshtaryForoshandeModels.get(index), moshtaryAddressModelsNew.get(index), String.valueOf(index + 4));
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        }, TreasuryListMapActivity.this);
        map.getOverlays().add(itemItemizedIconOverlay);
    }


    @Override
    public void onGetAllEditedTodayTreasuryList(final ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels, ArrayList<MoshtaryAddressModel> moshtaryAddressModelArrayList)
    {
        ArrayList<OverlayItem> items = new ArrayList<>();
        for (int i=0 ; i<darkhastFaktorMoshtaryForoshandeModels.size() ; i++)
        {
            items.add(new OverlayItem(darkhastFaktorMoshtaryForoshandeModels.get(i).getFullNameMoshtary(), "", new GeoPoint(darkhastFaktorMoshtaryForoshandeModels.get(i).getLatitude(), darkhastFaktorMoshtaryForoshandeModels.get(i).getLongitude()))); // Lat/Lon decimal degrees
        }

        ItemizedIconOverlay<OverlayItem> itemItemizedIconOverlay = new ItemizedIconOverlay<>(items, getResources().getDrawable(R.drawable.ic_green_star), new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item)
            {
                mapController.animateTo(new GeoPoint(darkhastFaktorMoshtaryForoshandeModels.get(index).getLatitude(), darkhastFaktorMoshtaryForoshandeModels.get(index).getLongitude()));
                // به ایندکس 4 عدد اضافه میکنیم، سه تا برای اولویت های یک تا 3، یکی هم برای اینکه اندیس ها از صفر شروع می شوند.
                //showBottomSheet(darkhastFaktorMoshtaryForoshandeModels.get(index) , true, String.valueOf(index + 4));
                mPresenter.getCustomerFaktors(darkhastFaktorMoshtaryForoshandeModels.get(index), moshtaryAddressModelArrayList.get(index), String.valueOf(index + 4));
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        }, TreasuryListMapActivity.this);
        map.getOverlays().add(itemItemizedIconOverlay);
    }

    @Override
    public void onGetEditedTodayTreasuryList(final ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels, ArrayList<MoshtaryAddressModel> moshtaryAddressModelArrayList)
    {
        ArrayList<OverlayItem> items = new ArrayList<>();
        for (int i=0 ; i<darkhastFaktorMoshtaryForoshandeModels.size() ; i++)
        {
            items.add(new OverlayItem(darkhastFaktorMoshtaryForoshandeModels.get(i).getFullNameMoshtary(), "", new GeoPoint(darkhastFaktorMoshtaryForoshandeModels.get(i).getLatitude(), darkhastFaktorMoshtaryForoshandeModels.get(i).getLongitude()))); // Lat/Lon decimal degrees
        }

        ItemizedIconOverlay<OverlayItem> itemItemizedIconOverlay = new ItemizedIconOverlay<>(items, getResources().getDrawable(R.drawable.ic_green_star), new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item)
            {
                mapController.animateTo(new GeoPoint(darkhastFaktorMoshtaryForoshandeModels.get(index).getLatitude(), darkhastFaktorMoshtaryForoshandeModels.get(index).getLongitude()));
                //showBottomSheet(darkhastFaktorMoshtaryForoshandeModels.get(index) , false, "-1");
                mPresenter.getCustomerFaktors(darkhastFaktorMoshtaryForoshandeModels.get(index) , moshtaryAddressModelArrayList.get(index), "-1");
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        }, TreasuryListMapActivity.this);
        map.getOverlays().add(itemItemizedIconOverlay);
    }

    @Override
    public void showCustomerFaktors(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels, MoshtaryAddressModel moshtaryAddressModel , String customerPriority , DarkhastFaktorMoshtaryForoshandeModel customerInfo)
    {
        showBottomSheet(darkhastFaktorMoshtaryForoshandeModels, customerInfo, moshtaryAddressModel, true, customerPriority);
    }

    @Override
    public void onGetFaktorImage(byte[] faktorImage)
    {
        customAlertDialog.showImage(TreasuryListMapActivity.this, faktorImage, false, new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {}
            @Override
            public void setOnApplyClick() {}
        });
    }

    @Override
    public void openDarkhastKalaActivity(long ccDarkhastFaktor, int ccMoshtary)
    {
        Intent intent = new Intent(TreasuryListMapActivity.this , DarkhastKalaActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        TreasuryListMapActivity.this.finish();
    }

    @Override
    public void onSuccessRouting(final String customerName, ArrayList<GeoPoint> pointsOfPolyline, String routingResponse)
    {
        this.routingResponse = routingResponse;
        fabRouteActivity.setVisibility(View.VISIBLE);
        removePolyline();
        polyline = new Polyline();
        polyline.setPoints(pointsOfPolyline);
        polyline.setColor(getResources().getColor(R.color.colorPolyline));
        polyline.setWidth(5.0F);
        map.getOverlayManager().add(polyline);
        map.invalidate();
    }

    @Override
    public void showAlertDialog(int resId, String parameter, int messageType)
    {
        customAlertDialog.showMessageAlert(TreasuryListMapActivity.this, false, "", getString(resId, parameter), messageType, getString(R.string.apply));
    }

    @Override
    public void showAlertDialog(int resId, boolean closeActivity, int messageType)
    {
        customAlertDialog.showMessageAlert(TreasuryListMapActivity.this, closeActivity, "", getString(resId), messageType, getString(R.string.apply));
    }

	@Override
    public void showAlertDialog(String message, boolean closeActivity, int messageType)
    {
        customAlertDialog.showMessageAlert(TreasuryListMapActivity.this, closeActivity, "", message, messageType, getString(R.string.apply));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(TreasuryListMapActivity.this, getString(resId), messageType, duration);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_INVOICE_SETTLEMENT)
        {
            mPresenter.getTodayTreasuryList(sortType);
        }
    }

    public void showBottomSheet(final ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels, final DarkhastFaktorMoshtaryForoshandeModel customerInfo, MoshtaryAddressModel moshtaryAddressModel, boolean showEditButton, String priority)
    {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
        {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        TextView lblCustomerCodeName = findViewById(R.id.lblCodeNameCustomer);
        TextView lblNameForoshandeh = findViewById(R.id.lblNameForoshandeh);
        TextView lblCountDarkhastFaktor = findViewById(R.id.lblCountFaktor);
        TextView lblPriority = findViewById(R.id.lblPriority);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageView imgRoute = findViewById(R.id.imgRoute);
        ImageView imgShowFaktorImage = findViewById(R.id.imgShowFaktorImage);
        ImageView imgShowFaktorDetail = findViewById(R.id.imgFaktorDetail);
        ImageView imgEditDarkhast = findViewById(R.id.imgEditDarkhast);
        ImageView imgClearingTreasury = findViewById(R.id.imgClearingTreasury);
        ImageView imgSendTreasury = findViewById(R.id.imgSendTreasury);
        ImageView imgNextFaktor = findViewById(R.id.imgNextFaktor);
        ImageView imgSendLocation = findViewById(R.id.imgSendLocation);
        ImageView imgMarjoee = findViewById(R.id.imgMarjoee);
        TreasuryMapFaktorAdapter adapter = new TreasuryMapFaktorAdapter(TreasuryListMapActivity.this, darkhastFaktorMoshtaryForoshandeModels);
        recyclerView.setOnFlingListener(null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TreasuryListMapActivity.this , LinearLayoutManager.HORIZONTAL , false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        lblPriority.setTypeface(font);
        lblCustomerCodeName.setTypeface(font);
        lblNameForoshandeh.setTypeface(font);
        lblCountDarkhastFaktor.setTypeface(font);

        int visibility = darkhastFaktorMoshtaryForoshandeModels.size() > 1 ? View.VISIBLE : View.GONE;
        imgNextFaktor.setVisibility(visibility);

        if (priority == null || priority.equals("-1"))
        {
            lblPriority.setVisibility(View.GONE);
        }
        else
        {
            lblPriority.setVisibility(View.VISIBLE);
            lblPriority.setText(priority);
        }
        lblCustomerCodeName.setText(String.format("%1$s - %2$s", customerInfo.getCodeMoshtary(), customerInfo.getFullNameMoshtary()));
        lblNameForoshandeh.setText(customerInfo.getFullNameForoshande());
        lblCountDarkhastFaktor.setText(String.valueOf(customerInfo.getCountDarkhastFaktor()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        if (showEditButton)
        {
            imgEditDarkhast.setVisibility(View.VISIBLE);
            imgMarjoee.setVisibility(View.GONE);
        }
        else
        {
            imgEditDarkhast.setVisibility(View.GONE);
            imgMarjoee.setVisibility(View.VISIBLE);
        }

        imgRoute.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                mPresenter.routingFromCurrentLocation(customerInfo.getCcMoshtary(), customerInfo.getFullNameMoshtary(), moshtaryAddressModel.getLatitude_y(), moshtaryAddressModel.getLongitude_x());
            }
        });

        imgShowFaktorImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    int position = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mPresenter.getFaktorImage(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
                catch (Exception e)
                {
                    showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                    e.printStackTrace();
                }
            }
        });

        imgShowFaktorDetail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    int position = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    openFaktorDetailActivity(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
                catch (Exception e)
                {
                    showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                    e.printStackTrace();
                }
            }
        });


        imgEditDarkhast.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                mPresenter.setDarkhastFaktorShared(darkhastFaktorMoshtaryForoshandeModels.get(position));
            }
        });


        imgClearingTreasury.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                mPresenter.checkClearingTreasury(noeMasouliat, darkhastFaktorMoshtaryForoshandeModels.get(position));
//                mPresenter.checkEditTreasury(noeMasouliat , darkhastFaktorMoshtaryForoshandeModels.get(position));
            }
        });

        imgMarjoee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                startActivityBundle(DarkhastFaktorMarjoeeActivity.class, "marjoee", String.valueOf(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor()), "ccMoshtaryMarjoee", String.valueOf(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcMoshtary()));
            }
        });

        imgSendTreasury.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final int position = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                customAlertDialog.showLogMessageAlert(TreasuryListMapActivity.this, false, "", getResources().getString(R.string.sendWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
                    @Override
                    public void setOnCancelClick() {}
                    @Override
                    public void setOnApplyClick() {
                        mPresenter.sendDariaftPardakht(noeMasouliat , darkhastFaktorMoshtaryForoshandeModels.get(position));
                    }
                });
            }
        });


        imgNextFaktor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    int position = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    if (position == darkhastFaktorMoshtaryForoshandeModels.size() - 1)
                    {
                        recyclerView.smoothScrollToPosition(0);
                    }
                    else
                    {
                        recyclerView.smoothScrollToPosition(position + 1);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        imgSendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                mPresenter.checkMoshtaryKharejAzMahal(noeMasouliat,darkhastFaktorMoshtaryForoshandeModels.get(position));
            }
        });

        /**
         * check for marjoee
         */
        int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() + 1;
        if ((darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktorNoeForosh() == Constants.ccNoeHavale) && ((noeMasouliat == 4 && darkhastFaktorMoshtaryForoshandeModels.get(position).getCodeVazeiat() == 99) || (noeMasouliat == 5 && darkhastFaktorMoshtaryForoshandeModels.get(position).getExtraProp_IsSend() == 0 && darkhastFaktorMoshtaryForoshandeModels.get(position).getCodeVazeiat() < 6))) {
            imgEditDarkhast.setVisibility(View.VISIBLE);
            imgMarjoee.setVisibility(View.GONE);
        } else {
            imgMarjoee.setVisibility(View.VISIBLE);
            imgEditDarkhast.setVisibility(View.GONE);
        }


    }

	@Override
    public void openInvoiceSettlement(int ccMoshtary , long ccDarkhastFaktor)
    {
        Intent intent = new Intent(TreasuryListMapActivity.this , InvoiceSettlementActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        intent.putExtra("ccDarkhastFaktor" , ccDarkhastFaktor);
        intent.putExtra("sourceActivity" , "TreasuryListActivity");
        startActivityForResult(intent , OPEN_INVOICE_SETTLEMENT);
    }

    private void openFaktorDetailActivity(long ccDarkhastFaktor)
    {
        Intent intent = new Intent(TreasuryListMapActivity.this , FaktorDetailsActivity.class);
        intent.putExtra("ccDarkhastFaktor" , ccDarkhastFaktor);
        intent.putExtra("sourceActivity" , "TreasuryListActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }
    /**
     * @param otherActivityClass second class start
     * @param keyValue1          key value bundle
     * @param keyValue2          key value bundle
     * @param bundle1            your object
     * @param bundle2            your object
     */
    private void startActivityBundle(Class<?> otherActivityClass, String keyValue1, String bundle1, String keyValue2, String bundle2) {
        Intent i = new Intent(TreasuryListMapActivity.this, otherActivityClass);
        i.putExtra(keyValue1, bundle1);
        i.putExtra(keyValue2, bundle2);
        startActivity(i);
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

    private void removePolyline()
    {
        if (polyline != null)
        {
            map.getOverlays().remove(polyline);
            map.invalidate();
            polyline = null;
        }
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "startMVPOps", "");
        }
    }


    private void initialize(TreasuryListMapMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new TreasuryListMapPresenter(view);
            stateMaintainer.put(TreasuryListMapMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "initialize", "");
        }
    }


    private void reinitialize(TreasuryListMapMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(TreasuryListMapMVP.PresenterOps.class.getSimpleName());
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

    @Override
    public void closeLoadingDialog()
    {
        if (alertDialog != null)
        {
            try
            {
                alertDialog.dismiss();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorMandehDarActivity", "closeLoadingDialog", "");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(false);

    }
}
