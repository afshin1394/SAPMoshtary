package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryEtebarSazmanForoshDAO;
import com.saphamrah.Model.MoshtaryEtebarSazmanForoshModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryEtebarSazmanForoshRepository {
    Context context;
    MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO;


    public MoshtaryEtebarSazmanForoshRepository(Context context) {
        this.context = context;
        moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryEtebarSazmanForoshDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryEtebarSazmanForoshModel> moshtaryEtebarSazmanForoshModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryEtebarSazmanForoshDAO.insertGroup(moshtaryEtebarSazmanForoshModels);
            }
        };

    }
    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }
    public Observable<Boolean> insertGroup(ArrayList<MoshtaryEtebarSazmanForoshModel> moshtaryEtebarSazmanForoshModels) {
        return RxAsync.makeObservable(insertGroupCallable(moshtaryEtebarSazmanForoshModels))
                .subscribeOn(Schedulers.io());
    }
}
