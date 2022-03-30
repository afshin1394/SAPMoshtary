package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.BrandDAO;
import com.saphamrah.DAO.ConfigMaliDAO;
import com.saphamrah.Model.BrandModel;
import com.saphamrah.Model.ConfigMaliModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.DataResponse.ConfigMaliResponse;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ConfigMaliRepository {
    Context context;
    ConfigMaliDAO configMaliDAO;


    public ConfigMaliRepository(Context context) {
        this.context = context;
        configMaliDAO = new ConfigMaliDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return configMaliDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ConfigMaliModel> configMaliModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return configMaliDAO.insertGroup(configMaliModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ConfigMaliModel> configMaliModels) {
        return RxAsync.makeObservable(insertGroupCallable(configMaliModels))
                .subscribeOn(Schedulers.io());
    }


    public Observable<ArrayList<ConfigMaliModel>> fetchConfigMaliRx(ServerIpModel serverIpModel, String activityNameForLog, String ccMarkazforosh, String ccSazmanForosh) {
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);

        return apiServiceRxjava.getConfigMali(ccMarkazforosh,ccSazmanForosh)
                .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(),activityNameForLog,"fetchApiServiceRx","fetchConfigMaliRx"))
                .map(configMaliResponse ->{
                    if (configMaliResponse.body().getData() !=null)
                    {
                        return configMaliResponse.body().getData();
                    } else
                        return new ArrayList<ConfigMaliModel>();
                })
                .subscribeOn(Schedulers.io());
    }


}
