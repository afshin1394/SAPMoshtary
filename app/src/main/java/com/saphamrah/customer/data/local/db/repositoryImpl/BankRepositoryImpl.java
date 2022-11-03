package com.saphamrah.customer.data.local.db.repositoryImpl;

import com.saphamrah.customer.data.local.db.entity.Bank;
import com.saphamrah.customer.data.local.db.repository.BankRepository;

import java.util.List;

import io.reactivex.Flowable;

public class BankRepositoryImpl extends BankRepository {
    @Override
    public void insertAll(List<Bank> items) {

    }

    @Override
    public Flowable<Bank> getAll() {
        return null;
    }

    @Override
    public void delete(Bank bank) {

    }
}
