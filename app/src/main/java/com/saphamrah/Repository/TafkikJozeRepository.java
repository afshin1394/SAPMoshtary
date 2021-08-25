package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.TafkikJozeDAO;
import com.saphamrah.Model.TafkikJozeModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TafkikJozeRepository {
    Context context;
    TafkikJozeDAO tafkikJozeDAO;


    public TafkikJozeRepository(Context context) {
        this.context = context;
        tafkikJozeDAO = new TafkikJozeDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> tafkikJozeDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<TafkikJozeModel> tafkikJozeModels) {
        return () -> tafkikJozeDAO.insertGroup(tafkikJozeModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<TafkikJozeModel> tafkikJozeModels) {
        return RxAsync.makeObservable(insertGroupCallable(tafkikJozeModels))
                .subscribeOn(Schedulers.io());
    }
}
