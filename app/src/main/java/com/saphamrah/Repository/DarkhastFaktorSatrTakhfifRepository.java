package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DarkhastFaktorSatrTakhfifDAO;
import com.saphamrah.Model.DarkhastFaktorSatrTakhfifModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DarkhastFaktorSatrTakhfifRepository {

    Context context;
    DarkhastFaktorSatrTakhfifDAO darkhastFaktorSatrTakhfifDAO;


    public DarkhastFaktorSatrTakhfifRepository(Context context) {
        this.context = context;
        darkhastFaktorSatrTakhfifDAO = new DarkhastFaktorSatrTakhfifDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return darkhastFaktorSatrTakhfifDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<DarkhastFaktorSatrTakhfifModel> darkhastFaktorSatrTakhfifModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return darkhastFaktorSatrTakhfifDAO.insertGroup(darkhastFaktorSatrTakhfifModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<DarkhastFaktorSatrTakhfifModel> darkhastFaktorSatrTakhfifModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(darkhastFaktorSatrTakhfifModels))
                .subscribeOn(Schedulers.io());
    }
}
