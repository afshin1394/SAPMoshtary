package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DariaftPardakhtPPCRepository {

    Context context;
    DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO;


    public DariaftPardakhtPPCRepository(Context context) {
        this.context = context;
        dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return dariaftPardakhtPPCDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return dariaftPardakhtPPCDAO.insertGroup(dariaftPardakhtPPCModels,true);
            }
        };

    }
    private Callable<Boolean> deleteByccDariaftPardakhtsCallable(String ccDariaftPardakht) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return dariaftPardakhtPPCDAO.deleteByccDariaftPardakhts(ccDariaftPardakht);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(dariaftPardakhtPPCModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> deleteByccDariaftPardakhts(String ccDaryaftPardakhts) {
        return RxDAOUtils.makeObservable(deleteByccDariaftPardakhtsCallable(ccDaryaftPardakhts))
                .subscribeOn(Schedulers.io());
    }
}
