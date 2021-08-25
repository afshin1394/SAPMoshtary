package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryShomarehHesabDAO;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryShomarehHesabRepository {
    Context context;
    MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO;


    public MoshtaryShomarehHesabRepository(Context context) {
        this.context = context;
        moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryShomarehHesabDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryShomarehHesabDAO.insertGroup(moshtaryShomarehHesabModels);
            }
        };

    }




    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels) {
        return RxAsync.makeObservable(insertGroupCallable(moshtaryShomarehHesabModels))
                .subscribeOn(Schedulers.io());
    }
}
