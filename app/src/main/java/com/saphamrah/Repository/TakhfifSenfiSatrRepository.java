package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.TakhfifSenfiSatrDAO;
import com.saphamrah.Model.TakhfifSenfiSatrModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TakhfifSenfiSatrRepository {
    Context context;
    TakhfifSenfiSatrDAO takhfifSenfiSatrDAO;

    public TakhfifSenfiSatrRepository(Context context) {
        this.context = context;
        takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return () -> takhfifSenfiSatrDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrModels) {
        return () -> takhfifSenfiSatrDAO.insertGroup(takhfifSenfiSatrModels);

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrModels) {
        return RxAsync.makeObservable(insertGroupCallable(takhfifSenfiSatrModels))
                .subscribeOn(Schedulers.io());
    }
}
