package com.saphamrah.customer.presentation.view.customView;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.presentation.view.adapter.recycler.AsyncSearchListAdapter;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.ArrayList;

public class BottomSheetRecyclerView<T extends BaseBottomSheetRecyclerModel> {

    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView recyclerViewSearchResult;
    private AsyncSearchListAdapter recyclerViewAdapter;
    private AdapterItemListener<T> adapterItemListener;

    public BottomSheetRecyclerView(AdapterItemListener<T> adapterItemListener) {
        this.adapterItemListener = adapterItemListener;
    }

    public void bottomSheetWithSearchAndRecyclerView(Context context,
                                                     View view,
                                                     ArrayList<T> items) {
        LinearLayout lnrlayBottomsheet = view.findViewById(R.id.linBottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(lnrlayBottomsheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        recyclerViewSearchResult = view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewSearchResult.setLayoutManager(linearLayoutManager);

        recyclerViewAdapter = new AsyncSearchListAdapter<T>((model, position, action) -> {
            adapterItemListener.onItemSelect(model, position, action);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            recyclerViewSearchResult.setVisibility(View.GONE);
            recyclerViewSearchResult.removeAllViews();
        });

        recyclerViewSearchResult.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.submitList(items);


        recyclerViewSearchResult.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight() / 3);


    }

}
