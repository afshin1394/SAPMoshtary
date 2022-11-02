package com.saphamrah.customer.data.local.db.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.saphamrah.customer.data.local.db.entity.Company;

import java.util.List;

import io.reactivex.Flowable;

public interface CompanyDao {

    @Query("SELECT * FROM company")
    Flowable<List<Company>> getAll();

    @Insert
    void insertAll(Company... companies);

    @Delete
    void delete(Company company);

}
