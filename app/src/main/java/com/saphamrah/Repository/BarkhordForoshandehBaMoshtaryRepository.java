package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.BarkhordForoshandehBaMoshtaryDAO;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

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



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(barkhordForoshandehBaMoshtaryModels))
                .subscribeOn(Schedulers.io());
    }
}
