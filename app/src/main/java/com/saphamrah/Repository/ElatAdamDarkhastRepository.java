package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ElatAdamDarkhastDAO;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ElatAdamDarkhastRepository {

    Context context;
    ElatAdamDarkhastDAO elatAdamDarkhastDAO;


    public ElatAdamDarkhastRepository(Context context) {
        this.context = context;
        elatAdamDarkhastDAO = new ElatAdamDarkhastDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return elatAdamDarkhastDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return elatAdamDarkhastDAO.insertGroup(elatAdamDarkhastModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(elatAdamDarkhastModels))
                .subscribeOn(Schedulers.io());
    }
}
