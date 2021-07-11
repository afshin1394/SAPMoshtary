package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.TakhfifHajmiSatrDAO;
import com.saphamrah.Model.TakhfifHajmiSatrModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TakhfifHajmiSatrRepository {

    Context context;
    TakhfifHajmiSatrDAO takhfifHajmiSatrDAO;

    public TakhfifHajmiSatrRepository(Context context) {
        this.context = context;
        takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return takhfifHajmiSatrDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return takhfifHajmiSatrDAO.insertGroup(takhfifHajmiSatrModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(takhfifHajmiSatrModels))
                .subscribeOn(Schedulers.io());
    }
}
