package com.saphamrah.customer.domain.repository;

import com.saphamrah.customer.data.local.db.dao.CompanyDao;
import com.saphamrah.customer.data.local.db.entity.Company;

import java.util.List;

import io.reactivex.Completable;

public class CompanyRepository {
    private CompanyDao companyDao;

    public CompanyRepository(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public List<Company> getAllCompanies() {

        return companyDao.getAll().blockingFirst();
    }

    public Completable insertCompanies(List<Company> companies) {
        return Completable.fromAction(() -> companyDao.insert(companies));
    }
}
