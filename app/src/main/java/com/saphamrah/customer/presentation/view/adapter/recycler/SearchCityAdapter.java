package com.saphamrah.customer.presentation.view.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.data.CityDbModel;
import com.saphamrah.customer.databinding.ItemSearchCityBinding;
import com.saphamrah.customer.listeners.CityListener;

import java.util.ArrayList;

public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CityDbModel> cityDbModels;
    private CityListener cityListener;

    public SearchCityAdapter(Context context, ArrayList<CityDbModel> cityDbModels, CityListener cityListener) {
        this.context = context;
        this.cityDbModels = cityDbModels;
        this.cityListener = cityListener;
    }

    @NonNull
    @Override
    public SearchCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchCityBinding itemSearchCityBinding =
                ItemSearchCityBinding.inflate(LayoutInflater.from(context), parent, false);
        return new SearchCityAdapter.ViewHolder(itemSearchCityBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCityAdapter.ViewHolder holder, int position) {
        holder.bind(cityDbModels.get(position));
    }

    @Override
    public int getItemCount() {
        return cityDbModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemSearchCityBinding binding;
        public ViewHolder(@NonNull ItemSearchCityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CityDbModel cityDbModel) {
            binding.txtSearchCity.setText(cityDbModel.getName());
            binding.getRoot().setOnClickListener(v -> cityListener.onClick(cityDbModel));
        }
    }
}
