package com.saphamrah.customer.createRequest.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.List;

public class FilterChildChoiceAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<FilterSortModel> filterSortModels;
    private AdapterItemListener<FilterSortModel> listener;

    public FilterChildChoiceAdapter(Context context, List<FilterSortModel> filterSortModels, AdapterItemListener<FilterSortModel> listener) {
        this.context = context;
        this.filterSortModels = filterSortModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_choice_list_child_itemview, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FilterSortModel filterSortModel = filterSortModels.get(position);
        ((ViewHolder) holder).bind(filterSortModel);
    }

    @Override
    public int getItemCount() {
        return filterSortModels.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
         private MaterialCheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.CB_filter_sort_choice);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemSelect(filterSortModels.get(getAdapterPosition()),getAdapterPosition(), AdapterAction.TOGGLE);
                }
            });
        }

        public void bind( FilterSortModel filterSortModel)
        {
            checkBox.setText(filterSortModel.getName());
        }
    }
}
