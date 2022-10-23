package com.saphamrah.customer.presentation.view.adapter.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.data.LocationDbModel;

import java.util.Objects;

public class SearchListAdapterDiffUtil<T extends BaseBottomSheetRecyclerModel> extends DiffUtil.ItemCallback<T> {
    @Override
    public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName());
    }

    @Override
    public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName());
    }
}
