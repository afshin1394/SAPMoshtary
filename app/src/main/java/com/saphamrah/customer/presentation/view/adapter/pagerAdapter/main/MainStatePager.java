package com.saphamrah.customer.presentation.view.adapter.pagerAdapter.main;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.saphamrah.customer.data.local.AdvertiseModel;
import com.saphamrah.customer.presentation.view.fragments.main.ShoppingListFragment;
import com.saphamrah.customer.presentation.view.fragments.main.WelcomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainStatePager extends FragmentStatePagerAdapter {
    private final int MAX_FRAGMENT = 2;
    private final int FRAGMENT_SHOPPING_LIST = 0;
    private final int FRAGMENT_WELCOME = 1;
    private List<AdvertiseModel> advertiseList = new ArrayList<>();
    private WelcomeFragment welcomeFragment ;
    private ShoppingListFragment shoppingListFragment;

    public MainStatePager(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public int getCount() {
        return MAX_FRAGMENT;
    }

    @Override
    public Fragment getItem(int position) {
        return getFragmentBasedOnPosition(position);
    }

    private Fragment getFragmentBasedOnPosition(int position) {
        switch (position) {
            case FRAGMENT_SHOPPING_LIST:
                shoppingListFragment = new ShoppingListFragment();
                return shoppingListFragment;
            case FRAGMENT_WELCOME:
            default:
                welcomeFragment = new WelcomeFragment();
//                addPages();
                return welcomeFragment;
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int virtualPosition = position % MAX_FRAGMENT;
        return super.instantiateItem(container, virtualPosition);

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        int virtualPosition = position % MAX_FRAGMENT;
        super.destroyItem(container, virtualPosition, object);
    }

    public void addPages(){
        AdvertiseModel advertiseModel = new AdvertiseModel();
        for (int i = 0; i <= 2; i++) {
            advertiseModel.setImageUrl("@drawable/advertising");
            advertiseList.add(advertiseModel);
            if (i == 0) {
                welcomeFragment.addPages(advertiseModel, "Chicken");
            }
            if (i == 1) {
                welcomeFragment.addPages(advertiseModel, "Pizza");
            }
            if (i == 2) {
                welcomeFragment.addPages(advertiseModel, "Beef Steak");
            }
        }
    }
}
