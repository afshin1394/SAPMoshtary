package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryJadidDarkhastDAO;
import com.saphamrah.Model.MoshtaryJadidDarkhastModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryJadidDarkhastRepository {
    Context context;
    MoshtaryJadidDarkhastDAO moshtaryJadidDarkhastDAO;


    public MoshtaryJadidDarkhastRepository(Context context) {
        this.context = context;
        moshtaryJadidDarkhastDAO = new MoshtaryJadidDarkhastDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> moshtaryJadidDarkhastDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryJadidDarkhastModel> moshtaryJadidDarkhastModels) {
        return () -> moshtaryJadidDarkhastDAO.insertGroup(moshtaryJadidDarkhastModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<MoshtaryJadidDarkhastModel> moshtaryJadidDarkhastModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(moshtaryJadidDarkhastModels))
                .subscribeOn(Schedulers.io());
    }
}
