package com.saphamrah.customer.base;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import kotlinx.coroutines.flow.Flow;

public interface BaseLocalDataSource<T> {
    void insertAll(List<T> items);
    Observable<List<T>> getAll();
}
