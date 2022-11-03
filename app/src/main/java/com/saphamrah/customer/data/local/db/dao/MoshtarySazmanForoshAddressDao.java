package com.saphamrah.customer.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.saphamrah.customer.data.local.db.entity.MoshtarySazmanForoshAddress;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MoshtarySazmanForoshAddressDao {

    @Query("SELECT * FROM moshtarysazmanforoshaddress")
    Flowable<List<MoshtarySazmanForoshAddress>> getAll();

    @Insert
    void insertAll(MoshtarySazmanForoshAddress... moshtarySazmanForoshAddresses);

    @Delete
    void delete(MoshtarySazmanForoshAddress moshtarySazmanForoshAddress);

}
