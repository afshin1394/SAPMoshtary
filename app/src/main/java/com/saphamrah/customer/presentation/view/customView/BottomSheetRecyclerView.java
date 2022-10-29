package com.saphamrah.customer.presentation.view.customView;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.presentation.view.adapter.recycler.AsyncSearchListAdapter;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemMultiSelectListener;

import java.util.ArrayList;

public class BottomSheetRecyclerView<T extends BaseBottomSheetRecyclerModel> {

    private ConstraintLayout.LayoutParams buttonLayoutParams;
    private static int collapsedMargin; //Button margin in collapsed state
    private static int buttonHeight;
    private static int expandedHeight; //Height of bottom sheet in expanded state

    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView recyclerView;
    private Button btnApply;
    private AsyncSearchListAdapter recyclerViewAdapter;
    private AdapterItemListener<T> adapterItemListener;
    private AdapterItemMultiSelectListener<T> adapterItemMultiSelectListener;

    public BottomSheetRecyclerView(AdapterItemListener<T> adapterItemListener, AdapterItemMultiSelectListener<T> adapterItemMultiSelectListener) {
        this.adapterItemListener = adapterItemListener;
        this.adapterItemMultiSelectListener = adapterItemMultiSelectListener;
    }

    public void bottomSheetWithRecyclerView(Context context,
                                            View view,
                                            ArrayList<T> items,
                                            boolean isMultiSelect) {

        CardView cardViewBottomSheet = view.findViewById(R.id.cardViewBottomSheet);

        bottomSheetBehavior = BottomSheetBehavior.from(cardViewBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        btnApply = view.findViewById(R.id.sheet_button);
        recyclerView = view.findViewById(R.id.sheet_recyclerview);

        //Retrieve button parameters
        buttonLayoutParams = (ConstraintLayout.LayoutParams) btnApply.getLayoutParams();

        //Retrieve bottom sheet parameters
        ViewGroup.LayoutParams bottomSheetLayoutParams = cardViewBottomSheet.getLayoutParams();
        bottomSheetLayoutParams.height = getWindowHeight(context);

        expandedHeight = bottomSheetLayoutParams.height;
        int peekHeight = (int) (expandedHeight / 2.3); //Peek height to 70% of expanded height (Change based on your view)

        //Setup bottom sheet
        cardViewBottomSheet.setLayoutParams(bottomSheetLayoutParams);
        BottomSheetBehavior.from(cardViewBottomSheet).setSkipCollapsed(false);
        BottomSheetBehavior.from(cardViewBottomSheet).setPeekHeight(peekHeight);
        BottomSheetBehavior.from(cardViewBottomSheet).setHideable(true);

        //Calculate button margin from top
        buttonHeight = btnApply.getHeight(); //How tall is the button + experimental distance from bottom (Change based on your view)
        collapsedMargin = peekHeight - buttonHeight; //Button margin in bottom sheet collapsed state
        buttonLayoutParams.topMargin = collapsedMargin;
        btnApply.setLayoutParams(buttonLayoutParams);

       /* //OPTIONAL - Setting up recyclerview margins
        ConstraintLayout.LayoutParams recyclerLayoutParams = (ConstraintLayout.LayoutParams) recyclerView.getLayoutParams();
        float k = (buttonHeight - 60) / (float) buttonHeight; //60 is amount that you want to be hidden behind button
        recyclerLayoutParams.bottomMargin = (int) (k*buttonHeight); //Recyclerview bottom margin (from button)
        recyclerView.setLayoutParams(recyclerLayoutParams);*/

        if (!isMultiSelect)
            btnApply.setVisibility(View.GONE);

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration divider =
                new DividerItemDecoration(context,
                        DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        divider.setDrawable(ContextCompat.getDrawable(context,
                R.drawable.layer_line_divider));

        recyclerView.addItemDecoration(divider);

        recyclerViewAdapter = new AsyncSearchListAdapter<T>(isMultiSelect, new AdapterItemListener<T>() {
            @Override
            public void onItemSelect(T model, int position, AdapterAction action) {
                adapterItemListener.onItemSelect(model, position, action);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                recyclerView.setVisibility(View.GONE);
                recyclerView.removeAllViews();
            }
        });

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.submitList(items);

        recyclerView.setVisibility(View.VISIBLE);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterItemMultiSelectListener.onItemMultiSelect(recyclerViewAdapter.getSelectedItems(), AdapterAction.SELECT);
            }
        });

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if(slideOffset > 0) //Sliding happens from 0 (Collapsed) to 1 (Expanded) - if so, calculate margins
                    buttonLayoutParams.topMargin = (int) (((expandedHeight - buttonHeight) - collapsedMargin - buttonHeight/2) * slideOffset + collapsedMargin);
                else //If not sliding above expanded, set initial margin
                    buttonLayoutParams.topMargin = collapsedMargin;
                btnApply.setLayoutParams(buttonLayoutParams); //Set layout params to button (margin from top)
            }
        });

    }

    private int getWindowHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public void bottomSheetBehaviorStateHandler() {
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setPeekHeight(0);

        }
    }

}
