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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.saphamrah.customer.databinding.BottomSheetRecyclerSearchBinding;
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

import com.saphamrah.customer.utils.customViews.bottomSheetModule.BaseBottomSheet;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.list.BaseBottomSheetRecyclerView;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.list.BottomSheetDynamicListSingleSelect;
import com.saphamrah.customer.utils.dialogs.DoubleActionFragmentDialog;
import com.saphamrah.customer.utils.dialogs.IDoubleActionDialog;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;


public class MainFragment extends BaseFragment<MainPresenter, FragmentMainBinding, MainActivity> implements MainInteracts.RequiredViewOps,
        AdapterItemListener<BaseBottomSheetRecyclerModel> {

    private ConstraintLayout mBottomSheetLayout;

    private MainStatePager viewPagerAdapter;
    private BottomSheetBehavior sheetBehavior;
    private View header_Arrow_Image;
    private NavigationView navigationView;

    private BottomSheetRecyclerSearchBinding bottomSheetRecyclerSearchBinding;

    private ArrayList<LocationDbModel> baseSazmanForoshModels;

    private DividerItemDecoration divider;
    private BaseBottomSheet baseBottomSheetRecyclerView;

    private BottomSheetDynamicListSingleSelect bottomSheetDynamicListSingleSelect;
    private BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder baseBottomSheetRecyclerViewBuilder;

    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initViews() {

        initFragments();
        mBottomSheetLayout = requireView().findViewById(R.id.bottom_sheet_layout);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
        header_Arrow_Image = requireView().findViewById(R.id.arrow_down_bottom_sheet);

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
        FragmentMainBinding fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false);
        View view = fragmentMainBinding.getRoot();
        bottomSheetRecyclerSearchBinding = BottomSheetRecyclerSearchBinding.bind(view);
        return fragmentMainBinding;
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
//        advertiseModels.add(new AdvertiseModel("@drawable/tuna", "http://www.kadbanoco.com/fa/", "."));
        advertiseModels.add(new AdvertiseModel("@drawable/dlpazir_ketchap_front", "http://www.kadbanoco.com/fa/", ".", "980703" , 1, "دلپذیر"));
//        advertiseModels.add(new AdvertiseModel("@drawable/bastani", "http://www.mihan-food.com/", "."));


        baseSazmanForoshModels.add(new LocationDbModel("پخش پگاه","1",R.drawable.logo_pegah));
//        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_domino));
//        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_mihan));
        baseSazmanForoshModels.add(new LocationDbModel("پخش پرنیان","3",R.drawable.logo_parnian));
        baseSazmanForoshModels.add(new LocationDbModel("لینا","7",R.drawable.logo_lina));
//        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_mihan));
//        baseSazmanForoshModels.add(new LocationDbModel("دلپذیر","1",R.drawable.logo_delpazir));
        baseSazmanForoshModels.add(new LocationDbModel("کاله","8",R.drawable.logo_kalleh));
//        baseSazmanForoshModels.add(new LocationDbModel("میهن","2",R.drawable.logo_mihan));


        viewPagerAdapter = new MainStatePager(requireActivity().getSupportFragmentManager(), advertiseModels);

        viewBinding.viewPagerMainFrag.setAdapter(viewPagerAdapter);
        viewBinding.viewPagerMainFrag.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        viewBinding.viewPagerMainFrag.setCurrentItem(1);
        viewBinding.viewPagerMainFrag.setPageTransformer(false, new ZoomOutPageTransformer());

//        viewPagerMainFrag.setOnTouchListener(onTouchListener);
    }

    private void makeSazmanForoshBottomSheet() {

        baseBottomSheetRecyclerView = new BaseBottomSheet(
                bottomSheetRecyclerSearchBinding,
                getContext(),
                R.id.cardView_recyclerView_bottomSheet);

        closeBottomSheetBehavior();

        divider =
                new DividerItemDecoration(getContext(),
                        DividerItemDecoration.VERTICAL);

        divider.setDrawable(ContextCompat.getDrawable(getContext(),
                R.drawable.layer_line_divider));

        baseBottomSheetRecyclerViewBuilder = new BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder();

        bottomSheetDynamicListSingleSelect = new BottomSheetDynamicListSingleSelect(
                bottomSheetRecyclerSearchBinding,
                getContext(),
                R.id.cardView_recyclerView_bottomSheet,
                new LinearLayoutManager(getContext()),
                baseBottomSheetRecyclerViewBuilder.setDividerItemDecoration(divider),
                true,
                getResources().getString(R.string.searchCompany),
                baseSazmanForoshModels,
                this
        );

    }


    private void closeBottomSheetBehavior() {
        baseBottomSheetRecyclerView.closeBottomSheet();
    }


//    @Override
//    public void onItemSelect(LocationDbModel model, int position, AdapterAction Action) {
//        Log.i(TAG, "onItemSelect: "+model.toString());
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), model.getResId());
//        Log.i(TAG, "onItemSelect: "+bm);
////        Uri uri = ImageUtils.convertBitmapToUri(context,"sazmanIcon",bm);
////        Log.i(TAG, "onItemSelect: "+uri);
//        startActivity(new Intent(activity, CreateRequestActivity.class).putExtra("sazmanName",model.getName()).putExtra("ccSazmanForosh",Integer.parseInt(model.getType())));
//        handleBottomSheetBehaviorState();
//    }



    private void showMenuItem()
    {
        navigationView = activity.findViewById(R.id.drawer_main);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_profile).setVisible(true);
    }

    @Override
    public void onItemSelect(BaseBottomSheetRecyclerModel model, int position, AdapterAction Action) {
                Log.i(TAG, "onItemSelect: "+model.toString());
        Bitmap bm = BitmapFactory.decodeResource(getResources(), model.getImageRes());
        Log.i(TAG, "onItemSelect: "+bm);
//        Uri uri = ImageUtils.convertBitmapToUri(context,"sazmanIcon",bm);
//        Log.i(TAG, "onItemSelect: "+uri);
        int ccSazmanForosh = Integer.parseInt(model.getType());
        if (ccSazmanForosh == 7)
        {
            FragmentTransaction wft = getChildFragmentManager().beginTransaction();
            new DoubleActionFragmentDialog(String.format(getString(R.string.orderedBefore), model.getName()), true, new IDoubleActionDialog() {
                @Override
                public void onConfirmClick() {
                    Intent intent = new Intent(activity, CreateRequestActivity.class);
                    intent.putExtra("sazmanName",model.getName()).putExtra("ccSazmanForosh",Integer.parseInt(model.getType()));
                    intent.putExtra("advertiseProductCodeKala","554893");
                    startActivity(intent);                }

                @Override
                public void onCancelClick() {

                }
            }).show(wft,"dialog");
        }
        else
        {
            Intent intent = new Intent(activity, CreateRequestActivity.class);
            intent.putExtra("sazmanName",model.getName()).putExtra("ccSazmanForosh",Integer.parseInt(model.getType()));
            intent.putExtra("advertiseProductCodeKala","554893");
            startActivity(intent);
        }
    }
}