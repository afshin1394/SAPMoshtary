package com.saphamrah.customer.data.local.db.repositoryImpl;

import com.saphamrah.customer.Application;
import com.saphamrah.customer.data.local.db.SapDatabase;
import com.saphamrah.customer.data.local.db.dao.MoshtarySazmanForoshAddressDao;
import com.saphamrah.customer.data.local.db.entity.MoshtarySazmanForoshAddress;
import com.saphamrah.customer.data.local.db.repository.MoshtarySazmanForoshAddressRepository;

import java.util.List;

import io.reactivex.Flowable;

public class MoshtarySazmanForoshAddressImpl extends MoshtarySazmanForoshAddressRepository {

    private MoshtarySazmanForoshAddressDao moshtarySazmanForoshAddressDao;

    public MoshtarySazmanForoshAddressImpl(Application application) {
        SapDatabase db = SapDatabase.getDatabase(application);
        moshtarySazmanForoshAddressDao = db.moshtarySazmanForoshAddressDao();
    }

    @Override
    public void insertAll(List<MoshtarySazmanForoshAddress> items) {
        moshtarySazmanForoshAddressDao.insert(items);
    }

    @Override
    public Flowable<List<MoshtarySazmanForoshAddress>> getAll() {
        return moshtarySazmanForoshAddressDao.getAll();
    }

    @Override
    public void delete(MoshtarySazmanForoshAddress moshtarySazmanForoshAddress) {
        moshtarySazmanForoshAddressDao.delete(moshtarySazmanForoshAddress);
    }

}
