package com.saphamrah.customer.presentation.view.fragments.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.databinding.FragmentAddressBinding;
import com.saphamrah.customer.databinding.FragmentCreditBinding;
import com.saphamrah.customer.presentation.presenters.profile.ProfilePresenter;


public class CreditFragment extends BaseFragment<ProfilePresenter, FragmentCreditBinding> {


    public CreditFragment() {
        super(R.layout.fragment_credit);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentCreditBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentCreditBinding.inflate(inflater, container, false);
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
        return requireContext();
    }
}