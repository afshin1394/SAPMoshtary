package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryAmargarImageTmpDAO;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryAmargarImageTmpRepository {
    Context context;
    MoshtaryAmargarImageTmpDAO moshtaryAmargarImageTmpDAO;


    public MoshtaryAmargarImageTmpRepository(Context context) {
        this.context = context;
        moshtaryAmargarImageTmpDAO = new MoshtaryAmargarImageTmpDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryAmargarImageTmpDAO.deleteAll();
            }
        };

    }
    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }
}
