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
import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.data.LocationDbModel;
import com.saphamrah.customer.presentation.view.adapter.recycler.AsyncSearchListAdapter;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.ArrayList;

public class BottomSheetWithRecyclerView<T extends BaseBottomSheetRecyclerModel> {
    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetBehavior;
    private MaterialSearchView searchView;
    private RecyclerView recyclerViewSearchResult;
    private AsyncSearchListAdapter recyclerViewAdapter;
    private ArrayList<T> filteredListBaseSearchDbModel;
    private AdapterItemListener<T> adapterItemListener;

    public BottomSheetWithRecyclerView(AdapterItemListener<T> adapterItemListener) {
        this.adapterItemListener = adapterItemListener;
    }

    public void bottomSheetWithSearchAndRecyclerView(Context context,
                                              View view,
                                              ArrayList<T> items,
                                              String searchHint,
                                              boolean hasSearch
    ) {
        LinearLayout lnrlayBottomsheet = view.findViewById(R.id.linBottomSheet);
        searchView = view.findViewById(R.id.searchView);
        bottomSheetBehavior = BottomSheetBehavior.from(lnrlayBottomsheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        if (!hasSearch) {
            searchView.setVisibility(View.GONE);
        } else {
            searchView.setVisibility(View.VISIBLE);
        }

        recyclerViewSearchResult = view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewSearchResult.setLayoutManager(linearLayoutManager);

        recyclerViewAdapter = new AsyncSearchListAdapter<T>((model, position, action) -> {
            adapterItemListener.onItemSelect(model, position, action);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            searchView.closeSearch();
            recyclerViewSearchResult.setVisibility(View.GONE);
            recyclerViewSearchResult.removeAllViews();
        });

        recyclerViewSearchResult.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.submitList(items);


        recyclerViewSearchResult.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight() / 3);
        searchView.showSearch(true);
        searchView.setHint(searchHint);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0) {
                    filter(newText, items);
                } else {
                    visibleCloseSearchIcon(view);
                    recyclerViewAdapter.submitList(items);
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

    private void filter(String query,
                        ArrayList<T> items) {
        filteredListBaseSearchDbModel = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {

            final String text = items.get(i).getName().toLowerCase();
            if (text.contains(query)) {

                filteredListBaseSearchDbModel.add(items.get(i));
            }
        }

        Log.d("BottomSheetSearch", "filteredList: " + filteredListBaseSearchDbModel.size());

         recyclerViewAdapter = new AsyncSearchListAdapter<T>((model, position, action) -> {
            adapterItemListener.onItemSelect(model, position, action);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            searchView.closeSearch();
            filteredListBaseSearchDbModel.clear();
            recyclerViewSearchResult.setVisibility(View.GONE);
            recyclerViewSearchResult.removeAllViews();
        });
        recyclerViewSearchResult.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.submitList(filteredListBaseSearchDbModel);

    }

    private void visibleCloseSearchIcon(View view) {
        view.findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);

    }
}
