package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryAddressRepository {
    Context context;
    MoshtaryAddressDAO moshtaryAddressDAO;


    public MoshtaryAddressRepository(Context context) {
        this.context = context;
        moshtaryAddressDAO = new MoshtaryAddressDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return () -> moshtaryAddressDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryAddressModel> moshtaryAddressModels) {
        return () -> moshtaryAddressDAO.insertGroup(moshtaryAddressModels);

    }

    private Callable<Boolean> deleteByccMoshtaryCallable(int ccMoshtary) {
        return () -> moshtaryAddressDAO.deleteByccMoshtary(ccMoshtary);

    }

    private Callable<Boolean> deleteByccAddressMoshtariesCallable(String ccMoshtaries) {
        return () -> moshtaryAddressDAO.deleteByccMoshtaries(ccMoshtaries);

    }
    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MoshtaryAddressModel> moshtaryAddressModels) {
        return RxAsync.makeObservable(insertGroupCallable(moshtaryAddressModels))
                .subscribeOn(Schedulers.io());
    }


    public Observable<Boolean> deleteByccMoshtary(int ccMoshtary) {
        return RxAsync.makeObservable(deleteByccMoshtaryCallable(ccMoshtary))
                .subscribeOn(Schedulers.io());
    }


    public Observable<Boolean> deleteByccMoshtaries(String ccMoshtaries) {
        return RxAsync.makeObservable(deleteByccAddressMoshtariesCallable(ccMoshtaries))
                .subscribeOn(Schedulers.io());
    }
}
