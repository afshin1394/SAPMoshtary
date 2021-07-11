package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryAmargarImageDAO;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryAmargarImageRepository {
    Context context;
    MoshtaryAmargarImageDAO moshtaryAmargarImageDAO;


    public MoshtaryAmargarImageRepository(Context context) {
        this.context = context;
        moshtaryAmargarImageDAO = new MoshtaryAmargarImageDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryAmargarImageDAO.deleteAll();
            }
        };

    }
    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }
}
