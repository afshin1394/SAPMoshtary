package com.saphamrah.customer.utils.AdapterUtil;

import java.util.ArrayList;

public interface AdapterItemMultiSelectListener<T> {
    void onItemMultiSelect(ArrayList<T> model, AdapterAction action);

}
