package com.saphamrah.customer.presentation.createRequest.filter.view.fragment;


import static com.saphamrah.customer.utils.Constants.FILTER;
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

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseBottomDialogFragment;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.presentation.createRequest.filter.interactor.FilterMVPInteractor;
import com.saphamrah.customer.presentation.createRequest.filter.presenter.FilterMVPPresenter;
import com.saphamrah.customer.databinding.FragmentFilterBinding;
import com.saphamrah.customer.presentation.createRequest.filter.view.adapter.FilterChoiceAdapter;
import com.saphamrah.customer.presentation.createRequest.filter.view.adapter.SortChoiceAdapter;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;


public class FilterFragment extends BaseBottomDialogFragment<CreateRequestActivity,FilterMVPPresenter, FragmentFilterBinding> implements FilterMVPInteractor.RequiredViewOps {

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
                Log.i(TAG, "BTNConfirm:" + activity.getFilterSortModels());
                if (sortFilterType == 1)
                    filterSortModels = new ArrayList<>(sortChoiceAdapter.getFilterSortList());
                else
                    filterSortModels = new ArrayList<>(filterChoiceAdapter.getFilterSortList());

                Log.i(TAG, "BTNConfirm:" + activity.getFilterSortModels());
                FilterSortModel[] array = new FilterSortModel[filterSortModels.size()];
                for (int i = 0; i < filterSortModels.size(); i++)
                    array[i] = filterSortModels.get(i);


                if (sortFilterType == SORT)
                    activity.disableFilters();
                if (sortFilterType == FILTER)
                    activity.disableSort();
                selectedFilterSortModels.addAll(Observable.fromIterable(filterSortModels).filter(filterSortModel -> filterSortModel.isEnabled()).toList().blockingGet());
                Set<FilterSortModel> filterSortModels = new HashSet<>(selectedFilterSortModels);
                setFragmentResult("filters", filterSortModels);


                NavHostFragment.findNavController(FilterFragment.this).navigateUp();
                Log.i(TAG, "BTNConfirm:" + activity.getFilterSortModels());
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

        filterSortModels = activity.getFilterSortModels();
        Log.i(TAG, "setRecycler: "+activity.getFilterSortModels());
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
                    activity.updateSortChoice(model);
                    Log.i(TAG, "setRecycler: "+activity.getFilterSortModels());
                    if (!selectedFilterSortModels.contains(model) && model.isEnabled())
                    {
                        for (int i = 0; i < selectedFilterSortModels.size(); i++) {

                            if (selectedFilterSortModels.get(i).getType() == SORT)
                                selectedFilterSortModels.remove(i);

                        }
                        selectedFilterSortModels.add(model);
                    }
                    Log.i(TAG, "setRecycler: "+activity.getFilterSortModels());
                }
            });

            viewBinding.RecyclerFilterSortChoice.setLayoutManager(gridLayoutManager);
            viewBinding.RecyclerFilterSortChoice.setAdapter(sortChoiceAdapter);
        } else {

            filterChoiceAdapter = new FilterChoiceAdapter(context, filterSortModels, (model, position, Action) -> {
                if(!selectedFilterSortModels.contains(model) && model.isEnabled()) {
                    selectedFilterSortModels.add(model);
                }else {
                    if (model.getFilterType() == 3) {
                        selectedFilterSortModels.remove(model);
                    }
                }
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