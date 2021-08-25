package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MarjoeeKamelImageTmpDAO;
import com.saphamrah.Model.MarjoeeKamelImageTmpModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MarjoeeKamelImageTmpRepository {
    Context context;
    MarjoeeKamelImageTmpDAO marjoeeKamelImageTmpDAO;

    public MarjoeeKamelImageTmpRepository(Context context) {
        this.context = context;
        marjoeeKamelImageTmpDAO = new MarjoeeKamelImageTmpDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return () -> marjoeeKamelImageTmpDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MarjoeeKamelImageTmpModel> marjoeeKamelImageTmpModels) {
        return () -> marjoeeKamelImageTmpDAO.insertGroup(marjoeeKamelImageTmpModels);

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MarjoeeKamelImageTmpModel> marjoeeKamelImageTmpModels) {
        return RxAsync.makeObservable(insertGroupCallable(marjoeeKamelImageTmpModels))
                .subscribeOn(Schedulers.io());
    }
}
