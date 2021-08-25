package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.KalaOlaviatDAO;
import com.saphamrah.Model.KalaOlaviatModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class KalaOlaviatRepository {

    Context context;
    KalaOlaviatDAO kalaOlaviatDAO;


    public KalaOlaviatRepository(Context context) {
        this.context = context;
        kalaOlaviatDAO = new KalaOlaviatDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return kalaOlaviatDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<KalaOlaviatModel> kalaOlaviatModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return kalaOlaviatDAO.insertGroup(kalaOlaviatModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<KalaOlaviatModel> kalaOlaviatModels) {
        return RxAsync.makeObservable(insertGroupCallable(kalaOlaviatModels))
                .subscribeOn(Schedulers.io());
    }
}
