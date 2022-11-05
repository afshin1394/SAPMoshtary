package com.saphamrah.customer.base;

import java.util.List;

import io.reactivex.Flowable;

public interface BaseRepository<T> {
    void insertAll(List<T> items);
    Flowable<List<T>> getAll();
    void delete(T t);
}
