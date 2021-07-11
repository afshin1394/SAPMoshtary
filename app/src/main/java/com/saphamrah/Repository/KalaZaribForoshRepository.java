package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.KalaZaribForoshDAO;
import com.saphamrah.Model.KalaZaribForoshModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class KalaZaribForoshRepository {

    Context context;
    KalaZaribForoshDAO kalaZaribForoshDAO;


    public KalaZaribForoshRepository(Context context) {
        this.context = context;
        kalaZaribForoshDAO = new KalaZaribForoshDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return kalaZaribForoshDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return kalaZaribForoshDAO.insertGroup(kalaZaribForoshModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(kalaZaribForoshModels))
                .subscribeOn(Schedulers.io());
    }
}
