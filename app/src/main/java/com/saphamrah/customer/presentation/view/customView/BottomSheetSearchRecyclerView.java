package com.saphamrah.customer.presentation.view.customView;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
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

public class BottomSheetSearchRecyclerView<T extends BaseBottomSheetRecyclerModel> {

    private ConstraintLayout.LayoutParams buttonLayoutParams;
    private int collapsedMargin; //Button margin in collapsed state
    private int buttonHeight;
    private int expandedHeight; //Height of bottom sheet in expanded state

    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetBehavior;
    private MaterialSearchView searchView;
    private RecyclerView recyclerViewSearchResult;
    private Button btnApply;
    private AsyncSearchListAdapter recyclerViewAdapter;
    private ArrayList<T> filteredListBaseSearchDbModel;
    private AdapterItemListener<T> adapterItemListener;
    private AdapterItemMultiSelectListener<T> adapterItemMultiSelectListener;


    public BottomSheetSearchRecyclerView(AdapterItemListener<T> adapterItemListener, AdapterItemMultiSelectListener<T> adapterItemMultiSelectListener) {
        this.adapterItemListener = adapterItemListener;
        this.adapterItemMultiSelectListener = adapterItemMultiSelectListener;

    }

    public void bottomSheetWithSearchAndRecyclerView(Context context,
                                                     View view,
                                                     ArrayList<T> items,
                                                     boolean isSearchEnable,
                                                     String searchHint,
                                                     boolean isMultiSelect
    ) {

        CardView cardViewBottomSheet = view.findViewById(R.id.cardViewBottomSheet);
        btnApply = view.findViewById(R.id.sheet_button);
        recyclerViewSearchResult = view.findViewById(R.id.sheet_recyclerview);
        searchView = view.findViewById(R.id.searchView);

        if (!isSearchEnable)
            searchView.setVisibility(View.GONE);
        else
            searchView.setVisibility(View.VISIBLE);


        if (!isMultiSelect)
            btnApply.setVisibility(View.INVISIBLE);
        else
            btnApply.setVisibility(View.VISIBLE);


        ((LinearLayout) ((LinearLayout) searchView.getChildAt(0)).getChildAt(2)).getChildAt(1).setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        bottomSheetBehavior = BottomSheetBehavior.from(cardViewBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

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

        //Retrieve button parameters
        buttonLayoutParams = (ConstraintLayout.LayoutParams) btnApply.getLayoutParams();

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

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset > 0) //Sliding happens from 0 (Collapsed) to 1 (Expanded) - if so, calculate margins
                    buttonLayoutParams.topMargin = (int) (((expandedHeight - buttonHeight) - collapsedMargin - buttonHeight / 2) * slideOffset + collapsedMargin);
                else //If not sliding above expanded, set initial margin
                    buttonLayoutParams.topMargin = collapsedMargin;
                btnApply.setLayoutParams(buttonLayoutParams); //Set layout params to button (margin from top)
            }
        });

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration divider =
                new DividerItemDecoration(context,
                        DividerItemDecoration.VERTICAL);
        recyclerViewSearchResult.setLayoutManager(linearLayoutManager);

        divider.setDrawable(ContextCompat.getDrawable(context,
                R.drawable.layer_line_divider));

        recyclerViewSearchResult.addItemDecoration(divider);

        recyclerViewAdapter = new AsyncSearchListAdapter<T>(isMultiSelect, new AdapterItemListener<T>() {
            @Override
            public void onItemSelect(T model, int position, AdapterAction action) {
                adapterItemListener.onItemSelect(model, position, action);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                searchView.setQuery("", false);
                /*recyclerViewSearchResult.setVisibility(View.GONE);
                recyclerViewSearchResult.removeAllViews();*/

            }
        });

        recyclerViewSearchResult.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.submitList(items);

        recyclerViewSearchResult.setVisibility(View.VISIBLE);

        searchView.setQueryHint(searchHint);

        searchView.addTextWatcher(s -> {
            if (s.trim().length() > 0) {
                filter(s, items, isMultiSelect);
            } else {
                recyclerViewAdapter.submitList(items);
            }
        }, 400);

        View closeBtn = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
                searchView.setQuery("", false);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehavior.setPeekHeight(0);
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterItemMultiSelectListener.onItemMultiSelect(recyclerViewAdapter.getSelectedItems(), AdapterAction.SELECT);
            }
        });

    }

    private int getWindowHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void filter(String query,
                        ArrayList<T> items,
                        boolean isMultiSelect) {
        filteredListBaseSearchDbModel = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {

            final String text = items.get(i).getName().toLowerCase();
            if (text.contains(query)) {

                filteredListBaseSearchDbModel.add(items.get(i));
            }
        }

        Log.d("BottomSheetSearch", "filteredList: " + filteredListBaseSearchDbModel.size());

        recyclerViewAdapter = new AsyncSearchListAdapter<T>(isMultiSelect, new AdapterItemListener<T>() {
            @Override
            public void onItemSelect(T model, int position, AdapterAction action) {
                adapterItemListener.onItemSelect(model, position, action);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                searchView.setQuery("", false);
                filteredListBaseSearchDbModel.clear();
                /*recyclerViewSearchResult.setVisibility(View.GONE);
                recyclerViewSearchResult.removeAllViews();*/
            }
        });

        recyclerViewSearchResult.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.submitList(filteredListBaseSearchDbModel);

    }

    public void bottomSheetBehaviorStateHandler() {
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setPeekHeight(0);

        }
    }
}
