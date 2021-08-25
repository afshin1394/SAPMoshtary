package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.RptHadafForoshDAO;
import com.saphamrah.Model.HadafForosh.RptHadafForoshModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RptHadafForoshRepository {

    Context context;
    RptHadafForoshDAO rptHadafForoshDAO;


    public RptHadafForoshRepository(Context context) {
        this.context = context;
        rptHadafForoshDAO = new RptHadafForoshDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> rptHadafForoshDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<RptHadafForoshModel> rptMandehdarModels) {
        return () -> rptHadafForoshDAO.insertGroup(rptMandehdarModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<RptHadafForoshModel> rptHadafForoshModels) {
        return RxAsync.makeObservable(insertGroupCallable(rptHadafForoshModels))
                .subscribeOn(Schedulers.io());
    }
}
