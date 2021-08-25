package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ForoshandehEtebarDAO;
import com.saphamrah.Model.ForoshandehEtebarModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ForoshandehEtebarRepository {
    Context context;
    ForoshandehEtebarDAO foroshandehEtebarDAO;


    public ForoshandehEtebarRepository(Context context) {
        this.context = context;
        foroshandehEtebarDAO = new ForoshandehEtebarDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return foroshandehEtebarDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ForoshandehEtebarModel> foroshandehEtebarModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return foroshandehEtebarDAO.insertGroup(foroshandehEtebarModels);
            }
        };

    }
    private Callable<Boolean> deleteByccForoshanhdeCallable(int ccForoshandeh) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return foroshandehEtebarDAO.deleteByccForoshanhde(ccForoshandeh);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ForoshandehEtebarModel> foroshandehEtebarModels) {
        return RxAsync.makeObservable(insertGroupCallable(foroshandehEtebarModels))
                .subscribeOn(Schedulers.io());
    }


    public Observable<Boolean> insertGroup(int ccForoshandeh) {
        return RxAsync.makeObservable(deleteByccForoshanhdeCallable(ccForoshandeh))
                .subscribeOn(Schedulers.io());
    }
}
