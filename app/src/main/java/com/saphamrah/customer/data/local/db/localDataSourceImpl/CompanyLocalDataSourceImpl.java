package com.saphamrah.customer.data.local.db.localDataSourceImpl;

import com.saphamrah.customer.Application;
import com.saphamrah.customer.data.local.db.SapDatabase;
import com.saphamrah.customer.data.local.db.dao.CompanyDao;
import com.saphamrah.customer.data.local.db.entity.Company;
import com.saphamrah.customer.data.local.db.localDataSource.CompanyLocalDataSource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class CompanyLocalDataSourceImpl extends CompanyLocalDataSource {

    private CompanyDao companyDao;

    public CompanyLocalDataSourceImpl(Application application) {
        SapDatabase db = SapDatabase.getDatabase(application);
        companyDao = db.companyDao();
    }

    @Override
    public void insertAll(List<Company> items) {
        companyDao.insert(items);
    }

    @Override
    public Observable<List<Company>> getAll() {
        return companyDao.getAll();
    }


}
