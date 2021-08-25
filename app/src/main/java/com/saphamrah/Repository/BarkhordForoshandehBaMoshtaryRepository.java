package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.BarkhordForoshandehBaMoshtaryDAO;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class BarkhordForoshandehBaMoshtaryRepository {
    Context context;
    BarkhordForoshandehBaMoshtaryDAO barkhordForoshandehBaMoshtaryDAO;


    public BarkhordForoshandehBaMoshtaryRepository(Context context) {
        this.context = context;
        barkhordForoshandehBaMoshtaryDAO = new BarkhordForoshandehBaMoshtaryDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> barkhordForoshandehBaMoshtaryDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels) {
        return () -> barkhordForoshandehBaMoshtaryDAO.insertGroup(barkhordForoshandehBaMoshtaryModels);
    }

    private Callable<Boolean> updateIsFavoriteByccBarkhordCallable(int ccBarkhord,boolean operation) {
        return () -> barkhordForoshandehBaMoshtaryDAO.updateIsFavoriteByccBarkhord(ccBarkhord,operation);
    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels) {
        return RxAsync.makeObservable(insertGroupCallable(barkhordForoshandehBaMoshtaryModels))
                .subscribeOn(Schedulers.io());
    }


    public Observable<Boolean> updateIsFavoriteByccBarkhord(int ccBarkhord,boolean operation) {
        return RxAsync.makeObservable(updateIsFavoriteByccBarkhordCallable(ccBarkhord,operation))
                .subscribeOn(Schedulers.io());
    }
}
