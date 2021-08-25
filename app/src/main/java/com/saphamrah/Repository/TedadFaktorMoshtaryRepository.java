package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.TedadFaktorMoshtaryDAO;
import com.saphamrah.Model.TedadFaktorMoshtaryModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TedadFaktorMoshtaryRepository {
    Context context;
    TedadFaktorMoshtaryDAO tedadFaktorMoshtaryDAO;

    public TedadFaktorMoshtaryRepository(Context context) {
        this.context = context;
        tedadFaktorMoshtaryDAO = new TedadFaktorMoshtaryDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return tedadFaktorMoshtaryDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<TedadFaktorMoshtaryModel> tedadFaktorMoshtaryModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return tedadFaktorMoshtaryDAO.insertGroup(tedadFaktorMoshtaryModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<TedadFaktorMoshtaryModel> tedadFaktorMoshtaryModels) {
        return RxAsync.makeObservable(insertGroupCallable(tedadFaktorMoshtaryModels))
                .subscribeOn(Schedulers.io());
    }
}
