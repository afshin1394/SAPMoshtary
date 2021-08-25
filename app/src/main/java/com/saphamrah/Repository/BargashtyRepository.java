package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.BargashtyDAO;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class BargashtyRepository {


    Context context;
    BargashtyDAO bargashtyDAO;


    public BargashtyRepository(Context context) {
        this.context = context;
        bargashtyDAO = new BargashtyDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return bargashtyDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<BargashtyModel> bargashtyModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return bargashtyDAO.insertGroup(bargashtyModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<BargashtyModel> bargashtyModels) {
        return RxAsync.makeObservable(insertGroupCallable(bargashtyModels))
                .subscribeOn(Schedulers.io());
    }
}
