package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.NoeVosolMoshtaryDAO;
import com.saphamrah.Model.NoeVosolMoshtaryModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class NoeVosolMoshtaryRepository {
    Context context;
    NoeVosolMoshtaryDAO noeVosolMoshtaryDAO;


    public NoeVosolMoshtaryRepository(Context context) {
        this.context = context;
        noeVosolMoshtaryDAO = new NoeVosolMoshtaryDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> noeVosolMoshtaryDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels) {
        return () -> noeVosolMoshtaryDAO.insertGroup(noeVosolMoshtaryModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(noeVosolMoshtaryModels))
                .subscribeOn(Schedulers.io());
    }
}
