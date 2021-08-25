package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.NoeFaaliatForMoarefiMoshtaryJadidDAO;
import com.saphamrah.Model.NoeFaaliatForMoarefiMoshtaryJadidModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class NoeFaaliatForMoarefiMoshtaryJadidRepository {

    Context context;
    NoeFaaliatForMoarefiMoshtaryJadidDAO noeFaaliatForMoarefiMoshtaryJadidDAO;


    public NoeFaaliatForMoarefiMoshtaryJadidRepository(Context context) {
        this.context = context;
        noeFaaliatForMoarefiMoshtaryJadidDAO = new NoeFaaliatForMoarefiMoshtaryJadidDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> noeFaaliatForMoarefiMoshtaryJadidDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<NoeFaaliatForMoarefiMoshtaryJadidModel> noeFaaliatForMoarefiMoshtaryJadidModels) {
        return () -> noeFaaliatForMoarefiMoshtaryJadidDAO.insertGroup(noeFaaliatForMoarefiMoshtaryJadidModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<NoeFaaliatForMoarefiMoshtaryJadidModel> noeFaaliatForMoarefiMoshtaryJadidModels) {
        return RxAsync.makeObservable(insertGroupCallable(noeFaaliatForMoarefiMoshtaryJadidModels))
                .subscribeOn(Schedulers.io());
    }
}
