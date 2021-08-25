package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MasirVaznHajmMashinDAO;
import com.saphamrah.Model.MasirVaznHajmMashinModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MasirVaznHajmMashinRepository {
    Context context;
    MasirVaznHajmMashinDAO masirVaznHajmMashinDAO;


    public MasirVaznHajmMashinRepository(Context context) {
        this.context = context;
        masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return masirVaznHajmMashinDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MasirVaznHajmMashinModel> masirVaznHajmMashinModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return masirVaznHajmMashinDAO.insertGroup(masirVaznHajmMashinModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MasirVaznHajmMashinModel> masirVaznHajmMashinModels) {
        return RxAsync.makeObservable(insertGroupCallable(masirVaznHajmMashinModels))
                .subscribeOn(Schedulers.io());
    }
}
