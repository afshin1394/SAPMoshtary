package com.saphamrah.customer.domain.repository;

import com.saphamrah.customer.data.local.db.dao.BankDao;
import com.saphamrah.customer.data.local.db.entity.Bank;

import java.util.List;

import io.reactivex.Completable;

public class BankRepository {
    private BankDao bankDao;

    public BankRepository(BankDao bankDao) {
        this.bankDao = bankDao;
    }


    public List<Bank> getAllBanks() {

        return bankDao.getAll().blockingFirst();
    }

    public Completable insertBanks(List<Bank> banks) {
        return Completable.fromAction(() -> bankDao.insert(banks));
    }

}
