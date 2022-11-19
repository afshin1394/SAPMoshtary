package com.saphamrah.customer.data.repository;

import com.saphamrah.customer.data.local.db.dao.MoshtarySazmanForoshAddressDao;
import com.saphamrah.customer.data.local.db.entity.MoshtarySazmanForoshAddress;

import java.util.List;

import io.reactivex.Completable;

public class MoshtarySazmanForoshAddressRepository {

    private MoshtarySazmanForoshAddressDao moshtarySazmanForoshAddressDao;

    public MoshtarySazmanForoshAddressRepository(MoshtarySazmanForoshAddressDao moshtarySazmanForoshAddressDao) {
        this.moshtarySazmanForoshAddressDao = moshtarySazmanForoshAddressDao;
    }

    public List<MoshtarySazmanForoshAddress> getAllMoshtarySazmanForoshAddresses() {

        return moshtarySazmanForoshAddressDao.getAll().blockingFirst();
    }

    public Completable insertCompanies(List<MoshtarySazmanForoshAddress> moshtarySazmanForoshAddresses) {
        return Completable.fromAction(() -> moshtarySazmanForoshAddressDao.insert(moshtarySazmanForoshAddresses));
    }
}
