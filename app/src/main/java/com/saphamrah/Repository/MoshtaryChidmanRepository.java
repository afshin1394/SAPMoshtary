package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryChidmanDAO;
import com.saphamrah.Model.MoshtaryChidmanModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryChidmanRepository {

    Context context;
    MoshtaryChidmanDAO moshtaryChidmanDAO;

    public MoshtaryChidmanRepository(Context context) {
        this.context = context;
        moshtaryChidmanDAO = new MoshtaryChidmanDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return () -> moshtaryChidmanDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels) {
        return () -> moshtaryChidmanDAO.insertGroup(moshtaryChidmanModels);

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(moshtaryChidmanModels))
                .subscribeOn(Schedulers.io());
    }
}
