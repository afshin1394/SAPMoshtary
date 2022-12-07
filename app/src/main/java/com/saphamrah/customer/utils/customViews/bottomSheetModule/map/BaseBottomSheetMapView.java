package com.saphamrah.customer.utils.customViews.bottomSheetModule.map;

import static com.saphamrah.customer.utils.ScreenUtils.getWindowHeight;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.viewbinding.ViewBinding;


import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saphamrah.customer.R;
import com.saphamrah.customer.utils.customViews.SearchWatcher;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.BaseBottomSheet;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.abstractions.IMap;
import com.saphamrah.customer.utils.mapModule.MapInstructor;
import com.saphamrah.customer.utils.mapModule.Models.MapObjectModel;

import org.osmdroid.config.Configuration;

public class BaseBottomSheetMapView<V extends ViewBinding, M extends MapObjectModel> extends BaseBottomSheet<V> implements IMap {

    protected M mapModel;
    protected View view;

    protected SearchWatcher searchView;
    protected Button btnApply;
    protected Button btnCancel;
    public FloatingActionButton fabGetCurrentLocation;

    protected ImageView closeBtn;
    protected ImageView searchIcon;
    protected EditText searchEditText;

    public BaseBottomSheetMapView(V viewBinding,
                                  Context context,
                                  int parentLayoutBottomSheetResId,
                                  M mapModel
                                  ) {

        super(viewBinding, context, parentLayoutBottomSheetResId);

        this.mapModel = mapModel;

        view = viewBinding.getRoot();

        searchView = view.findViewById(R.id.searchView_mapView_bottomSheet);
        fabGetCurrentLocation = view.findViewById(R.id.fab_location_mapView_bottomSheet);

        closeBtn = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        btnApply = view.findViewById(R.id.button_apply_mapView_bottomSheet);
        btnCancel = view.findViewById(R.id.button_cancel_mapView_bottomSheet);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                bottomSheetBehavior.setDraggable(newState != BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        initMapView();
    }

    @Override
    protected void initBottomSheetView(Context context) {
        viewBottomsheet.setBackgroundResource(R.drawable.bottom_dialog_shape);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        //Retrieve bottom sheet parameters

        //Setup bottom sheet
        BottomSheetBehavior.from(viewBottomsheet).setSkipCollapsed(false);
        BottomSheetBehavior.from(viewBottomsheet).setHideable(true);
    }

    @Override
    public void initMapView() {
        initBottomSheetView(context);
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

    }

    public FloatingActionButton getFabGetCurrentLocation() {
        return fabGetCurrentLocation;
    }
}
