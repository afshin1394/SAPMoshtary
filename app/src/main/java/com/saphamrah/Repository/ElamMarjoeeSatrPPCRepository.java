package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ElamMarjoeeSatrPPCDAO;
import com.saphamrah.Model.ElamMarjoeeSatrPPCModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ElamMarjoeeSatrPPCRepository {
    Context context;
    ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO;


    public ElamMarjoeeSatrPPCRepository(Context context) {
        this.context = context;
        elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> elamMarjoeeSatrPPCDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ElamMarjoeeSatrPPCModel> elamMarjoeeSatrPPCModels) {
        return () -> elamMarjoeeSatrPPCDAO.insertGroup(elamMarjoeeSatrPPCModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ElamMarjoeeSatrPPCModel> elamMarjoeeSatrPPCModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(elamMarjoeeSatrPPCModels))
                .subscribeOn(Schedulers.io());
    }
}
