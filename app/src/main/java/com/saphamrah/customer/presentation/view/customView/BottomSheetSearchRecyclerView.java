package com.saphamrah.customer.presentation.view.customView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.cardview.widget.CardView;
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
import com.saphamrah.customer.utils.customViews.MaterialSearchWatcher;

import java.util.ArrayList;

public class BottomSheetSearchRecyclerView<T extends BaseBottomSheetRecyclerModel> {
    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetBehavior;
    private MaterialSearchWatcher searchView;
    private RecyclerView recyclerViewSearchResult;
    private AsyncSearchListAdapter recyclerViewAdapter;
    private ArrayList<T> filteredListBaseSearchDbModel;
    private AdapterItemListener<T> adapterItemListener;

    public BottomSheetSearchRecyclerView(AdapterItemListener<T> adapterItemListener) {
        this.adapterItemListener = adapterItemListener;
    }

    public void bottomSheetWithSearchAndRecyclerView(Context context,
                                                     View view,
                                                     ArrayList<T> items,
                                                     String searchHint,
                                                     boolean isMultiSelect
    ) {
        CardView cardViewBottomSheet = view.findViewById(R.id.cardViewBottomSheetSearch);
        searchView = view.findViewById(R.id.searchView);

        bottomSheetBehavior = BottomSheetBehavior.from(cardViewBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        recyclerViewSearchResult = view.findViewById(R.id.recyclerViewSearch);
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
                searchView.closeSearch();
                recyclerViewSearchResult.setVisibility(View.GONE);
                recyclerViewSearchResult.removeAllViews();

            }
        });

        recyclerViewSearchResult.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.submitList(items);


        recyclerViewSearchResult.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight() / 3);
        searchView.showSearch(true);
        searchView.setHint(searchHint);
        view.findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);
        view.findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn).setVisibility(View.GONE);

        searchView.addTextWatcher(s -> {
            if (s.trim().length() > 0) {
                filter(s, items, isMultiSelect);
            } else {
//                visibleCloseSearchIcon(view);
                recyclerViewAdapter.submitList(items);
            }
        }, 400);

        view.findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setPeekHeight(0);
            searchView.closeSearch();

        });

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
                searchView.closeSearch();
                filteredListBaseSearchDbModel.clear();
                recyclerViewSearchResult.setVisibility(View.GONE);
                recyclerViewSearchResult.removeAllViews();
            }
        });


        recyclerViewSearchResult.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.submitList(filteredListBaseSearchDbModel);

    }

/*    private void visibleCloseSearchIcon(View view) {
        view.findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);

    }*/

    public void bottomSheetBehaviorStateHandler() {
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setPeekHeight(0);

        }
    }
}
