package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DariaftPardakhtDarkhastFaktorPPCRepository {
    Context context;
    DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO;


    public DariaftPardakhtDarkhastFaktorPPCRepository(Context context) {
        this.context = context;
        dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return dariaftPardakhtDarkhastFaktorPPCDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels,boolean fromGetProgram) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return dariaftPardakhtDarkhastFaktorPPCDAO.insertGroup(dariaftPardakhtDarkhastFaktorPPCModels,fromGetProgram);
            }
        };

    }




    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels,boolean fromGetProgram) {
        return RxDAOUtils.makeObservable(insertGroupCallable(dariaftPardakhtDarkhastFaktorPPCModels,fromGetProgram))
                .subscribeOn(Schedulers.io());
    }


}
