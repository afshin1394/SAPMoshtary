package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ModatVosolDAO;
import com.saphamrah.Model.ModatVosolModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ModatVosolRepository {

    Context context;
    ModatVosolDAO modatVosolDAO;


    public ModatVosolRepository(Context context) {
        this.context = context;
        modatVosolDAO = new ModatVosolDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return modatVosolDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ModatVosolModel> modatVosolModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return modatVosolDAO.insertGroup(modatVosolModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ModatVosolModel> modatVosolModels) {
        return RxAsync.makeObservable(insertGroupCallable(modatVosolModels))
                .subscribeOn(Schedulers.io());
    }
}
