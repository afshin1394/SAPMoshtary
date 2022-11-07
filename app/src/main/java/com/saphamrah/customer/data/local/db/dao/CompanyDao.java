package com.saphamrah.customer.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.saphamrah.customer.base.BaseDao;
import com.saphamrah.customer.data.local.db.entity.Company;

import java.util.List;

import io.reactivex.Observable;

@Dao
public abstract class CompanyDao implements BaseDao<Company> {

    @Query("SELECT * FROM company")
    public abstract Observable<List<Company>> getAll();

}
