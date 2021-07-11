package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.KalaMojodiDAO;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class KalaMojodiRepository {

    Context context;
    KalaMojodiDAO kalaMojodiDAO;


    public KalaMojodiRepository(Context context) {
        this.context = context;
        kalaMojodiDAO = new KalaMojodiDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return kalaMojodiDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<KalaMojodiModel> kalaMojodiModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return kalaMojodiDAO.insertGroup(kalaMojodiModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<KalaMojodiModel> kalaMojodiModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(kalaMojodiModels))
                .subscribeOn(Schedulers.io());
    }
}
