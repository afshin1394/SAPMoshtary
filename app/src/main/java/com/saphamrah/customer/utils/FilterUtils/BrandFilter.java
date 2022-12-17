package com.saphamrah.customer.utils.FilterUtils;

import android.util.Log;

import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.data.local.temp.ProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BrandFilter implements Filter<ProductModel,FilterSortModel>{
    private static final String TAG = BrandFilter.class.getSimpleName();

    @Override
    public List<ProductModel> apply(List<ProductModel> model,List<FilterSortModel> filterSortModels) {
        List<ProductModel> filteredList = new ArrayList<>();
        for (ProductModel productModel : model) {
            if (productModel.getBrandId() ==  filterSortModels.get(0).getId()) {
                filteredList.add(productModel);
            }
        }
        Log.i(TAG, "apply-->BRAND:" + filteredList);
        return filteredList;
    }
}
