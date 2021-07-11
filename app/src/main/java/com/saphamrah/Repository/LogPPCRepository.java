package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.LogPPCDAO;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class LogPPCRepository {
    Context context;
    LogPPCDAO logPPCDAO;


    public LogPPCRepository(Context context) {
        this.context = context;
        logPPCDAO = new LogPPCDAO(context);
    }




    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return logPPCDAO.deleteAll();
            }
        };

    }

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }


}
