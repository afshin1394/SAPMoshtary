package com.saphamrah.Utils.RxUtils;


import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class RxAsync {


    private static final String TAG = "RxAsync";

    private static RxAsync instance = null;

    public static RxAsync getInstance() {
        if (instance == null) {
            instance = new RxAsync();
        }

        return instance;
    }

    public RxAsync() {
    }

    public static <T> Observable<T> makeObservable(final Callable<T> func) {

        return new Observable<T>() {
            @Override
            protected void subscribeActual(Observer<? super T> observer) {
                try {
                    observer.onNext(func.call());

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "Error transaction from the database");

                }
            }
        };
    }

    public static <T> Observable<T> mergeRequests(ArrayList<Observable<T>> tasks, int concurrency) {
        return Observable.merge(tasks, concurrency)
                .subscribeOn(Schedulers.io());
    }

    public static ObservableTransformer<Boolean, Boolean> parseSQlErrors(String CLASS_NAME, String FUNCTION_NAME, String TABLE_NAME, String QUERY_TYPE) {
        return upstream -> upstream
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext((Consumer<Boolean>) aBoolean -> {
                    if (aBoolean.equals(false)){
                        PubFunc.Logger logger   = new PubFunc().new Logger();
                        String message = String.format("error body : %1$s , table : %2$s , query : %3$s", BaseApplication.getContext().getResources().getString(R.string.errorUpdate),TABLE_NAME,QUERY_TYPE);
                        logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(),message,CLASS_NAME,"",TABLE_NAME,QUERY_TYPE);
                        throw new Exception();
                    }
                });

    }
}


