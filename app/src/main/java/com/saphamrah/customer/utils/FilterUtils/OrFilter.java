package com.saphamrah.customer.utils.FilterUtils;

import java.util.ArrayList;
import java.util.List;

public class OrFilter<T,K> implements Filter<T,K>{
    private Filter<T,K> firstFilter;
    private Filter<T,K> secondFilter;

    public OrFilter(Filter<T, K> firstFilter, Filter<T, K> secondFilter) {
        this.firstFilter = firstFilter;
        this.secondFilter = secondFilter;
    }

    @Override
    public List<T> apply(List<T> model, List<K> filterSortModel) {
        List<K> filter1 = new ArrayList<>();
        List<K> filter2 = new ArrayList<>();
        filter1.add(filterSortModel.get(0));
        filter2.add(filterSortModel.get(1));
        List<T> firstFilteredEmployees = firstFilter.apply(model,filter1);
        List<T> secondFilterEmployees = secondFilter.apply(firstFilteredEmployees,filter2);

        List<T> orFilteredEmployees = new ArrayList<>(firstFilteredEmployees);
        secondFilterEmployees.removeAll(firstFilteredEmployees);
        orFilteredEmployees.addAll(secondFilterEmployees);
        return orFilteredEmployees;
    }
}
