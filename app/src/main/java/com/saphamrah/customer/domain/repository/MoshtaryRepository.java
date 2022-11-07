package com.saphamrah.customer.domain.repository;

import android.content.Context;
import android.util.Log;

import com.saphamrah.customer.data.local.database.AppDatabase;
import com.saphamrah.customer.data.local.database.entity.MoshtaryTable;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryRepository {
    private AppDatabase database;

    public MoshtaryRepository(Context context){
        database = AppDatabase.getInstance(context);
    }

    public Completable insertGroup(List<MoshtaryTable> moshtarys){
        return database.moshtaryDao()
                .insert(moshtarys)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
