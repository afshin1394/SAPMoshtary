package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.KardexDAO;
import com.saphamrah.Model.KardexModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class KardexRepository {

    Context context;
    KardexDAO kardexDAO;


    public KardexRepository(Context context) {
        this.context = context;
        kardexDAO = new KardexDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> kardexDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<KardexModel> kardexModels) {
        return () -> kardexDAO.insertGroup(kardexModels);

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<KardexModel> kardexModels) {
        return RxAsync.makeObservable(insertGroupCallable(kardexModels))
                .subscribeOn(Schedulers.io());
    }
}
