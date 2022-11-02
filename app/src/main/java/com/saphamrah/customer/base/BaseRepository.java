package com.saphamrah.customer.base;

import java.util.List;

import io.reactivex.Flowable;

public abstract class BaseRepository<T> {
    protected abstract void insertAll(List<T> items);
    protected abstract Flowable<T> getAll();
    protected abstract void delete(T t);
}
