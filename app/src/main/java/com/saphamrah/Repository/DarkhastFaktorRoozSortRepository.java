package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.DarkhastFaktorRoozSortDAO;
import com.saphamrah.Model.DarkhastFaktorRoozSortModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

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
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insert(DarkhastFaktorRoozSortModel darkhastFaktorRoozSortModel) {
        return RxDAOUtils.makeObservable(insertCallable(darkhastFaktorRoozSortModel))
                .subscribeOn(Schedulers.io());
    }
}
