package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryPolygonDAO;
import com.saphamrah.Model.MoshtaryPolygonModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryPolygonRepository {
    Context context;
    MoshtaryPolygonDAO moshtaryPolygonDAO;


    public MoshtaryPolygonRepository(Context context) {
        this.context = context;
        moshtaryPolygonDAO = new MoshtaryPolygonDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryPolygonDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryPolygonModel> moshtaryPolygonModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return moshtaryPolygonDAO.insertGroup(moshtaryPolygonModels);
            }
        };

    }




    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MoshtaryPolygonModel> moshtaryPolygonModels) {
        return RxAsync.makeObservable(insertGroupCallable(moshtaryPolygonModels))
                .subscribeOn(Schedulers.io());
    }
}
