package com.saphamrah.customer.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.saphamrah.customer.data.local.db.entity.Bank;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface BankDao {

    @Query("SELECT * FROM bank")
    Flowable<List<Bank>> getAll();

    @Insert
    void insertAll(Bank... banks);

    @Delete
    void delete(Bank bank);

}
