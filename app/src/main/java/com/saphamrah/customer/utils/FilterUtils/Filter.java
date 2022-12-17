package com.saphamrah.customer.utils.FilterUtils;

import com.saphamrah.customer.data.local.temp.FilterSortModel;

import java.util.Collection;
import java.util.List;

public interface Filter<T,K> {
    List<T> apply(List<T> model, List<K> filterSortModel);
}
