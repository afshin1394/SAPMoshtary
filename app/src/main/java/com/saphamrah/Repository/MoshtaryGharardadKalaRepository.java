package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryGharardadKalaDAO;
import com.saphamrah.Model.MoshtaryGharardadKalaModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryGharardadKalaRepository {
    Context context;
    MoshtaryGharardadKalaDAO moshtaryGharardadKalaDAO;


    public MoshtaryGharardadKalaRepository(Context context) {
        this.context = context;
        moshtaryGharardadKalaDAO = new MoshtaryGharardadKalaDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> moshtaryGharardadKalaDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModelsList) {
        return () -> moshtaryGharardadKalaDAO.insertGroup(moshtaryGharardadKalaModelsList);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModelsList) {
        return RxDAOUtils.makeObservable(insertGroupCallable(moshtaryGharardadKalaModelsList))
                .subscribeOn(Schedulers.io());
    }
}
