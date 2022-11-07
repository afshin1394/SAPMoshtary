package com.saphamrah.customer.presentation.main.welcome.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.AdvertiseModel;
import com.saphamrah.customer.databinding.FragmentAddressBinding;
import com.saphamrah.customer.databinding.FragmentAdvertiseBinding;
import com.saphamrah.customer.presentation.main.MainInteracts;
import com.saphamrah.customer.presentation.main.MainPresenter;
import com.saphamrah.customer.presentation.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.profile.ProfilePresenter;

import java.util.List;


public class AdvertiseFragment extends BaseFragment<MainPresenter, FragmentAdvertiseBinding> implements MainInteracts.RequiredViewOps {


    public AdvertiseFragment() {
        super(R.layout.fragment_advertise);
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initViews() {
        setAdsItems();
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentAdvertiseBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentAdvertiseBinding.inflate(inflater, container, false);
    }

    private void setAdsItems() {
        assert getArguments() != null;
        AdvertiseModel model = (AdvertiseModel) getArguments().getSerializable("data");

        String uri = model.getImageUrl();

        int imageResource = getResources().getIdentifier(uri, null, getContext().getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        viewBinding.adsImageview.setImageDrawable(res);
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