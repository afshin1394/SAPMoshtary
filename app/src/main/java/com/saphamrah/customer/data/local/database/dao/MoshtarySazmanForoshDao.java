package com.saphamrah.customer.data.local.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.saphamrah.customer.data.local.database.entity.MoshtarySazmanForoshTable;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface MoshtarySazmanForoshDao {

    @Insert
    Completable insert(List<MoshtarySazmanForoshTable> list);

    @Query(" SELECT * FROM MoshtarySazmanForoshTable ")
    List<MoshtarySazmanForoshTable> getAll();
}
