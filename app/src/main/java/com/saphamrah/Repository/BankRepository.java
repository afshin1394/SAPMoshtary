package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.BankDAO;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class BankRepository {
    Context context;
    BankDAO bankDAO;



    public BankRepository(Context context) {
        this.context = context;
        bankDAO = new BankDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return bankDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<BankModel> bankModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return bankDAO.insertGroup(bankModels);
            }
        };

    }

    private Callable<ArrayList<BankModel>> getAllCallable() {
        return new Callable<ArrayList<BankModel>>() {
            @Override
            public ArrayList<BankModel> call()  {
                return bankDAO.getAll();
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<BankModel> bankModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(bankModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<BankModel>> getAll() {
        return RxDAOUtils.makeObservable(getAllCallable())
                .subscribeOn(Schedulers.io());
    }



}
