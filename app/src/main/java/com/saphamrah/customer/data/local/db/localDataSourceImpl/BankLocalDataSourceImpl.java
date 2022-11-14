package com.saphamrah.customer.data.local.db.localDataSourceImpl;

import android.content.Context;

import com.saphamrah.customer.Application;
import com.saphamrah.customer.data.local.db.SapDatabase;
import com.saphamrah.customer.data.local.db.dao.BankDao;
import com.saphamrah.customer.data.local.db.entity.Bank;
import com.saphamrah.customer.data.local.db.localDataSource.BankLocalDataSource;

import java.util.List;


import io.reactivex.Observable;

public class BankLocalDataSourceImpl extends BankLocalDataSource  {

    private BankDao bankDao;

    public BankLocalDataSourceImpl() {
        SapDatabase db = SapDatabase.getDatabase(Application.getInstance());
        bankDao = db.bankDao();
    }


    @Override
    public void insertAll(List<Bank> items) {
        bankDao.insert(items);
    }


    @Override
    public Observable<List<Bank>> getAll() {
        return bankDao.getAll();
    }

    @Override
    protected void getmoshtary() {

    }
}
