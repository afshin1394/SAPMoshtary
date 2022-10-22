package com.saphamrah.customer.presentation.view.fragments.login;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.CityDbModel;
import com.saphamrah.customer.data.ProvinceDbModel;
import com.saphamrah.customer.data.network.model.RegisterNetworkModel;
import com.saphamrah.customer.databinding.FragmentRegisterBinding;
import com.saphamrah.customer.presentation.interactors.RegisterInteracts;
import com.saphamrah.customer.presentation.presenters.RegisterPresenter;
import com.saphamrah.customer.presentation.view.adapter.recycler.SearchCityAdapter;
import com.saphamrah.customer.presentation.view.adapter.recycler.SearchProvinceAdapter;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.RxTextWatcher;

import java.util.ArrayList;

public class RegisterFragment extends BaseFragment<RegisterPresenter, FragmentRegisterBinding> implements RegisterInteracts.RequiredViewOps {

    private RegisterNetworkModel registerNetworkModel;
    private ArrayList<ProvinceDbModel> provinceDbModels;
    private ArrayList<CityDbModel> cityDbModels;
    private RxTextWatcher rxTextWatcher;
    private SearchProvinceAdapter searchProvinceAdapter;
    private SearchCityAdapter searchCityAdapter;
    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetBehavior;
    private MaterialSearchView searchView;
    private RecyclerView recyclerViewSearchResult;
    private ArrayList<ProvinceDbModel> filteredListProvinceDbModel;
    private ArrayList<CityDbModel> filteredListCityDbModel;


    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    protected void setPresenter() {
        presenter = new RegisterPresenter(this);
    }

    @Override
    protected void initViews() {

        //BottomSheet
        LinearLayout lnrlayBottomsheet = getView().findViewById(R.id.linBottomSheet);
        searchView = getView().findViewById(R.id.searchView);
        bottomSheetBehavior = BottomSheetBehavior.from(lnrlayBottomsheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        clickListeners();

        rxTextWatcher = new RxTextWatcher();
        provinceDbModels = new ArrayList<>();
        cityDbModels = new ArrayList<>();

        provinceDbModels.add(new ProvinceDbModel("tehran"));
        provinceDbModels.add(new ProvinceDbModel("tehran1"));
        provinceDbModels.add(new ProvinceDbModel("tabriz"));
        provinceDbModels.add(new ProvinceDbModel("tabriz1"));

        cityDbModels.add(new CityDbModel("tehran"));
        cityDbModels.add(new CityDbModel("tehran1"));
        cityDbModels.add(new CityDbModel("tabriz"));
        cityDbModels.add(new CityDbModel("tabriz1"));

        recyclerViewSearchResult = getView().findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSearchResult.setLayoutManager(linearLayoutManager);
        recyclerViewSearchResult.addItemDecoration(
                new DividerItemDecoration(
                        recyclerViewSearchResult.getContext(),
                        DividerItemDecoration.HORIZONTAL));


    }

    private void filterCity(String query) {
        filteredListCityDbModel = new ArrayList<>();

        for (int i = 0; i < cityDbModels.size(); i++) {

            final String text = cityDbModels.get(i).getName().toLowerCase();
            if (text.contains(query)) {

                filteredListCityDbModel.add(cityDbModels.get(i));
            }
        }

        Log.d("RegisterFragment", "filteredList: " + filteredListCityDbModel);

        searchCityAdapter = new SearchCityAdapter(getContext(), filteredListCityDbModel, new AdapterItemListener<CityDbModel>() {
            @Override
            public void onItemSelect(CityDbModel cityDbModel, int position, AdapterAction Action) {
                viewBinding.edtInputCity.setText(cityDbModel.getName());
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                searchView.closeSearch();
                filteredListCityDbModel.clear();
                recyclerViewSearchResult.setVisibility(View.GONE);
                recyclerViewSearchResult.removeAllViews();
                searchCityAdapter.notifyDataSetChanged();
            }

        });
        recyclerViewSearchResult.setAdapter(searchCityAdapter);

        recyclerViewSearchResult.removeAllViews();
        searchCityAdapter.notifyDataSetChanged();  // data set changed

    }

    private void filterProvince(String query) {
        filteredListProvinceDbModel = new ArrayList<>();

        for (int i = 0; i < provinceDbModels.size(); i++) {

            final String text = provinceDbModels.get(i).getName().toLowerCase();
            if (text.contains(query)) {

                filteredListProvinceDbModel.add(provinceDbModels.get(i));
            }
        }

        Log.d("RegisterFragment", "filteredList: " + filteredListProvinceDbModel);

        searchProvinceAdapter = new SearchProvinceAdapter(getContext(), filteredListProvinceDbModel, new AdapterItemListener<ProvinceDbModel>() {
            @Override
            public void onItemSelect(ProvinceDbModel provinceDbModel, int position, AdapterAction Action) {
                viewBinding.edtInputProvince.setText(provinceDbModel.getName());
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                searchView.closeSearch();
                filteredListProvinceDbModel.clear();
                recyclerViewSearchResult.setVisibility(View.GONE);
                recyclerViewSearchResult.removeAllViews();
                searchProvinceAdapter.notifyDataSetChanged();
            }
        });
        recyclerViewSearchResult.setAdapter(searchProvinceAdapter);

        recyclerViewSearchResult.removeAllViews();
        searchProvinceAdapter.notifyDataSetChanged();  // data set changed

    }

    private void clickListeners() {
        viewBinding.btnApply.setOnClickListener(v -> checkValidityOfRegisterData());
        viewBinding.edtInputProvince.setOnClickListener(v -> handleSearchProvince());
        viewBinding.edtInputCity.setOnClickListener(v -> handleSearchCity());
        getView().findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            searchView.closeSearch();
           /* filteredListProvinceDbModel.clear();
            filteredListCityDbModel.clear();*/
            recyclerViewSearchResult.setVisibility(View.GONE);
            recyclerViewSearchResult.removeAllViews();
           /* searchProvinceAdapter.notifyDataSetChanged();
            searchCityAdapter.notifyDataSetChanged();*/// data set changed

        });

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
        searchCityAdapter = new SearchCityAdapter(getContext(), cityDbModels, new AdapterItemListener<CityDbModel>() {
            @Override
            public void onItemSelect(CityDbModel cityDbModel, int position, AdapterAction Action) {
                viewBinding.edtInputCity.setText(cityDbModel.getName());
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                searchView.closeSearch();
                recyclerViewSearchResult.setVisibility(View.GONE);
                recyclerViewSearchResult.removeAllViews();
                searchCityAdapter.notifyDataSetChanged();

            }

        });
        recyclerViewSearchResult.setAdapter(searchCityAdapter);

        recyclerViewSearchResult.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(getView().getMeasuredHeight()/3);
        searchView.showSearch(true);
        searchView.setHint(getResources().getString(R.string.searchCity));

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchWord = query.trim();
                filterCity(searchWord);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0) {
                    filterCity(newText);
                } else {
                    visibleCloseSearchIcon();
                }
                return false;
            }
        });
    }


    private void handleSearchProvince() {
        searchProvinceAdapter = new SearchProvinceAdapter(getContext(), provinceDbModels, new AdapterItemListener<ProvinceDbModel>() {
            @Override
            public void onItemSelect(ProvinceDbModel provinceDbModel, int position, AdapterAction Action) {
                viewBinding.edtInputProvince.setText(provinceDbModel.getName());
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                searchView.closeSearch();
                recyclerViewSearchResult.setVisibility(View.GONE);
                recyclerViewSearchResult.removeAllViews();
                searchProvinceAdapter.notifyDataSetChanged();

            }

        });
        recyclerViewSearchResult.setAdapter(searchProvinceAdapter);

        recyclerViewSearchResult.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(getView().getMeasuredHeight()/3);
        searchView.showSearch(true);
        searchView.setHint(getResources().getString(R.string.searchProvince));

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchWord = query.trim();
                filterProvince(searchWord);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0) {
                    filterProvince(newText);
                } else {
                    visibleCloseSearchIcon();
                }
                return false;
            }
        });

    }

    private void visibleCloseSearchIcon() {
        getView().findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);

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
}
