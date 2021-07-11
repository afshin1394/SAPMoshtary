package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ElatMarjoeeKalaDAO;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ElatMarjoeeKalaRepository {

    Context context;
    ElatMarjoeeKalaDAO elatMarjoeeKalaDAO;


    public ElatMarjoeeKalaRepository(Context context) {
        this.context = context;
        elatMarjoeeKalaDAO = new ElatMarjoeeKalaDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return elatMarjoeeKalaDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return elatMarjoeeKalaDAO.insertGroup(elatMarjoeeKalaModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(elatMarjoeeKalaModels))
                .subscribeOn(Schedulers.io());
    }
}
