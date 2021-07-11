package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MandehMojodyMashinDAO;
import com.saphamrah.Model.MandehMojodyMashinModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MandehMojodyMashinRepository {
    Context context;
    MandehMojodyMashinDAO mandehMojodyMashinDAO;


    public MandehMojodyMashinRepository(Context context) {
        this.context = context;
        mandehMojodyMashinDAO = new MandehMojodyMashinDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> mandehMojodyMashinDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels) {
        return () -> mandehMojodyMashinDAO.insertGroup(mandehMojodyMashinModels);

    }

    private Callable<ArrayList<MandehMojodyMashinModel>> getAllCallable() {
        return () -> mandehMojodyMashinDAO.getAll();

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(mandehMojodyMashinModels))
                .subscribeOn(Schedulers.io());
    }

    public Observable<ArrayList<MandehMojodyMashinModel>> getAll() {
        return RxDAOUtils.makeObservable(getAllCallable())
                .subscribeOn(Schedulers.io());
    }
}
