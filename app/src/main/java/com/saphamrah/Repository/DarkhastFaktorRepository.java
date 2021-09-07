package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

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
    private Callable<Boolean> insertGroupFromGetProgramCallable(ArrayList<DarkhastFaktorModel> darkhastFaktorModels,int noeMasouliat) {
        return () -> darkhastFaktorDAO.insertGroupFromGetProgram(darkhastFaktorModels,noeMasouliat);

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

    private Callable<ArrayList<Integer>> getccMarkazSazmanForoshSakhtarForoshAllCallable() {
        return () -> darkhastFaktorDAO.getccMarkazSazmanForoshSakhtarForoshAll();
    }

    private Callable<String> getCcMoshtaryForZanjireCallable() {
        return () -> darkhastFaktorDAO.getCcMoshtaryForZanjire();
    }

    private Callable<JSONArray> getZangireiFaktorInfoCallable() {
        return () -> darkhastFaktorDAO.getZangireiFaktorInfo();
    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroupFromGetProgram(ArrayList<DarkhastFaktorModel> darkhastFaktorModels, int noeMasouliat) {
        return RxAsync.makeObservable(insertGroupFromGetProgramCallable(darkhastFaktorModels,noeMasouliat))
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

    public Observable<ArrayList<Integer>> getccMarkazSazmanForoshSakhtarForoshAll() {
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
}
