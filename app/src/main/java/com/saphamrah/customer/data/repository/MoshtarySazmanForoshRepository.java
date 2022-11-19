package com.saphamrah.customer.data.repository;

import android.content.Context;
import android.widget.LinearLayout;
import com.saphamrah.customer.data.local.database.AppDatabase;
import com.saphamrah.customer.data.local.database.entity.MoshtarySazmanForoshTable;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MoshtarySazmanForoshRepository {
    public AppDatabase database;

    public MoshtarySazmanForoshRepository(Context context) {
        database = AppDatabase.getInstance(context);
    }

    public Completable insertGroup(List<MoshtarySazmanForoshTable> list){
        return database.moshtarySazmanForoshDao()
                .insert(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
