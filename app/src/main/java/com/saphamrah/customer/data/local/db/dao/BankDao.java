package com.saphamrah.customer.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.saphamrah.customer.base.BaseDao;
import com.saphamrah.customer.data.local.db.entity.Bank;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class BankDao implements BaseDao<Bank> {

    @Query("SELECT * FROM bank")
    abstract Flowable<List<Bank>> getAll();

}
