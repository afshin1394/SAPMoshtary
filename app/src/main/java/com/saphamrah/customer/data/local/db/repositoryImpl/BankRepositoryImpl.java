package com.saphamrah.customer.data.local.db.repositoryImpl;

import com.saphamrah.customer.Application;
import com.saphamrah.customer.data.local.db.SapDatabase;
import com.saphamrah.customer.data.local.db.dao.BankDao;
import com.saphamrah.customer.data.local.db.entity.Bank;
import com.saphamrah.customer.data.local.db.repository.BankRepository;

import java.util.List;

import io.reactivex.Flowable;

public class BankRepositoryImpl extends BankRepository {

    private BankDao bankDao;

    public BankRepositoryImpl(Application application) {
        SapDatabase db = SapDatabase.getDatabase(application);
        bankDao = db.bankDao();
    }

    @Override
    public void insertAll(List<Bank> items) {
        bankDao.insert(items);
    }

    @Override
    public Flowable<List<Bank>> getAll() {
        return bankDao.getAll();
    }

    @Override
    public void delete(Bank bank) {
        bankDao.delete(bank);
    }
}
