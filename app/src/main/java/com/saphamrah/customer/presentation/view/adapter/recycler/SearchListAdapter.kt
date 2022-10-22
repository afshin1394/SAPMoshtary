package com.saphamrah.customer.presentation.view.adapter.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saphamrah.customer.data.LocationDbModel
import com.saphamrah.customer.databinding.ItemBaseSearchBinding
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener


class SearchListAdapter(private val listener: AdapterItemListener<LocationDbModel>
) :
    ListAdapter<LocationDbModel, SearchListAdapter.ViewHolder>(SearchListAdapterDiffUtil()) {

    private val mDiffer: AsyncListDiffer<LocationDbModel> = AsyncListDiffer(this, SearchListAdapterDiffUtil())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBaseSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setIsRecyclable(false)
        holder.bind(mDiffer.currentList[position], listener)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun submitList(list: List<LocationDbModel>?) {
        mDiffer.submitList(list)
    }

    class ViewHolder(private val binding: ItemBaseSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("CheckResult")
        fun bind(item: LocationDbModel, listener: AdapterItemListener<LocationDbModel>) {
            binding.txtSearch.text = item.name

            binding.root.setOnClickListener {
                listener.onItemSelect(item, adapterPosition, AdapterAction.SELECT)
            }
        }

    }

}
