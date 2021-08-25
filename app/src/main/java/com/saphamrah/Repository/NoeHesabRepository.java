package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.NoeHesabDAO;
import com.saphamrah.Model.NoeHesabModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class NoeHesabRepository {
    Context context;
    NoeHesabDAO noeHesabDAO;


    public NoeHesabRepository(Context context) {
        this.context = context;
        noeHesabDAO = new NoeHesabDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return noeHesabDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<NoeHesabModel> noeHesabModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return noeHesabDAO.insertGroup(noeHesabModels);
            }
        };

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<NoeHesabModel> noeHesabModels) {
        return RxAsync.makeObservable(insertGroupCallable(noeHesabModels))
                .subscribeOn(Schedulers.io());
    }
}
