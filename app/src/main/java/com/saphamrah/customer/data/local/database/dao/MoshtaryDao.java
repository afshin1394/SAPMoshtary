package com.saphamrah.customer.data.local.database.dao;

import androidx.room.Dao;
import androidx.room.DeleteColumn;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.saphamrah.customer.data.local.database.entity.MoshtaryTable;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface MoshtaryDao {

    @Query("SELECT * FROM MoshtaryTable")
    List<MoshtaryTable> getAll();

    @Insert
    Completable insert(List<MoshtaryTable> moshtary);


}
