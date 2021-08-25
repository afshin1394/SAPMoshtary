package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.RptMandehdarDAO;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RptMandehdarRepository {
    Context context;
    RptMandehdarDAO rptMandehdarDAO;


    public RptMandehdarRepository(Context context) {
        this.context = context;
        rptMandehdarDAO = new RptMandehdarDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> rptMandehdarDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<RptMandehdarModel> rptMandehdarModels) {
        return () -> rptMandehdarDAO.insertGroup(rptMandehdarModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<RptMandehdarModel> rptMandehdarModels) {
        return RxAsync.makeObservable(insertGroupCallable(rptMandehdarModels))
                .subscribeOn(Schedulers.io());
    }
}
