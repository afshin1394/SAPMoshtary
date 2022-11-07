package com.saphamrah.customer.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.saphamrah.customer.data.local.AdvertiseModel;

import java.util.ArrayList;


public class BaseStatePager extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> stringList = new ArrayList<>();
    private ArrayList<AdvertiseModel> advertiseList = new ArrayList<>();

    public BaseStatePager(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }

    public void addFragment(Fragment fragment, AdvertiseModel advertise, String string) {
        fragmentList.add(fragment);
        stringList.add(string);
        advertiseList.add(advertise);
    }
}
