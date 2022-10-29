package com.saphamrah.customer.presentation.view.customView;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
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
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemMultiSelectListener;

import java.util.ArrayList;

public class BottomSheetRecyclerView<T extends BaseBottomSheetRecyclerModel> {

    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView recyclerViewSearchResult;
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
        recyclerViewSearchResult = view.findViewById(R.id.recyclerView);


        bottomSheetBehavior = BottomSheetBehavior.from(cardViewBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        Button btnApply = view.findViewById(R.id.btnApplyBottomSheetRecycler);

        if (!isMultiSelect)
            btnApply.setVisibility(View.GONE);

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
                recyclerViewSearchResult.setVisibility(View.GONE);
                recyclerViewSearchResult.removeAllViews();
            }
        });

        recyclerViewSearchResult.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.submitList(items);

        recyclerViewSearchResult.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight() / 3);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterItemMultiSelectListener.onItemMultiSelect(recyclerViewAdapter.getSelectedItems(), AdapterAction.SELECT);
            }
        });


    }

    public void bottomSheetBehaviorStateHandler() {
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setPeekHeight(0);

        }
    }

}
