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
import com.saphamrah.customer.presentation.view.adapter.pagerAdapter.main.AdvertiseStatePager;

import java.util.ArrayList;
import java.util.List;


public class WelcomeFragment extends Fragment {

    private AdvertiseStatePager adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<AdvertiseModel> advertiseList = new ArrayList<>();

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
        adapter = new AdvertiseStatePager(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        AdvertiseModel advertiseModel = new AdvertiseModel();
        for (int i = 0; i <= 2; i++) {
            advertiseModel.setImageUrl("@drawable/advertisement");
            advertiseList.add(advertiseModel);
//            advertiseList.add(advertiseModel);
            if (i == 0) {
                addPages(advertiseModel, "ads3");
            }
            if (i == 1) {
                addPages(advertiseModel, "ads2");
            }
            if (i == 2) {
                addPages(advertiseModel, "ads1");
            }
        }

        viewPager.setCurrentItem(advertiseList.size()-1);
    }

    public void addPages(AdvertiseModel advertiseModel, String title) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", advertiseModel);
        AdvertiseFragment fragmentChild = new AdvertiseFragment();
        fragmentChild.setArguments(bundle);
        adapter.addFragment(fragmentChild, advertiseModel, title);
        if (adapter.getCount() > 0) {
            tabLayout.setupWithViewPager(viewPager);
        }
        adapter.notifyDataSetChanged();
    }
}