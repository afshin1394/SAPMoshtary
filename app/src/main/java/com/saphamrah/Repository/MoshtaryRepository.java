package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryParentModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.RxService.APIServiceRxjava;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MoshtaryRepository {
    Context context;
    MoshtaryDAO moshtaryDAO;


    public MoshtaryRepository(Context context) {
        this.context = context;
        moshtaryDAO = new MoshtaryDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return () -> moshtaryDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MoshtaryModel> moshtaryModels) {
        return () -> moshtaryDAO.insertGroup(moshtaryModels);

    }

    private Callable<Boolean> insertCallable(MoshtaryModel moshtaryModel) {
        return () -> moshtaryDAO.insertGroup(moshtaryModel);

    }

    private Callable<Boolean> updateExtraOlaviatFromOlaviatCallable(){
        return () -> moshtaryDAO.updateExtraOlaviatFromOlaviat();

    }

    private Callable<Boolean> updateccMoshtaryParentInMoshtaryCallable(ArrayList<MoshtaryParentModel> moshtaryParentModels){
        return () -> moshtaryDAO.updateccMoshtaryParentInMoshtary(moshtaryParentModels);

    }
    private Callable<MoshtaryModel> getByccMoshtaryCallable(int ccMoshtary) {
        return () -> moshtaryDAO.getByccMoshtary(ccMoshtary);

    }
    private Callable<Integer> getccMasirByCodeMoshtaryCallable(int codeMoshtary) {
        return () -> moshtaryDAO.getCcMasirByCodeMoshtary(codeMoshtary);

    }
    private Callable<Integer> getccMasirByccForoshandehCallable(int ccForoshandeh) {
        return () -> moshtaryDAO.getCcMasirByCcForoshandeh(ccForoshandeh);

    }

    private Callable<Boolean> deleteByCodeMoshtarysCallable(String codeMoshtary) {
        return () -> moshtaryDAO.deleteByCodeMoshtarys(codeMoshtary);

    }
    private Callable<Boolean> deleteByCcMoshtaryCallable(int codeMoshtary) {
        return () -> moshtaryDAO.deleteByccMoshtary(codeMoshtary);

    }

    private Callable<String> getAllccNoeSenfCallable() {
        return () -> moshtaryDAO.getAllccNoeSenf();

    }



    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MoshtaryModel> moshtaryModels) {
        return RxAsync.makeObservable(insertGroupCallable(moshtaryModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insert(MoshtaryModel moshtaryModel) {
        return RxAsync.makeObservable(insertCallable(moshtaryModel))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> updateExtraOlaviatFromOlaviat() {
        return RxAsync.makeObservable(updateExtraOlaviatFromOlaviatCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> updateccMoshtaryParentInMoshtary(ArrayList<MoshtaryParentModel> moshtaryParentModels) {
        return RxAsync.makeObservable(updateccMoshtaryParentInMoshtaryCallable(moshtaryParentModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<MoshtaryModel> getByccMoshtary(int ccMoshtary) {
        return RxAsync.makeObservable(getByccMoshtaryCallable(ccMoshtary))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> deleteByCodeMoshtarys(String ccMoshtary) {
        return RxAsync.makeObservable(deleteByCodeMoshtarysCallable(ccMoshtary))
                .subscribeOn(Schedulers.io());
    }
    public Observable<Boolean> deleteByCcMoshtary(int ccMoshtary) {
        return RxAsync.makeObservable(deleteByCcMoshtaryCallable(ccMoshtary))
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> getAllccNoeSenf() {
        return RxAsync.makeObservable(getAllccNoeSenfCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Integer> getccMasirByCodeMoshtary(int codeMoshtary) {
        return RxAsync.makeObservable(getccMasirByCodeMoshtaryCallable(codeMoshtary))
                .subscribeOn(Schedulers.io());
    }
    public Observable<Integer> getccMasirByccForoshandeh(int ccForoshande) {
        return RxAsync.makeObservable(getccMasirByccForoshandehCallable(ccForoshande))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<MoshtaryModel>> fetchMoshtaryByccMasirServiceRx(ServerIpModel serverIpModel, String activityNameForLog, String ccForoshandeh,  String ccMasirs,  String codeMoshtary) {
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);

        return apiServiceRxjava.getAllMoshtaryByccMasir(ccForoshandeh , ccMasirs , codeMoshtary)
                .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(),activityNameForLog,"fetchApiServiceRx","fetchDariaftPardakhtServiceRx"))
                .map(getAllMoshtaryResult ->{
                    if (getAllMoshtaryResult.body().getData() !=null){
                        return getAllMoshtaryResult.body().getData();
                    } else
                        return new ArrayList<MoshtaryModel>();
                })
                .subscribeOn(Schedulers.io());
    }

    public Observable<MoshtaryModel> fetchSingleMoshtaryByccMasirServiceRx(ServerIpModel serverIpModel, String activityNameForLog, String ccForoshandeh,  String ccMasirs,  String codeMoshtary) {
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);

        return apiServiceRxjava.getAllMoshtaryByccMasir(ccForoshandeh , ccMasirs , codeMoshtary)
                .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(),activityNameForLog,"fetchApiServiceRx","fetchDariaftPardakhtServiceRx"))
                .map(getAllMoshtaryResult ->{
                    if (getAllMoshtaryResult.body().getData() !=null){
                        return getAllMoshtaryResult.body().getData().get(0);
                    } else
                        return new MoshtaryModel();
                })
                .subscribeOn(Schedulers.io());
    }

}
