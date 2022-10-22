package com.saphamrah.customer.presentation.view.adapter.recycler

import androidx.recyclerview.widget.DiffUtil
import com.saphamrah.customer.data.LocationDbModel

class SearchListAdapterDiffUtil: DiffUtil.ItemCallback<LocationDbModel>() {
    override fun areItemsTheSame(oldItem: LocationDbModel, newItem: LocationDbModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: LocationDbModel, newItem: LocationDbModel): Boolean {
        return oldItem.name == newItem.name
    }
}