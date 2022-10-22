package com.saphamrah.customer.presentation.view.adapter.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.data.LocationDbModel;
import com.saphamrah.customer.databinding.ItemBaseSearchBinding;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.List;

public class AsyncSearchListAdapter extends ListAdapter<LocationDbModel, AsyncSearchListAdapter.ViewHolder> {
    private final AsyncListDiffer<LocationDbModel> mDiffer = new AsyncListDiffer(this, new SearchListAdapterDiffUtil());

    private AdapterItemListener<LocationDbModel> adapterItemListener;


    public AsyncSearchListAdapter(AdapterItemListener<LocationDbModel> adapterItemListener) {
        super(new SearchListAdapterDiffUtil());
        this.adapterItemListener = adapterItemListener;
    }

    @NonNull
    @Override
    public AsyncSearchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBaseSearchBinding itemBaseSearchBinding =
                ItemBaseSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AsyncSearchListAdapter.ViewHolder(itemBaseSearchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AsyncSearchListAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.bind(mDiffer.getCurrentList().get(position));

    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    @Override
    public void submitList(List<LocationDbModel> list) {
        mDiffer.submitList(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemBaseSearchBinding binding;

        public ViewHolder(@NonNull ItemBaseSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(LocationDbModel baseSearchDbModel) {
            binding.txtSearch.setText(baseSearchDbModel.getName());
            binding.getRoot().setOnClickListener(v ->
                    adapterItemListener.onItemSelect(baseSearchDbModel, getAdapterPosition(), AdapterAction.SELECT));
        }
    }
}
