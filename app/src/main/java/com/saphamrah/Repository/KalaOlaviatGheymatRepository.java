package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.KalaOlaviatDAO;
import com.saphamrah.DAO.KalaOlaviatGheymatDAO;
import com.saphamrah.Model.KalaOlaviatGheymatModel;
import com.saphamrah.Model.KalaOlaviatModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class KalaOlaviatGheymatRepository {
    Context context;
    KalaOlaviatGheymatDAO kalaOlaviatGheymatDAO;


    public KalaOlaviatGheymatRepository(Context context) {
        this.context = context;
        kalaOlaviatGheymatDAO = new KalaOlaviatGheymatDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> kalaOlaviatGheymatDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<KalaOlaviatGheymatModel> kalaOlaviatGheymatModels) {
        return () -> kalaOlaviatGheymatDAO.insertGroup(kalaOlaviatGheymatModels);

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<KalaOlaviatGheymatModel> kalaOlaviatGheymatModels) {
        return RxAsync.makeObservable(insertGroupCallable(kalaOlaviatGheymatModels))
                .subscribeOn(Schedulers.io());
    }
}
