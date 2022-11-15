package com.saphamrah.customer.presentation.createRequest.returned.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.databinding.FragmentReturnBinding;
import com.saphamrah.customer.databinding.FragmentReturnedBinding;
import com.saphamrah.customer.databinding.FragmentSelectableBonusBinding;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.presentation.createRequest.cart.view.fragment.CartFragmentDirections;
import com.saphamrah.customer.presentation.createRequest.returned.interactor.ReturnedInteractor;
import com.saphamrah.customer.presentation.createRequest.returned.presenter.ReturnedPresenter;
import com.saphamrah.customer.presentation.createRequest.returned.view.adapter.ReturnedAdapter;
import com.saphamrah.customer.presentation.createRequest.returned.view.adapter.SelectedReturnedAdapter;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.interactor.SelectableBonusInteractor;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ReturnFragment extends BaseFragment<ReturnedInteractor.PresenterOps, FragmentReturnBinding, CreateRequestActivity> implements ReturnedInteractor.RequiredViewOps {
    private ReturnedAdapter marjoeeAdapter;
    private boolean init;
    private SelectedReturnedAdapter selectedReturnedAdapter;
    BottomSheetBehavior bottomSheetBehavior;
    public ReturnFragment() {
        super(R.layout.fragment_returned);
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initViews() {
        bottomSheetBehavior=BottomSheetBehavior.from(viewBinding.btmShtReturn.linBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        setSelectedMarjoeeRecycler();
        viewBinding.btmShtReturn.linConfirmMarjoee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setMarjoee = true;
                navigateUp();
            }
        });
    }

    private void setSelectedMarjoeeRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,false);
        selectedReturnedAdapter = new SelectedReturnedAdapter(context);
        viewBinding.btmShtReturn.RVSelectedMarjoee.setLayoutManager(linearLayoutManager);
        viewBinding.btmShtReturn.RVSelectedMarjoee.setAdapter(selectedReturnedAdapter);
    }

    private void setMarjoeeRecycler(List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels) {
        List<ElamMarjoeeForoshandehModel> backup = new ArrayList<>();

        backup.addAll(elamMarjoeeForoshandehModels);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,false);

        marjoeeAdapter = new ReturnedAdapter(context, elamMarjoeeForoshandehModels, new AdapterItemListener<ElamMarjoeeForoshandehModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelect(ElamMarjoeeForoshandehModel model, int position, AdapterAction Action) {
                 switch (Action){
                     case REMOVE:
                     case ADD:
                         Log.i(TAG, "onItemSelect: "+elamMarjoeeForoshandehModels.get(position).getTedad3());
                         Log.i(TAG, "onItemSelect: "+backup.get(position).getTedad3());
                         List<ElamMarjoeeForoshandehModel> marjoees = backup.stream().filter(backup -> backup.getTedad3()>0).collect(Collectors.toList());
                         if (marjoees.size()> 0)
                             expand();
                         else
                             collapse();

                         selectedReturnedAdapter.submitList(marjoees);

                         break;
                 }
            }
        });
        viewBinding.RVOrderedProducts.setLayoutManager(linearLayoutManager);
        viewBinding.RVOrderedProducts.setAdapter(marjoeeAdapter);
    }

    @Override
    protected void setPresenter() {
        presenter = new ReturnedPresenter(this);
        init = true;
        presenter.getMarjoee();

    }

    @Override
    protected FragmentReturnBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentReturnBinding.inflate(inflater, container, false);
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onGetMarjoee(List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels) {
        activity.setElamMarjoeeForoshandehModelsGlobal(elamMarjoeeForoshandehModels);
        if (init) {
            setMarjoeeRecycler(elamMarjoeeForoshandehModels);
            init = false;
        }
        else
        {
            marjoeeAdapter.notifyDataSetChanged();
        }
    }


    private void expand() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
    private void collapse() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
    }