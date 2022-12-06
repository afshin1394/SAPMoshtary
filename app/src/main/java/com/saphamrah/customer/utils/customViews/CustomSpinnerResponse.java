package com.saphamrah.customer.utils.customViews;

import java.util.ArrayList;

public interface CustomSpinnerResponse {

    void onSingleSelect(int selectedIndex);
    void onMultiSelect(ArrayList<Integer> selectedIndexes);
}
