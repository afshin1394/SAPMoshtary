package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.TaghiratVersionPPCDAO;
import com.saphamrah.Model.TaghiratVersionPPCModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TaghiratVersionPPCRepository {
    Context context;
    TaghiratVersionPPCDAO taghiratVersionPPCDAO;


    public TaghiratVersionPPCRepository(Context context) {
        this.context = context;
        taghiratVersionPPCDAO = new TaghiratVersionPPCDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return () -> taghiratVersionPPCDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<TaghiratVersionPPCModel> taghiratVersionPPCModel) {
        return () -> taghiratVersionPPCDAO.insertGroup(taghiratVersionPPCModel);

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<TaghiratVersionPPCModel> taghiratVersionPPCModel) {
        return RxDAOUtils.makeObservable(insertGroupCallable(taghiratVersionPPCModel))
                .subscribeOn(Schedulers.io());
    }
}
