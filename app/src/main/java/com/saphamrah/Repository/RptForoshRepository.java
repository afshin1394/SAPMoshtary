package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.RptForoshDAO;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RptForoshRepository {
    Context context;
    RptForoshDAO rptForoshDAO;


    public RptForoshRepository(Context context) {
        this.context = context;
        rptForoshDAO = new RptForoshDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> rptForoshDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<RptForoshModel> rptForoshModels) {
        return () -> rptForoshDAO.insertGroup(rptForoshModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<RptForoshModel> rptForoshModels) {
        return RxAsync.makeObservable(insertGroupCallable(rptForoshModels))
                .subscribeOn(Schedulers.io());
    }
}
