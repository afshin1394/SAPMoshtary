package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DarkhastFaktorTakhfifDAO;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DarkhastFaktorTakhfifRepository {

    Context context;
    DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO;


    public DarkhastFaktorTakhfifRepository(Context context) {
        this.context = context;
        darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return darkhastFaktorTakhfifDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return darkhastFaktorTakhfifDAO.insertGroup(darkhastFaktorTakhfifModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(darkhastFaktorTakhfifModels))
                .subscribeOn(Schedulers.io());
    }
}
