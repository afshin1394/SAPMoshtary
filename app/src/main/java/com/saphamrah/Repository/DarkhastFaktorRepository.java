package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.RxService.APIServiceRxjava;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DarkhastFaktorRepository {

    Context context;
    DarkhastFaktorDAO darkhastFaktorDAO;


    public DarkhastFaktorRepository(Context context) {
        this.context = context;
        darkhastFaktorDAO = new DarkhastFaktorDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> darkhastFaktorDAO.deleteAll();
    }
    private Callable<Boolean> deleteByccDarkhastFaktorCallable(long ccDarkhastFaktor) {
        return () -> darkhastFaktorDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
    }
    private Callable<Boolean> insertGroupFromGetProgramCallable(ArrayList<DarkhastFaktorModel> darkhastFaktorModels,int noeMasouliat) {
        return () -> darkhastFaktorDAO.insertGroupFromGetProgram(darkhastFaktorModels,noeMasouliat);

    }
    private Callable<Boolean> insertGroupCallable(DarkhastFaktorModel darkhastFaktorModel) {
        return () -> darkhastFaktorDAO.insertGroup(darkhastFaktorModel);
    }



    private Callable<String> getccDarkhastFaktorsByNoeFaktorHavaleRoozCallable(int ccNoeFaktor) {
        return () -> darkhastFaktorDAO.getccDarkhastFaktorsByNoeFaktorHavaleRooz(ccNoeFaktor);
    }

    private Callable<String> getccDarkhastFaktorsByNoeFaktorHavaleCallable(int ccNoeHavale) {
        return () -> darkhastFaktorDAO.getccDarkhastFaktorsByNoeFaktorHavale(ccNoeHavale);
    }


    private Callable<String> getAllccForoshandehCallable() {
        return () -> darkhastFaktorDAO.getAllccForoshandeh();
    }


    private Callable<Map<Integer, String>> getccMoshtaryPakhshForForoshandehCallable(int codeVaziatFaktorTasvie) {
        return () -> darkhastFaktorDAO.getccMoshtaryPakhshForForoshandeh(codeVaziatFaktorTasvie);

    }

    private Callable<ArrayList<DarkhastFaktorModel>> getAllByNotCodeVazeiatCallable(int codeVaziat) {
        return () -> darkhastFaktorDAO.getAllByNotCodeVazeiat(codeVaziat);
    }

    private Callable<ArrayList<DarkhastFaktorModel>> getAllByNoeFaktorHavaleAndNotCodeVazeiatCallable(int noeFaktorHavale,int codeVaziat) {
        return () -> darkhastFaktorDAO.getAllByNoeFaktorHavaleAndNotCodeVazeiat(noeFaktorHavale,codeVaziat);
    }

    private Callable<Integer> getccMarkazSazmanForoshSakhtarForoshAllCallable() {
        return () -> darkhastFaktorDAO.getccMarkazSazmanForoshSakhtarForosh();
    }

    private Callable<String> getCcMoshtaryForZanjireCallable() {
        return () -> darkhastFaktorDAO.getCcMoshtaryForZanjire();
    }

    private Callable<JSONArray> getZangireiFaktorInfoCallable() {
        return () -> darkhastFaktorDAO.getZangireiFaktorInfo();
    }

    private Callable<String> getAllccMoshtaryCallable() {
        return () -> darkhastFaktorDAO.getAllccMoshtary();
    }


    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> deleteByccDarkhastFaktor(long ccDarkhastFaktor) {
        return RxAsync.makeObservable(deleteByccDarkhastFaktorCallable(ccDarkhastFaktor))
                .subscribeOn(Schedulers.io());
    }


    public Observable<Boolean> insertGroupFromGetProgram(ArrayList<DarkhastFaktorModel> darkhastFaktorModels, int noeMasouliat) {
        return RxAsync.makeObservable(insertGroupFromGetProgramCallable(darkhastFaktorModels,noeMasouliat))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insert(DarkhastFaktorModel darkhastFaktorModel) {
        return RxAsync.makeObservable(insertGroupCallable(darkhastFaktorModel))
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> getccDarkhastFaktorsByNoeFaktorHavaleRooz(int ccNoeFaktor) {
        return RxAsync.makeObservable(getccDarkhastFaktorsByNoeFaktorHavaleRoozCallable(ccNoeFaktor))
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> getccDarkhastFaktorsByNoeFaktorHavale(int ccNoeHavale) {
        return RxAsync.makeObservable(getccDarkhastFaktorsByNoeFaktorHavaleCallable(ccNoeHavale))
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> getAllccForoshandeh() {
        return RxAsync.makeObservable(getAllccForoshandehCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> getccMoshtaryPakhshForForoshandeh() {
        return RxAsync.makeObservable(getAllccForoshandehCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Map<Integer, String>> getccMoshtaryPakhshForForoshandeh(int codeVaziatFaktorTasvie) {
        return RxAsync.makeObservable(getccMoshtaryPakhshForForoshandehCallable(codeVaziatFaktorTasvie))
                .subscribeOn(Schedulers.io());
    }


    public Observable<ArrayList<DarkhastFaktorModel>> getAllByNotCodeVazeiat(int codeVaziat) {
        return RxAsync.makeObservable(getAllByNotCodeVazeiatCallable(codeVaziat))
                .subscribeOn(Schedulers.io());
    }
    public Observable<ArrayList<DarkhastFaktorModel>> getAllByNoeFaktorHavaleAndNotCodeVazeiat(int noeFaktorHavale,int codeVaziat) {
        return RxAsync.makeObservable(getAllByNoeFaktorHavaleAndNotCodeVazeiatCallable(noeFaktorHavale,codeVaziat))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Integer> getccMarkazSazmanForoshSakhtarForoshAll() {
        return RxAsync.makeObservable(getccMarkazSazmanForoshSakhtarForoshAllCallable())
                .subscribeOn(Schedulers.io());
    }


    public Observable<String> getCcMoshtaryForZanjire() {
        return RxAsync.makeObservable(getCcMoshtaryForZanjireCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<JSONArray> getZangireiFaktorInfo() {
        return RxAsync.makeObservable(getZangireiFaktorInfoCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> getAllccMoshtary() {
        return RxAsync.makeObservable(getAllccMoshtaryCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<DarkhastFaktorModel>> fetchDarkhastFaktorByccDarkhastFaktorServiceRx(ServerIpModel serverIpModel, String activityNameForLog, String ccDarkhastFaktor) {
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);

        return apiServiceRxjava.getDarkhastFaktorByccDarkhastFaktor(ccDarkhastFaktor)
                .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(),activityNameForLog,"fetchApiServiceRx","getDarkhastFaktorByccDarkhastFaktor"))
                .map(getAllDarkhastFaktorResult ->{
                    if (getAllDarkhastFaktorResult.body().getData() !=null){
                        return getAllDarkhastFaktorResult.body().getData();
                    } else
                        return new ArrayList<DarkhastFaktorModel>();
                })
                .subscribeOn(Schedulers.io());
    }


}
