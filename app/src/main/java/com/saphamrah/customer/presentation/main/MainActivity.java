package com.saphamrah.customer.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.navigation.NavigationView;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseActivity;
import com.saphamrah.customer.base.BasePermissionModel;
import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.data.LocationDbModel;
import com.saphamrah.customer.databinding.ActivityMainBinding;
import com.saphamrah.customer.databinding.BottomSheetRecyclerSearchBinding;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.BaseBottomSheet;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.list.BaseBottomSheetRecyclerView;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.list.BottomSheetDynamicListSingleSelect;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainInteracts.PresenterOps, ActivityMainBinding> implements MainInteracts.RequiredViewOps, NavigationView.OnNavigationItemSelectedListener, AdapterItemListener<BaseBottomSheetRecyclerModel> {

    private DividerItemDecoration divider;

    private BottomSheetRecyclerSearchBinding bottomSheetRecyclerSearchBinding;
    private BottomSheetDynamicListSingleSelect bottomSheetDynamicListSingleSelect;
    private BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder baseBottomSheetRecyclerViewBuilder;

    public ActionBarDrawerToggle actionBarDrawerToggle;

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
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(inflater);
        View view = activityMainBinding.getRoot();
        bottomSheetRecyclerSearchBinding = BottomSheetRecyclerSearchBinding.bind(view);
        return activityMainBinding;
    }

    @Override
    public void onPermission(ArrayList<BasePermissionModel> basePermissionModels) {

    }


    private void makeSazmanForoshBottomSheet() {
        List<LocationDbModel> baseSazmanForoshModels = new ArrayList<>();
        baseSazmanForoshModels.add(new LocationDbModel("دلپذیر","1",R.drawable.logo_delpazir));
//        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_domino));
//        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_mihan));
        baseSazmanForoshModels.add(new LocationDbModel("مهرام","3",R.drawable.logo_mahram));
        baseSazmanForoshModels.add(new LocationDbModel("لینا","7",R.drawable.logo_lina));
//        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_mihan));
//        baseSazmanForoshModels.add(new LocationDbModel("دلپذیر","1",R.drawable.logo_delpazir));
        baseSazmanForoshModels.add(new LocationDbModel("کاله","8",R.drawable.logo_kalleh));
//        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_mihan));


        divider =
                new DividerItemDecoration(this,
                        DividerItemDecoration.VERTICAL);

        divider.setDrawable(ContextCompat.getDrawable(this,
                R.drawable.layer_line_divider));

        baseBottomSheetRecyclerViewBuilder = new BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder();

        bottomSheetDynamicListSingleSelect = new BottomSheetDynamicListSingleSelect(
                bottomSheetRecyclerSearchBinding,
                this,
                R.id.cardView_recyclerView_bottomSheet,
                new LinearLayoutManager(this),
                baseBottomSheetRecyclerViewBuilder.setDividerItemDecoration(divider),
                true,
                getResources().getString(R.string.searchCompany),
                (ArrayList) baseSazmanForoshModels,
                this
                );

    }


    private void closeBottomSheetBehavior() {
        bottomSheetDynamicListSingleSelect.closeBottomSheet();
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
//            Navigation.findNavController(this,R.id.mainNavigation_host).navigate(R.id.action_profileFragment_to_mainFragment);
            this.finish();
            startActivity(new Intent(this, MainActivity.class));
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
    public void onItemSelect(BaseBottomSheetRecyclerModel model, int position, AdapterAction Action) {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), (model.getImageRes()));
//        Uri uri = ImageUtils.convertBitmapToUri(context,"sazmanIcon",bm);
//        Log.i(TAG, "onItemSelect: "+uri);
        startActivity(new Intent(MainActivity.this, CreateRequestActivity.class).putExtra("sazmanName",model.getName()).putExtra("ccSazmanForosh",Integer.parseInt(model.getType())));
        closeBottomSheetBehavior();
    }
}