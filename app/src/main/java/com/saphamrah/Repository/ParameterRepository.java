package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ParameterDAO;
import com.saphamrah.Model.ParameterModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ParameterRepository
        {
    Context context;
    ParameterDAO parameterDAO;


    public ParameterRepository(Context context) {
        this.context = context;
        parameterDAO = new ParameterDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> parameterDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ParameterModel> parameterModels) {
        return () -> parameterDAO.insertGroup(parameterModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ParameterModel> parameterModels) {
        return RxAsync.makeObservable(insertGroupCallable(parameterModels))
                .subscribeOn(Schedulers.io());
    }
}
