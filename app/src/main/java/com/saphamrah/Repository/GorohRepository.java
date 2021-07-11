package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.GorohDAO;
import com.saphamrah.Model.GorohModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GorohRepository {
    Context context;
    GorohDAO gorohDAO;


    public GorohRepository(Context context) {
        this.context = context;
        gorohDAO = new GorohDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return gorohDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<GorohModel> gorohModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return gorohDAO.insertGroup(gorohModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<GorohModel> gorohModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(gorohModels))
                .subscribeOn(Schedulers.io());
    }


}
