package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.BankDAO;
import com.saphamrah.DAO.NoePishnahadDAO;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.NoePishnahadModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class NoePishnahadRepository {
    Context context;
    NoePishnahadDAO noePishnahadDAO;



    public NoePishnahadRepository(Context context) {
        this.context = context;
        noePishnahadDAO = new NoePishnahadDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return noePishnahadDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<NoePishnahadModel> noePishnahadModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return noePishnahadDAO.insertGroup(noePishnahadModels);
            }
        };

    }

    private Callable<ArrayList<NoePishnahadModel>> getAllCallable() {
        return new Callable<ArrayList<NoePishnahadModel>>() {
            @Override
            public ArrayList<NoePishnahadModel> call()  {
                return noePishnahadDAO.getAll();
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<NoePishnahadModel> noePishnahadModels) {
        return RxAsync.makeObservable(insertGroupCallable(noePishnahadModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<NoePishnahadModel>> getAll() {
        return RxAsync.makeObservable(getAllCallable())
                .subscribeOn(Schedulers.io());
    }



}
