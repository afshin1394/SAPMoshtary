package com.saphamrah.customer.presentation.profile.accountNumber.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.AccountNumberModel;
import com.saphamrah.customer.databinding.FragmentAddressBinding;
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.presentation.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.profile.ProfilePresenter;
import com.saphamrah.customer.presentation.profile.accountNumber.view.adapter.AccountNumberAdapter;

import java.util.ArrayList;


public class AccountNumberFragment extends BaseFragment<ProfilePresenter, FragmentAddressBinding, MainActivity> implements ProfileInteracts.RequiredViewOps {


    @Override
    protected void onBackPressed() {
        navigateUp();
    }

    @Override
    protected void initViews() {
        ArrayList<AccountNumberModel> models = new ArrayList<>();

        models.add(new AccountNumberModel("بانک ملت", "حساب جاری", "8594 9641 2569 7843"));
        models.add(new AccountNumberModel("بانک تجارت", "حساب جاری", "8594 9641 2569 7843"));
        models.add(new AccountNumberModel("بانک ملت", "حساب جاری", "8594 9641 2569 7843"));
        models.add(new AccountNumberModel("بانک سپه", "حساب سپرده", "8594 9641 2569 7843"));
        models.add(new AccountNumberModel("بانک ملت", "حساب جاری", "8594 9641 2569 7843"));
        models.add(new AccountNumberModel("بانک ملت", "حساب سپرده", "8594 9641 2569 7843"));
        models.add(new AccountNumberModel("بانک اقتصاد", "حساب جاری", "8594 9641 2569 7843"));
        models.add(new AccountNumberModel("بانک ملت", "حساب جاری", "8594 9641 2569 7843"));
        models.add(new AccountNumberModel("بانک ملت", "حساب جاری", "8594 9641 2569 7843"));
        models.add(new AccountNumberModel("بانک تجارت", "حساب جاری", "8594 9641 2569 7843"));
        models.add(new AccountNumberModel("بانک ملت", "حساب سپرده", "8594 9641 2569 7843"));

        AccountNumberAdapter adapter = new AccountNumberAdapter(requireContext(),models);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        viewBinding.recyclerView.setLayoutManager(mLayoutManager);
        viewBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentAddressBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentAddressBinding.inflate(inflater, container, false);
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