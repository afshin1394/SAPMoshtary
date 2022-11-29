package com.saphamrah.customer.presentation.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.databinding.FragmentAddressBinding;
import com.saphamrah.customer.databinding.FragmentMainBinding;
import com.saphamrah.customer.databinding.FragmentProfileBinding;
import com.saphamrah.customer.presentation.main.MainActivity;


public class ProfileFragment extends BaseFragment<ProfilePresenter, FragmentProfileBinding, MainActivity> implements ProfileInteracts.RequiredViewOps {

    NavigationView navigationView;

    public ProfileFragment(){
        super(R.layout.fragment_profile);
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initViews() {
        hideMenuItem();

        viewBinding.personalInfoLl.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(),R.id.mainNavigation_host).navigate(R.id.action_profileFragment_to_personalInfoFragment);
        });

        viewBinding.addressLl.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(),R.id.mainNavigation_host).navigate(R.id.action_profileFragment_to_addressFragment);
        });

        viewBinding.hesabLl.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(),R.id.mainNavigation_host).navigate(R.id.action_profileFragment_to_accountNumberFragment);
        });

        viewBinding.saleInfoLl.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(),R.id.mainNavigation_host).navigate(R.id.action_profileFragment_to_saleInfoFragment);
        });
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentProfileBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentProfileBinding.inflate(inflater, container, false);
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

    private void hideMenuItem()
    {
        navigationView = activity.findViewById(R.id.drawer_main);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_profile).setVisible(false);
    }
}