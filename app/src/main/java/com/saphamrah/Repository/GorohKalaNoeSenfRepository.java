package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.GorohKalaNoeSenfDAO;
import com.saphamrah.Model.GorohKalaNoeSenfModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GorohKalaNoeSenfRepository {
    Context context;
    GorohKalaNoeSenfDAO gorohKalaNoeSenfDAO;


    public GorohKalaNoeSenfRepository(Context context) {
        this.context = context;
        gorohKalaNoeSenfDAO = new GorohKalaNoeSenfDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> gorohKalaNoeSenfDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<GorohKalaNoeSenfModel> gorohKalaNoeSenfModels) {
        return () -> gorohKalaNoeSenfDAO.insertGroup(gorohKalaNoeSenfModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<GorohKalaNoeSenfModel> gorohKalaNoeSenfModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(gorohKalaNoeSenfModels))
                .subscribeOn(Schedulers.io());
    }
}
