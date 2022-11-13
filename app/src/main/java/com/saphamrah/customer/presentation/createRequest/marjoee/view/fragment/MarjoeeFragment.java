package com.saphamrah.customer.presentation.createRequest.marjoee.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.databinding.FragmentMarjoeeBinding;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.presentation.createRequest.marjoee.interactor.MarjoeeInteractor;
import com.saphamrah.customer.presentation.createRequest.marjoee.model.MarjoeeModel;
import com.saphamrah.customer.presentation.createRequest.marjoee.presenter.MarjoeePresenter;


public class MarjoeeFragment extends BaseFragment<MarjoeeInteractor.PresenterOps, FragmentMarjoeeBinding, CreateRequestActivity> implements MarjoeeInteractor.RequiredViewOps {


    public MarjoeeFragment() {
        super(R.layout.fragment_marjoee);
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setPresenter() {
     presenter = new MarjoeePresenter(this);
    }

    @Override
    protected FragmentMarjoeeBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_marjoee, container, false);
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