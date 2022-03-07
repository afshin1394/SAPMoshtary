package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.PrintFaktorDAO;
import com.saphamrah.DAO.RptDarkhastFaktorVazeiatPPCDAO;
import com.saphamrah.Model.PrintFaktorModel;
import com.saphamrah.Model.RptJashnvarehForoshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.ServiceResponse.GetPrintFaktorSingleResult;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class PrintFaktorRepository {
    Context context;
    PrintFaktorDAO printFaktorDAO;
    public PrintFaktorRepository(Context context) {
        this.context = context;
        printFaktorDAO = new PrintFaktorDAO(context);
    }

    /*******************************************************************Callable*****************************************************************/

    private Callable<PrintFaktorModel> getImageWithUniqIDCallable(String uniqueID) {
        return () -> printFaktorDAO.getImageWithUniqID(uniqueID);
    }

    private Callable<Boolean> updateImageCallable(String image,String uniqueID) {
        return () -> printFaktorDAO.updateImage(image,uniqueID);
    }

    private Callable<Boolean> checkPrintFaktorExistCallable(String uniqueID) {
        return () -> printFaktorDAO.checkPrintFaktorExist(uniqueID);
    }
    /*******************************************************************Observable*****************************************************************/
    public Observable<PrintFaktorModel> getImageWithUniqID(String uniqueID) {
        return RxAsync.makeObservable(getImageWithUniqIDCallable(uniqueID))
                .subscribeOn(Schedulers.io());
    }
    public Observable<Boolean> updateImage(String image,String uniqueID) {
        return RxAsync.makeObservable(updateImageCallable(image,uniqueID))
                .subscribeOn(Schedulers.io());
    }
    public Observable<Boolean> checkPrintFaktorExist(String uniqueID) {
        return RxAsync.makeObservable(checkPrintFaktorExistCallable(uniqueID))
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> fetchApiServiceRx(ServerIpModel serverIpModel, String activityNameForLog, String ccDarkhastFaktor, String ccDarkhastHavaleh) {
        APIServiceRxjava apiServiceRxjava =  RxHttpRequest.getInstance().getApiRx(serverIpModel);

        return apiServiceRxjava.getPrintFaktorImage(ccDarkhastFaktor,ccDarkhastHavaleh)
                .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(),activityNameForLog,"fetchApiServiceRx","getPrintFaktor"))
                .map(getPrintFaktorSingleResultResponse -> {
                    if (getPrintFaktorSingleResultResponse.body()!=null) {
                        if (getPrintFaktorSingleResultResponse.body().getData()!=null)
                           if (getPrintFaktorSingleResultResponse.body().getData().get(0)!=null)
                               if (getPrintFaktorSingleResultResponse.body().getData().get(0).getImage()!=null)
                        return getPrintFaktorSingleResultResponse.body().getData().get(0).getImage();
                    }

                        return  "";
                }).subscribeOn(Schedulers.io());


    }
}
