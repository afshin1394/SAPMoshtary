package com.saphamrah.customer.createRequest.view.adapter;

import static com.saphamrah.customer.utils.Constants.FILTER_LIST;
import static com.saphamrah.customer.utils.Constants.FILTER_SLIDER;
import static com.saphamrah.customer.utils.Constants.SORT;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AnimationUtils;
import com.saphamrah.customer.utils.customViews.PersianRangeSlider;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterChoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<FilterSortModel> models;
    private List<FilterSortModel> allFilters;
    private AdapterItemListener<FilterSortModel> listener;
    private int filterSortType;


    public FilterChoiceAdapter(int filterSortType, Context context, List<FilterSortModel> filterGlobal, List<FilterSortModel> models, AdapterItemListener<FilterSortModel> listener) {
        this.filterSortType = filterSortType;
        this.context = context;
        this.models = models;
        this.listener = listener;
        this.allFilters = filterGlobal;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if (viewType == SORT) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_choice_list_itemview, parent, false);
                return new ViewHolderSortList(view1);
            }
            else if (viewType == FILTER_LIST)
            {
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_choice_itemview, parent, false);
                return new ViewHolderFilter(view2);
            }else if (viewType == FILTER_SLIDER){
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_choice_slider_itemview, parent, false);
                return new ViewHolderFilterSlider(view2);
            }else{
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_choice_slider_itemview, parent, false);
                return new ViewHolderFilterSlider(view2);
            }


    }

    @Override
    public int getItemViewType(int position) {
        if (filterSortType == 1) {
            return SORT;
        } else {
           return models.get(position).getType();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FilterSortModel filterModel = models.get(position);

        switch (holder.getItemViewType()) {
            case SORT:
                ViewHolderSortList viewHolderSort = (ViewHolderSortList) holder;
                viewHolderSort.bind(filterModel);
                break;

            case FILTER_LIST:
                ViewHolderFilter viewHolderFilter = (ViewHolderFilter) holder;
                viewHolderFilter.bind(filterModel);
                break;
            case FILTER_SLIDER:
                ViewHolderFilterSlider viewHolderFilterSlider = (ViewHolderFilterSlider) holder;
                viewHolderFilterSlider.bind(filterModel);
                break;
        }


    }


    @Override
    public int getItemCount() {
        return models.size();
    }


    public List<FilterSortModel> getFilterSortList() {
        return allFilters;
    }

    public class ViewHolderSortList extends RecyclerView.ViewHolder {
        private SwitchMaterial switchMaterial;


        @RequiresApi(api = Build.VERSION_CODES.N)
        public ViewHolderSortList(View view) {
            super(view);
            switchMaterial = view.getRootView().findViewById(R.id.S_sort_choice);
            switchMaterial.setOnClickListener(view1 -> {
                FilterSortModel filterSortModel = models.stream().filter(filterSortModel1 -> filterSortModel1.getId() == models.get(getAdapterPosition()).getId()).collect(Collectors.toList()).get(0);
                listener.onItemSelect(filterSortModel, getAdapterPosition(), AdapterAction.TOGGLE);
            });
        }

        public void bind(FilterSortModel filterModel) {
            switchMaterial.setText(filterModel.getName());
        }
    }
    public class ViewHolderFilter extends RecyclerView.ViewHolder implements AdapterItemListener<FilterSortModel> {
        private TextView title;
        private RecyclerView recyclerView;
        private FilterChildChoiceAdapter filterChildChoiceAdapter;
        private LinearLayoutManager linearLayoutManager;
        private ImageView imgArrow;
        private boolean isTouch = false;

        public ViewHolderFilter(View view) {
            super(view);
            title = view.findViewById(R.id.TV_filter_title);
            recyclerView = view.findViewById(R.id.RV_filter_list);
            imgArrow = view.findViewById(R.id.img_arrow);
            linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);


        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(FilterSortModel filterModel) {

            title.setText(filterModel.getName());
            imgArrow.setOnClickListener(view -> {


                if (!filterModel.isExpanded())
                {
                    imgArrow.setRotation(180);
                    AnimationUtils.expand(recyclerView);
                    filterModel.setExpanded(true);
                    setList(filterModel);
                } else {
                    imgArrow.setRotation(0);
                    AnimationUtils.collapse(recyclerView);
                    filterModel.setExpanded(false);
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

        @RequiresApi(api = Build.VERSION_CODES.N)
        private void setList(FilterSortModel filterModel) {

            List<FilterSortModel> filterSortModels = allFilters.stream().filter(new Predicate<FilterSortModel>() {
                @Override
                public boolean test(FilterSortModel filterSortModel) {
                    return filterModel.getId() == filterSortModel.getParent_id();
                }
            }).collect(Collectors.toList());

            filterChildChoiceAdapter = new FilterChildChoiceAdapter(context, filterSortModels, this);
            recyclerView.setAdapter(filterChildChoiceAdapter);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onItemSelect(FilterSortModel model, int position, AdapterAction Action) {
            switch (Action) {
                case TOGGLE:
                    FilterSortModel filterSortModel = allFilters.stream().filter(filterSortModel1 -> filterSortModel1.getId() == model.getId()).collect(Collectors.toList()).get(0);
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
        private RangeSlider rangeSlider;


        @RequiresApi(api = Build.VERSION_CODES.N)
        public ViewHolderFilterSlider(View view) {
            super(view);
            sliderTitle = view.findViewById(R.id.TV_slider_title);
            rangeSlider = view.findViewById(R.id.Slider_range_identifier);

        }

        public void bind(FilterSortModel filterModel) {
            sliderTitle.setText(filterModel.getName());

        }
    }


}
