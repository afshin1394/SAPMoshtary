package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryEtebarPishFarzDAO;
import com.saphamrah.Model.MoshtaryEtebarPishFarzModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryEtebarPishFarzRepository {
    Context context;
    MoshtaryEtebarPishFarzDAO moshtaryEtebarPishFarzDAO;


    public MoshtaryEtebarPishFarzRepository(Context context) {
        this.context = context;
        moshtaryEtebarPishFarzDAO = new MoshtaryEtebarPishFarzDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> moshtaryEtebarPishFarzDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryEtebarPishFarzModel> moshtaryEtebarPishFarzModels) {
        return () -> moshtaryEtebarPishFarzDAO.insertGroup(moshtaryEtebarPishFarzModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<MoshtaryEtebarPishFarzModel> moshtaryEtebarPishFarzModels) {
        return RxAsync.makeObservable(insertGroupCallable(moshtaryEtebarPishFarzModels))
                .subscribeOn(Schedulers.io());
    }
}
