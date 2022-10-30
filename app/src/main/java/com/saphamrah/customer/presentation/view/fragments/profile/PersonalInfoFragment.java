package com.saphamrah.customer.presentation.view.fragments.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.databinding.FragmentFirstLastNameBinding;
import com.saphamrah.customer.databinding.FragmentPersonalInfoBinding;
import com.saphamrah.customer.presentation.interactors.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.presenters.profile.ProfilePresenter;


public class PersonalInfoFragment extends BaseFragment<ProfilePresenter, FragmentPersonalInfoBinding> implements ProfileInteracts.RequiredViewOps {


    public PersonalInfoFragment() {
        super(R.layout.fragment_personal_info);
    }

    @Override
    protected void initViews() {

        viewBinding.firstLastNameLl.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(),R.id.mainNavigation_host).navigate(R.id.action_personalInfoFragment_to_firstLastNameFragment);
        });

    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentPersonalInfoBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentPersonalInfoBinding.inflate(inflater, container, false);
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