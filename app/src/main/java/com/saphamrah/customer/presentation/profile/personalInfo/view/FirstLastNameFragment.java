package com.saphamrah.customer.presentation.profile.personalInfo.view;

import android.content.Context;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.databinding.FragmentFirstLastNameBinding;
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.presentation.main.welcome.view.MainFragment;
import com.saphamrah.customer.presentation.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.profile.ProfilePresenter;

public class FirstLastNameFragment extends BaseFragment<ProfilePresenter, FragmentFirstLastNameBinding, MainActivity> implements ProfileInteracts.RequiredViewOps {


    @Override
    protected void onBackPressed() {
        navigateUp();

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