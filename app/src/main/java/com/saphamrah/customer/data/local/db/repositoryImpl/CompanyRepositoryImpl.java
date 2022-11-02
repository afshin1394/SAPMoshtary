package com.saphamrah.customer.data.local.db.repositoryImpl;

import com.saphamrah.customer.data.local.db.entity.Company;
import com.saphamrah.customer.data.local.db.repository.CompanyRepository;

import java.util.List;

import io.reactivex.Flowable;

public class CompanyRepositoryImpl extends CompanyRepository {
    @Override
    protected void insertAll(List<Company> items) {

    }

    @Override
    protected Flowable<Company> getAll() {
        return null;
    }

    @Override
    protected void delete(Company company) {

    }
}
