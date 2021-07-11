package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ElamMarjoeePPCDAO;
import com.saphamrah.Model.ElamMarjoeePPCModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ElamMarjoeePPCRepository {
    Context context;
    ElamMarjoeePPCDAO elamMarjoeePPCDAO;


    public ElamMarjoeePPCRepository(Context context) {
        this.context = context;
        elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> elamMarjoeePPCDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ElamMarjoeePPCModel> elamMarjoeePPCModels) {
        return () -> elamMarjoeePPCDAO.insertGroup(elamMarjoeePPCModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ElamMarjoeePPCModel> elamMarjoeePPCModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(elamMarjoeePPCModels))
                .subscribeOn(Schedulers.io());
    }
}
