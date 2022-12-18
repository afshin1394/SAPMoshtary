package com.saphamrah.customer.utils.FilterUtils;

import android.util.Log;

import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.data.local.temp.ProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SellPriceTrackFilter implements Filter<ProductModel,FilterSortModel>{
    private static final String TAG = SellPriceTrackFilter.class.getSimpleName();

    @Override
    public List<ProductModel> apply(List<ProductModel> model,List<FilterSortModel> filterSortModel) {
        List<ProductModel> filteredList = new ArrayList<>();
        for (ProductModel productModel : model) {
            if (productModel.getSellPrice() > filterSortModel.get(0).getMinValue() && productModel.getSellPrice() < filterSortModel.get(0).getMaxValue()) {
                filteredList.add(productModel);
            }
        }
        Log.i(TAG, "checkFilters-->SELL_PRICE_TRACK:" + model);
        return filteredList;
    }
}
