package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.NoeMoshtaryRialKharidDAO;
import com.saphamrah.Model.NoeMoshtaryRialKharidModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class NoeMoshtaryRialKharidRepository {
    Context context;
    NoeMoshtaryRialKharidDAO noeMoshtaryRialKharidDAO;


    public NoeMoshtaryRialKharidRepository(Context context) {
        this.context = context;
        noeMoshtaryRialKharidDAO = new NoeMoshtaryRialKharidDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> noeMoshtaryRialKharidDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels) {
        return () -> noeMoshtaryRialKharidDAO.insertGroup(noeMoshtaryRialKharidModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels) {
        return RxAsync.makeObservable(insertGroupCallable(noeMoshtaryRialKharidModels))
                .subscribeOn(Schedulers.io());
    }
}
