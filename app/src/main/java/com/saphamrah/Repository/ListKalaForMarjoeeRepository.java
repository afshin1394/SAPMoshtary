package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ListKalaForMarjoeeDAO;
import com.saphamrah.Model.ListKalaForMarjoeeModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ListKalaForMarjoeeRepository {

    Context context;
    ListKalaForMarjoeeDAO listKalaForMarjoeeDAO;


    public ListKalaForMarjoeeRepository(Context context) {
        this.context = context;
        listKalaForMarjoeeDAO = new ListKalaForMarjoeeDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return listKalaForMarjoeeDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return listKalaForMarjoeeDAO.insertGroup(listKalaForMarjoeeModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModels) {
        return RxAsync.makeObservable(insertGroupCallable(listKalaForMarjoeeModels))
                .subscribeOn(Schedulers.io());
    }
}
