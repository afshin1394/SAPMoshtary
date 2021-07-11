package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryMorajehShodehRoozDAO;
import com.saphamrah.Model.MoshtaryMorajehShodehRoozModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryMorajehShodehRoozRepository {
    Context context;
    MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO;


    public MoshtaryMorajehShodehRoozRepository(Context context) {
        this.context = context;
        moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return () -> moshtaryMorajehShodehRoozDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryMorajehShodehRoozModel> moshtaryEtebarSazmanForoshModels) {
        return () -> moshtaryMorajehShodehRoozDAO.insertGroup(moshtaryEtebarSazmanForoshModels);

    }
    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }
    public Observable<Boolean> insertGroup(ArrayList<MoshtaryMorajehShodehRoozModel> moshtaryMorajehShodehRoozModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(moshtaryMorajehShodehRoozModels))
                .subscribeOn(Schedulers.io());
    }
}
