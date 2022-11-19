package com.saphamrah.customer.data.repository;

import android.content.Context;

import com.saphamrah.customer.data.local.database.AppDatabase;
import com.saphamrah.customer.data.local.database.entity.MoshtaryShomarehHesabTable;
import com.saphamrah.customer.data.local.database.entity.MoshtaryTable;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryShomarehHesabRepository {
    private AppDatabase database;

    public MoshtaryShomarehHesabRepository(Context context){
        database = AppDatabase.getInstance(context);
    }

    public Completable insertGroup(List<MoshtaryShomarehHesabTable> shomarehHesabs){
        return database.moshtaryShomarehHesabDao()
                .insert(shomarehHesabs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
