package com.saphamrah.customer.presentation.main.welcome.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.data.LocationDbModel;
import com.saphamrah.customer.data.local.AdvertiseModel;
import com.saphamrah.customer.data.local.MenuModel;
import com.saphamrah.customer.databinding.FragmentAddressBinding;
import com.saphamrah.customer.databinding.FragmentMainBinding;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.presentation.login.LoginActivity;
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.presentation.main.MainInteracts;
import com.saphamrah.customer.presentation.main.MainPresenter;
import com.saphamrah.customer.presentation.main.welcome.view.adapter.MainStatePager;
import com.saphamrah.customer.presentation.main.welcome.view.adapter.DialogMenuAdapter;
import com.saphamrah.customer.utils.ImageUtils;
import com.saphamrah.customer.utils.customViews.ZoomOutPageTransformer;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemMultiSelectListener;
import com.saphamrah.customer.utils.CheckTabletOrPhone;
import com.saphamrah.customer.utils.SnapToBlock;
import com.saphamrah.customer.utils.customViews.BottomSheetSearchRecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;


public class MainFragment extends BaseFragment<MainPresenter, FragmentMainBinding, MainActivity> implements MainInteracts.RequiredViewOps,
        AdapterItemListener<LocationDbModel>, AdapterItemMultiSelectListener<LocationDbModel> {

    private ConstraintLayout mBottomSheetLayout;

    private MainStatePager viewPagerAdapter;
    private BottomSheetBehavior sheetBehavior;
    private View header_Arrow_Image;
    private NavigationView navigationView;

    private BottomSheetSearchRecyclerView bottomSheetSearch;

    private ArrayList<LocationDbModel> baseSazmanForoshModels;



    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initViews() {

        initFragments();
        mBottomSheetLayout = requireView().findViewById(R.id.bottom_sheet_layout);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
        header_Arrow_Image = requireView().findViewById(R.id.arrow_down_bottom_sheet);
        bottomSheetSearch = new BottomSheetSearchRecyclerView(this, this);

        RecyclerView recyclerView = requireView().findViewById(R.id.recycler_view_bottom_sheet);


        ArrayList<MenuModel> menuModels = new ArrayList<>();
        menuModels.add(new MenuModel("درخواست کالا",R.drawable.ic_add_request));
        menuModels.add(new MenuModel("پروفایل کاربر", R.drawable.ic_account));
        menuModels.add(new MenuModel("ثبت نام", R.drawable.ic_add_user));

        DialogMenuAdapter adapter = new DialogMenuAdapter(getContext(), menuModels, (model, position) -> {

            switch (position){
                case 0:
                    makeSazmanForoshBottomSheet();
                    break;
                case 1:
                    Navigation.findNavController(requireActivity(),R.id.mainNavigation_host).navigate(R.id.action_mainFragment_to_profileFragment);
                    break;
                case 2:
                    startActivity(new Intent(context, LoginActivity.class));
                    break;
            }
        });
//        GridLayoutManager mLayoutManager = new GridLayoutManager(this,5);
        GridLayoutManager mLayoutManager = setConfigForDifferentScreens();
        recyclerView.setLayoutManager(mLayoutManager);

        SnapToBlock snapToBlock = new SnapToBlock(2);
        snapToBlock.attachToRecyclerView(recyclerView);



//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext() , DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext() , DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(adapter);


        header_Arrow_Image.setOnClickListener(v -> {

            if(sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        showMenuItem();
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentMainBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentMainBinding.inflate(inflater, container, false);
    }

    private GridLayoutManager setConfigForDifferentScreens(){
        GridLayoutManager mLayoutManager;
        CheckTabletOrPhone checkTabletOrPhone = new CheckTabletOrPhone(getContext());


        if (checkTabletOrPhone.isTablet()) {
            mLayoutManager = new GridLayoutManager(getContext(), 5);
        }
        else {
            mLayoutManager = new GridLayoutManager(getContext(), 3);
        }

        return mLayoutManager;
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
        return requireContext();
    }


    public void initFragments() {

        ArrayList<AdvertiseModel> advertiseModels = new ArrayList<>();
        baseSazmanForoshModels = new ArrayList<>();

//        advertiseModels.add(new AdvertiseModel("@drawable/carrnina_mihan", "http://www.mihan-food.com/", "."));
        advertiseModels.add(new AdvertiseModel("@drawable/tuna", "http://www.kadbanoco.com/fa/", "."));
        advertiseModels.add(new AdvertiseModel("@drawable/dressing", "http://www.kadbanoco.com/fa/", "."));
//        advertiseModels.add(new AdvertiseModel("@drawable/bastani", "http://www.mihan-food.com/", "."));


        baseSazmanForoshModels.add(new LocationDbModel("دلپذیر","1",R.drawable.logo_delpazir));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_domino));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_mihan));
        baseSazmanForoshModels.add(new LocationDbModel("مهرام","3",R.drawable.logo_kalleh));
        baseSazmanForoshModels.add(new LocationDbModel("لینا","7",R.drawable.logo_lina));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_mihan));
        baseSazmanForoshModels.add(new LocationDbModel("دلپذیر","1",R.drawable.logo_delpazir));
        baseSazmanForoshModels.add(new LocationDbModel("کاله","8",R.drawable.logo_kalleh));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_mihan));


        viewPagerAdapter = new MainStatePager(requireActivity().getSupportFragmentManager(), advertiseModels);

        viewBinding.viewPagerMainFrag.setAdapter(viewPagerAdapter);
        viewBinding.viewPagerMainFrag.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        viewBinding.viewPagerMainFrag.setCurrentItem(1);
        viewBinding.viewPagerMainFrag.setPageTransformer(false, new ZoomOutPageTransformer());

//        viewPagerMainFrag.setOnTouchListener(onTouchListener);
    }

    private void makeSazmanForoshBottomSheet() {

        handleBottomSheetBehaviorState();

        bottomSheetSearch.bottomSheetWithSearchAndRecyclerView(getContext(),
                viewBinding,
                baseSazmanForoshModels,
                true,
                getContext().getResources().getString(R.string.searchSazman),
                false);

    }


    private void handleBottomSheetBehaviorState() {
        bottomSheetSearch.bottomSheetBehaviorStateHandler();
    }


    @Override
    public void onItemSelect(LocationDbModel model, int position, AdapterAction Action) {
        Log.i(TAG, "onItemSelect: "+model.toString());
        Bitmap bm = BitmapFactory.decodeResource(getResources(), model.getResId());
        Log.i(TAG, "onItemSelect: "+bm);
//        Uri uri = ImageUtils.convertBitmapToUri(context,"sazmanIcon",bm);
//        Log.i(TAG, "onItemSelect: "+uri);
        startActivity(new Intent(activity, CreateRequestActivity.class).putExtra("sazmanName",model.getName()).putExtra("ccSazmanForosh",Integer.parseInt(model.getType())));
        handleBottomSheetBehaviorState();
    }

    @Override
    public void onItemMultiSelect(ArrayList<LocationDbModel> model, AdapterAction action) {

    }

    private void showMenuItem()
    {
        navigationView = activity.findViewById(R.id.drawer_main);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_profile).setVisible(true);
    }
}