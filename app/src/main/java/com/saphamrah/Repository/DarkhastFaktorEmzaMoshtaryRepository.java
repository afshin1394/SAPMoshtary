package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DarkhastFaktorEmzaMoshtaryDAO;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DarkhastFaktorEmzaMoshtaryRepository {
    Context context;
    DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO;


    public DarkhastFaktorEmzaMoshtaryRepository(Context context) {
        this.context = context;
        darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(context);
    }




    private Callable<Boolean> deleteAllCallable(){
        return () -> darkhastFaktorEmzaMoshtaryDAO.deleteAll();

    }


    private Callable<Boolean> insertCallable(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel){
        return () -> darkhastFaktorEmzaMoshtaryDAO.insert(darkhastFaktorEmzaMoshtaryModel);

    }

    private Callable<Boolean> insertGroupCallable(ArrayList< DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels){
        return () -> darkhastFaktorEmzaMoshtaryDAO.insertGroup(darkhastFaktorEmzaMoshtaryModels);

    }

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }


    public Observable<Boolean> insert(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel){
        return RxDAOUtils.makeObservable(insertCallable(darkhastFaktorEmzaMoshtaryModel))
                .subscribeOn(Schedulers.io());
    }
    public Observable<Boolean> insertGroup(ArrayList< DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels){
        return RxDAOUtils.makeObservable(insertGroupCallable(darkhastFaktorEmzaMoshtaryModels))
                .subscribeOn(Schedulers.io());
    }
}
