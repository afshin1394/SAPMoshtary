package com.saphamrah.customer.presentation.profile.saleInfo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.SaleInfoModel;
import com.saphamrah.customer.databinding.FragmentSaleInfoBinding;
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.presentation.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.profile.ProfilePresenter;
import com.saphamrah.customer.presentation.profile.saleInfo.view.adapter.SaleInfoAdapter;

import java.util.ArrayList;


public class SaleInfoFragment extends BaseFragment<ProfilePresenter, FragmentSaleInfoBinding, MainActivity> implements ProfileInteracts.RequiredViewOps {



    @Override
    protected void onBackPressed() {
        navigateUp();
    }

    @Override
    protected void initViews() {
        ArrayList<SaleInfoModel> saleInfoModels = new ArrayList<>();

        saleInfoModels.add(new SaleInfoModel("خرده","خواربار فروشی","درجه 4","وجه نقد",10,"پخش پگاه"));
        saleInfoModels.add(new SaleInfoModel("عمده","خواربار فروشی","درجه 4","چک",10,"میهن"));
        saleInfoModels.add(new SaleInfoModel("خرده","خواربار فروشی","درجه 3","وجه نقد",15,"دلپذیر"));
        saleInfoModels.add(new SaleInfoModel("عمده","خواربار فروشی","درجه 2","وجه نقد",10,"پگاه"));
        saleInfoModels.add(new SaleInfoModel("خرده","خواربار فروشی","درجه 3","وجه نقد",20,"لینا"));

        SaleInfoAdapter adapter = new SaleInfoAdapter(requireContext(),saleInfoModels);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        viewBinding.recyclerView.setLayoutManager(mLayoutManager);
        viewBinding.recyclerView.setAdapter(adapter);

    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentSaleInfoBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSaleInfoBinding.inflate(inflater, container, false);
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