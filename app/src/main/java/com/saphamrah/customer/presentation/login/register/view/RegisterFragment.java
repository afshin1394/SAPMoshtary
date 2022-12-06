package com.saphamrah.customer.presentation.login.register.view;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.saphamrah.customer.Constants;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.data.LocationDbModel;
import com.saphamrah.customer.databinding.BottomSheetMapSearchBinding;
import com.saphamrah.customer.databinding.BottomSheetRecyclerSearchBinding;
import com.saphamrah.customer.databinding.FragmentRegisterBinding;
import com.saphamrah.customer.presentation.login.LoginActivity;
import com.saphamrah.customer.presentation.login.register.interactor.RegisterInteractor;
import com.saphamrah.customer.presentation.login.register.presenter.RegisterPresenter;
import com.saphamrah.customer.receivers.LocationReceiver;
import com.saphamrah.customer.service.GpsTracker;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.BaseBottomSheet;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.list.BaseBottomSheetRecyclerView;
import com.saphamrah.customer.utils.customViews.CustomSnackBar;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemMultiSelectListener;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.list.BottomSheetDynamicListMultiSelect;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.list.BottomSheetDynamicListSingleSelect;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.map.ApplyButtonMap;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.map.BottomSheetMap;
import com.saphamrah.customer.utils.mapModule.Enums.MapType;
import com.saphamrah.customer.utils.mapModule.Interfaces.IMapClickEvents;
import com.saphamrah.customer.utils.mapModule.MapDesigns.OsmDroid;
import com.saphamrah.customer.utils.mapModule.Models.MapObjectModel;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class RegisterFragment extends BaseFragment<RegisterPresenter, FragmentRegisterBinding, LoginActivity> implements RegisterInteractor.RequiredViewOps, AdapterItemListener<BaseBottomSheetRecyclerModel>, AdapterItemMultiSelectListener<BaseBottomSheetRecyclerModel> {

    private OsmDroid osmDroid;
    private MapView mapView;
    private MapObjectModel currentMapModel;
    private BaseBottomSheet baseBottomSheetMap;
    private BottomSheetMap bottomSheetMap;
    private BottomSheetMap.BottomSheetMapBuilder<BottomSheetMapSearchBinding> bottomSheetMapBuilder;


    private BaseBottomSheet baseBottomSheetRecyclerView;
    private BottomSheetDynamicListSingleSelect bottomSheetDynamicListSingleSelect;
    private BottomSheetDynamicListMultiSelect bottomSheetDynamicListMultiSelect;
    private BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder<BottomSheetRecyclerSearchBinding> bottomSheetRecyclerViewBuilder;
    private ArrayList<LocationDbModel> baseSearchProvinceDbModels;
    private ArrayList<LocationDbModel> baseSearchCityDbModels;

    private BottomSheetRecyclerSearchBinding bottomSheetRecyclerSearchBinding;
    private BottomSheetMapSearchBinding bottomSheetMapSearchBinding;

    private DividerItemDecoration divider;


    @Override
    protected FragmentRegisterBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        FragmentRegisterBinding fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = fragmentRegisterBinding.getRoot();
        bottomSheetRecyclerSearchBinding = BottomSheetRecyclerSearchBinding.bind(view);
        bottomSheetMapSearchBinding = BottomSheetMapSearchBinding.bind(view);
        return fragmentRegisterBinding;
    }


    @Override
    protected void setPresenter() {
        presenter = new RegisterPresenter(this);
    }

    @Override
    protected void onBackPressed() {
        if (baseBottomSheetMap.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetMap.closeBottomSheet();
        } else {
            navigateUp();
        }
    }

    @Override
    protected void initViews() {

        baseBottomSheetMap = new BaseBottomSheet(bottomSheetMapSearchBinding, getContext(), R.id.cardView_mapView_BottomSheet);
        baseBottomSheetRecyclerView = new BaseBottomSheet(bottomSheetRecyclerSearchBinding, getContext(), R.id.cardView_recyclerView_bottomSheet);
        bottomSheetRecyclerViewBuilder = new BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder<>();
        bottomSheetMapBuilder = new BottomSheetMap.BottomSheetMapBuilder<>();


        clickListeners();

        baseSearchProvinceDbModels = new ArrayList<>();
        baseSearchCityDbModels = new ArrayList<>();

        baseSearchProvinceDbModels.add(new LocationDbModel("تهران", "province"));
        baseSearchProvinceDbModels.add(new LocationDbModel("تبریز", "province"));
        baseSearchProvinceDbModels.add(new LocationDbModel("گیلان", "province"));
        baseSearchProvinceDbModels.add(new LocationDbModel("گرگان", "province"));

        baseSearchCityDbModels.add(new LocationDbModel("تهران", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("تبریز", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("اراک", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("ایلام", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("ایلام", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("ایلام", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("ایلام", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("ایلام", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("ایلام", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("ایلام", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("ایلام", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("ایلام", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("ایلام", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("تهران", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("تبریز", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("اراک", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("تهران", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("تبریز", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("اراک", "city"));

    }

    private void clickListeners() {
        viewBinding.btnApply.setOnClickListener(v -> checkValidityOfRegisterData());
        viewBinding.edtInputProvince.setOnClickListener(v -> handleSearchProvince());
        viewBinding.edtInputCity.setOnClickListener(v -> handleSearchCity());
        viewBinding.edtInputLocation.setOnClickListener(v -> handleGetLocation());
        viewBinding.edtInputIdentity.setOnClickListener(v -> handleGetIdentityType());
        viewBinding.edtInputIdentity.setOnFocusChangeListener((v, hasFocus) -> handleGetIdentityType());
        viewBinding.edtInputFname.setOnFocusChangeListener((v, haseFocus) -> closeBottomSheetBehavior());
        viewBinding.edtInputLname.setOnFocusChangeListener((v, haseFocus) -> closeBottomSheetBehavior());
        viewBinding.edtInputMobile.setOnFocusChangeListener((v, haseFocus) -> closeBottomSheetBehavior());
        viewBinding.edtInputBoardName.setOnFocusChangeListener((v, haseFocus) -> closeBottomSheetBehavior());
        viewBinding.edtInputNationalCode.setOnFocusChangeListener((v, haseFocus) -> closeBottomSheetBehavior());
        viewBinding.edtInputMainStreet.setOnFocusChangeListener((v, haseFocus) -> closeBottomSheetBehavior());
        viewBinding.edtInputMainAlley.setOnFocusChangeListener((v, haseFocus) -> closeBottomSheetBehavior());
        viewBinding.edtInputPlaque.setOnFocusChangeListener((v, haseFocus) -> closeBottomSheetBehavior());
        viewBinding.edtInputPostalCode.setOnFocusChangeListener((v, haseFocus) -> closeBottomSheetBehavior());
        viewBinding.edtInputLocation.setOnFocusChangeListener((v, haseFocus) -> closeBottomSheetBehavior());

    }

    private void handleGetIdentityType() {
        presenter.getIdentities();
    }

    private void handleGetLocation() {


        mapView = bottomSheetMapSearchBinding.mapViewBottomSheet;
        mapView.setClickable(true);
        mapView.setMultiTouchControls(true);

        osmDroid = new OsmDroid(getContext(),mapView,true, MapType.OsmDroid);
        osmDroid.zoomCameraToSpecificPosition(osmDroid.getCurrentLocation(),20);
        osmDroid.removeAllAvailableFeaturesOnMap();



        currentMapModel = new MapObjectModel.Builder()
                .setGroup_key(osmDroid.CURRENT_LOCATION_GROUP_ID())
                .setLayer_id(osmDroid.CURRENT_LOCATION_LAYER_ID())
                .setSource_id(osmDroid.CURRENT_LOCATION_SOURCE_ID())
                .setImage_id(osmDroid.CURRENT_LOCATION_IMAGE_ID())
                .setLatLng(new LatLng(osmDroid.getCurrentLocation().getLatitude(), osmDroid.getCurrentLocation().getLongitude()))
                .setDrawable(getResources().getDrawable(R.drawable.ic_user_marker))
                .create();

        osmDroid.addSingleLocationLayer(currentMapModel);
        osmDroid.onMapContentClickListener(osmDroid.CURRENT_LOCATION_GROUP_ID(), new IMapClickEvents() {
            @Override
            public void onMarkSingleTap(int index, Object object) {

            }

            @Override
            public void onMarkLongTap(int index, Object object) {

            }

            @Override
            public void onOtherItemsClick(GeoPoint point) {

                osmDroid.removeExistingFeatures(osmDroid.CURRENT_LOCATION_GROUP_ID());

                currentMapModel = new MapObjectModel.Builder()
                        .setGroup_key(osmDroid.CURRENT_LOCATION_GROUP_ID())
                        .setLayer_id(osmDroid.CURRENT_LOCATION_GROUP_ID())
                        .setSource_id(osmDroid.CURRENT_LOCATION_GROUP_ID())
                        .setImage_id(osmDroid.CURRENT_LOCATION_GROUP_ID())
                        .setLatLng(new LatLng(point.getLatitude(), point.getLongitude()))
                        .setDrawable(getResources().getDrawable(R.drawable.ic_user_marker))
                        .create();

                osmDroid.addSingleLocationLayer(currentMapModel);
                mapView.invalidate();


                bottomSheetMap = new BottomSheetMap<>(
                        bottomSheetMapSearchBinding,
                        getContext(),
                        R.id.cardView_mapView_BottomSheet,
                        currentMapModel,
                        bottomSheetMapBuilder
                                .setSearchEnable(true)
                                .setButtonApplyEnable(true)
                                .setSearchHint(getString(R.string.search_address)),
                        new ApplyButtonMap() {
                            @Override
                            public void onApplyButtonListener(LatLng latLng) {
                                Log.d("RegisterFragment", "latlng: "+ latLng);

                            }
                        }
                );
            }
        });

        bottomSheetMap = new BottomSheetMap<>(
                bottomSheetMapSearchBinding,
                getContext(),
                R.id.cardView_mapView_BottomSheet,
                currentMapModel,
                bottomSheetMapBuilder
                        .setSearchEnable(true)
                        .setButtonApplyEnable(true)
                        .setSearchHint(getString(R.string.search_address)),
                new ApplyButtonMap() {
                    @Override
                    public void onApplyButtonListener(LatLng latLng) {
                        Log.d("RegisterFragment", "latlng: "+ latLng);

                    }
                }
        );

    }

    public void startGPSService(int minDistance, int timeInterval, int fastestTimeInterval, int maxAccurancy) {
        Intent intent = new Intent(getContext(), GpsTracker.class);
        intent.putExtra(Constants.DISTANCE(), minDistance);
        intent.putExtra(Constants.INTERVAL(), timeInterval);
        intent.putExtra(Constants.FASTEST_INTERVAL(), fastestTimeInterval);
        intent.putExtra(Constants.ACCURACY(), maxAccurancy);
       /* intent.putExtra("ccAfrad" , ccAfrad);
        intent.putExtra("ccForoshandeh" , ccForoshandeh);
        intent.putExtra("ccMamorPakhsh" , ccMamorPakhsh);
        intent.putExtra("ccMasir" , ccMasir);*/
        try {
            Log.d("locationReceiver", "service start");
            FragmentActivity fragmentActivity = getActivity();

            if (Build.VERSION.SDK_INT >= 26) {
                LocationReceiver locationReceiver = new LocationReceiver();
                IntentFilter filter = new IntentFilter("com.sap.gpstracker");

                fragmentActivity.registerReceiver(locationReceiver, filter);
                fragmentActivity.startForegroundService(intent);
                //Toast.makeText(MainActivity.this, "Tracking Started..", Toast.LENGTH_SHORT).show();
            } else {
                fragmentActivity.startService(intent);
                //Toast.makeText(MainActivity.this, "Tracking Started..", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
//            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainActivity", "onCreate" , "");
        }
    }

    private void checkValidityOfRegisterData() {
       /* registerNetworkModel = new RegisterNetworkModel();
        registerNetworkModel.setFirstName("Mojtaba");
        registerNetworkModel.setLastName("Shirkhani");
        registerNetworkModel.setMobile("09052436189");
        registerNetworkModel.setBoardName("");
        registerNetworkModel.setNationalCode("");
        registerNetworkModel.setProvince("");
        registerNetworkModel.setCity("");
        registerNetworkModel.setMainStreet("");
        registerNetworkModel.setMainAlley("");
        registerNetworkModel.setPlaque("");
        registerNetworkModel.setPostalCode("");
        registerNetworkModel.setLatLng(new Pair<>(0.0, 0.0));
        presenter.checkValidityOfRegisterData(registerNetworkModel);*/

        navigate(R.id.action_RegisterFragment_to_VerifyOtpLoginFragment);
    }


    private void handleSearchCity() {

        clearFocusEditTextsWithoutBtmSheet();
        closeBottomSheetBehavior();

        divider =
                new DividerItemDecoration(context,
                        DividerItemDecoration.VERTICAL);

        divider.setDrawable(ContextCompat.getDrawable(context,
                R.drawable.layer_line_divider));

        bottomSheetDynamicListMultiSelect = new BottomSheetDynamicListMultiSelect(
                bottomSheetRecyclerSearchBinding,
                getContext(),
                R.id.cardView_recyclerView_bottomSheet,
                new LinearLayoutManager(getContext()),
                bottomSheetRecyclerViewBuilder.setDividerItemDecoration(divider),
                true,
                getContext().getResources().getString(R.string.searchCity),
                baseSearchCityDbModels,
                this);


    }


    private void handleSearchProvince() {

        clearFocusEditTextsWithoutBtmSheet();
        closeBottomSheetBehavior();

        divider =
                new DividerItemDecoration(context,
                        DividerItemDecoration.VERTICAL);

        divider.setDrawable(ContextCompat.getDrawable(context,
                R.drawable.layer_line_divider));

        bottomSheetDynamicListSingleSelect = new BottomSheetDynamicListSingleSelect(
                bottomSheetRecyclerSearchBinding,
                getContext(),
                R.id.cardView_recyclerView_bottomSheet,
                new LinearLayoutManager(getContext()),
                bottomSheetRecyclerViewBuilder.setDividerItemDecoration(divider),
                true,
                getContext().getResources().getString(R.string.searchProvince),
                baseSearchCityDbModels,
                this);

    }

    private void clearFocusEditTextsWithoutBtmSheet() {
        viewBinding.edtInputFname.clearFocus();
        viewBinding.edtInputLname.clearFocus();
        viewBinding.edtInputMobile.clearFocus();
        viewBinding.edtInputBoardName.clearFocus();
        viewBinding.edtInputNationalCode.clearFocus();
        viewBinding.edtInputMainStreet.clearFocus();
        viewBinding.edtInputMainAlley.clearFocus();
        viewBinding.edtInputPlaque.clearFocus();
        viewBinding.edtInputPostalCode.clearFocus();
        viewBinding.edtInputLocation.clearFocus();

    }

    private void closeBottomSheetBehavior() {
        baseBottomSheetRecyclerView.closeBottomSheet();
        baseBottomSheetMap.closeBottomSheet();
    }

    @Override
    public void onError(String error) {
        CustomSnackBar.showSnack(requireContext(), getView(), error, Snackbar.LENGTH_SHORT, "").show();
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoConnection() {

    }

    @Override
    public Context getAppContext() {
        return getContext();
    }

    @Override
    public void onGetIdentities(ArrayList<BaseBottomSheetRecyclerModel> itemTitles) {

        clearFocusEditTextsWithoutBtmSheet();
        closeBottomSheetBehavior();

        divider =
                new DividerItemDecoration(context,
                        DividerItemDecoration.VERTICAL);

        divider.setDrawable(ContextCompat.getDrawable(context,
                R.drawable.layer_line_divider));

        bottomSheetDynamicListMultiSelect = new BottomSheetDynamicListMultiSelect(
                bottomSheetRecyclerSearchBinding,
                getContext(),
                R.id.cardView_recyclerView_bottomSheet,
                new LinearLayoutManager(getContext()),
                bottomSheetRecyclerViewBuilder
                        .setDividerItemDecoration(divider),
                false,
                "",
                itemTitles,
                this);

    }

    @Override
    public void onGetProvinces() {

    }

    @Override
    public void onGetCities() {

    }

    @Override
    public void onSendRegisterData() {

    }

    @Override
    public void onErrorFirstName() {
        viewBinding.txtInputFname.setError(getResources().getString(R.string.errorFirstName));

    }

    @Override
    public void onErrorLastName() {
        viewBinding.txtInputLname.setError(getResources().getString(R.string.errorLastName));

    }

    @Override
    public void onErrorMobile() {
        viewBinding.txtInputMobile.setError(getResources().getString(R.string.errorMobile));

    }

    @Override
    public void onItemSelect(BaseBottomSheetRecyclerModel model, int position, AdapterAction Action) {

        Log.d("RegisterFragment", "isSelected: " + model.isSelected());
        closeBottomSheetBehavior();

        if (model.getType() == null) {
            viewBinding.edtInputIdentity.setText(model.getName());
        } else {
            if (model.getType().equals("province"))
                viewBinding.edtInputProvince.setText(model.getName());
            else if (model.getType().equals("city"))
                viewBinding.edtInputCity.setText(model.getName());
        }

    }

    @Override
    public void onItemMultiSelect(ArrayList<BaseBottomSheetRecyclerModel> models, AdapterAction action) {
        Log.d("RegisterFragment", "models: " + models.size());

    }

}
