package com.saphamrah.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.saphamrah.MVP.View.MainViewPagerFragments.FragmentChartHadafForoshDarsad;
import com.saphamrah.MVP.View.MainViewPagerFragments.FragmentChartHadafForoshTedad;
import com.saphamrah.MVP.View.MainViewPagerFragments.FragmentChartMablaghForosh;
import com.saphamrah.MVP.View.MainViewPagerFragments.FragmentChartTedadFactorForosh;

public class SliderPagerMainFrag extends FragmentStatePagerAdapter {
    private final int MAX_FRAGMENT = 4;
    private final int FRAGMENT_CHART_HADAF_FOROSH_DARSAD = 1;
    private final int FRAGMENT_CHART_HADAF_FOROSH_TEDAD = 2;
    private final int FRAGMENT_CHART_MABLAGH_FOROSH = 3;
    private final int FRAGMENT_CHART_FACTOR_FOROSH = 4;

    public SliderPagerMainFrag(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Fragment getItem(int position) {
        return getFragmentBasedOnPosition(position);
    }

    private Fragment getFragmentBasedOnPosition(int position) {
        int fragmentPos = position % MAX_FRAGMENT;
        switch (fragmentPos) {
            case FRAGMENT_CHART_HADAF_FOROSH_DARSAD:
                FragmentChartHadafForoshDarsad fragmentChartHadafForoshDarsad = new FragmentChartHadafForoshDarsad();
                return fragmentChartHadafForoshDarsad;
            case FRAGMENT_CHART_HADAF_FOROSH_TEDAD:
                FragmentChartHadafForoshTedad fragmentChartHadafForoshTedad = new FragmentChartHadafForoshTedad();
                return fragmentChartHadafForoshTedad;

            case FRAGMENT_CHART_MABLAGH_FOROSH:
                FragmentChartMablaghForosh fragmentChartMablaghForosh = new FragmentChartMablaghForosh();
                return fragmentChartMablaghForosh;
            case FRAGMENT_CHART_FACTOR_FOROSH:
            default:
                FragmentChartTedadFactorForosh fragmentChartTedadFactorForosh = new FragmentChartTedadFactorForosh();
                return fragmentChartTedadFactorForosh;

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
