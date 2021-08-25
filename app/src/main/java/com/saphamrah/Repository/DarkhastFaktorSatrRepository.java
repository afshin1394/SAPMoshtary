package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DarkhastFaktorSatrRepository {
    Context context;
    DarkhastFaktorSatrDAO darkhastFaktorSatrDAO;


    public DarkhastFaktorSatrRepository(Context context) {
        this.context = context;
        darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return darkhastFaktorSatrDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<DarkhastFaktorSatrModel> darkhastFaktorModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return darkhastFaktorSatrDAO.insertGroup(darkhastFaktorModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels) {
        return RxAsync.makeObservable(insertGroupCallable(darkhastFaktorSatrModels))
                .subscribeOn(Schedulers.io());
    }
}
