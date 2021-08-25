package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.TakhfifHajmiDAO;
import com.saphamrah.Model.TakhfifHajmiModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TakhfifHajmiRepository {
    Context context;
    TakhfifHajmiDAO takhfifHajmiDAO;

    public TakhfifHajmiRepository(Context context) {
        this.context = context;
        takhfifHajmiDAO = new TakhfifHajmiDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return takhfifHajmiDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<TakhfifHajmiModel> takhfifHajmiModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return takhfifHajmiDAO.insertGroupConditional(takhfifHajmiModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<TakhfifHajmiModel> takhfifHajmiModels) {
        return RxAsync.makeObservable(insertGroupCallable(takhfifHajmiModels))
                .subscribeOn(Schedulers.io());
    }
}
