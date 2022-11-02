package com.saphamrah.customer.data.local.db.repositoryImpl;

import com.saphamrah.customer.data.local.db.entity.Bank;
import com.saphamrah.customer.data.local.db.repository.BankRepository;

import io.reactivex.Flowable;

public class BankRepositoryImpl extends BankRepository {
    @Override
    protected void insertAll(Flowable<Bank> items) {

    }

    @Override
    protected Flowable<Bank> getAll() {
        return null;
    }

    @Override
    protected void delete(Bank bank) {

    }
}
