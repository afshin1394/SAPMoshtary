package com.saphamrah.customer.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.saphamrah.customer.base.BaseDao;
import com.saphamrah.customer.data.local.db.entity.Company;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class CompanyDao implements BaseDao<Company> {

    @Query("SELECT * FROM company")
    abstract Flowable<List<Company>> getAll();

}
