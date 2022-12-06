package com.saphamrah.customer.presentation.main.welcome.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.AdvertiseModel;
import com.saphamrah.customer.base.BaseStatePager;
import com.saphamrah.customer.databinding.FragmentWelcomeBinding;
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.presentation.main.MainInteracts;
import com.saphamrah.customer.presentation.main.MainPresenter;

import java.util.ArrayList;


public class WelcomeFragment extends BaseFragment<MainPresenter, FragmentWelcomeBinding, MainActivity> implements MainInteracts.RequiredViewOps {

    private BaseStatePager adapter;
    private ArrayList<AdvertiseModel> advertiseList;

    public WelcomeFragment(ArrayList<AdvertiseModel> advertiseList){
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
    protected void onBackPressed() {

    }

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