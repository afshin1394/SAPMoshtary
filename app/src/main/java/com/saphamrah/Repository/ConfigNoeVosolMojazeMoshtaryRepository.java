package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ConfigNoeVosolMojazeMoshtaryDAO;
import com.saphamrah.Model.ConfigNoeVosolMojazeMoshtaryModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ConfigNoeVosolMojazeMoshtaryRepository {
    Context context;
    ConfigNoeVosolMojazeMoshtaryDAO configNoeVosolMojazeMoshtaryDAO;


    public ConfigNoeVosolMojazeMoshtaryRepository(Context context) {
        this.context = context;
        configNoeVosolMojazeMoshtaryDAO = new ConfigNoeVosolMojazeMoshtaryDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> configNoeVosolMojazeMoshtaryDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeFaktorModels) {
        return () -> configNoeVosolMojazeMoshtaryDAO.insertGroup(configNoeVosolMojazeFaktorModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels) {
        return RxAsync.makeObservable(insertGroupCallable(configNoeVosolMojazeMoshtaryModels))
                .subscribeOn(Schedulers.io());
    }
}
