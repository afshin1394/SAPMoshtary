package com.saphamrah.customer.presentation.view.adapter.recycler.pagerAdapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.saphamrah.customer.presentation.view.fragment.ShoppingListFragment;
import com.saphamrah.customer.presentation.view.fragment.WelcomeFragment;

public class SliderPagerMainFrag extends FragmentStatePagerAdapter {
    private final int MAX_FRAGMENT = 2;
    private final int FRAGMENT_SHOPPING_LIST = 0;
    private final int FRAGMENT_WELCOME = 1;

    public SliderPagerMainFrag(FragmentManager fm) {
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
                ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
                return shoppingListFragment;
            case FRAGMENT_WELCOME:
            default:
                WelcomeFragment welcomeFragment = new WelcomeFragment();
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
}
