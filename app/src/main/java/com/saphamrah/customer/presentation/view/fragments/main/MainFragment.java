package com.saphamrah.customer.presentation.view.fragments.main;

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
import com.saphamrah.customer.data.local.AdvertiseModel;
import com.saphamrah.customer.data.local.MenuModel;
import com.saphamrah.customer.databinding.FragmentMainBinding;
import com.saphamrah.customer.presentation.view.adapter.pagerAdapter.main.MainStatePager;
import com.saphamrah.customer.presentation.view.adapter.recycler.DialogMenuAdapter;
import com.saphamrah.customer.presentation.view.customView.ZoomOutPageTransformer;
import com.saphamrah.customer.utils.CheckTabletOrPhone;
import com.saphamrah.customer.utils.SnapToBlock;

import java.util.ArrayList;


public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private ConstraintLayout mBottomSheetLayout;

    private MainStatePager viewPagerAdapter;
    private BottomSheetBehavior sheetBehavior;
    private View header_Arrow_Image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragments();

        mBottomSheetLayout = view.findViewById(R.id.bottom_sheet_layout);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
        header_Arrow_Image = view.findViewById(R.id.arrow_down_bottom_sheet);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_bottom_sheet);


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

        DialogMenuAdapter adapter = new DialogMenuAdapter(getContext(), menuModels);
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


    public void initFragments() {
        ArrayList<AdvertiseModel> advertiseModels = new ArrayList<>();

        advertiseModels.add(new AdvertiseModel("@drawable/advertising", "", "."));
        advertiseModels.add(new AdvertiseModel("@drawable/advertising", "", "."));
        advertiseModels.add(new AdvertiseModel("@drawable/advertising", "", "."));

        viewPagerAdapter = new MainStatePager(requireActivity().getSupportFragmentManager(), advertiseModels);

        binding.viewPagerMainFrag.setAdapter(viewPagerAdapter);
        binding.viewPagerMainFrag.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.viewPagerMainFrag.setCurrentItem(1);
        binding.viewPagerMainFrag.setPageTransformer(false, new ZoomOutPageTransformer());

//        viewPagerMainFrag.setOnTouchListener(onTouchListener);
    }
}