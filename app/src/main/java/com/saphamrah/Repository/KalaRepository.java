package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class KalaRepository {

    Context context;
    KalaDAO kalaDAO;


    public KalaRepository(Context context) {
        this.context = context;
        kalaDAO = new KalaDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return kalaDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<KalaModel> kalaModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return kalaDAO.insertGroup(kalaModels);
            }
        };

    }
    private Callable<KalaModel> getKalaByMaxMojodiCallable(int ccKalaCode) {
        return new Callable<KalaModel>() {
            @Override
            public KalaModel call()  {
                return kalaDAO.getKalaByMaxMojody(ccKalaCode);
            }
        };

    }
    private Callable<ArrayList<KalaModel>> getKalaByMaxMojodi2Callable(ArrayList<Integer> ccKalaCodes) {
        return new Callable<ArrayList<KalaModel>>() {
            @Override
            public ArrayList<KalaModel> call()  {
                return kalaDAO.getKalaByMaxMojodyAll(ccKalaCodes);
            }
        };

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<KalaModel> kalaModels) {
        return RxAsync.makeObservable(insertGroupCallable(kalaModels))
                .subscribeOn(Schedulers.io());
    }


    public Observable<KalaModel> getKalaByMaxMojody(int ccKalaCode) {
        return RxAsync.makeObservable(getKalaByMaxMojodiCallable(ccKalaCode))
                .subscribeOn(Schedulers.io());
    }
    public Observable<ArrayList<KalaModel>> getKalaByMaxMojodyAll(ArrayList<Integer> ccKalaCodes) {
        return RxAsync.makeObservable(getKalaByMaxMojodi2Callable(ccKalaCodes))
                .subscribeOn(Schedulers.io());
    }
}
