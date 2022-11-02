package com.saphamrah.customer.data.local.db.repositoryImpl;

import com.saphamrah.customer.data.local.db.entity.Company;
import com.saphamrah.customer.data.local.db.repository.CompanyRepository;

import io.reactivex.Flowable;

public class CompanyRepositoryImpl extends CompanyRepository {
    @Override
    protected void insertAll(Flowable<Company> items) {

    }

    @Override
    protected Flowable<Company> getAll() {
        return null;
    }

    @Override
    protected void delete(Company company) {

    }
}
