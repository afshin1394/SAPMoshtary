package com.saphamrah.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.saphamrah.MVP.View.AddCustomerAddressFragment;
import com.saphamrah.MVP.View.AddCustomerApplyFragment;
import com.saphamrah.MVP.View.AddCustomerBaseInfoFragment;
import com.saphamrah.MVP.View.AddCustomerDocsFragment;
import com.saphamrah.MVP.View.AddCustomerShomareHesabFragment;

public class AddCustomerPagerAdapter extends FragmentPagerAdapter
{

    public AddCustomerPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }


    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return  new AddCustomerApplyFragment();
            case 1:
                return new AddCustomerShomareHesabFragment();
            case 2:
                return new AddCustomerAddressFragment();
            case 3:
                return new AddCustomerBaseInfoFragment();
            default:
                return null;
        }
    }


    @Override
    public int getCount()
    {
        return 4;
    }

}
