package com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.ArrayList;
import java.util.List;

public class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.ViewHolder>
{

    private Context context;
    private List<FilterSortModel> models;
    private AdapterItemListener<FilterSortModel> listener;


    public FilterListAdapter(Context context, List<FilterSortModel> models, AdapterItemListener<FilterSortModel> listener)
    {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_itemview , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        FilterSortModel filterSortModel = models.get(position);
        holder.bind(position,filterSortModel);
    }


    @Override
    public int getItemCount()
    {
        return models.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView removeImg;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.txt_name);
            removeImg = view.findViewById(R.id.img_close);

        }

        public void bind(int position,FilterSortModel filterSortModel)
        {
            title.setText(filterSortModel.getName());
            removeImg.setOnClickListener(view -> listener.onItemSelect(filterSortModel,position, AdapterAction.REMOVE));
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataChanged(List<FilterSortModel> models, AdapterItemListener<FilterSortModel> listener){
        this.models = models;
        this.listener = listener;
        notifyDataSetChanged();
    }

}
