package com.saphamrah.customer.utils.customViews.bottomSheetModule.abstractions;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.R;
import com.saphamrah.customer.utils.customViews.SearchWatcher;

public abstract class AbstractBottomSheet {


    protected abstract void initBottomSheetView(Context context);
    public abstract void closeBottomSheet();
    public abstract int getState();

    protected void initSearchView(Context context,
                              SearchWatcher searchView,
                              String searchHint,
                              ImageView closeBtn,
                              ImageView searchIcon,
                              EditText searchEditText) {

        searchEditText.setHintTextColor(context.getResources().getColor(R.color.colorTextSecondary));
        searchEditText.setTextColor(context.getResources().getColor(R.color.colorTextSecondary));

        closeBtn.setColorFilter(context.getResources().getColor(R.color.colorTextSecondary));
//        searchIcon.setColorFilter(context.getResources().getColor(R.color.colorTextSecondary));

        searchView.setIconifiedByDefault(false);

       /* // open search view by default
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.clearFocus();*/

        searchView.setQueryHint(searchHint);

       /* searchView.addTextWatcher(s -> {
            if (s.trim().length() > 0) {
                filter(s, items);
            } else {
                asyncSearchListAdapter.submitList(items);
            }
        }, 400);*/
    }

    protected void initBottomSheetCallback(BottomSheetBehavior bottomSheetBehavior,
                                           Button btnApply,
                                           ConstraintLayout.LayoutParams buttonLayoutParams,
                                           int collapsedMargin,
                                           int expandedHeight,
                                           int buttonHeight) {
        
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset > 0) //Sliding happens from 0 (Collapsed) to 1 (Expanded) - if so, calculate margins
                    buttonLayoutParams.topMargin = (int) (((expandedHeight - buttonHeight *1.2) - collapsedMargin - buttonHeight / 2) * slideOffset + collapsedMargin);
                else //If not sliding above expanded, set initial margin
                    buttonLayoutParams.topMargin = collapsedMargin;
                btnApply.setLayoutParams(buttonLayoutParams); //Set layout params to button (margin from top)
            }
        });
    }
}
