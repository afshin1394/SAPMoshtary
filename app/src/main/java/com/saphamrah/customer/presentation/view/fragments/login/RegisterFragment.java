package com.saphamrah.customer.presentation.view.fragments.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.databinding.FragmentRegisterBinding;
import com.saphamrah.customer.presentation.interactors.RegisterInteracts;
import com.saphamrah.customer.presentation.presenters.RegisterPresenter;

public class RegisterFragment extends BaseFragment<RegisterPresenter, FragmentRegisterBinding> implements RegisterInteracts.RequiredViewOps {

    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    protected void setPresenter() {
        presenter = new RegisterPresenter(this);
    }

    @Override
    protected void initViews() {

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
}
