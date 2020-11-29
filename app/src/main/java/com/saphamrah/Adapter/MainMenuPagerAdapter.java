package com.saphamrah.Adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.saphamrah.MVP.View.MainFragment;
import com.saphamrah.MVP.View.MainMenuFragment;
import com.saphamrah.Utils.Constants;


public class MainMenuPagerAdapter extends FragmentPagerAdapter
{
    private int noeMasouliat = 0;

    public MainMenuPagerAdapter(FragmentManager fm, int noeMasouliat)
    {
        super(fm);
        this.noeMasouliat = noeMasouliat;
    }


    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                MainMenuFragment systemFragment = new MainMenuFragment();
                Bundle bundleSystem = new Bundle();
                bundleSystem.putString("parentsId" , String.valueOf(Constants.SYSTEM()));
                systemFragment.setArguments(bundleSystem);
                return systemFragment;
            case 1:
                MainMenuFragment reportsFragment = new MainMenuFragment();
                Bundle bundleReport = new Bundle();
                bundleReport.putString("parentsId" , Constants.SALES_REPORT() + "," + Constants.VOSOL_REPORT());
                reportsFragment.setArguments(bundleReport);
                return reportsFragment;
            case 2:
                if (noeMasouliat == 7)
                {
                    MainMenuFragment marketing = new MainMenuFragment();
                    Bundle marketingBundle = new Bundle();
                    marketingBundle.putString("parentsId" , String.valueOf(Constants.MARKETING()));
                    marketing.setArguments(marketingBundle);
                    return marketing;
                }
                else
                {
                    MainMenuFragment vosolFragment = new MainMenuFragment();
                    Bundle bundleVosol = new Bundle();
                    bundleVosol.putString("parentsId" , String.valueOf(Constants.VOSOL()));
                    vosolFragment.setArguments(bundleVosol);
                    return vosolFragment;
                }
            case 3:
                MainMenuFragment saleFragment = new MainMenuFragment();
                Bundle bundleSale = new Bundle();
                bundleSale.putString("parentsId" , String.valueOf(Constants.SALE()));
                saleFragment.setArguments(bundleSale);
                return saleFragment;
            case 4:
                return new MainFragment();
            default:
                return new MainFragment();
        }
    }


    @Override
    public int getCount()
    {
        return 5;
    }

}
