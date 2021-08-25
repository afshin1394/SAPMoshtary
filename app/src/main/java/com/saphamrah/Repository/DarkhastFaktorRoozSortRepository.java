package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DarkhastFaktorRoozSortDAO;
import com.saphamrah.Model.DarkhastFaktorRoozSortModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DarkhastFaktorRoozSortRepository {
    Context context;
    DarkhastFaktorRoozSortDAO darkhastFaktorRoozSortDAO;


    public DarkhastFaktorRoozSortRepository(Context context) {
        this.context = context;
        darkhastFaktorRoozSortDAO = new DarkhastFaktorRoozSortDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> darkhastFaktorRoozSortDAO.deleteAll();

    }
    private Callable<Boolean> insertCallable(DarkhastFaktorRoozSortModel darkhastFaktorRoozSortModel) {
        return () -> darkhastFaktorRoozSortDAO.insert(darkhastFaktorRoozSortModel);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insert(DarkhastFaktorRoozSortModel darkhastFaktorRoozSortModel) {
        return RxAsync.makeObservable(insertCallable(darkhastFaktorRoozSortModel))
                .subscribeOn(Schedulers.io());
    }
}
