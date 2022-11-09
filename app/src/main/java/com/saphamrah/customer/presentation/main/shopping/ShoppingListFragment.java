package com.saphamrah.customer.presentation.main.shopping;

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
import com.saphamrah.customer.databinding.FragmentShoppingListBinding;
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.presentation.main.MainInteracts;
import com.saphamrah.customer.presentation.main.MainPresenter;

public class ShoppingListFragment extends BaseFragment<MainPresenter, FragmentShoppingListBinding, MainActivity> implements MainInteracts.RequiredViewOps {


    public ShoppingListFragment() {
        super(R.layout.fragment_shopping_list);
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentShoppingListBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentShoppingListBinding.inflate(inflater, container, false);
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
        return requireActivity();
    }
}