package com.saphamrah.customer.presentation.view.customView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.LocationDbModel;
import com.saphamrah.customer.presentation.view.adapter.recycler.BaseSearchAdapter;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.ArrayList;

public class BottomSheetSearch {
    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetBehavior;
    private MaterialSearchView searchView;
    private RecyclerView recyclerViewSearchResult;
    private BaseSearchAdapter recyclerViewAdapter;
    private ArrayList<LocationDbModel> filteredListBaseSearchDbModel;
    private AdapterItemListener<LocationDbModel> adapterItemListener;

    public BottomSheetSearch(AdapterItemListener<LocationDbModel> adapterItemListener) {
        this.adapterItemListener = adapterItemListener;
    }

    public void bottomSheetWithSearchAndRecyclerView(Context context,
                                              View view,
                                              ArrayList<LocationDbModel> items
    ) {
        LinearLayout lnrlayBottomsheet = view.findViewById(R.id.linBottomSheet);
        searchView = view.findViewById(R.id.searchView);
        bottomSheetBehavior = BottomSheetBehavior.from(lnrlayBottomsheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        recyclerViewSearchResult = view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewSearchResult.setLayoutManager(linearLayoutManager);

        recyclerViewAdapter = new BaseSearchAdapter(context, items, (model, position, action) -> {
            adapterItemListener.onItemSelect(model, position, action);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            searchView.closeSearch();
            recyclerViewSearchResult.setVisibility(View.GONE);
            recyclerViewSearchResult.removeAllViews();
            recyclerViewAdapter.notifyDataSetChanged();
        });
        recyclerViewSearchResult.setAdapter(recyclerViewAdapter);

        recyclerViewSearchResult.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight() / 3);
        searchView.showSearch(true);
        searchView.setHint(context.getResources().getString(R.string.searchProvince));

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchWord = query.trim();
                filter(context, searchWord, items);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0) {
                    filter(context, newText, items);
                } else {
                    visibleCloseSearchIcon(view);
                }
                return false;
            }
        });

        view.findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            searchView.closeSearch();
            recyclerViewSearchResult.setVisibility(View.GONE);
            recyclerViewSearchResult.removeAllViews();


        });

    }

    private void filter(Context context,
                        String query,
                        ArrayList<LocationDbModel> items) {
        filteredListBaseSearchDbModel = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {

            final String text = items.get(i).getName().toLowerCase();
            if (text.contains(query)) {

                filteredListBaseSearchDbModel.add(items.get(i));
            }
        }

        Log.d("BottomSheetSearch", "filteredList: " + filteredListBaseSearchDbModel.size());

         recyclerViewAdapter = new BaseSearchAdapter(context, filteredListBaseSearchDbModel, (model, position, action) -> {
            adapterItemListener.onItemSelect(model, position, action);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            searchView.closeSearch();
            filteredListBaseSearchDbModel.clear();
            recyclerViewSearchResult.setVisibility(View.GONE);
            recyclerViewSearchResult.removeAllViews();
        });
        recyclerViewSearchResult.setAdapter(recyclerViewAdapter);

        recyclerViewSearchResult.removeAllViews();
        recyclerViewAdapter.notifyDataSetChanged();  // data set changed
    }

    private void visibleCloseSearchIcon(View view) {
        view.findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);

    }
}
