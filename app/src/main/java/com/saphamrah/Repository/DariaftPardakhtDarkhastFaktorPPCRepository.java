package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.RxService.APIServiceRxjava;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DariaftPardakhtDarkhastFaktorPPCRepository {
    Context context;
    DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO;


    public DariaftPardakhtDarkhastFaktorPPCRepository(Context context) {
        this.context = context;
        dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return dariaftPardakhtDarkhastFaktorPPCDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> deleteccDarkhastFaktorCallable(long ccDarkhastFaktor) {
        return () -> dariaftPardakhtDarkhastFaktorPPCDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels,boolean fromGetProgram) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return dariaftPardakhtDarkhastFaktorPPCDAO.insertGroup(dariaftPardakhtDarkhastFaktorPPCModels,fromGetProgram);
            }
        };

    }




    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }
    public Observable<Boolean> deleteByccDarkhastFaktor(long ccDarkhastFaktor) {
        return RxAsync.makeObservable(deleteccDarkhastFaktorCallable(ccDarkhastFaktor))
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels,boolean fromGetProgram) {
        return RxAsync.makeObservable(insertGroupCallable(dariaftPardakhtDarkhastFaktorPPCModels,fromGetProgram))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<DariaftPardakhtDarkhastFaktorPPCModel>> fetchDariaftPardakhtDarkhastFaktorServiceRx(ServerIpModel serverIpModel, String activityNameForLog, String noeFaktorHavale , String ccDarkhastFaktor) {
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);

        return apiServiceRxjava.getDariaftPardakhtDarkhastFaktorHavalehPPC(noeFaktorHavale,ccDarkhastFaktor)
                .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(),activityNameForLog,"fetchApiServiceRx","fetchDariaftPardakhtServiceRx"))
                .map(getAllDariaftPardakhtDarkhastFaktorResult ->{
                    if (getAllDariaftPardakhtDarkhastFaktorResult.body().getData() !=null){
                        return getAllDariaftPardakhtDarkhastFaktorResult.body().getData();
                    } else
                        return new ArrayList<DariaftPardakhtDarkhastFaktorPPCModel>();
                })
                .subscribeOn(Schedulers.io());
    }

}
