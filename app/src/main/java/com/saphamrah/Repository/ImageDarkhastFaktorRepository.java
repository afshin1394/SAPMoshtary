package com.saphamrah.Repository;

import android.content.Context;

import com.saphamrah.DAO.ImageDarkhastFaktorDAO;
import com.saphamrah.Model.ImageDarkhastFaktorModel;
import com.saphamrah.Utils.RxUtils.RxAsync;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ImageDarkhastFaktorRepository {
    Context context;
    ImageDarkhastFaktorDAO imageDarkhastFaktorDAO;


    public ImageDarkhastFaktorRepository(Context context) {
        this.context = context;
        imageDarkhastFaktorDAO = new ImageDarkhastFaktorDAO(context);
    }




    /*******************************************************************Callable*****************************************************************/
    private Callable<Boolean> deleteAllCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return imageDarkhastFaktorDAO.deleteAll();
            }
        };

    }
    private Callable<Boolean> insertGroupCallable(ArrayList<ImageDarkhastFaktorModel> imageDarkhastFaktorModels) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return imageDarkhastFaktorDAO.insertGroup(imageDarkhastFaktorModels);
            }
        };

    }

    /*******************************************************************Observable*****************************************************************/
    public Observable<Boolean> deleteAll() {
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }



    public Observable<Boolean> insertGroup(ArrayList<ImageDarkhastFaktorModel> imageDarkhastFaktorModels) {
        return RxAsync.makeObservable(insertGroupCallable(imageDarkhastFaktorModels))
                .subscribeOn(Schedulers.io());
    }

}
