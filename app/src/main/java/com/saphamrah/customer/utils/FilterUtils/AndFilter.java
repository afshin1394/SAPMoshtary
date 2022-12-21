package com.saphamrah.customer.utils.FilterUtils;

import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.data.local.temp.ProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AndFilter<T,K> implements Filter<T,K>{
    private Filter<T,K> firstFilter;
    private Filter<T,K> secondFilter;

    public AndFilter(Filter<T,K> firstFilter, Filter<T,K> secondFilter) {
        this.firstFilter = firstFilter;
        this.secondFilter = secondFilter;
    }

    @Override
    public List<T> apply(List<T> model,List<K> filterSortModel) {
        List<K> filter1 = new ArrayList<>();
        List<K> filter2 = new ArrayList<>();
        filter1.add(filterSortModel.get(0));
        filter2.add(filterSortModel.get(1));
        List<T> firstFilteredEmployees = firstFilter.apply(model,filter1);
        List<T> secondFilterEmployees = secondFilter.apply(firstFilteredEmployees,filter2);
        return secondFilterEmployees;
    }
}
