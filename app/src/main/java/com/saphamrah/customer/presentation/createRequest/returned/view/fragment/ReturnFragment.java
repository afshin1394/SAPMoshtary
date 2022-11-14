package com.saphamrah.customer.presentation.createRequest.returned.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.databinding.FragmentReturnBinding;
import com.saphamrah.customer.databinding.FragmentReturnedBinding;
import com.saphamrah.customer.databinding.FragmentSelectableBonusBinding;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.presentation.createRequest.returned.interactor.ReturnedInteractor;
import com.saphamrah.customer.presentation.createRequest.returned.presenter.ReturnedPresenter;
import com.saphamrah.customer.presentation.createRequest.returned.view.adapter.ReturnedAdapter;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.interactor.SelectableBonusInteractor;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.List;


public class ReturnFragment extends BaseFragment<ReturnedInteractor.PresenterOps, FragmentReturnBinding, CreateRequestActivity> implements ReturnedInteractor.RequiredViewOps {
    private ReturnedAdapter marjoeeAdapter;
    private boolean init;
    public ReturnFragment() {
        super(R.layout.fragment_returned);
    }

    @Override
    protected void onBackPressed() {


    }

    @Override
    protected void initViews() {
        init = true;
        presenter.getMarjoee();
    }

    private void setMarjoeeRecycler(List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,false);
        marjoeeAdapter = new ReturnedAdapter(context, elamMarjoeeForoshandehModels, new AdapterItemListener<ElamMarjoeeForoshandehModel>() {
            @Override
            public void onItemSelect(ElamMarjoeeForoshandehModel model, int position, AdapterAction Action) {
            }
        });
        viewBinding.RVOrderedProducts.setLayoutManager(linearLayoutManager);
        viewBinding.RVOrderedProducts.setAdapter(marjoeeAdapter);
    }

    @Override
    protected void setPresenter() {
        presenter = new ReturnedPresenter(this);
    }

    @Override
    protected FragmentReturnBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentReturnBinding.inflate(inflater, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_returned, container, false);
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
        if (init) {
            setMarjoeeRecycler(elamMarjoeeForoshandehModels);
            init = false;
        }else{
            marjoeeAdapter.notifyDataSetChanged();
        }
    }
}