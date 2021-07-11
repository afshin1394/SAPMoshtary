package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.JayezehSatrDAO;
import com.saphamrah.Model.JayezehSatrModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class JayezehSatrRepository {
    Context context;
    JayezehSatrDAO jayezehSatrDAO;


    public JayezehSatrRepository(Context context) {
        this.context = context;
        jayezehSatrDAO = new JayezehSatrDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return jayezehSatrDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<JayezehSatrModel> jayezehSatrModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return jayezehSatrDAO.insertGroup(jayezehSatrModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<JayezehSatrModel> jayezehSatrModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(jayezehSatrModels))
                .subscribeOn(Schedulers.io());
    }
}
