package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ForoshandehDAO;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ForoshandehRepository {
    Context context;
    ForoshandehDAO foroshandehDAO;


    public ForoshandehRepository(Context context) {
        this.context = context;
        foroshandehDAO = new ForoshandehDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return foroshandehDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ForoshandehModel> foroshandehModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return foroshandehDAO.insertGroup(foroshandehModels);
            }
        };

    }

    private Callable<ArrayList<ForoshandehModel> > getAllCallable() {
        return new Callable<ArrayList<ForoshandehModel>>() {
            @Override
            public ArrayList<ForoshandehModel> call() throws Exception {
                return foroshandehDAO.getAll();
            }
        };

    }
    private Callable<ArrayList<String> > getDistinctccForoshandehCallable() {
        return new Callable<ArrayList<String>>() {
            @Override
            public ArrayList<String> call() throws Exception {
                return foroshandehDAO.getDistinctccForoshandeh();
            }
        };

    }


    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ForoshandehModel> foroshandehModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(foroshandehModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<ForoshandehModel>> getAll() {
        return RxDAOUtils.makeObservable(getAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<String>> getDistinctccForoshandeh() {
        return RxDAOUtils.makeObservable(getDistinctccForoshandehCallable())
                .subscribeOn(Schedulers.io());
    }

}
