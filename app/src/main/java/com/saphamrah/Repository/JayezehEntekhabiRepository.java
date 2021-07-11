package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.JayezehEntekhabiDAO;
import com.saphamrah.Model.JayezehEntekhabiModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class JayezehEntekhabiRepository {
    Context context;
    JayezehEntekhabiDAO jayezehEntekhabiDAO;


    public JayezehEntekhabiRepository(Context context) {
        this.context = context;
        jayezehEntekhabiDAO = new JayezehEntekhabiDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return jayezehEntekhabiDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return jayezehEntekhabiDAO.insertGroup(jayezehEntekhabiModels);
            }
        };

    }

    private Callable<Boolean> updateMablaghForoshCallable(double mablagheForosh, int ccKalaCode) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return jayezehEntekhabiDAO.updateMablaghForosh(mablagheForosh, ccKalaCode);
            }
        };

    }


    private Callable<Boolean> updateMablaghForosh2Callable(ArrayList<KalaModel> kalaModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return jayezehEntekhabiDAO.updateMablaghForoshAll(kalaModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(jayezehEntekhabiModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> updateMablaghForosh(double mablagheForosh, int ccKalaCode) {
        return RxDAOUtils.makeObservable(updateMablaghForoshCallable( mablagheForosh,  ccKalaCode))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> updateMablaghForoshAll(ArrayList<KalaModel> kalaModels) {
        return RxDAOUtils.makeObservable(updateMablaghForosh2Callable( kalaModels))
                .subscribeOn(Schedulers.io());
    }


}
