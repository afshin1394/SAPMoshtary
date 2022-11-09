package com.saphamrah.customer.presentation.profile.personalInfo.view;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.databinding.FragmentPersonalInfoBinding;
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.presentation.main.welcome.view.MainFragment;
import com.saphamrah.customer.presentation.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.profile.ProfilePresenter;


public class PersonalInfoFragment extends BaseFragment<ProfilePresenter, FragmentPersonalInfoBinding, MainActivity> implements ProfileInteracts.RequiredViewOps {


    public PersonalInfoFragment() {
        super(R.layout.fragment_personal_info);
    }

    @Override
    protected void onBackPressed() {

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