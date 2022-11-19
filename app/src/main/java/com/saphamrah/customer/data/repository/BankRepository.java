package com.saphamrah.customer.data.repository;

import android.content.Context;

import com.saphamrah.customer.data.local.db.entity.Bank;
import com.saphamrah.customer.data.local.db.localDataSource.BankLocalDataSource;
import com.saphamrah.customer.data.local.db.localDataSourceImpl.BankLocalDataSourceImpl;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BankRepository {
    private final BankLocalDataSource bankLocalDataSource;

    public BankRepository() {
        bankLocalDataSource = new BankLocalDataSourceImpl();
    }


    public List<Bank> getAllBanks() {

        return bankLocalDataSource.getAll().blockingFirst();
    }

    public Completable insertAllBanks(List<Bank> banks) {
        return Completable.fromAction(() -> bankLocalDataSource.insertAll(banks))
                .subscribeOn(Schedulers.io());

    }

}
