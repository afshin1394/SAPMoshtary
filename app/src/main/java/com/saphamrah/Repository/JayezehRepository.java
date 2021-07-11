package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.JayezehDAO;
import com.saphamrah.Model.JayezehModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class JayezehRepository {
    Context context;
    JayezehDAO jayezehDAO;


    public JayezehRepository(Context context) {
        this.context = context;
        jayezehDAO = new JayezehDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return jayezehDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<JayezehModel> jayezehModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                return jayezehDAO.insertGroup(jayezehModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<JayezehModel> jayezehModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(jayezehModels))
                .subscribeOn(Schedulers.io());
    }
}
