package com.saphamrah.customer.presentation.createRequest.filter.view.fragment;


import static com.saphamrah.customer.utils.Constants.MAX_CONSUMER_PRICE;
import static com.saphamrah.customer.utils.Constants.MAX_SELL_PRICE;
import static com.saphamrah.customer.utils.Constants.MIN_CONSUMER_PRICE;
import static com.saphamrah.customer.utils.Constants.MIN_SELL_PRICE;
import static com.saphamrah.customer.utils.Constants.SORT;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.customer.Constants;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseBottomDialogFragment;
import com.saphamrah.customer.data.local.temp.FilterCategoryModel;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.presentation.createRequest.filter.interactor.FilterMVPInteractor;
import com.saphamrah.customer.presentation.createRequest.filter.presenter.FilterMVPPresenter;
import com.saphamrah.customer.databinding.FragmentFilterBinding;
import com.saphamrah.customer.presentation.createRequest.filter.view.adapter.FilterChildChoiceAdapter;
import com.saphamrah.customer.presentation.createRequest.filter.view.adapter.FilterChoiceAdapter;
import com.saphamrah.customer.presentation.createRequest.filter.view.adapter.SortChoiceAdapter;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;


public class FilterFragment extends BaseBottomDialogFragment<FilterMVPPresenter, FragmentFilterBinding> implements FilterMVPInteractor.RequiredViewOps {

    private static final String TAG = FilterFragment.class.getSimpleName();
    private FilterChoiceAdapter filterChoiceAdapter;
    private SortChoiceAdapter sortChoiceAdapter;
    private List<FilterSortModel> filterSortModels;
    private List<FilterSortModel> selectedFilterSortModels = new ArrayList<>();
    private Context context;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    protected void onBackPressed() {
//        filterSortModels = new ArrayList<>(filterChoiceAdapter.getFilterSortList());
//        FilterSortModel[] array = new FilterSortModel[filterSortModels.size()];
//        for(int i = 0; i < filterSortModels.size(); i++) array[i] = filterSortModels.get(i);
//
//        Log.i(TAG, "onBackPressed: "+filterSortModels);
//
//        setFragmentResult("filters",filterSortModels);
//        NavHostFragment.findNavController(this).navigateUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews() {
        int sortFilterType = FilterFragmentArgs.fromBundle(getArguments()).getFilterSortType();
        setRecycler(sortFilterType);
        setViews(sortFilterType);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentFilterBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentFilterBinding.inflate(inflater, container, false);
    }


    private void setViews(int sortFilterType) {
        viewBinding.BTNCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FilterFragment.this).navigateUp();

            }
        });
        viewBinding.BTNConfirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (sortFilterType == 1)
                    filterSortModels = new ArrayList<>(sortChoiceAdapter.getFilterSortList());
                else
                    filterSortModels = new ArrayList<>(filterChoiceAdapter.getFilterSortList());

                FilterSortModel[] array = new FilterSortModel[filterSortModels.size()];
                for (int i = 0; i < filterSortModels.size(); i++)
                    array[i] = filterSortModels.get(i);

                Log.i(TAG, "onBackPressed222: " + selectedFilterSortModels);
                setFragmentResult("filters", selectedFilterSortModels);
                NavHostFragment.findNavController(FilterFragment.this).navigateUp();
            }
        });

        if (sortFilterType == 1) {
            viewBinding.TVFilterAndSortTitle.setText(R.string.sort);
        } else {
            viewBinding.TVFilterAndSortTitle.setText(R.string.filter);

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setRecycler(int sortFilterType) {
        ArrayList<FilterCategoryModel> filterCategoryModels = new ArrayList<>();
        FilterCategoryModel filterCategory = new FilterCategoryModel(1, "برند", 1);

        FilterCategoryModel filterCategory2 = new FilterCategoryModel(2, "گروه کالا", 2);

        filterCategoryModels.add(filterCategory);
        filterCategoryModels.add(filterCategory2);

        filterSortModels = new ArrayList<>();
        FilterSortModel filterModelBrand = new FilterSortModel(100, 2, 3, "برند", 0, false);
        FilterSortModel filterModelGorohKala = new FilterSortModel(200, 2, 3, "گروه کالا", 0, false);

        FilterSortModel filterModel = new FilterSortModel(101, 2, 3, "دلپذیر", 100, false);
        FilterSortModel filterModel2 = new FilterSortModel(102, 2, 3, "مهرام", 100, false);
        FilterSortModel filterModel3 = new FilterSortModel(103, 2, 3, "لینا", 100, false);
        FilterSortModel filterModel4 = new FilterSortModel(104, 2, 3, "کاله", 100, false);

        FilterSortModel filterModel5 = new FilterSortModel(1, 2, 3, "رب", 200, false);
        FilterSortModel filterModel6 = new FilterSortModel(3, 2, 3, "کنسرو", 200, false);
        FilterSortModel filterModel7 = new FilterSortModel(2, 2, 3, "سس", 200, false);
        FilterSortModel filterModel8 = new FilterSortModel(4, 2, 3, "بستنی", 200, false);
        FilterSortModel filterModel9 = new FilterSortModel(5, 2, 3, "آبمیوه", 200, false);
//        FilterSortModel filterModel8 = new FilterSortModel(8, 2, 3, "کبریت", 200, false);

        FilterSortModel filterModel10 = new FilterSortModel(9, 2, 4, "قیمت مصرف کننده", -1, false);
        FilterSortModel filterModel11 = new FilterSortModel(10, 2, 4, "موجودی", -1, false);
        FilterSortModel filterModel12 = new FilterSortModel(11, 2, 4, "قیمت فروش", -1, false);

        FilterSortModel filterGheymatForosh = new FilterSortModel(MAX_SELL_PRICE, 1, -1, "بیشترین قیمت فروش", 0, false);
        FilterSortModel filterGheymatMasrafKonandeh = new FilterSortModel(MIN_SELL_PRICE, 1, -1, "کمترین قیمت فروش", 0, false);
        FilterSortModel filterTarikh = new FilterSortModel(MAX_CONSUMER_PRICE, 1, -1, "بیشترین قیمت مصرف کننده", 0, false);
        FilterSortModel filterEngheza = new FilterSortModel(MIN_CONSUMER_PRICE, 1, -1, "کمترین قیمت مصرف کننده", 0, false);
        filterSortModels.add(filterModelBrand);
        filterSortModels.add(filterModelGorohKala);
        filterSortModels.add(filterGheymatForosh);
        filterSortModels.add(filterGheymatMasrafKonandeh);
        filterSortModels.add(filterTarikh);
        filterSortModels.add(filterEngheza);
        filterSortModels.add(filterModel);
        filterSortModels.add(filterModel2);
        filterSortModels.add(filterModel3);
        filterSortModels.add(filterModel4);
        filterSortModels.add(filterModel5);
        filterSortModels.add(filterModel6);
        filterSortModels.add(filterModel7);
        filterSortModels.add(filterModel9);
        filterSortModels.add(filterModel10);
        filterSortModels.add(filterModel11);
        filterSortModels.add(filterModel12);


        List<FilterSortModel> filterSortModelsTitle = filterSortModels.stream().filter(filterSortModel -> filterSortModel.getType() == 2 && filterSortModel.getFilterType() == 3 && filterSortModel.getParent_id() == 0).collect(Collectors.toList());
        List<FilterSortModel> sortList = Observable.fromIterable(filterSortModels).filter(new Predicate<FilterSortModel>() {
            @Override
            public boolean test(FilterSortModel filterSortModel) {
                return filterSortModel.getType() == 1;
            }
        }).toList().blockingGet();

        filterSortModelsTitle.forEach(filterSortModel -> filterSortModel.setExpanded(false));
        filterSortModels.forEach(filterSortModel -> filterSortModel.setExpanded(false));
        Log.i(TAG, "setRecycler: " + filterSortModelsTitle);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        if (sortFilterType == 1) {

            sortChoiceAdapter = new SortChoiceAdapter(context, sortList, new AdapterItemListener<FilterSortModel>() {
                @Override
                public void onItemSelect(FilterSortModel model, int position, AdapterAction Action) {
                    if (!selectedFilterSortModels.contains(model) && model.isEnabled()) {
                        for (int i = 0; i < selectedFilterSortModels.size(); i++) {
                            if (selectedFilterSortModels.get(i).getType() == SORT)
                                selectedFilterSortModels.remove(i);
                        }
                        selectedFilterSortModels.add(model);

                        }


                }
            });

            viewBinding.RecyclerFilterSortChoice.setLayoutManager(gridLayoutManager);
            viewBinding.RecyclerFilterSortChoice.setAdapter(sortChoiceAdapter);
        } else {

            filterChoiceAdapter = new FilterChoiceAdapter(context, filterSortModels, (model, position, Action) -> {
                if(!selectedFilterSortModels.contains(model))
                selectedFilterSortModels.add(model);
            });
            viewBinding.RecyclerFilterSortChoice.setLayoutManager(gridLayoutManager);
            viewBinding.RecyclerFilterSortChoice.setAdapter(filterChoiceAdapter);
        }
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
        return context;
    }
}