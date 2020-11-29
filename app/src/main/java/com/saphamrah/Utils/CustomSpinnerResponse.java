package com.saphamrah.Utils;

import java.util.ArrayList;

public interface CustomSpinnerResponse
{

    void onApplySingleSelection(int selectedIndex);
    void onApplyMultiSelection(ArrayList<Integer> selectedIndexes);

}
