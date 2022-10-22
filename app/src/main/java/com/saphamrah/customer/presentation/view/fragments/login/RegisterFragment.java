package com.saphamrah.customer.presentation.view.fragments.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.LocationDbModel;
import com.saphamrah.customer.data.CityDbModel;
import com.saphamrah.customer.data.ProvinceDbModel;
import com.saphamrah.customer.databinding.FragmentRegisterBinding;
import com.saphamrah.customer.presentation.interactors.RegisterInteracts;
import com.saphamrah.customer.presentation.presenters.RegisterPresenter;
import com.saphamrah.customer.presentation.view.customView.BottomSheetSearch;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.RxTextWatcher;

import java.util.ArrayList;

public class RegisterFragment extends BaseFragment<RegisterPresenter, FragmentRegisterBinding> implements RegisterInteracts.RequiredViewOps, AdapterItemListener<LocationDbModel> {


    private ArrayList<ProvinceDbModel> provinceDbModels;
    private ArrayList<CityDbModel> cityDbModels;
    private RxTextWatcher rxTextWatcher;
    private BottomSheetSearch bottomSheetSearch;
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
        bottomSheetSearch = new BottomSheetSearch(this);
        clickListeners();

        rxTextWatcher = new RxTextWatcher();
        provinceDbModels = new ArrayList<>();
        cityDbModels = new ArrayList<>();
        baseSearchProvinceDbModels = new ArrayList<>();
        baseSearchCityDbModels = new ArrayList<>();

        /*provinceDbModels.add(new ProvinceDbModel("tehran"));
        provinceDbModels.add(new ProvinceDbModel("tehran1"));
        provinceDbModels.add(new ProvinceDbModel("tabriz"));
        provinceDbModels.add(new ProvinceDbModel("tabriz1"));*/


        baseSearchProvinceDbModels.add(new LocationDbModel("tehran", "province"));
        baseSearchProvinceDbModels.add(new LocationDbModel("tehran1", "province"));
        baseSearchProvinceDbModels.add(new LocationDbModel("tabriz", "province"));
        baseSearchProvinceDbModels.add(new LocationDbModel("tabriz1", "province"));

        baseSearchCityDbModels.add(new LocationDbModel("tehran", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("tehran1", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("tabriz", "city"));
        baseSearchCityDbModels.add(new LocationDbModel("tabriz1", "city"));



       /* cityDbModels.add(new CityDbModel("tehran"));
        cityDbModels.add(new CityDbModel("tehran1"));
        cityDbModels.add(new CityDbModel("tabriz"));
        cityDbModels.add(new CityDbModel("tabriz1"));*/

    }

    private void clickListeners() {
        viewBinding.btnApply.setOnClickListener(v -> checkValidityOfRegisterData());
        viewBinding.edtInputProvince.setOnClickListener(v -> handleSearchProvince());
        viewBinding.edtInputCity.setOnClickListener(v -> handleSearchCity());

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
        bottomSheetSearch.bottomSheetWithSearchAndRecyclerView(getContext(), getView(), baseSearchCityDbModels);

    }


    private void handleSearchProvince() {
        bottomSheetSearch.bottomSheetWithSearchAndRecyclerView(getContext(), getView(), baseSearchProvinceDbModels);

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
    public void onGetIdentities() {

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
    public void onItemSelect(LocationDbModel model, int position, AdapterAction Action) {
        if (model.getType().equals("province"))
            viewBinding.edtInputProvince.setText(model.getName());
        else if (model.getType().equals("city"))
            viewBinding.edtInputCity.setText(model.getName());
    }
}
