package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.RptDarkhastFaktorVazeiatPPCDAO;
import com.saphamrah.Model.RptDarkhastFaktorVazeiatPPCModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RptDarkhastFaktorVazeiatPPCRepository {
    Context context;
    RptDarkhastFaktorVazeiatPPCDAO rptDarkhastFaktorVazeiatPPCDAO;


    public RptDarkhastFaktorVazeiatPPCRepository(Context context) {
        this.context = context;
        rptDarkhastFaktorVazeiatPPCDAO = new RptDarkhastFaktorVazeiatPPCDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return rptDarkhastFaktorVazeiatPPCDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<RptDarkhastFaktorVazeiatPPCModel> rptDarkhastFaktorVazeiatPPCModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return rptDarkhastFaktorVazeiatPPCDAO.insertGroup(rptDarkhastFaktorVazeiatPPCModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<RptDarkhastFaktorVazeiatPPCModel> rptDarkhastFaktorVazeiatPPCModels) {
        return RxAsync.makeObservable(insertGroupCallable(rptDarkhastFaktorVazeiatPPCModels))
                .subscribeOn(Schedulers.io());
    }
}
