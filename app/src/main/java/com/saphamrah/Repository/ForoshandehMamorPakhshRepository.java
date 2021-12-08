package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ForoshandehMamorPakhshRepository {
    Context context;
    ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO;


    public ForoshandehMamorPakhshRepository(Context context) {
        this.context = context;
        foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> foroshandehMamorPakhshDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels) {
        return () -> foroshandehMamorPakhshDAO.insertGroup(foroshandehMamorPakhshModels);

    }
    private Callable<ArrayList<ForoshandehMamorPakhshModel>>  getAllCallable() {
        return () -> foroshandehMamorPakhshDAO.getAll();

    }
    private Callable<ForoshandehMamorPakhshModel>  getForoshandehMamorPakhshCallable() {
        return () -> foroshandehMamorPakhshDAO.getIsSelect();

    }


    private Callable<ForoshandehMamorPakhshModel>  getIsSelectCallable() {
        return () -> foroshandehMamorPakhshDAO.getIsSelect();

    }
    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels) {
        return RxAsync.makeObservable(insertGroupCallable(foroshandehMamorPakhshModels))
                .subscribeOn(Schedulers.io());
    }



    public Observable<ArrayList<ForoshandehMamorPakhshModel>> getAll() {
        return RxAsync.makeObservable(getAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<ForoshandehMamorPakhshModel> getForoshandehMamorPakhsh() {
        return RxAsync.makeObservable(getForoshandehMamorPakhshCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<ForoshandehMamorPakhshModel> getIsSelect() {
        return RxAsync.makeObservable(getIsSelectCallable())
                .subscribeOn(Schedulers.io());
    }
}
