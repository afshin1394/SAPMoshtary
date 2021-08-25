package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
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
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return darkhastFaktorDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupFromGetProgramCallable(ArrayList<DarkhastFaktorModel> darkhastFaktorModels,int noeMasouliat) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return darkhastFaktorDAO.insertGroupFromGetProgram(darkhastFaktorModels,noeMasouliat);
            }
        };

    }

    private Callable<String> getccDarkhastFaktorsByNoeFaktorHavaleRoozCallable(int ccNoeFaktor) {
        return new Callable<String>() {
            @Override
            public String call()  {
                return darkhastFaktorDAO.getccDarkhastFaktorsByNoeFaktorHavaleRooz(ccNoeFaktor);
            }
        };

    }

    private Callable<String> getccDarkhastFaktorsByNoeFaktorHavaleCallable(int ccNoeHavale) {
        return new Callable<String>() {
            @Override
            public String call()  {
                return darkhastFaktorDAO.getccDarkhastFaktorsByNoeFaktorHavale(ccNoeHavale);
            }
        };

    }


    private Callable<String> getAllccForoshandehCallable() {
        return new Callable<String>() {
            @Override
            public String call()  {
                return darkhastFaktorDAO.getAllccForoshandeh();
            }
        };

    }


    private Callable<Map<Integer, String>> getccMoshtaryPakhshForForoshandehCallable(int codeVaziatFaktorTasvie) {
        return new Callable<Map<Integer, String>>() {
            @Override
            public Map<Integer, String> call()  {
                return darkhastFaktorDAO.getccMoshtaryPakhshForForoshandeh(codeVaziatFaktorTasvie);
            }
        };

    }

    private Callable<ArrayList<DarkhastFaktorModel>> getAllByNotCodeVazeiatCallable(int codeVaziat) {
        return new Callable<ArrayList<DarkhastFaktorModel>>() {
            @Override
            public ArrayList<DarkhastFaktorModel> call()  {
                return darkhastFaktorDAO.getAllByNotCodeVazeiat(codeVaziat);
            }
        };

    }

    private Callable<ArrayList<DarkhastFaktorModel>> getAllByNoeFaktorHavaleAndNotCodeVazeiatCallable(int noeFaktorHavale,int codeVaziat) {
        return new Callable<ArrayList<DarkhastFaktorModel>>() {
            @Override
            public ArrayList<DarkhastFaktorModel> call()  {
                return darkhastFaktorDAO.getAllByNoeFaktorHavaleAndNotCodeVazeiat(noeFaktorHavale,codeVaziat);
            }
        };

    }

    private Callable<ArrayList<Integer>> getccMarkazSazmanForoshSakhtarForoshAllCallable() {
        return new Callable<ArrayList<Integer>>() {
            @Override
            public ArrayList<Integer> call()  {
                return darkhastFaktorDAO.getccMarkazSazmanForoshSakhtarForoshAll();
            }
        };

    }

    private Callable<String> getCcMoshtaryForZanjireCallable() {
        return () -> darkhastFaktorDAO.getCcMoshtaryForZanjire();

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
}
