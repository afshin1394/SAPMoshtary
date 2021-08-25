package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.BrandDAO;
import com.saphamrah.Model.BrandModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class BrandRepository {

    Context context;
    BrandDAO brandDAO;


    public BrandRepository(Context context) {
        this.context = context;
        brandDAO = new BrandDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return brandDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<BrandModel> brandModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return brandDAO.insertGroup(brandModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<BrandModel> brandModels) {
        return RxAsync.makeObservable(insertGroupCallable(brandModels))
                .subscribeOn(Schedulers.io());
    }
}
