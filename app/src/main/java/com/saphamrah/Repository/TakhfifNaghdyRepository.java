package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.TakhfifNaghdyDAO;
import com.saphamrah.Model.TakhfifNaghdyModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TakhfifNaghdyRepository {
    Context context;
    TakhfifNaghdyDAO takhfifNaghdyDAO;

    public TakhfifNaghdyRepository(Context context) {
        this.context = context;
        takhfifNaghdyDAO = new TakhfifNaghdyDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call(){
                return takhfifNaghdyDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<TakhfifNaghdyModel> takhfifNaghdyModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return takhfifNaghdyDAO.insertGroup(takhfifNaghdyModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<TakhfifNaghdyModel> takhfifNaghdyModels) {
        return RxAsync.makeObservable(insertGroupCallable(takhfifNaghdyModels))
                .subscribeOn(Schedulers.io());
    }
}
