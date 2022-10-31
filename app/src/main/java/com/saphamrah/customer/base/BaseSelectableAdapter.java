package com.saphamrah.customer.base;

import android.util.SparseBooleanArray;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSelectableAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private static final String TAG = BaseSelectableAdapter.class.getSimpleName();

    private final SparseBooleanArray selectedItems;

    BaseSelectableAdapter() {

        selectedItems = new SparseBooleanArray();

    }

    boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public void toggleSelection(int position) {


        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);

        } else {
            selectedItems.put(position, true);

        }
        notifyItemChanged(position);
    }

    public void selectAll() {
        for (int i = 0; i < getItemCount(); i++) {
            if (!(selectedItems.get(i, false))) {
                selectedItems.put(i, true);
            }
            notifyItemChanged(i);
        }
        notifyDataSetChanged();
    }

    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }
}