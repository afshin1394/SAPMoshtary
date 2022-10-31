package com.saphamrah.customer.presentation.login.register.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.data.LocationDbModel;
import com.saphamrah.customer.databinding.FragmentRegisterBinding;
import com.saphamrah.customer.presentation.login.register.interactor.RegisterInteractor;
import com.saphamrah.customer.presentation.login.register.presenter.RegisterPresenter;
import com.saphamrah.customer.utils.customViews.BottomSheetSearchRecyclerView;
import com.saphamrah.customer.utils.customViews.CustomSnackBar;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemMultiSelectListener;

import java.util.ArrayList;

public class RegisterFragment extends BaseFragment<RegisterPresenter, FragmentRegisterBinding> implements RegisterInteractor.RequiredViewOps, AdapterItemListener<BaseBottomSheetRecyclerModel>, AdapterItemMultiSelectListener<BaseBottomSheetRecyclerModel> {


    private BottomSheetSearchRecyclerView bottomSheetSearch;
    private ArrayList<LocationDbModel> baseSearchProvinceDbModels;
    private ArrayList<LocationDbModel> baseSearchCityDbModels;



    @Override
    protected void setPresenter() {
        presenter = new RegisterPresenter(this);
    }

    @Override
    protected void initViews() {

        bottomSheetSearch = new BottomSheetSearchRecyclerView(this, this);

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


    }

    private void clickListeners() {
        viewBinding.btnApply.setOnClickListener(v -> checkValidityOfRegisterData());
        viewBinding.edtInputProvince.setOnClickListener(v -> handleSearchProvince());
        viewBinding.edtInputCity.setOnClickListener(v -> handleSearchCity());
        viewBinding.edtInputLocation.setOnClickListener(v -> handleGetLocation());
        viewBinding.edtInputIdentity.setOnClickListener(v -> handleGetIdentityType());
        viewBinding.edtInputIdentity.setOnFocusChangeListener((v, hasFocus) -> handleGetIdentityType());
        viewBinding.edtInputFname.setOnFocusChangeListener((v, haseFocus)  -> handleBottomSheetBehaviorState());
        viewBinding.edtInputLname.setOnFocusChangeListener((v, haseFocus)  -> handleBottomSheetBehaviorState());
        viewBinding.edtInputMobile.setOnFocusChangeListener((v, haseFocus)  -> handleBottomSheetBehaviorState());
        viewBinding.edtInputBoardName.setOnFocusChangeListener((v, haseFocus)  -> handleBottomSheetBehaviorState());
        viewBinding.edtInputNationalCode.setOnFocusChangeListener((v, haseFocus)  -> handleBottomSheetBehaviorState());
        viewBinding.edtInputMainStreet.setOnFocusChangeListener((v, haseFocus)  -> handleBottomSheetBehaviorState());
        viewBinding.edtInputMainAlley.setOnFocusChangeListener((v, haseFocus)  -> handleBottomSheetBehaviorState());
        viewBinding.edtInputPlaque.setOnFocusChangeListener((v, haseFocus)  -> handleBottomSheetBehaviorState());
        viewBinding.edtInputPostalCode.setOnFocusChangeListener((v, haseFocus)  -> handleBottomSheetBehaviorState());
        viewBinding.edtInputLocation.setOnFocusChangeListener((v, haseFocus)  -> handleBottomSheetBehaviorState());


    }

    private void handleGetIdentityType() {
        presenter.getIdentities();
    }

    private void handleGetLocation() {
      /*  viewBinding.mapView.setVisibility(View.VISIBLE);

        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));

        viewBinding.mapView.setTileSource(TileSourceFactory.MAPNIK);
        viewBinding.mapView.setMultiTouchControls(true);

        GoogleLocationProvider googleLocationProvider = new GoogleLocationProvider(getContext());

        IMapController mapController = new MapController(viewBinding.mapView);
        mapController.setCenter(new GeoPoint(googleLocationProvider.getLatitude() , googleLocationProvider.getLongitude()));
        mapController.setZoom(17.0);

        Marker marker = new Marker(viewBinding.mapView);
        marker.setPosition(new GeoPoint(googleLocationProvider.getLatitude() , googleLocationProvider.getLongitude()));
        marker.setTitle(getResources().getString(R.string.yourLocation));
        marker.setIcon(getResources().getDrawable(R.drawable.ic_user_marker));
        viewBinding.mapView.getOverlays().add(marker);*/
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
        handleBottomSheetBehaviorState();

        bottomSheetSearch.bottomSheetWithSearchAndRecyclerView(getContext(),
                getView(),
                baseSearchCityDbModels,
                true,
                getContext().getResources().getString(R.string.searchCity),
                false);

    }


    private void handleSearchProvince() {

        clearFocusEditTextsWithoutBtmSheet();
        handleBottomSheetBehaviorState();

        bottomSheetSearch.bottomSheetWithSearchAndRecyclerView(
                getContext(),
                getView(),
                baseSearchProvinceDbModels,
                true,
                getContext().getResources().getString(R.string.searchProvince),
                false);

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

    private void handleBottomSheetBehaviorState() {
        bottomSheetSearch.bottomSheetBehaviorStateHandler();
    }

    @Override
    protected FragmentRegisterBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentRegisterBinding.inflate(inflater, container, false);
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
        handleBottomSheetBehaviorState();

        bottomSheetSearch.bottomSheetWithSearchAndRecyclerView(getContext(),
                getView(),
                itemTitles,
                false,
                "",
                true);

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
        handleBottomSheetBehaviorState();

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
