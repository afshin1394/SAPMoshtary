package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.TakhfifSenfiDAO;
import com.saphamrah.Model.TakhfifSenfiModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TakhfifSenfiRepository {
    Context context;
    TakhfifSenfiDAO takhfifSenfiDAO;

    public TakhfifSenfiRepository(Context context) {
        this.context = context;
        takhfifSenfiDAO = new TakhfifSenfiDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return () -> takhfifSenfiDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<TakhfifSenfiModel> takhfifSenfiModels) {
        return () -> takhfifSenfiDAO.insertGroupConditional(takhfifSenfiModels);

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<TakhfifSenfiModel> takhfifSenfiModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(takhfifSenfiModels))
                .subscribeOn(Schedulers.io());
    }
}
