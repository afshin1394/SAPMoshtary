package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ConfigNoeVosolMojazeFaktorDAO;
import com.saphamrah.Model.ConfigNoeVosolMojazeFaktorModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ConfigNoeVosolMojazeFaktorRepository {
    Context context;
    ConfigNoeVosolMojazeFaktorDAO configNoeVosolMojazeFaktorDAO;


    public ConfigNoeVosolMojazeFaktorRepository(Context context) {
        this.context = context;
        configNoeVosolMojazeFaktorDAO = new ConfigNoeVosolMojazeFaktorDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> configNoeVosolMojazeFaktorDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels) {
        return () -> configNoeVosolMojazeFaktorDAO.insertGroup(configNoeVosolMojazeFaktorModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(configNoeVosolMojazeFaktorModels))
                .subscribeOn(Schedulers.io());
    }
}
