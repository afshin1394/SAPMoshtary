package com.saphamrah.Adapter;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.saphamrah.Adapter.Interfaces.MainPagerAdapterEvents;

import java.util.ArrayList;

public class MainFragPagerAdapter extends FragmentStatePagerAdapter implements View.OnTouchListener {
    ArrayList<Fragment> frgList=new ArrayList<>();
    private boolean isPagingEnabled=true;

    MainPagerAdapterEvents mainPagerAdapterEvents ;

    public MainFragPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> frgList, MainPagerAdapterEvents mainPagerAdapterEvents) {
        super(fm);
        this.frgList = frgList;
        this.mainPagerAdapterEvents = mainPagerAdapterEvents;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position>=frgList.size()){
            return  frgList.get(position%frgList.size());
        }
        return frgList.get(position);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //instantiate each item
    //initialize each fragment by its virtual index
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int virtualPosition = position % frgList.size();
        return super.instantiateItem(container, virtualPosition);

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        int virtualPosition = position % frgList.size();
        super.destroyItem(container, virtualPosition, object);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
     return true;
    }
}
