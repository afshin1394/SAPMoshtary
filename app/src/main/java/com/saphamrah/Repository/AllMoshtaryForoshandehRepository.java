package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.AllMoshtaryForoshandehDAO;
import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class AllMoshtaryForoshandehRepository {
    Context context;
    AllMoshtaryForoshandehDAO allMoshtaryForoshandehDAO;


    public AllMoshtaryForoshandehRepository(Context context) {
        this.context = context;
        allMoshtaryForoshandehDAO = new AllMoshtaryForoshandehDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> allMoshtaryForoshandehDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels) {
        return () -> allMoshtaryForoshandehDAO.insertGroup(allMoshtaryForoshandehModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels) {
        return RxAsync.makeObservable(insertGroupCallable(allMoshtaryForoshandehModels))
                .subscribeOn(Schedulers.io());
    }
}
