package com.saphamrah.Repository;

import android.content.Context;
import android.text.BoringLayout;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.C;
import com.saphamrah.DAO.RptHadafForoshDAO;
import com.saphamrah.DAO.RptJashnvarehDAO;
import com.saphamrah.Model.HadafForosh.RptHadafForoshModel;
import com.saphamrah.Model.RptJashnvarehForoshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.ServiceResponse.GetAllRptJashnvarehResult;
import com.saphamrah.WebService.ServiceResponse.GetAllvJayezehByccMarkazForoshResult;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RptJashnvarehForoshRepository {
    Context context;
    RptJashnvarehDAO rptJashnvarehDAO;


    public RptJashnvarehForoshRepository(Context context) {
        this.context = context;
        rptJashnvarehDAO = new RptJashnvarehDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> rptJashnvarehDAO.deleteAll();
    }
    private Callable<Boolean> insertGroupCallable(ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
        return () -> rptJashnvarehDAO.insertGroup(rptJashnvarehForoshModels);
    }

    private Callable<ArrayList<RptJashnvarehForoshModel>> getAllCallable() {
        return () -> rptJashnvarehDAO.getAll();
    }

    private Callable<ArrayList<RptJashnvarehForoshModel>> getAllMoshtariesCallable(int ccMoshtary) {
        return () -> rptJashnvarehDAO.getAllMoshtaries(ccMoshtary);
    }

    private Callable<ArrayList<RptJashnvarehForoshModel>> getJashnvarehListByccMoshtaryCallable(int ccMoshtary) {
        return () -> rptJashnvarehDAO.getJashnvarehListByccMoshtary(ccMoshtary);
    }

    private Callable<ArrayList<RptJashnvarehForoshModel>> getJashnvarehSatrByccJashnvarehAndccMoshtaryCallable(int ccMoshtary,int ccJashnvarehForosh) {
        return () -> rptJashnvarehDAO.getJashnvarehSatrByccJashnvarehAndccMoshtary(ccMoshtary,ccJashnvarehForosh);
    }

    private Callable<ArrayList<RptJashnvarehForoshModel>> getAllJashnvarehCallable(int ccMoshtary) {
        return () -> rptJashnvarehDAO.getAllJashnvareh(ccMoshtary);

    }

    private Callable<RptJashnvarehForoshModel> getSumForoshandehScoreCallable(int ccMoshtary) {
        return () -> rptJashnvarehDAO.getEmtiazForoshandeh(ccMoshtary);

    }

    private Callable<ArrayList<RptJashnvarehForoshModel>> getAllMoshtaryByJashnvarehCallable(int ccjashnvarehForosh,int ccMoshtaryExtra) {
        return () -> rptJashnvarehDAO.getAllMoshtaryByccJashnvareh(ccjashnvarehForosh,ccMoshtaryExtra);

    }

    private Callable<ArrayList<RptJashnvarehForoshModel>> getAllJashnvarehByCustomerCallable(int ccMoshtary) {
        return () -> rptJashnvarehDAO.getAllJashnvarehByccCustomer(ccMoshtary);

    }

    private Callable<ArrayList<RptJashnvarehForoshModel>> getAllJashnvarehSatrByCustomerCallable(int ccMoshtary) {
        return () -> rptJashnvarehDAO.getAllJashnvarehSatrByccCustomer(ccMoshtary);

    }

    private Callable<ArrayList<RptJashnvarehForoshModel>> getAllMoshtarySatrByJashnvarehCallable(int ccMoshtary,int ccJashnvareh) {
        return () -> rptJashnvarehDAO.getAllMoshtarySatrByJashnvareh(ccMoshtary,ccJashnvareh);

    }

    private Callable<ArrayList<RptJashnvarehForoshModel>> getSumJashnvarehByccJashnvarehCallable(int ccJashnvareh,int ccMoshtaryExtra) {
        return () -> rptJashnvarehDAO.getJashnvarehSumByccJashnvareh(ccJashnvareh,ccMoshtaryExtra);

    }


    private Callable<Boolean> isJashnvarehAvailableCallable(int ccMoshtary) {
        return () -> rptJashnvarehDAO.isJashnvarehAvailable(ccMoshtary);

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }


    public Observable<Boolean> insertGroup(ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
        return RxAsync.makeObservable(insertGroupCallable(rptJashnvarehForoshModels))
                .subscribeOn(Schedulers.io());
    }


    public Observable<ArrayList<RptJashnvarehForoshModel>> getAll() {
        return RxAsync.makeObservable(getAllCallable())
                .subscribeOn(Schedulers.io());
    }


    public Observable<ArrayList<RptJashnvarehForoshModel>> getAllMoshtaries(int ccMoshtary) {
        return RxAsync.makeObservable(getAllMoshtariesCallable(ccMoshtary))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<RptJashnvarehForoshModel>> getJashnvarehListByccMoshtary(int ccMoshtary) {
        return RxAsync.makeObservable(getJashnvarehListByccMoshtaryCallable(ccMoshtary))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<RptJashnvarehForoshModel>> getJashnvarehSatrByccJashnvarehAndccMoshtary(int ccMoshtary,int ccJashnvarehForosh) {
        return RxAsync.makeObservable(getJashnvarehSatrByccJashnvarehAndccMoshtaryCallable(ccMoshtary,ccJashnvarehForosh))
                .subscribeOn(Schedulers.io());
    }



    public Observable<ArrayList<RptJashnvarehForoshModel>> getAllJashnvareh(int ccMoshtary) {
        return RxAsync.makeObservable(getAllJashnvarehCallable(ccMoshtary))
                .subscribeOn(Schedulers.io());
    }

    public Observable<RptJashnvarehForoshModel> getSumForoshandehScore(int ccMoshtary) {
        return RxAsync.makeObservable(getSumForoshandehScoreCallable(ccMoshtary))
                .subscribeOn(Schedulers.io());
    }


    public Observable<ArrayList<RptJashnvarehForoshModel>> getAllMoshtaryByJashnvareh(int ccJashnvarehForosh,int ccMoshtaryExtra) {
        return RxAsync.makeObservable(getAllMoshtaryByJashnvarehCallable(ccJashnvarehForosh,ccMoshtaryExtra))
                .subscribeOn(Schedulers.io());
    }


    public Observable<ArrayList<RptJashnvarehForoshModel>> getAllJashnvarehByCustomer(int ccMoshtary) {
        return RxAsync.makeObservable(getAllJashnvarehByCustomerCallable(ccMoshtary))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<RptJashnvarehForoshModel>> getAllJashnvarehSatrByCustomer(int ccMoshtary) {
        return RxAsync.makeObservable(getAllJashnvarehSatrByCustomerCallable(ccMoshtary))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<RptJashnvarehForoshModel>> getAllMoshtarySatrByccJashnvareh(int ccMoshtary,int ccJashnvarehForosh) {
        return RxAsync.makeObservable(getAllMoshtarySatrByJashnvarehCallable(ccMoshtary,ccJashnvarehForosh))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<RptJashnvarehForoshModel>> getSumJashnvarehByccJashnvareh(int ccJashnvarehForosh,int ccMoshtaryExtra) {
        return RxAsync.makeObservable(getSumJashnvarehByccJashnvarehCallable(ccJashnvarehForosh,ccMoshtaryExtra))
                .subscribeOn(Schedulers.io());
    }


    public Observable<Boolean> isJashnvarehAvailable(int ccMoshtary) {
        return RxAsync.makeObservable(isJashnvarehAvailableCallable(ccMoshtary))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<RptJashnvarehForoshModel>> fetchApiServiceRx(ServerIpModel serverIpModel,String activityNameForLog,String ccForoshandeh,String ccMoshtaries) {
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);

        return apiServiceRxjava.getRptJashnvarehForosh(ccForoshandeh,ccMoshtaries)
                .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(),activityNameForLog,"fetchApiServiceRx","getRptJashnvarehForosh"))
                .map(getAllRptJashnvarehResultResponse -> {
                    if (getAllRptJashnvarehResultResponse.body()!=null) {
                        return getAllRptJashnvarehResultResponse.body().getData();
                    }
                    else
                        return new ArrayList<RptJashnvarehForoshModel>();
                }).subscribeOn(Schedulers.io());


    }

}
