package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.RptSanadDAO;
import com.saphamrah.Model.RptSanadModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RptSanadRepository {
    Context context;
    RptSanadDAO rptSanadDAO;


    public RptSanadRepository(Context context) {
        this.context = context;
        rptSanadDAO = new RptSanadDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> rptSanadDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<RptSanadModel> rptMandehdarModels) {
        return () -> rptSanadDAO.insertGroup(rptMandehdarModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<RptSanadModel> rptMandehdarModels) {
        return RxAsync.makeObservable(insertGroupCallable(rptMandehdarModels))
                .subscribeOn(Schedulers.io());
    }
}
