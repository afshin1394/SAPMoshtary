package com.saphamrah.customer.data.local.db.localDataSourceImpl;

import com.saphamrah.customer.Application;
import com.saphamrah.customer.data.local.db.SapDatabase;
import com.saphamrah.customer.data.local.db.dao.MoshtarySazmanForoshAddressDao;
import com.saphamrah.customer.data.local.db.entity.MoshtarySazmanForoshAddress;
import com.saphamrah.customer.data.local.db.localDataSource.MoshtarySazmanForoshAddressLocalDataSource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class MoshtarySazmanForoshAddressLocalDataSourceImpl extends MoshtarySazmanForoshAddressLocalDataSource {

    private MoshtarySazmanForoshAddressDao moshtarySazmanForoshAddressDao;

    public MoshtarySazmanForoshAddressLocalDataSourceImpl(Application application) {
        SapDatabase db = SapDatabase.getDatabase(application);
        moshtarySazmanForoshAddressDao = db.moshtarySazmanForoshAddressDao();
    }

    @Override
    public void insertAll(List<MoshtarySazmanForoshAddress> items) {
        moshtarySazmanForoshAddressDao.insert(items);
    }

    @Override
    public Observable<List<MoshtarySazmanForoshAddress>> getAll() {
        return moshtarySazmanForoshAddressDao.getAll();
    }

}
