package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MarkazShomarehHesabDAO;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MarkazShomarehHesabRepository {

    Context context;
    MarkazShomarehHesabDAO markazShomarehHesabDAO;

    public MarkazShomarehHesabRepository(Context context) {
        this.context = context;
        markazShomarehHesabDAO = new MarkazShomarehHesabDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return markazShomarehHesabDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return markazShomarehHesabDAO.insertGroup(markazShomarehHesabModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(markazShomarehHesabModels))
                .subscribeOn(Schedulers.io());
    }
}
