package com.saphamrah.customer.presentation.main.shopping;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.RptStatusModel;
import com.saphamrah.customer.databinding.FragmentShoppingListBinding;
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.presentation.main.MainInteracts;
import com.saphamrah.customer.presentation.main.MainPresenter;
import com.saphamrah.customer.presentation.main.shopping.adapter.RptStatusAdapter;

import java.util.ArrayList;

public class ShoppingListFragment extends BaseFragment<MainPresenter, FragmentShoppingListBinding, MainActivity> implements MainInteracts.RequiredViewOps {



    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initViews() {

        ArrayList<RptStatusModel> models = new ArrayList<>();

        models.add(new RptStatusModel("2254652145", "1401/05/09","2,450,000","نقد",1,true));
        models.add(new RptStatusModel("2254652145", "1401/05/09","2,450,000","نقد",2,true));
        models.add(new RptStatusModel("2254652145", "1401/05/09","2,450,000","نقد",3,true));
        models.add(new RptStatusModel("2254652145", "1401/05/09","2,450,000","نقد",4,true));
        models.add(new RptStatusModel("2254652145", "1401/05/09","2,450,000","نقد","کمبود موجودی",1,false));

        RptStatusAdapter adapter = new RptStatusAdapter(requireContext(),models);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        viewBinding.recyclerView.setLayoutManager(mLayoutManager);
        viewBinding.recyclerView.setAdapter(adapter);

        viewBinding.shimmerViewContainer.startShimmer();

        new Handler().postDelayed(() -> {
            viewBinding.shimmerViewContainer.stopShimmer();
            viewBinding.shimmerViewContainer.setVisibility(View.GONE);
        }, 5000);

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