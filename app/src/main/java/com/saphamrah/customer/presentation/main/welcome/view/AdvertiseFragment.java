package com.saphamrah.customer.presentation.main.welcome.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.presentation.main.MainInteracts;
import com.saphamrah.customer.presentation.main.MainPresenter;
import com.saphamrah.customer.presentation.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.profile.ProfilePresenter;
import com.saphamrah.customer.utils.customViews.OnSingleClickListener;

import java.util.List;


public class AdvertiseFragment extends BaseFragment<MainPresenter, FragmentAdvertiseBinding, MainActivity> implements MainInteracts.RequiredViewOps {


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

        //link
        String link = model.getLink();
        viewBinding.adsImageview.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        });


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