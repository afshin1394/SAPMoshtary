package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.RptMandehdarDAO;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.RxService.APIServiceRxjava;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RptMandehdarRepository {
    Context context;
    RptMandehdarDAO rptMandehdarDAO;


    public RptMandehdarRepository(Context context) {
        this.context = context;
        rptMandehdarDAO = new RptMandehdarDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> rptMandehdarDAO.deleteAll();
    }

    private Callable<ArrayList<RptMandehdarModel>> getAllCallable(){
        return  ()-> rptMandehdarDAO.getAll();
    }
    private Callable<Boolean> insertGroupCallable(ArrayList<RptMandehdarModel> rptMandehdarModels) {
        return () -> rptMandehdarDAO.insertGroup(rptMandehdarModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<RptMandehdarModel>> getAll() {
        return RxAsync.makeObservable(getAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<RptMandehdarModel> rptMandehdarModels) {
        return RxAsync.makeObservable(insertGroupCallable(rptMandehdarModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<RptMandehdarModel>> fetchAllMandehdar(ServerIpModel serverIpModel, String activityNameForLog, String ccForoshandeh) {
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);

        return apiServiceRxjava.getAllrptListMoavaghForoshandeh(ccForoshandeh)
                .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(),activityNameForLog,"fetchApiServiceRx","fetchAllMandehdar"))
                .map(mandehdar ->{
                    if (mandehdar.body().getData()!=null){
                        return mandehdar.body().getData();
                    } else
                        return new ArrayList<RptMandehdarModel>();
                })
                .subscribeOn(Schedulers.io());
    }
}
