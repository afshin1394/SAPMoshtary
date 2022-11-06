package com.saphamrah.customer.domain.repository;

import android.content.Context;

import com.saphamrah.customer.data.local.db.entity.Bank;
import com.saphamrah.customer.data.local.db.localDataSource.BankLocalDataSource;
import com.saphamrah.customer.data.local.db.localDataSourceImpl.BankLocalDataSourceImpl;

import java.util.List;

import io.reactivex.Completable;

public class BankRepository {
   /* private final BankLocalDataSource bankLocalDataSource;

    public BankRepository() {
        bankLocalDataSource = new BankLocalDataSourceImpl();
    }


    public List<Bank> getAllBanks() {

        return bankLocalDataSource.getAll().blockingFirst();
    }

    public Completable insertAllBanks(List<Bank> banks) {
        return Completable.fromAction(() -> bankLocalDataSource.insertAll(banks));
    }*/

}
