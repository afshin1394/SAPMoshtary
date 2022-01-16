package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.GPSDataMashinDAO;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GPSDataMashinRepository {

    Context context;
    GPSDataMashinDAO gpsDataMashinDAO;


    public GPSDataMashinRepository(Context context) {
        this.context = context;
        gpsDataMashinDAO = new GPSDataMashinDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return gpsDataMashinDAO.deleteAll();
            }
        };

    }


    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }


}
