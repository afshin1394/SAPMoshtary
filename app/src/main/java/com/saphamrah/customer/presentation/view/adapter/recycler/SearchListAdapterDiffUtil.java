package com.saphamrah.customer.presentation.view.adapter.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.saphamrah.customer.data.LocationDbModel;

import java.util.Objects;

public class SearchListAdapterDiffUtil extends DiffUtil.ItemCallback<LocationDbModel> {
    @Override
    public boolean areItemsTheSame(@NonNull LocationDbModel oldItem, @NonNull LocationDbModel newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName());
    }

    @Override
    public boolean areContentsTheSame(@NonNull LocationDbModel oldItem, @NonNull LocationDbModel newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName());
    }
}
