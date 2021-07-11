package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryTaghiratDAO;
import com.saphamrah.Model.MoshtaryTaghiratModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryTaghiratRepository {
    Context context;
    MoshtaryTaghiratDAO moshtaryTaghiratDAO;


    public MoshtaryTaghiratRepository(Context context) {
        this.context = context;
        moshtaryTaghiratDAO = new MoshtaryTaghiratDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryTaghiratDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryTaghiratModel> moshtaryTaghiratModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryTaghiratDAO.insertGroup(moshtaryTaghiratModels);
            }
        };

    }




    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MoshtaryTaghiratModel> moshtaryTaghiratModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(moshtaryTaghiratModels))
                .subscribeOn(Schedulers.io());
    }
}
