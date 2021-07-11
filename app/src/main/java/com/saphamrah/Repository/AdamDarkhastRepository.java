package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.AdamDarkhastDAO;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class AdamDarkhastRepository {

    Context context;
    AdamDarkhastDAO adamDarkhastDAO;


    public AdamDarkhastRepository(Context context) {
        this.context = context;
        adamDarkhastDAO = new AdamDarkhastDAO(context);
    }

    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return adamDarkhastDAO.deleteAll();
            }
        };

    }


    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }
}
