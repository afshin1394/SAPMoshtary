package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.ElatAdamTahvilDarkhastDAO;
import com.saphamrah.Model.ElatAdamTahvilDarkhastModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.RxService.APIServiceRxjava;

import java.util.ArrayList;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ElatAdamTahvilDarkhastRepository {
    Context context;
    ElatAdamTahvilDarkhastDAO elatAdamTahvilDarkhastDAO;

    public ElatAdamTahvilDarkhastRepository(Context context) {
        this.context = context;
        elatAdamTahvilDarkhastDAO = new ElatAdamTahvilDarkhastDAO(BaseApplication.getContext());
    }

    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return () -> elatAdamTahvilDarkhastDAO.deleteAll();
    }

    private Callable<Boolean> insertAllCallable(ArrayList<ElatAdamTahvilDarkhastModel> models){
        return () -> elatAdamTahvilDarkhastDAO.insertGroup(models);
    }



    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertAll(ArrayList<ElatAdamTahvilDarkhastModel> models){
        return RxAsync.makeObservable(insertAllCallable(models))
                .subscribeOn(Schedulers.io());
    }

    public  Observable<ArrayList<ElatAdamTahvilDarkhastModel>> fetchApiServiceRx(ServerIpModel serverIpModel,String activityNameForLog){
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);
        return apiServiceRxjava.getElatTahvilDarkhast()
                        .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(),activityNameForLog,"fetchApiServiceRx","getElatTahvilDarkhast"))
                .map(models -> {
                    if (models.body() !=null){
                        return models.body().getData();
                    } else
                        return new ArrayList<ElatAdamTahvilDarkhastModel>();
                }).subscribeOn(Schedulers.io());
    }
}
