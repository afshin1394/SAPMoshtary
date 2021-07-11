package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.AnbarakAfradDAO;
import com.saphamrah.Model.AnbarakAfradModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class AnbarakAfradRepository {

    Context context;
    AnbarakAfradDAO anbarakAfradDAO;


    public AnbarakAfradRepository(Context context) {
        this.context = context;
        anbarakAfradDAO = new AnbarakAfradDAO(context);
    }

    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return anbarakAfradDAO.deleteAll();
            }
        };

    }

    private Callable<Boolean> insertGroupCallable(ArrayList<AnbarakAfradModel> anbarakAfradModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return anbarakAfradDAO.insertGroup(anbarakAfradModels);
            }
        };

    }


    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<AnbarakAfradModel> anbarakAfradModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(anbarakAfradModels))
                .subscribeOn(Schedulers.io());
    }
}
