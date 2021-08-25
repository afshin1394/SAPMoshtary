package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MojoodiGiriDAO;
import com.saphamrah.Model.MojoodiGiriModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MojoodiGiriRepository {
    Context context;
    MojoodiGiriDAO mojodiGiriDAO;


    public MojoodiGiriRepository(Context context) {
        this.context = context;
        mojodiGiriDAO = new MojoodiGiriDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mojodiGiriDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MojoodiGiriModel> mojoodiGiriModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mojodiGiriDAO.insertGroup(mojoodiGiriModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MojoodiGiriModel> mojoodiGiriModels) {
        return RxAsync.makeObservable(insertGroupCallable(mojoodiGiriModels))
                .subscribeOn(Schedulers.io());
    }
}
