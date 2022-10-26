package com.saphamrah.customer.presentation.view.fragments.login;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.data.LocationDbModel;
import com.saphamrah.customer.databinding.FragmentRegisterBinding;
import com.saphamrah.customer.presentation.interactors.RegisterInteracts;
import com.saphamrah.customer.presentation.presenters.RegisterPresenter;
import com.saphamrah.customer.presentation.view.customView.BottomSheetRecyclerView;
import com.saphamrah.customer.presentation.view.customView.BottomSheetSearchRecyclerView;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.KeyboardStateHandler;

import java.util.ArrayList;

public class RegisterFragment extends BaseFragment<RegisterPresenter, FragmentRegisterBinding> implements RegisterInteracts.RequiredViewOps, AdapterItemListener<BaseBottomSheetRecyclerModel> {


    private BottomSheetSearchRecyclerView bottomSheetSearch;
    private BottomSheetRecyclerView bottomSheetRecyclerView;
    private ArrayList<LocationDbModel> baseSearchProvinceDbModels;
    private ArrayList<LocationDbModel> baseSearchCityDbModels;


    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    protected void setPresenter() {
        presenter = new RegisterPresenter(this);
    }

    @Override
    protected void initViews() {
        bottomSheetSearch = new BottomSheetSearchRecyclerView(this);
        bottomSheetRecyclerView = new BottomSheetRecyclerView(this);

        clickListeners();

        baseSearchProvinceDbModels = new ArrayList<>();
        baseSearchCityDbModels = new ArrayList<>();

        baseSearchProvinceDbModels.add(new LocationDbModel("tehran", "province"));
        baseSearchProvinceDbModels.add(new LocationDbModel("tehran1", "province"));
        baseSearchProvinceDbModels.add(new LocationDbModel("tabriz", "province"));
        baseSearchProvinceDbModels.add(new LocationDbModel("tabriz1", "province"));

        baseSearchCityDbModels.add(new LocationDbModel("tehran", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("tehran1", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("tabriz", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("tabriz1", "city"));


    }

    private void clickListeners() {
        viewBinding.btnApply.setOnClickListener(v -> checkValidityOfRegisterData());
        viewBinding.edtInputProvince.setOnClickListener(v -> handleSearchProvince());
        viewBinding.edtInputCity.setOnClickListener(v -> handleSearchCity());
//        viewBinding.edtInputLocation.setOnClickListener(v -> handleGetLocation());
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
       /* viewBinding.mapView.setVisibility(View.VISIBLE);

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
                getContext().getResources().getString(R.string.searchCity));

    }


    private void handleSearchProvince() {

        clearFocusEditTextsWithoutBtmSheet();
        handleBottomSheetBehaviorState();

        bottomSheetSearch.bottomSheetWithSearchAndRecyclerView(
                getContext(),
                getView(),
                baseSearchProvinceDbModels,
                getContext().getResources().getString(R.string.searchProvince));

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
        bottomSheetRecyclerView.bottomSheetBehaviorStateHandler();
    }

    @Override
    protected FragmentRegisterBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentRegisterBinding.inflate(inflater, container, false);
    }

    @Override
    public void onError(String error) {

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

        KeyboardStateHandler.hideKeyboard(getContext(), getView());
        clearFocusEditTextsWithoutBtmSheet();
        bottomSheetSearch.bottomSheetBehaviorStateHandler();

        bottomSheetRecyclerView.bottomSheetWithSearchAndRecyclerView(getContext(),
                getView(),
                itemTitles);

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
}
