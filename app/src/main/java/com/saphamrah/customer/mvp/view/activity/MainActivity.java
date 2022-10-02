package com.saphamrah.customer.mvp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.saphamrah.customer.adapter.pagerAdapter.SliderPagerMainFrag;
import com.saphamrah.customer.adapter.recycler.DialogMenuAdapter;
import com.saphamrah.customer.data.network.model.MenuModel;
import com.saphamrah.customer.mvp.view.customView.ZoomOutPageTransformer;
import com.saphamrah.customer.R;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPagerMainFrag;
    private SliderPagerMainFrag viewPagerAdapter;

    public DrawerLayout drawerLayout;
    private NavigationView drawerView;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private ConstraintLayout mBottomSheetLayout;
    private BottomSheetBehavior sheetBehavior;
    private View header_Arrow_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPagerMainFrag = findViewById(R.id.view_pager_mainFrag);
        initFragments();

        drawerLayout = findViewById(R.id.drawer_layout_main);
        drawerView = findViewById(R.id.drawer_main);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        drawerView.addHeaderView(getLayoutInflater().inflate(R.layout.layout_drawer_header, null));

        mBottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);

        header_Arrow_Image = findViewById(R.id.arrow_down_bottom_sheet);


        header_Arrow_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                header_Arrow_Image.setRotation(slideOffset * 180);
            }
        });

//        showBottomSheetDialog();
        // to make the Navigation drawer icon always appear on the action bar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ImageView menuImg = findViewById(R.id.menu_drawer_main);

        menuImg.setOnClickListener(view -> {
            if(drawerLayout.isDrawerOpen(drawerView))
                drawerLayout.closeDrawer(Gravity.LEFT);
            else
                drawerLayout.openDrawer(Gravity.RIGHT);
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view_bottom_sheet);

        ArrayList<MenuModel> menuModels = new ArrayList<>();
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));


        DialogMenuAdapter adapter = new DialogMenuAdapter(this, menuModels);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,5);

        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(adapter);
    }

    public void initFragments() {

        viewPagerAdapter = new SliderPagerMainFrag(getSupportFragmentManager());

        viewPagerMainFrag.setAdapter(viewPagerAdapter);
        viewPagerMainFrag.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        viewPagerMainFrag.setCurrentItem(1);
        viewPagerMainFrag.setPageTransformer(false, new ZoomOutPageTransformer());

//        viewPagerMainFrag.setOnTouchListener(onTouchListener);
    }


    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.fragment_bottom_sheet_dialog_layout);

        bottomSheetDialog.show();

    }
}