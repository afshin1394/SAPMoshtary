package com.saphamrah.customer.presentation.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.saphamrah.customer.R;

import com.saphamrah.customer.base.BaseActivity;
import com.saphamrah.customer.base.BasePermissionModel;
import com.saphamrah.customer.data.LocationDbModel;
import com.saphamrah.customer.databinding.ActivityMainBinding;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.presentation.main.welcome.view.MainFragment;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemMultiSelectListener;
import com.saphamrah.customer.utils.customViews.BottomSheetSearchRecyclerView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainInteracts.PresenterOps, ActivityMainBinding> implements MainInteracts.RequiredViewOps, NavigationView.OnNavigationItemSelectedListener, AdapterItemListener, AdapterItemMultiSelectListener {
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private BottomSheetSearchRecyclerView bottomSheetSearch;
    @Override
    protected void onKeyBoardVisibilityChange(boolean visible) {

    }

    @Override
    protected void initViews() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, viewBinding.drawerLayoutMain, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        viewBinding.drawerLayoutMain.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        viewBinding.drawerMain.addHeaderView(getLayoutInflater().inflate(R.layout.layout_drawer_header, null));

        viewBinding.drawerMain.setNavigationItemSelectedListener(this);

        viewBinding.menuDrawerMain.setOnClickListener(view -> {
            if(viewBinding.drawerLayoutMain.isDrawerOpen(viewBinding.drawerMain))
                viewBinding.drawerLayoutMain.closeDrawer(Gravity.LEFT);
            else
                viewBinding.drawerLayoutMain.openDrawer(Gravity.RIGHT);
        });
    }

    @Override
    protected ActivityMainBinding inflateBiding(LayoutInflater inflater) {
        return ActivityMainBinding.inflate(inflater);
    }

    @Override
    public void onPermission(ArrayList<BasePermissionModel> basePermissionModels) {

    }


    private void makeSazmanForoshBottomSheet() {
        List<LocationDbModel> baseSazmanForoshModels = new ArrayList<>();
        baseSazmanForoshModels.add(new LocationDbModel("میهن","1"));
        baseSazmanForoshModels.add(new LocationDbModel("پگاه","1"));
        baseSazmanForoshModels.add(new LocationDbModel("دلپذیر","1"));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","1"));
        baseSazmanForoshModels.add(new LocationDbModel("پگاه","1"));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","1"));
        baseSazmanForoshModels.add(new LocationDbModel("دلپذیر","1"));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","1"));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","1"));

        bottomSheetSearch = new BottomSheetSearchRecyclerView(this, this);
        handleBottomSheetBehaviorState();
        bottomSheetSearch.bottomSheetWithSearchAndRecyclerView(this,
                viewBinding.getRoot(),
                baseSazmanForoshModels,
                true,
                getResources().getString(R.string.searchSazman),
                false);

    }


    private void handleBottomSheetBehaviorState() {
        bottomSheetSearch.bottomSheetBehaviorStateHandler();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        viewBinding.drawerLayoutMain.closeDrawer(Gravity.RIGHT);
        if (id == R.id.nav_request){

            makeSazmanForoshBottomSheet();
//            startActivity(new Intent(MainActivity.this, CreateRequestActivity.class));
        }
        if (id == R.id.nav_profile) {
            Navigation.findNavController(this,R.id.mainNavigation_host).navigate(R.id.action_mainFragment_to_profileFragment);
        }
        if (id == R.id.nav_main) {
            Navigation.findNavController(this,R.id.mainNavigation_host).navigate(R.id.action_profileFragment_to_mainFragment);
        }
        return true;
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
        return MainActivity.this;
    }

    @Override
    public void onItemSelect(Object model, int position, AdapterAction Action) {
        startActivity(new Intent(MainActivity.this, CreateRequestActivity.class));




    }

    @Override
    public void onItemMultiSelect(ArrayList model, AdapterAction action) {
    }



}