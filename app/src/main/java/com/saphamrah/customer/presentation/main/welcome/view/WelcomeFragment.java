package com.saphamrah.customer.presentation.main.welcome.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.AdvertiseModel;
import com.saphamrah.customer.base.BaseStatePager;
import com.saphamrah.customer.data.local.database.AppDatabase;
import com.saphamrah.customer.data.local.database.entity.MoshtaryTable;
import com.saphamrah.customer.databinding.FragmentAddressBinding;
import com.saphamrah.customer.databinding.FragmentWelcomeBinding;
import com.saphamrah.customer.domain.repository.MoshtaryRepository;
import com.saphamrah.customer.presentation.main.MainInteracts;
import com.saphamrah.customer.presentation.main.MainPresenter;
import com.saphamrah.customer.presentation.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.profile.ProfilePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


public class WelcomeFragment extends BaseFragment<MainPresenter, FragmentWelcomeBinding> implements MainInteracts.RequiredViewOps {

    private BaseStatePager adapter;
    private ArrayList<AdvertiseModel> advertiseList;

    public WelcomeFragment(ArrayList<AdvertiseModel> advertiseList){
        super(R.layout.fragment_welcome);
        this.advertiseList = advertiseList;
    }

//        MoshtaryRepository moshtaryRepository = new MoshtaryRepository(getContext());
//
//        List<MoshtaryTable> moshtarys = new ArrayList<>();
//
//        moshtarys.add(new MoshtaryTable(11,"test","test","test","test","test","test","test","test","test","test"
//                ,"test",1,1,1,1));
//
//        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        compositeDisposable.add(moshtaryRepository.insertGroup(moshtarys).subscribe(() -> {
//                Log.i("TAG1234", "onSubscribe: ");
//            },
//            throwable -> {
//                Log.i("TAG1234", "onThrow: " + throwable.getMessage());
//        }
//        ));


    @Override
    protected void initViews() {
        setViewPager();
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentWelcomeBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentWelcomeBinding.inflate(inflater, container, false);
    }

    private void setViewPager() {
        adapter = new BaseStatePager(getChildFragmentManager());
        viewBinding.viewpager.setAdapter(adapter);

        for (AdvertiseModel aModel: advertiseList){
            addPages(aModel);
        }

        viewBinding.viewpager.setCurrentItem(advertiseList.size()-1);
    }

    public void addPages(AdvertiseModel advertiseModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", advertiseModel);
        AdvertiseFragment fragmentChild = new AdvertiseFragment();
        fragmentChild.setArguments(bundle);
        adapter.addFragment(fragmentChild, advertiseModel, advertiseModel.getTitle());
        if (adapter.getCount() > 0) {
            viewBinding.tabLayout.setupWithViewPager(viewBinding.viewpager);
        }
        adapter.notifyDataSetChanged();
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