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
import com.saphamrah.customer.databinding.FragmentFirstLastNameBinding;
import com.saphamrah.customer.databinding.FragmentSendOtpLoginBinding;
import com.saphamrah.customer.presentation.interactors.login.SendOtpLoginInteracts;
import com.saphamrah.customer.presentation.interactors.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.presenters.login.SendOtpLoginPresenter;
import com.saphamrah.customer.presentation.presenters.profile.ProfilePresenter;

public class FirstLastNameFragment extends BaseFragment<ProfilePresenter, FragmentFirstLastNameBinding> implements ProfileInteracts.RequiredViewOps {


    public FirstLastNameFragment() {
        super(R.layout.fragment_first_last_name);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setPresenter() {
        presenter = new ProfilePresenter(this);
    }

    @Override
    protected FragmentFirstLastNameBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentFirstLastNameBinding.inflate(inflater, container, false);
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
        return null;
    }
}