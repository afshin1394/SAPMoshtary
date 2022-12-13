package com.saphamrah.customer.presentation.createRequest.filter.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.FilterSortModel;

import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.List;

import io.reactivex.Observable;

public class SortChoiceAdapter extends RecyclerView.Adapter<SortChoiceAdapter.ViewHolder> {

    private Context context;
    private List<FilterSortModel> sortModels;
    private AdapterItemListener<FilterSortModel> listener;
    private int prevSelection = -1;


    public SortChoiceAdapter(Context context, List<FilterSortModel> sortModels, AdapterItemListener<FilterSortModel> listener) {
        this.context = context;
        this.sortModels = sortModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SortChoiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_choice_list_itemview, parent, false);
        return new SortChoiceAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SortChoiceAdapter.ViewHolder holder, int position) {
           holder.materialCheckBox.setOnCheckedChangeListener(null);
           holder.bind(sortModels.get(position),position);
           holder.materialCheckBox.setOnCheckedChangeListener(holder.checkedListener);
    }

    @Override
    public int getItemCount() {
        return sortModels.size();
    }


    public List<FilterSortModel> getFilterSortList() {
        return sortModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private MaterialCheckBox materialCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materialCheckBox = itemView.findViewById(R.id.S_sort_choice);
        }

        public void bind(FilterSortModel filterModel, int position) {
            materialCheckBox.setText(filterModel.getName());

            if (filterModel.isEnabled()) {
                materialCheckBox.setChecked(true);
                prevSelection = position;
            } else {
                materialCheckBox.setChecked(false);
            }

        }

        private MaterialCheckBox.OnCheckedChangeListener checkedListener = (compoundButton, isChecked) -> {
            if (isChecked) {
                if (prevSelection >= 0) {
                    sortModels.get(prevSelection).setEnabled(false);
                    notifyItemChanged(prevSelection);
                }
                sortModels.get(getBindingAdapterPosition()).setEnabled(true);
                prevSelection = getBindingAdapterPosition();
            } else {
                sortModels.get(getBindingAdapterPosition()).setEnabled(false);
            }
            FilterSortModel filterSortModel = Observable.fromIterable(sortModels).filter(filterSortModel1 -> filterSortModel1.getId() == sortModels.get(getBindingAdapterPosition()).getId()).toList().blockingGet().get(0);
            listener.onItemSelect(filterSortModel, getBindingAdapterPosition(), AdapterAction.TOGGLE);
        };



    }
}
