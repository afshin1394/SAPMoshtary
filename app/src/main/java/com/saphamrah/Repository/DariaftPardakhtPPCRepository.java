package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.RxService.APIServiceRxjava;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DariaftPardakhtPPCRepository {

    Context context;
    DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO;


    public DariaftPardakhtPPCRepository(Context context) {
        this.context = context;
        dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return dariaftPardakhtPPCDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return dariaftPardakhtPPCDAO.insertGroup(dariaftPardakhtPPCModels,true);
            }
        };

    }
    private Callable<Boolean> deleteByccDariaftPardakhtsCallable(String ccDariaftPardakht) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return dariaftPardakhtPPCDAO.deleteByccDariaftPardakhts(ccDariaftPardakht);
            }
        };

    }

    private Callable<Boolean> deleteByccDarkhastFaktorCallable(long ccDarkhastFaktor) {
        return () -> dariaftPardakhtPPCDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels) {
        return RxAsync.makeObservable(insertGroupCallable(dariaftPardakhtPPCModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> deleteByccDariaftPardakhts(String ccDaryaftPardakhts) {
        return RxAsync.makeObservable(deleteByccDariaftPardakhtsCallable(ccDaryaftPardakhts))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> deleteByccDarkhastFaktor(long ccDarkhastFaktor) {
        return RxAsync.makeObservable(deleteByccDarkhastFaktorCallable(ccDarkhastFaktor))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<DariaftPardakhtPPCModel>> fetchDariaftPardakhtServiceRx(ServerIpModel serverIpModel, String activityNameForLog,String noeFaktorHavale , String ccDarkhastFaktor) {
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);

        return apiServiceRxjava.getDariaftPardakhtHavalePPC(noeFaktorHavale,ccDarkhastFaktor)
                .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(),activityNameForLog,"fetchApiServiceRx","fetchDariaftPardakhtServiceRx"))
                .map(getAllDariaftPardakhtResult ->{
                    if (getAllDariaftPardakhtResult.body().getData() !=null){
                        return getAllDariaftPardakhtResult.body().getData();
                    } else
                        return new ArrayList<DariaftPardakhtPPCModel>();
                })
                .subscribeOn(Schedulers.io());
    }
}
