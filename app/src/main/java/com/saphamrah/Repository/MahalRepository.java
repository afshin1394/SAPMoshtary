package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MahalDAO;
import com.saphamrah.Model.MahalModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MahalRepository {

    Context context;
    MahalDAO mahalDAO;


    public MahalRepository(Context context) {
        this.context = context;
        mahalDAO = new MahalDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mahalDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MahalModel> mahalModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mahalDAO.insertGroup(mahalModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<MahalModel> mahalModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(mahalModels))
                .subscribeOn(Schedulers.io());
    }
}
