package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MarkazDAO;
import com.saphamrah.Model.MarkazModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MarkazRepository {
    Context context;
    MarkazDAO markazDAO;

    public MarkazRepository(Context context) {
        this.context = context;
        markazDAO = new MarkazDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return markazDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MarkazModel> markazModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return markazDAO.insertGroup(markazModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MarkazModel> markazModels) {
        return RxAsync.makeObservable(insertGroupCallable(markazModels))
                .subscribeOn(Schedulers.io());
    }
}
