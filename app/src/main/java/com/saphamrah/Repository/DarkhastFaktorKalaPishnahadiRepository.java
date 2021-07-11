package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DarkhastFaktorKalaPishnahadiDAO;
import com.saphamrah.Model.DarkhastFaktorKalaPishnahadiModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DarkhastFaktorKalaPishnahadiRepository {


    Context context;
    DarkhastFaktorKalaPishnahadiDAO darkhastFaktorKalaPishnahadiDAO;


    public DarkhastFaktorKalaPishnahadiRepository(Context context) {
        this.context = context;
        darkhastFaktorKalaPishnahadiDAO = new DarkhastFaktorKalaPishnahadiDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return darkhastFaktorKalaPishnahadiDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<DarkhastFaktorKalaPishnahadiModel> darkhastFaktorKalaPishnahadiModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return darkhastFaktorKalaPishnahadiDAO.insertGroup(darkhastFaktorKalaPishnahadiModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<DarkhastFaktorKalaPishnahadiModel> darkhastFaktorKalaPishnahadiModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(darkhastFaktorKalaPishnahadiModels))
                .subscribeOn(Schedulers.io());
    }
}
