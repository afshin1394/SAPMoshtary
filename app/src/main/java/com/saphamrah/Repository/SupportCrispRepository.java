package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.SupportCrispDAO;
import com.saphamrah.Model.SupportCrispModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SupportCrispRepository {
    Context context;
    SupportCrispDAO supportCrispDAO;


    public SupportCrispRepository(Context context) {
        this.context = context;
        supportCrispDAO = new SupportCrispDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> supportCrispDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<SupportCrispModel> supportCrispModels) {
        return () -> supportCrispDAO.insertGroup(supportCrispModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<SupportCrispModel> supportCrispModels) {
        return RxAsync.makeObservable(insertGroupCallable(supportCrispModels))
                .subscribeOn(Schedulers.io());
    }
}
