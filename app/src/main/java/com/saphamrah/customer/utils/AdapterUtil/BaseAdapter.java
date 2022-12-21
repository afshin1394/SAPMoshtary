package com.saphamrah.customer.utils.AdapterUtil;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.List;

public abstract class BaseAdapter<V extends ViewBinding,T extends Object> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected View view;
    protected List<T> models;
    protected AdapterItemListener<T> listener;
    private int lastPosition = -1;
    private int animRes;

    public BaseAdapter(View view,int animRes, List<T> models, AdapterItemListener<T> listener) {
        this.view = view;
        this.models = models;
        this.listener = listener;
        this.animRes = animRes;
    }

    protected abstract void setData(int position);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseAdapter.BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder) holder).bind(position);
        setAnimation(holder.itemView,position,animRes);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(int position) {
            lastPosition = position;
            setData(position);
        }
    }
    protected void setAnimation(View view,int position,int animRes){
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), animRes);
            view.startAnimation(animation);
            lastPosition = position;
        }
    }

}
