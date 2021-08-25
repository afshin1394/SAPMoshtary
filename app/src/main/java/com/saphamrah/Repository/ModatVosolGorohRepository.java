package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ModatVosolGorohDAO;
import com.saphamrah.Model.ModatVosolGorohModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ModatVosolGorohRepository {

    Context context;
    ModatVosolGorohDAO modatVosolGorohDAO;


    public ModatVosolGorohRepository(Context context) {
        this.context = context;
        modatVosolGorohDAO = new ModatVosolGorohDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return modatVosolGorohDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ModatVosolGorohModel> modatVosolGorohModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return modatVosolGorohDAO.insertGroup(modatVosolGorohModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ModatVosolGorohModel> modatVosolGorohModels) {
        return RxAsync.makeObservable(insertGroupCallable(modatVosolGorohModels))
                .subscribeOn(Schedulers.io());
    }
}
