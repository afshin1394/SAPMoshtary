package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ModatVosolMarkazDAO;
import com.saphamrah.Model.ModatVosolMarkazModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ModatVosolMarkazRepository {
    Context context;
    ModatVosolMarkazDAO modatVosolMarkazDAO;


    public ModatVosolMarkazRepository(Context context) {
        this.context = context;
        modatVosolMarkazDAO = new ModatVosolMarkazDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return modatVosolMarkazDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ModatVosolMarkazModel> modatVosolMarkazModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return modatVosolMarkazDAO.insertGroup(modatVosolMarkazModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ModatVosolMarkazModel> modatVosolMarkazModels) {
        return RxAsync.makeObservable(insertGroupCallable(modatVosolMarkazModels))
                .subscribeOn(Schedulers.io());
    }
}
