package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.GPSDataPpcDAO;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GPSDataPpcRepository {

    Context context;
    GPSDataPpcDAO gpsDataPpcDAO;


    public GPSDataPpcRepository(Context context) {
        this.context = context;
        gpsDataPpcDAO = new GPSDataPpcDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteSendedRecordsCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return gpsDataPpcDAO.deleteSendedRecords();
            }
        };

    }
//    private Callable<Boolean> insertGroupCallable(ArrayList<GPSDataPpcRepository> gorohModels) {
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return gpsDataPpcDAO.insertGroup(gorohModels);
//            }
//        };
//
//    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteSendedRecords() {
        return RxAsync.makeObservable(deleteSendedRecordsCallable())
                .subscribeOn(Schedulers.io());
    }



//    public Observable<Boolean> insertGroup(ArrayList<GorohModel> gorohModels) {
//        return RxAsync.makeObservable(insertGroupCallable(gorohModels))
//                .subscribeOn(Schedulers.io());
//    }

}
