package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.GPSDataMashinDAO;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

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
//    private Callable<Boolean> insertGroupCallable(ArrayList<GPSDataMashinModel> gpsDataMashinModels) {
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return gpsDataMashinDAO.insertGroup(gpsDataMashinModels);
//            }
//        };
//
//    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



//    public Observable<Boolean> insertGroup(ArrayList<GorohModel> gorohModels) {
//        return RxDAOUtils.makeObservable(insertGroupCallable(gorohModels))
//                .subscribeOn(Schedulers.io());
//    }

}
