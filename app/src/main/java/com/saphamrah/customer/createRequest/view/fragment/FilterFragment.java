package com.saphamrah.customer.createRequest.view.fragment;


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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseBottomDialogFragment;
import com.saphamrah.customer.base.BaseDialogFragment;
import com.saphamrah.customer.data.local.temp.FilterCategoryModel;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.createRequest.interactor.FilterMVPInteracts;
import com.saphamrah.customer.createRequest.presenter.FilterMVPPresenter;
import com.saphamrah.customer.createRequest.view.adapter.FilterChoiceAdapter;
import com.saphamrah.customer.databinding.FragmentFilterBinding;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class FilterFragment extends BaseBottomDialogFragment<FilterMVPPresenter, FragmentFilterBinding> implements FilterMVPInteracts.RequiredViewOps {

    private static final String TAG = FilterFragment.class.getSimpleName();
    private FilterChoiceAdapter filterChoiceAdapter;
    private ArrayList<FilterSortModel> filterSortModels;
    private Context context;

    public FilterFragment() {
        super(R.layout.fragment_filter);
    }



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








    private void setViews(int sortFilterType)
    {
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
                filterSortModels = new ArrayList<>(filterChoiceAdapter.getFilterSortList());
                FilterSortModel[] array = new FilterSortModel[filterSortModels.size()];
                for(int i = 0; i < filterSortModels.size(); i++) array[i] = filterSortModels.get(i);

                Log.i(TAG, "onBackPressed: "+filterSortModels);
                setFragmentResult("filters",filterSortModels.stream().filter(FilterSortModel::isEnabled).collect(Collectors.toList()));
                NavHostFragment.findNavController(FilterFragment.this).navigateUp();
            }
        });

        if (sortFilterType == 1){
            viewBinding.TVFilterAndSortTitle.setText(R.string.sort);
        }else{
            viewBinding.TVFilterAndSortTitle.setText(R.string.filter);

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setRecycler(int sortFilterType)
    {
        ArrayList<FilterCategoryModel> filterCategoryModels = new ArrayList<>();
        FilterCategoryModel filterCategory = new FilterCategoryModel(1,"برند",1);

        FilterCategoryModel filterCategory2 = new FilterCategoryModel(2,"گروه کالا",2);

        filterCategoryModels.add(filterCategory);
        filterCategoryModels.add(filterCategory2);

        filterSortModels = new ArrayList<>();
        FilterSortModel filterModelBrand = new FilterSortModel(100,2,"برند",0,false);
        FilterSortModel filterModelGorohKala = new FilterSortModel(200,2,"گروه کالا",0,false);
        FilterSortModel filterGheymatForosh = new FilterSortModel(300,3,"قیمت فروش",0,false);
        FilterSortModel filterGheymatMasrafKonandeh = new FilterSortModel(300,3,"قیمت مصرف کننده",0,false);
        FilterSortModel filterTarikh = new FilterSortModel(400,3,"تاریخ تولید",0,false);
        FilterSortModel filterEngheza = new FilterSortModel(500,3,"تاریخ انقضا",0,false);
        FilterSortModel filterModel = new FilterSortModel(1,2,"دلپذیر",100,false);
        FilterSortModel filterModel2 = new FilterSortModel(2,2,"پاکان",100,false);
        FilterSortModel filterModel3 = new FilterSortModel(3,2,"توکلی",100,false);
        FilterSortModel filterModel4 = new FilterSortModel(4,2,"فروتلند",100,false);
        FilterSortModel filterModel5 = new FilterSortModel(5,2,"سس",200,false);
        FilterSortModel filterModel6 = new FilterSortModel(6,2,"پنیر",200,false);
        FilterSortModel filterModel7 = new FilterSortModel(7,2,"دستمال کاغذی",200,false);
        FilterSortModel filterModel8 = new FilterSortModel(8,2,"کبریت",200,false);
        FilterSortModel filterModel9 = new FilterSortModel(9,1,"قیمت مصرف کننده",-1,false);
        FilterSortModel filterModel10 = new FilterSortModel(10,1,"موجودی",-1,false);
        FilterSortModel filterModel11 = new FilterSortModel(11,1,"قیمت فروش",-1,false);
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
        filterSortModels.add(filterModel8);
        filterSortModels.add(filterModel9);
        filterSortModels.add(filterModel10);
        filterSortModels.add(filterModel11);

        List<FilterSortModel> filterSortModelsAdapter = filterSortModels.stream().filter(filterSortModel -> (filterSortModel.getType() == 2 || filterSortModel.getType() == 3) && filterSortModel.getParent_id() == 0).collect(Collectors.toList());
        filterSortModelsAdapter.forEach(filterSortModel -> filterSortModel.setExpanded(false));
        filterSortModels.forEach(filterSortModel -> filterSortModel.setExpanded(false));
        Log.i(TAG, "setRecycler: " + filterSortModelsAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        filterChoiceAdapter = new FilterChoiceAdapter(sortFilterType,context,filterSortModels, filterSortModelsAdapter, (model, position, Action) -> {

        });
        viewBinding.RecyclerFilterSortChoice.setLayoutManager(gridLayoutManager);
        viewBinding.RecyclerFilterSortChoice.setAdapter(filterChoiceAdapter);
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