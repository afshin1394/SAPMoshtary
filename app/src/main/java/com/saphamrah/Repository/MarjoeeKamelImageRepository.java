package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MarjoeeKamelImageDAO;
import com.saphamrah.Model.MarjoeeKamelImageModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MarjoeeKamelImageRepository {
    Context context;
    MarjoeeKamelImageDAO marjoeeKamelImageDAO;

    public MarjoeeKamelImageRepository(Context context) {
        this.context = context;
        marjoeeKamelImageDAO = new MarjoeeKamelImageDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return marjoeeKamelImageDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MarjoeeKamelImageModel> marjoeeKamelImageModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return marjoeeKamelImageDAO.insertGroup(marjoeeKamelImageModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MarjoeeKamelImageModel> marjoeeKamelImageModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(marjoeeKamelImageModels))
                .subscribeOn(Schedulers.io());
    }
}
