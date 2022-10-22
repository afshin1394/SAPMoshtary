package com.saphamrah.customer.presentation.view.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.data.ProvinceDbModel;
import com.saphamrah.customer.databinding.ItemSearchProvinceBinding;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.ArrayList;

public class SearchProvinceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ProvinceDbModel> provinceDbModels;
    private AdapterItemListener<ProvinceDbModel> provinceListener;

    public SearchProvinceAdapter(Context context, ArrayList<ProvinceDbModel> provinceDbModels, AdapterItemListener<ProvinceDbModel> provinceListener) {
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(provinceDbModels.get(position));

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
            binding.getRoot().setOnClickListener(v ->
                    provinceListener.onItemSelect(provinceDbModel, getAdapterPosition(), AdapterAction.SELECT));
        }
    }
}
