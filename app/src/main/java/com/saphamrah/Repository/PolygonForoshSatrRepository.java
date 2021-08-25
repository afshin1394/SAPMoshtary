package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.PolygonForoshSatrDAO;
import com.saphamrah.Model.PolygonForoshSatrModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class PolygonForoshSatrRepository {
    Context context;
    PolygonForoshSatrDAO polygonForoshSatrDAO;


    public PolygonForoshSatrRepository(Context context) {
        this.context = context;
        polygonForoshSatrDAO = new PolygonForoshSatrDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return polygonForoshSatrDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return polygonForoshSatrDAO.insertGroup(polygonForoshSatrModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels) {
        return RxAsync.makeObservable(insertGroupCallable(polygonForoshSatrModels))
                .subscribeOn(Schedulers.io());
    }
}
