package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.RptMarjoeeDAO;
import com.saphamrah.Model.RptMarjoeeKalaModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RptMarjoeeRepository {
    Context context;
    RptMarjoeeDAO rptMarjoeeDAO;


    public RptMarjoeeRepository(Context context) {
        this.context = context;
        rptMarjoeeDAO = new RptMarjoeeDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> rptMarjoeeDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModels) {
        return () -> rptMarjoeeDAO.insertGroup(rptMarjoeeKalaModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModels) {
        return RxAsync.makeObservable(insertGroupCallable(rptMarjoeeKalaModels))
                .subscribeOn(Schedulers.io());
    }
}
