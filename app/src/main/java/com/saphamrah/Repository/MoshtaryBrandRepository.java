package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryBrandDAO;
import com.saphamrah.Model.MoshtaryBrandModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryBrandRepository {
    Context context;
    MoshtaryBrandDAO moshtaryBrandDAO;


    public MoshtaryBrandRepository(Context context) {
        this.context = context;
        moshtaryBrandDAO = new MoshtaryBrandDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> moshtaryBrandDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        return () -> moshtaryBrandDAO.insertGroup(moshtaryBrandModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(moshtaryBrandModels))
                .subscribeOn(Schedulers.io());
    }
}
