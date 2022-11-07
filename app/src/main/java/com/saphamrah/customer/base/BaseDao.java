package com.saphamrah.customer.base;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

public interface BaseDao<T> {
    @Insert
    void insert(T entity);

    @Insert
    void insert(List<T> entities);

    @Update
    void update(T entity);

    @Update
    void update(List<T> entities);

    @Delete
    void delete(T entity);

    @Delete
    void delete(List<T> entities);
}
