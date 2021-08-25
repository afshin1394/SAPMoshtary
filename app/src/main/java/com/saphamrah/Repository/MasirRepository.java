package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MasirDAO;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MasirRepository {

    Context context;
    MasirDAO masirDAO;


    public MasirRepository(Context context) {
        this.context = context;
        masirDAO = new MasirDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return () -> masirDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MasirModel> masirModels) {
        return () -> masirDAO.insertGroup(masirModels);

    }
    private Callable<Boolean> updateTarikhMasirCallable(String date) {
        return () -> masirDAO. updateTarikhMasir(date);

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MasirModel> masirModels) {
        return RxAsync.makeObservable(insertGroupCallable(masirModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> updateTarikhMasir(String date) {
        return RxAsync.makeObservable(updateTarikhMasirCallable(date))
                .subscribeOn(Schedulers.io());
    }
}
