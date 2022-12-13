package com.saphamrah.customer.presentation.createRequest.filter.view.adapter;

import static com.saphamrah.customer.utils.Constants.FILTER;
import static com.saphamrah.customer.utils.Constants.FILTER_LIST;
import static com.saphamrah.customer.utils.Constants.FILTER_SLIDER;
import static com.saphamrah.customer.utils.Constants.SORT;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.presentation.createRequest.filter.view.adapter.FilterChildChoiceAdapter;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AnimationUtils;
import com.saphamrah.customer.utils.Constants;
import com.saphamrah.customer.utils.customViews.PersianRangeSlider;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

public class FilterChoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<FilterSortModel> allFilters;
    private AdapterItemListener<FilterSortModel> listener;

    List<FilterSortModel> parentListWithSlider;



    public FilterChoiceAdapter( Context context, List<FilterSortModel> filterGlobal,  AdapterItemListener<FilterSortModel> listener) {
        this.context = context;
        this.listener = listener;
        this.allFilters = filterGlobal;
        this.parentListWithSlider = Observable.fromIterable(allFilters).filter(filterSortModel -> filterSortModel.getParent_id() == 0 && filterSortModel.getType() == 2 && filterSortModel.getFilterType() == 3 || filterSortModel.getType() == 2 && filterSortModel.getFilterType() == 4).toList().blockingGet();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {




        if (viewType == FILTER_LIST) {
            View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_choice_itemview, parent, false);
            return new ViewHolderFilter(view2);
        } else {
            View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_choice_slider_itemview, parent, false);
            return new ViewHolderFilterSlider(view2);
        }


    }

    @Override
    public int getItemViewType(int position) {
        return parentListWithSlider.get(position).getFilterType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FilterSortModel filterModel = parentListWithSlider.get(position);

        switch (holder.getItemViewType()) {

            case FILTER_LIST:
                if (filterModel.getParent_id() == 0) {
                    ViewHolderFilter viewHolderFilter = (ViewHolderFilter) holder;
                    viewHolderFilter.bind(filterModel);
                }
                break;
            case FILTER_SLIDER:

                ViewHolderFilterSlider viewHolderFilterSlider = (ViewHolderFilterSlider) holder;
                viewHolderFilterSlider.bind(filterModel);
                break;
        }

    }


    @Override
    public int getItemCount() {
        return parentListWithSlider.size();
    }


    public List<FilterSortModel> getFilterSortList() {
        return allFilters;
    }



    public class ViewHolderFilter extends RecyclerView.ViewHolder implements AdapterItemListener<FilterSortModel> {
        private TextView title;
        private RecyclerView recyclerView;
        private FilterChildChoiceAdapter filterChildChoiceAdapter;
        private LinearLayoutManager linearLayoutManager;
        private LinearLayout linFilterTitle;
        private ImageView imgArrow;
        private boolean isTouch = false;

        public ViewHolderFilter(View view) {
            super(view);
            title = view.findViewById(R.id.TV_filter_title);
            recyclerView = view.findViewById(R.id.RV_filter_list);
            imgArrow = view.findViewById(R.id.img_arrow);
            linFilterTitle = view.findViewById(R.id.lin_filter_title);
            linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        boolean collapseNotify = false;

        public void bind(FilterSortModel filterModel) {

            title.setText(filterModel.getName());
            linFilterTitle.setOnClickListener(view -> {


                if (!filterModel.isExpanded()) {
                    imgArrow.setRotation(180);
                    AnimationUtils.expand(recyclerView);
                    filterModel.setExpanded(true);
                    setList(filterModel);
                } else {
                    imgArrow.setRotation(0);
                    AnimationUtils.collapse(recyclerView);
                    filterModel.setExpanded(false);
                    collapseNotify = true;
                    notifyDataSetChanged();
                }
            });


            if (filterModel.isExpanded()) {
                imgArrow.setRotation(180);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                imgArrow.setRotation(0);
                recyclerView.setVisibility(View.GONE);
                setList(filterModel);
            }


        }

        private void setList(FilterSortModel filterModel) {

            List<FilterSortModel> filterSortModels = Observable.fromIterable(allFilters).filter(filterSortModel -> filterModel.getId() == filterSortModel.getParent_id()).toList().blockingGet();

            filterChildChoiceAdapter = new FilterChildChoiceAdapter(context, filterSortModels, this);
            recyclerView.setAdapter(filterChildChoiceAdapter);
        }

        @Override
        public void onItemSelect(FilterSortModel model, int position, AdapterAction Action) {
            switch (Action)
            {
                case TOGGLE:
                    FilterSortModel filterSortModel = Observable.fromIterable(allFilters).filter(filterSortModel1 -> filterSortModel1.getId() == model.getId()).blockingFirst();
                    if (model.isEnabled()) {
                        filterSortModel.setEnabled(false);
                    } else {
                        filterSortModel.setEnabled(true);
                    }
                    listener.onItemSelect(filterSortModel, position, AdapterAction.SELECT);
                    break;
            }
        }
    }


    public class ViewHolderFilterSlider extends RecyclerView.ViewHolder {
        private TextView sliderTitle;
        private TextView sliderMax;
        private TextView sliderMin;
        private PersianRangeSlider simpleRangeView;


        public ViewHolderFilterSlider(View view) {
            super(view);
            sliderTitle = view.findViewById(R.id.TV_slider_title);
            sliderMax = view.findViewById(R.id.TV_slider_max);
            sliderMin = view.findViewById(R.id.TV_slider_min);
            simpleRangeView = view.findViewById(R.id.range_slider);
            simpleRangeView.addOnSliderTouchListener(sliderTouchListener);
            sliderMin.setText(String.format("%1$s%2$s%3$s",context.getString(R.string.from),"1000","ريال"));
            sliderMax.setText(String.format("%1$s%2$s%3$s",context.getString(R.string.to),"800000","ريال"));

        }

        public void bind(FilterSortModel filterModel) {
            sliderTitle.setText(filterModel.getName());

        }

        RangeSlider.OnSliderTouchListener sliderTouchListener = new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                sliderMin.setText(String.format("%1$s%2$s%3$s",context.getString(R.string.from),slider.getValues().get(0).intValue(),"ريال"));
                sliderMax.setText(String.format("%1$s%2$s%3$s",context.getString(R.string.to),slider.getValues().get(1).intValue(),"ريال"));
            }
        };
    }


}
