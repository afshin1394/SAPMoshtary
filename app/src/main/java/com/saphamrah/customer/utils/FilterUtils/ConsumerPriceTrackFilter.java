package com.saphamrah.customer.utils.FilterUtils;

import android.util.Log;

import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.data.local.temp.ProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConsumerPriceTrackFilter implements Filter<ProductModel,FilterSortModel>{
    private static final String TAG = ConsumerPriceTrackFilter.class.getSimpleName();

    @Override
    public List<ProductModel> apply(List<ProductModel> model,List<FilterSortModel> filterSortModel) {
        List<ProductModel> filteredList = new ArrayList<>();
        for (ProductModel productModel : model)
        {
            if (productModel.getConsumerPrice() > filterSortModel.get(0).getMinValue() && productModel.getConsumerPrice() < filterSortModel.get(0).getMaxValue()) {
                filteredList.add(productModel);
            }
        }
        Log.i(TAG, "checkFilters-->CONSUMER_PRICE_TRACK:" + model);
        return filteredList;
    }
}
