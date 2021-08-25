package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ElamMarjoeeSatrPPCTedadDAO;
import com.saphamrah.Model.ElamMarjoeeSatrPPCTedadModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ElamMarjoeeSatrPPCTedadRepository {
    Context context;
    ElamMarjoeeSatrPPCTedadDAO elamMarjoeeSatrPPCTedadDAO;


    public ElamMarjoeeSatrPPCTedadRepository(Context context) {
        this.context = context;
        elamMarjoeeSatrPPCTedadDAO = new ElamMarjoeeSatrPPCTedadDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> elamMarjoeeSatrPPCTedadDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ElamMarjoeeSatrPPCTedadModel> elamMarjoeeSatrPPCTedadModels) {
        return () -> elamMarjoeeSatrPPCTedadDAO.insertGroup(elamMarjoeeSatrPPCTedadModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ElamMarjoeeSatrPPCTedadModel> elamMarjoeeSatrPPCTedadModels) {
        return RxAsync.makeObservable(insertGroupCallable(elamMarjoeeSatrPPCTedadModels))
                .subscribeOn(Schedulers.io());
    }
}
