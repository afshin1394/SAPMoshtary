package com.saphamrah.customer.utils.customViews;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.utils.AdapterUtil.BottomSheetRecyclerAdapter;
import com.saphamrah.customer.utils.AdapterUtil.asyncSearchAdapter.AsyncSearchListAdapter;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemMultiSelectListener;

import java.util.ArrayList;

public class BottomSheetSearchRecyclerView<T extends BaseBottomSheetRecyclerModel, V extends ViewBinding> {

    private ConstraintLayout.LayoutParams buttonLayoutParams;
    private int collapsedMargin; //Button margin in collapsed state
    private int buttonHeight;
    private int expandedHeight; //Height of bottom sheet in expanded state
    private int peekHeight;

    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetBehavior;
    private CardView cardViewBottomSheet;
    private SearchWatcher searchView;
    private RecyclerView recyclerViewSearchResult;
    private Button btnApply;
    private AsyncSearchListAdapter asyncSearchListAdapter;
    private BottomSheetRecyclerAdapter bottomSheetRecyclerAdapter;
    private ArrayList<T> filteredListBaseSearchDbModel;
    private AdapterItemListener<T> adapterItemListener;
    private AdapterItemMultiSelectListener<T> adapterItemMultiSelectListener;

   /* // Without any select
    public BottomSheetSearchRecyclerView() {

    }*/

    // Single select
    public BottomSheetSearchRecyclerView(AdapterItemListener<T> adapterItemListener) {
        this.adapterItemListener = adapterItemListener;
    }

    // Multiple Select
    public BottomSheetSearchRecyclerView(AdapterItemMultiSelectListener<T> adapterItemMultiSelectListener) {
        this.adapterItemMultiSelectListener = adapterItemMultiSelectListener;
    }

    // Both Multiple & Single Select
    public BottomSheetSearchRecyclerView(AdapterItemListener<T> adapterItemListener, AdapterItemMultiSelectListener<T> adapterItemMultiSelectListener) {
        this.adapterItemListener = adapterItemListener;
        this.adapterItemMultiSelectListener = adapterItemMultiSelectListener;
    }

    public void bottomSheetWithSearchAndRecyclerView(Context context,
                                                     V viewBinding,
                                                     ArrayList<T> items,
                                                     boolean isSearchEnable,
                                                     String searchHint,
                                                     boolean isMultiSelect
    ) {

        initBottomSheetView(context, viewBinding, isSearchEnable, isMultiSelect);

        //Dynamic List
        if (isSearchEnable) {
            if (isMultiSelect)
                bottomSheetDynamicListWithSearchAndMultiSelect(context, items, true, searchHint);
            else
                bottomSheetDynamicListWithSearchAndSingleSelect(context, items, false, searchHint);

            // Static List
        } else {
            if (isMultiSelect)
                bottomSheetStaticListWithMultiSelect(items, true);
            else
                bottomSheetStaticListWithSingleSelect(items, false);

        }

    }

    private void initBottomSheetView(Context context, V viewBinding, boolean isSearchEnable, boolean isMultiSelect) {
        View view = viewBinding.getRoot();
        cardViewBottomSheet = view.findViewById(R.id.cardViewBottomSheet);
        recyclerViewSearchResult = view.findViewById(R.id.sheet_recyclerview);
        btnApply = view.findViewById(R.id.sheet_button);
        searchView = view.findViewById(R.id.searchView);


        cardViewBottomSheet.setBackgroundResource(R.drawable.bottom_dialog_shape);

        if (!isSearchEnable)
            searchView.setVisibility(View.GONE);
        else
            searchView.setVisibility(View.VISIBLE);


        if (!isMultiSelect)
            btnApply.setVisibility(View.INVISIBLE);
        else
            btnApply.setVisibility(View.VISIBLE);

        bottomSheetBehavior = BottomSheetBehavior.from(cardViewBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        //Retrieve bottom sheet parameters
        ViewGroup.LayoutParams bottomSheetLayoutParams = cardViewBottomSheet.getLayoutParams();
        bottomSheetLayoutParams.height = getWindowHeight(context);

        expandedHeight = bottomSheetLayoutParams.height;
        peekHeight = (int) (expandedHeight / 2.3); //Peek height to 70% of expanded height (Change based on your view)

        //Setup bottom sheet
        cardViewBottomSheet.setLayoutParams(bottomSheetLayoutParams);
        BottomSheetBehavior.from(cardViewBottomSheet).setSkipCollapsed(false);
        BottomSheetBehavior.from(cardViewBottomSheet).setPeekHeight(peekHeight);
        BottomSheetBehavior.from(cardViewBottomSheet).setHideable(true);

        buttonLayoutParams = (ConstraintLayout.LayoutParams) btnApply.getLayoutParams();

        //Calculate button margin from top
        buttonHeight = btnApply.getHeight(); //How tall is the button + experimental distance from bottom (Change based on your view)
        collapsedMargin = peekHeight - buttonHeight; //Button margin in bottom sheet collapsed state
        buttonLayoutParams.topMargin = collapsedMargin;
        btnApply.setLayoutParams(buttonLayoutParams);
        recyclerViewSearchResult.setPadding(0,0,0, (int) (buttonHeight *1.5));

       /* //OPTIONAL - Setting up recyclerview margins
        ConstraintLayout.LayoutParams recyclerLayoutParams = (ConstraintLayout.LayoutParams) recyclerView.getLayoutParams();
        float k = (buttonHeight - 60) / (float) buttonHeight; //60 is amount that you want to be hidden behind button
        recyclerLayoutParams.bottomMargin = (int) (k*buttonHeight); //Recyclerview bottom margin (from button)
        recyclerView.setLayoutParams(recyclerLayoutParams);*/


        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration divider =
                new DividerItemDecoration(context,
                        DividerItemDecoration.VERTICAL);
        recyclerViewSearchResult.setLayoutManager(linearLayoutManager);

        divider.setDrawable(ContextCompat.getDrawable(context,
                R.drawable.layer_line_divider));

        recyclerViewSearchResult.addItemDecoration(divider);

    }

    private void initBottomSheetCallback() {
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

    }

    private void bottomSheetStaticListWithMultiSelect(ArrayList<T> items, boolean isMultiSelect) {

        initBottomSheetCallback();

        initStaticRecyclerViewAdapter(items, isMultiSelect);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapterItemMultiSelectListener.onItemMultiSelect(bottomSheetRecyclerAdapter.getSelectedItems(), AdapterAction.SELECT);

            }
        });

    }

    private void bottomSheetStaticListWithSingleSelect(ArrayList<T> items, boolean isMultiSelect) {

        initStaticRecyclerViewAdapter(items, isMultiSelect);

    }

    private void bottomSheetDynamicListWithSearchAndMultiSelect(Context context, ArrayList<T> items, boolean isMultiSelect, String searchHint) {

        initBottomSheetCallback();

        initAsyncSearchListAdapter(items, isMultiSelect);

        initSearchView(context, items, isMultiSelect, searchHint);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapterItemMultiSelectListener.onItemMultiSelect(asyncSearchListAdapter.getSelectedItems(), AdapterAction.SELECT);

            }
        });

    }

    private void bottomSheetDynamicListWithSearchAndSingleSelect(Context context, ArrayList<T> items, boolean isMultiSelect, String searchHint) {

        initAsyncSearchListAdapter(items, isMultiSelect);

        initSearchView(context, items, isMultiSelect, searchHint);

    }

    private void initStaticRecyclerViewAdapter(ArrayList<T> items, boolean isMultiSelect) {
        bottomSheetRecyclerAdapter = new BottomSheetRecyclerAdapter<T>(items, isMultiSelect, new AdapterItemListener<T>() {
            @Override
            public void onItemSelect(T model, int position, AdapterAction action) {
                adapterItemListener.onItemSelect(model, position, action);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        recyclerViewSearchResult.setAdapter(bottomSheetRecyclerAdapter);
    }

    private void initAsyncSearchListAdapter(ArrayList<T> items, boolean isMultiSelect) {

        asyncSearchListAdapter = new AsyncSearchListAdapter<T>(isMultiSelect, new AdapterItemListener<T>() {
            @Override
            public void onItemSelect(T model, int position, AdapterAction action) {
                adapterItemListener.onItemSelect(model, position, action);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                searchView.setQuery("", false);

            }
        });

        asyncSearchListAdapter.submitList(items);
        recyclerViewSearchResult.setAdapter(asyncSearchListAdapter);
    }

    private void initSearchView(Context context, ArrayList<T> items, boolean isMultiSelect, String searchHint) {
        //
        ImageView closeBtn = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);

        EditText searchEditText = ((EditText)  searchView.findViewById(androidx.appcompat.R.id.search_src_text));

        searchEditText.setHintTextColor(context.getResources().getColor(R.color.colorTextSecondary));
        searchEditText.setTextColor(context.getResources().getColor(R.color.colorTextSecondary));

        closeBtn.setColorFilter(context.getResources().getColor(R.color.colorTextSecondary));
        searchIcon.setColorFilter(context.getResources().getColor(R.color.colorTextSecondary));

        // open search view by default
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.clearFocus();

        searchView.setQueryHint(searchHint);

        searchView.addTextWatcher(s -> {
            if (s.trim().length() > 0) {
                filter(s, items, isMultiSelect);
            } else {
                asyncSearchListAdapter.submitList(items);
            }
        }, 400);




        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
                searchView.setQuery("", false);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehavior.setPeekHeight(0);
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

        asyncSearchListAdapter = new AsyncSearchListAdapter<T>(isMultiSelect, new AdapterItemListener<T>() {
            @Override
            public void onItemSelect(T model, int position, AdapterAction action) {
                adapterItemListener.onItemSelect(model, position, action);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                searchView.setQuery("", false);
                filteredListBaseSearchDbModel.clear();

            }
        });

        recyclerViewSearchResult.setAdapter(asyncSearchListAdapter);
        asyncSearchListAdapter.submitList(filteredListBaseSearchDbModel);

    }

    public void bottomSheetBehaviorStateHandler() {
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setPeekHeight(0);

        }
    }
}
