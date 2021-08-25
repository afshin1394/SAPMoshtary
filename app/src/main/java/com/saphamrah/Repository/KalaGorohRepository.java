package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.KalaGorohDAO;
import com.saphamrah.Model.KalaGorohModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class KalaGorohRepository {
    Context context;
    KalaGorohDAO kalaGorohDAO;


    public KalaGorohRepository(Context context) {
        this.context = context;
        kalaGorohDAO = new KalaGorohDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return kalaGorohDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<KalaGorohModel> kalaGorohModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return kalaGorohDAO.insertGroup(kalaGorohModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<KalaGorohModel> kalaGorohModels) {
        return RxAsync.makeObservable(insertGroupCallable(kalaGorohModels))
                .subscribeOn(Schedulers.io());
    }
}
