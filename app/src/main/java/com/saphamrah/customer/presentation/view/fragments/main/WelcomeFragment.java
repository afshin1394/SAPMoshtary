package com.saphamrah.customer.presentation.view.fragments.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.AdvertiseModel;
import com.saphamrah.customer.base.BaseStatePager;

import java.util.ArrayList;


public class WelcomeFragment extends Fragment {

    private BaseStatePager adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<AdvertiseModel> advertiseList;

    public WelcomeFragment(ArrayList<AdvertiseModel> advertiseList){
        this.advertiseList = advertiseList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabLayout);
        setViewPager();
        return view;
    }

    private void setViewPager() {
        adapter = new BaseStatePager(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        for (AdvertiseModel aModel: advertiseList){
            addPages(aModel);
        }

        viewPager.setCurrentItem(advertiseList.size()-1);
    }

    public void addPages(AdvertiseModel advertiseModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", advertiseModel);
        AdvertiseFragment fragmentChild = new AdvertiseFragment();
        fragmentChild.setArguments(bundle);
        adapter.addFragment(fragmentChild, advertiseModel, advertiseModel.getTitle());
        if (adapter.getCount() > 0) {
            tabLayout.setupWithViewPager(viewPager);
        }
        adapter.notifyDataSetChanged();
    }
}