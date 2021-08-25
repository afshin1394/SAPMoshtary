package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.NoeMalekiatMoshtaryDAO;
import com.saphamrah.Model.NoeMalekiatMoshtaryModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class NoeMalekiatMoshtaryRepository {
    Context context;
    NoeMalekiatMoshtaryDAO noeMalekiatMoshtaryDAO;


    public NoeMalekiatMoshtaryRepository(Context context) {
        this.context = context;
        noeMalekiatMoshtaryDAO = new NoeMalekiatMoshtaryDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return noeMalekiatMoshtaryDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<NoeMalekiatMoshtaryModel> noeMalekiatMoshtaryModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return noeMalekiatMoshtaryDAO.insertGroup(noeMalekiatMoshtaryModels);
            }
        };

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<NoeMalekiatMoshtaryModel> noeMalekiatMoshtaryModels) {
        return RxAsync.makeObservable(insertGroupCallable(noeMalekiatMoshtaryModels))
                .subscribeOn(Schedulers.io());
    }
}
