package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryAfradDAO;
import com.saphamrah.Model.MoshtaryAfradModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryAfradRepository {
    Context context;
    MoshtaryAfradDAO moshtaryAfradDAO;


    public MoshtaryAfradRepository(Context context) {
        this.context = context;
        moshtaryAfradDAO = new MoshtaryAfradDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryAfradDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> deleteGroupCallable(String ccAfrad){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryAfradDAO.deleteGroup(ccAfrad);
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryAfradModel> moshtaryAfradModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryAfradDAO.insertGroup(moshtaryAfradModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }
    public Observable<Boolean> deleteGroup(String ccAfrad) {
        return RxAsync.makeObservable(deleteGroupCallable(ccAfrad))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MoshtaryAfradModel> moshtaryAfradModels) {
        return RxAsync.makeObservable(insertGroupCallable(moshtaryAfradModels))
                .subscribeOn(Schedulers.io());
    }
}
