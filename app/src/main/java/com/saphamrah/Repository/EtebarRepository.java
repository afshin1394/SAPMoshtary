package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.EtebarDAO;
import com.saphamrah.Model.EtebarModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class EtebarRepository {
    Context context;
    EtebarDAO etebarDAO;


    public EtebarRepository(Context context) {
        this.context = context;
        etebarDAO = new EtebarDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return etebarDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<EtebarModel> etebarModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return etebarDAO.insertGroup(etebarModels);
            }
        };

    }


    private Callable<ArrayList<String>> deleteByCcForohsandehCallable(String ccForoshande) {
        return new Callable<ArrayList<String>>() {
            @Override
            public ArrayList<String> call()  {
                return etebarDAO.deleteByccForoshanhdeString(ccForoshande);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<EtebarModel> etebarModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(etebarModels))
                .subscribeOn(Schedulers.io());
    }


    public Observable<ArrayList<String>> deleteByCcForohsandehString(String ccForoshandeString) {
        return RxDAOUtils.makeObservable(deleteByCcForohsandehCallable(ccForoshandeString))
                .subscribeOn(Schedulers.io());
    }


}
