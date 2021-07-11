package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.TaghvimTatilDAO;
import com.saphamrah.Model.TaghvimTatilModel;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TaghvimTatilRepository {
    Context context;
    TaghvimTatilDAO taghvimTatilDAO;

    public TaghvimTatilRepository(Context context) {
        this.context = context;
        taghvimTatilDAO = new TaghvimTatilDAO(context);
    }



    /*******************************************************************Callable*****************************************************************/

    private Callable<Boolean> deleteAllCallable(){
        return () -> taghvimTatilDAO.deleteAll();

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<TaghvimTatilModel> taghvimTatilModels) {
        return () -> taghvimTatilDAO.insertGroup(taghvimTatilModels);

    }

    /*******************************************************************Observable*****************************************************************/

    public Observable<Boolean> deleteAll(){
        return RxDAOUtils.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertGroup(ArrayList<TaghvimTatilModel> taghvimTatilModels) {
        return RxDAOUtils.makeObservable(insertGroupCallable(taghvimTatilModels))
                .subscribeOn(Schedulers.io());
    }
}
