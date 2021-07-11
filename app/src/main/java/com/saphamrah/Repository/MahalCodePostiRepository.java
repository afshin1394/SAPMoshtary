package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.MahalCodePostiDAO;
import com.saphamrah.Model.MahalCodePostiModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MahalCodePostiRepository {
    Context context;
    MahalCodePostiDAO mahalCodePostiDAO;


    public MahalCodePostiRepository(Context context) {
        this.context = context;
        mahalCodePostiDAO = new MahalCodePostiDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> mahalCodePostiDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<MahalCodePostiModel> mahalCodePostiModels) {
        return () -> mahalCodePostiDAO.insertGroup(mahalCodePostiModels);

    }



    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<MahalCodePostiModel> mahalCodePostiModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(mahalCodePostiModels))
                .subscribeOn(Schedulers.io());
    }
}
