package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.KardexSatrDAO;
import com.saphamrah.Model.KardexSatrModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class KardexSatrRepository {
    Context context;
    KardexSatrDAO kardexSatrDAO;


    public KardexSatrRepository(Context context) {
        this.context = context;
        kardexSatrDAO = new KardexSatrDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> kardexSatrDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<KardexSatrModel> kardexSatrModels) {
        return () -> kardexSatrDAO.insertGroup(kardexSatrModels);

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<KardexSatrModel> kardexSatrModels) {
        return RxAsync.makeObservable(insertGroupCallable(kardexSatrModels))
                .subscribeOn(Schedulers.io());
    }
}
