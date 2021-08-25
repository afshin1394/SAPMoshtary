package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MarkazShahrMarkaziDAO;
import com.saphamrah.Model.MarkazShahrMarkaziModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MarkazShahrMarkaziRepository {
    Context context;
    MarkazShahrMarkaziDAO markazShahrMarkaziDAO;


    public MarkazShahrMarkaziRepository(Context context) {
        this.context = context;
        markazShahrMarkaziDAO = new MarkazShahrMarkaziDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> markazShahrMarkaziDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels) {
        return () -> markazShahrMarkaziDAO.insertGroup(markazShahrMarkaziModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels) {
        return RxAsync.makeObservable(insertGroupCallable(markazShahrMarkaziModels))
                .subscribeOn(Schedulers.io());
    }
}
