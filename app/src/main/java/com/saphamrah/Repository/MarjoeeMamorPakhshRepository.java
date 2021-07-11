package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MarjoeeMamorPakhshDAO;
import com.saphamrah.Model.MarjoeeMamorPakhshModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MarjoeeMamorPakhshRepository {

    Context context;
    MarjoeeMamorPakhshDAO marjoeeMamorPakhshDAO;

    public MarjoeeMamorPakhshRepository(Context context) {
        this.context = context;
        marjoeeMamorPakhshDAO = new MarjoeeMamorPakhshDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return () -> marjoeeMamorPakhshDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModels) {
        return () -> marjoeeMamorPakhshDAO.insertGroup(marjoeeMamorPakhshModels);

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(marjoeeMamorPakhshModels))
                .subscribeOn(Schedulers.io());
    }
}
