package com.saphamrah.customer.utils.customViews.bottomSheetModule.map;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.utils.customViews.SearchWatcher;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.abstractions.IApplyButton;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.abstractions.ICancelButton;
import com.saphamrah.customer.utils.customViews.bottomSheetModule.abstractions.ISearch;
import com.saphamrah.customer.utils.mapModule.MapDesigns.OsmDroid;
import com.saphamrah.customer.utils.mapModule.MapInstructor;
import com.saphamrah.customer.utils.mapModule.Models.MapObjectModel;

import org.osmdroid.views.MapView;

public class BottomSheetMap<V extends ViewBinding, M extends MapObjectModel> extends BaseBottomSheetMapView<V, M> implements ISearch, IApplyButton, ICancelButton {

    private ApplyButtonMap applyButtonMap;

    private boolean isSearchEnable;
    private boolean isButtonEnable;
    private String searchHint;

    private ConstraintLayout.LayoutParams buttonLayoutParams;
    private int collapsedMargin; //Button margin in collapsed state
    private int buttonHeight;

    public BottomSheetMap(V viewBinding,
                          Context context,
                          int parentLayoutBottomSheetResId,
                          M mapModel,
                          BottomSheetMapBuilder<V> mapViewBuilder,
                          ApplyButtonMap applyButtonMap) {

        super(viewBinding, context, parentLayoutBottomSheetResId, mapModel);

        this.isSearchEnable = mapViewBuilder.isSearchEnable;
        this.isButtonEnable = mapViewBuilder.isButtonEnable;
        this.searchHint = mapViewBuilder.searchHint;
        this.applyButtonMap = applyButtonMap;

        if (isSearchEnable) {
            searchView.setVisibility(View.VISIBLE);
            initSearchView(context, searchView, searchHint, closeBtn, searchIcon, searchEditText);
            closeButtonSearchListener();

        } else
            searchView.setVisibility(View.GONE);

        if (isButtonEnable) {
            btnApply.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            initApplyButtonView();
            onClickApplyButton();
            initCancelButtonView();
            onClickCancelButton();

        } else {
            btnApply.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);

        }

    }

    @Override
    protected void initSearchView(Context context, SearchWatcher searchView, String searchHint, ImageView closeBtn, ImageView searchIcon, EditText searchEditText) {
        super.initSearchView(context, searchView, searchHint, closeBtn, searchIcon, searchEditText);
    }

    @Override
    public void closeButtonSearchListener() {
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);

            }
        });
    }

    @Override
    public void initApplyButtonView() {

    }

    @Override
    public void onClickApplyButton() {
        //TODO hold lat lng
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapModel != null)
                applyButtonMap.onApplyButtonListener(mapModel.getLatLng());

                BottomSheetMap.super.closeBottomSheet();
            }
        });
    }

    @Override
    public void initCancelButtonView() {

    }

    @Override
    public void onClickCancelButton() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetMap.super.closeBottomSheet();
            }
        });
    }

    public static class BottomSheetMapBuilder<V extends ViewBinding> {
    /*    private V viewBinding;
        private Context context;
        private int resId;*/

        private boolean isSearchEnable;
        private boolean isButtonEnable;
        private String searchHint;

      /*  public BottomSheetMapBuilder<V> setViewBinding(V viewBinding) {
            this.viewBinding = viewBinding;
            return this;
        }

        public  BottomSheetMapBuilder<V> setContext(Context context) {
            this.context = context;
            return this;
        }

        public BottomSheetMapBuilder<V> setResId(int resId) {
            this.resId = resId;
            return this;
        }*/

        public BottomSheetMapBuilder<V> setButtonApplyEnable(boolean isButtonEnable) {
            this.isButtonEnable = isButtonEnable;
            return this;
        }

        public BottomSheetMapBuilder<V> setSearchEnable(boolean isSearchEnable) {
            this.isSearchEnable = isSearchEnable;
            return this;
        }

        public BottomSheetMapBuilder<V> setSearchHint(String searchHint) {
            this.searchHint = searchHint;
            return this;
        }

       /* public BottomSheetMap<V> create() {
            return new BottomSheetMap<V>(viewBinding, context, resId, this);
        }*/
    }

}
