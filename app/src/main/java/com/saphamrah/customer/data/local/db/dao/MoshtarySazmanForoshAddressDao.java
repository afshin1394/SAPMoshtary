package com.saphamrah.customer.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.saphamrah.customer.base.BaseDao;
import com.saphamrah.customer.data.local.db.entity.MoshtarySazmanForoshAddress;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class MoshtarySazmanForoshAddressDao implements BaseDao<MoshtarySazmanForoshAddress> {

    @Query("SELECT * FROM moshtarysazmanforoshaddress")
    public abstract Flowable<List<MoshtarySazmanForoshAddress>> getAll();

}
