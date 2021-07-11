package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.PosShomarehHesabDAO;
import com.saphamrah.Model.PosShomarehHesabModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class PosShomarehHesabRepository {
    Context context;
    PosShomarehHesabDAO posShomarehHesabDAO;

    public PosShomarehHesabRepository(Context context) {
        this.context = context;
        posShomarehHesabDAO = new PosShomarehHesabDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return posShomarehHesabDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<PosShomarehHesabModel> posShomarehHesabModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return posShomarehHesabDAO.insertGroup(posShomarehHesabModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<PosShomarehHesabModel> posShomarehHesabModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(posShomarehHesabModels))
                .subscribeOn(Schedulers.io());
    }

}
