package com.saphamrah.customer.presentation.view.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.data.ProvinceDbModel;
import com.saphamrah.customer.databinding.ItemSearchProvinceBinding;
import com.saphamrah.customer.listeners.ProvinceListener;

import java.util.ArrayList;

public class SearchProvinceAdapter extends RecyclerView.Adapter<SearchProvinceAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ProvinceDbModel> provinceDbModels;
    private ProvinceListener provinceListener;

    public SearchProvinceAdapter(Context context, ArrayList<ProvinceDbModel> provinceDbModels, ProvinceListener provinceListener) {
        this.context = context;
        this.provinceDbModels = provinceDbModels;
        this.provinceListener = provinceListener;
    }

    @NonNull
    @Override
    public SearchProvinceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchProvinceBinding itemSearchProvinceBinding =
                ItemSearchProvinceBinding.inflate(LayoutInflater.from(context), parent, false);
        return new SearchProvinceAdapter.ViewHolder(itemSearchProvinceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchProvinceAdapter.ViewHolder holder, int position) {

        holder.bind(provinceDbModels.get(position));
    }

    @Override
    public int getItemCount() {
        return provinceDbModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemSearchProvinceBinding binding;

        public ViewHolder(@NonNull ItemSearchProvinceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ProvinceDbModel provinceDbModel) {
            binding.txtSearchProvince.setText(provinceDbModel.getName());
            binding.getRoot().setOnClickListener(v -> provinceListener.onClick(provinceDbModel));
        }
    }
}
