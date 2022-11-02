package com.saphamrah.customer.base;

import io.reactivex.Flowable;

public abstract class BaseRepository<T> {
    protected abstract void insertAll(Flowable<T> items);
    protected abstract Flowable<T> getAll();
    protected abstract void delete(T t);
}
