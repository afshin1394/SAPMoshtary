package com.saphamrah.customer.data.local.db.repositoryImpl;

import com.saphamrah.customer.data.local.db.entity.Company;
import com.saphamrah.customer.data.local.db.repository.CompanyRepository;

import java.util.List;

import io.reactivex.Flowable;

public class CompanyRepositoryImpl extends CompanyRepository {
    @Override
    public void insertAll(List<Company> items) {

    }

    @Override
    public Flowable<Company> getAll() {
        return null;
    }

    @Override
    public void delete(Company company) {

    }
}
