package com.saphamrah.customer.presentation.main.welcome.view;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.data.LocationDbModel;
import com.saphamrah.customer.data.local.AdvertiseModel;
import com.saphamrah.customer.data.local.MenuModel;
import com.saphamrah.customer.databinding.FragmentAddressBinding;
import com.saphamrah.customer.databinding.FragmentMainBinding;
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.presentation.main.MainInteracts;
import com.saphamrah.customer.presentation.main.MainPresenter;
import com.saphamrah.customer.presentation.main.welcome.view.adapter.MainStatePager;
import com.saphamrah.customer.presentation.main.welcome.view.adapter.DialogMenuAdapter;
import com.saphamrah.customer.utils.customViews.ZoomOutPageTransformer;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemMultiSelectListener;
import com.saphamrah.customer.utils.CheckTabletOrPhone;
import com.saphamrah.customer.utils.SnapToBlock;
import com.saphamrah.customer.utils.customViews.BottomSheetSearchRecyclerView;

import java.util.ArrayList;
import java.util.Objects;


public class MainFragment extends BaseFragment<MainPresenter, FragmentMainBinding, MainActivity> implements MainInteracts.RequiredViewOps,
        AdapterItemListener<BaseBottomSheetRecyclerModel>, AdapterItemMultiSelectListener<BaseBottomSheetRecyclerModel> {

    private ConstraintLayout mBottomSheetLayout;

    private MainStatePager viewPagerAdapter;
    private BottomSheetBehavior sheetBehavior;
    private View header_Arrow_Image;

    private BottomSheetSearchRecyclerView bottomSheetSearch;

    private ArrayList<LocationDbModel> baseSazmanForoshModels;

    public MainFragment() {
        super(R.layout.fragment_main);
    }

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
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));
        menuModels.add(new MenuModel("سفارش"));

        DialogMenuAdapter adapter = new DialogMenuAdapter(getContext(), menuModels, (model, position) -> {

            makeSazmanForoshBottomSheet();

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
    public void onItemSelect(BaseBottomSheetRecyclerModel model, int position, AdapterAction action) {

    }

    @Override
    public void onItemMultiSelect(ArrayList<BaseBottomSheetRecyclerModel> model, AdapterAction action) {

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

        advertiseModels.add(new AdvertiseModel("@drawable/advertising", "", "."));
        advertiseModels.add(new AdvertiseModel("@drawable/advertising", "", "."));
        advertiseModels.add(new AdvertiseModel("@drawable/advertising", "", "."));

        baseSazmanForoshModels.add(new LocationDbModel("میهن","1"));
        baseSazmanForoshModels.add(new LocationDbModel("پگاه","1"));
        baseSazmanForoshModels.add(new LocationDbModel("دلپذیر","1"));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","1"));
        baseSazmanForoshModels.add(new LocationDbModel("پگاه","1"));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","1"));
        baseSazmanForoshModels.add(new LocationDbModel("دلپذیر","1"));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","1"));
        baseSazmanForoshModels.add(new LocationDbModel("میهن","1"));

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
                getView(),
                baseSazmanForoshModels,
                true,
                getContext().getResources().getString(R.string.searchSazman),
                false);

    }


    private void handleBottomSheetBehaviorState() {
        bottomSheetSearch.bottomSheetBehaviorStateHandler();
    }

}