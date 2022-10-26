package com.saphamrah.customer.utils.AdapterUtil;


public interface AdapterItemListener<T> {
    void onItemSelect(T model, int position, AdapterAction action);
//    void onItemMultiSelect(ArrayList<T> model, ArrayList<Integer> position, AdapterAction action);
}
