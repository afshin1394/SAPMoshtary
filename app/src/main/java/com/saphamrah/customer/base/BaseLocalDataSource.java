package com.saphamrah.customer.base;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface BaseLocalDataSource<T> {
    void insertAll(List<T> items);
    Observable<List<T>> getAll();
}
