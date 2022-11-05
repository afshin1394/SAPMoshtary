package com.saphamrah.customer.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.saphamrah.customer.base.BaseDao;
import com.saphamrah.customer.data.local.db.entity.Bank;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public abstract class BankDao implements BaseDao<Bank> {

    @Query("SELECT * FROM bank")
    public abstract Observable<List<Bank>> getAll();

}
