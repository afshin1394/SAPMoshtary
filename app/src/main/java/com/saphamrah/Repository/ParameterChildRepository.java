package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ParameterChildRepository {
    Context context;
    ParameterChildDAO parameterChildDAO;


    public ParameterChildRepository(Context context) {
        this.context = context;
        parameterChildDAO = new ParameterChildDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> parameterChildDAO.deleteAll();

    }



    private Callable<Boolean> insertGroupCallable(ArrayList<ParameterChildModel> parameterChildModels) {
        return () -> parameterChildDAO.insertGroup(parameterChildModels);
    }

    private Callable<String> getValueByccChildParameterCallable(int ccChildParameter) {
        return () -> parameterChildDAO.getValueByccChildParameter(ccChildParameter);
    }


    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ParameterChildModel> parameterChildModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(parameterChildModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> getValueByccChildParameter(int ccChildParameter) {
        return RxDAOUtils.makeObservable(getValueByccChildParameterCallable(ccChildParameter))
                .subscribeOn(Schedulers.io());
    }

}
