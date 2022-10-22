package com.saphamrah.customer.presentation.view.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.data.BaseSearchDbModel;
import com.saphamrah.customer.data.CityDbModel;
import com.saphamrah.customer.databinding.ItemBaseSearchBinding;
import com.saphamrah.customer.databinding.ItemSearchCityBinding;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.ArrayList;

public class BaseSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<BaseSearchDbModel> items;
    private AdapterItemListener<BaseSearchDbModel> adapterItemListener;

    public BaseSearchAdapter(Context context, ArrayList<BaseSearchDbModel> items, AdapterItemListener<BaseSearchDbModel> adapterItemListener) {
        this.context = context;
        this.items = items;
        this.adapterItemListener = adapterItemListener;
    }

    @NonNull
    @Override
    public BaseSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBaseSearchBinding itemBaseSearchBinding =
                ItemBaseSearchBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BaseSearchAdapter.ViewHolder(itemBaseSearchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseSearchAdapter.ViewHolder)holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemBaseSearchBinding binding;
        public ViewHolder(@NonNull ItemBaseSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BaseSearchDbModel baseSearchDbModel) {
            binding.txtSearch.setText(baseSearchDbModel.getName());
            binding.getRoot().setOnClickListener(v ->
                    adapterItemListener.onItemSelect(baseSearchDbModel, getAdapterPosition(), AdapterAction.SELECT));
        }
    }
}
