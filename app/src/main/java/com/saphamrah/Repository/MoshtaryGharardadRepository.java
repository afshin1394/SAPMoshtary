package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryGharardadDAO;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryGharardadRepository {
    Context context;
    MoshtaryGharardadDAO moshtaryGharardadDAO;


    public MoshtaryGharardadRepository(Context context) {
        this.context = context;
        moshtaryGharardadDAO = new MoshtaryGharardadDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> moshtaryGharardadDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels) {
        return () -> moshtaryGharardadDAO.insertGroup(moshtaryGharardadModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(moshtaryGharardadModels))
                .subscribeOn(Schedulers.io());
    }
}
