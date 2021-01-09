package com.saphamrah.Network.RxNetwork;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.BaseResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * designed for all single api calls
 * {@by_ROUGEN
 */


public class RxHttpRequest {


    public static final int TIME_OUT = 10;
    /**
     * millis
     **/
    public static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_CLIENT_EXCEPTION = 400;
    private static final int HTTP_SERVER_EXCEPTION = 500;
    private static final int HTTP_NOT_FOUND_EXCEPTION = 404;



    private static RxHttpRequest instance = null;

    public static RxHttpRequest getInstance() {
        if (instance == null) {
            instance = new RxHttpRequest();
        }

        return instance;
    }

    private boolean isExecutable = false;
    private int retryCount = 0;


    private APIServiceRxjava api;

    public APIServiceRxjava getApiRx(ServerIpModel serverIpModel) {
        isExecutable = (!serverIpModel.getServerIp().trim().equals("") || !serverIpModel.getPort().trim().equals(""));

        if (!isExecutable) {
            serverIpModel.setServerIp("invalid");
            serverIpModel.setPort("80");
        }

        api = ApiClientGlobal.getInstance().getApiServiceRxjava(serverIpModel);
        return api;
    }


    public <T extends BaseResponse> void execute(Observable<Response<T>> observable, String ACTIVITY_NAME, String CLASS_NAME, String FUNCTION, final RxCallback<T> callback) {
        /**
         * step {@0} create observer
         * {@Observer}
         */

        Observer observer = new Observer<Response<T>>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i("Responsewww", "onNext: "+d);
                callback.onStart(d);
            }

            @Override
            public void onNext(@NonNull Response<T> t) {
                Log.i("Responsewww", "onNext: "+t);


                if (t != null) {
                    Log.i("Responsewww", "onNext: "+t.raw());
                    if(t.code()>=400 && t.code()<600){
                     onError(new Throwable(t.code() +" - "+ BaseApplication.getContext().getString(R.string.errorGetData),new Throwable(Constants.HTTP_ERROR())));
                    }
                    if (t.raw().body() != null) {

                        long contentLength = t.raw().body().contentLength();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, ACTIVITY_NAME, FUNCTION, "onResponse");
                    }
                    if (t.code() == HTTP_BAD_REQUEST) {
                          onError(new Throwable(Constants.HTTP_BAD_REQUEST()));
                    } else {

                        if (t.isSuccessful())
                            callback.onSuccess(t.body());
                    }
                }else {
                    onError(new Throwable(BaseApplication.getContext().getString(R.string.Http_internet_error),new Throwable(Constants.HTTP_NULL_RESPONSE())));
                }
            }

            /**
             * handles all possible errors
             * {@On_Error}
             */
            @Override
            public void onError(@NonNull Throwable e) {
                Log.i("--HTTP_RX_REQUEST--", "onError: " + e.getMessage());

                callback.onError(e.getMessage(),e.getCause().getMessage());
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s ", e.getMessage(),e.getCause().getMessage()), CLASS_NAME, ACTIVITY_NAME, FUNCTION, "onError");
            }

            /**
             * handles
             * {@On_Complete}
             */
            @Override
            public void onComplete() {

            }
        };

        /**
         * step {@1} check if baseUrl is appropriate
         * {@BaseURL}
         */
        if (!isExecutable) {

            observer.onError(new Throwable(BaseApplication.getContext().getString(R.string.Http_server_not_found),new Throwable(Constants.HTTP_WRONG_ENDPOINT())));
        }

        /**
         * step {@2} subscribe on observable
         * {@Observavle}
         */
        else {

            observable
                    //.compose(this.parseHttpErrors())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(TIME_OUT, TimeUnit.SECONDS)
                    .subscribe(observer)

            ;
        }


    }


    @SuppressLint("CheckResult")
    <T> ObservableTransformer<T, T> parseHttpErrors() {
        return observable -> observable.onErrorResumeNext((Function<Throwable, ObservableSource<? extends T>>) throwable -> {
            Log.i("--HTTP_RX_REQUEST--", "parseHttpError " + " " + throwable.getClass().getName());
            Log.i("--HTTP_RX_REQUEST--", "parseHttpError " + " " + throwable.getMessage().trim());
            if (throwable instanceof java.net.ConnectException) {

                return observable.retryWhen(new RetryWithDelay());

            } else if (throwable.getMessage().trim().equals(Constants.HTTP_WRONG_ENDPOINT()) | throwable.getMessage().trim().equals(Constants.HTTP_BAD_REQUEST())) {
                if (throwable.getMessage().trim().equals(Constants.HTTP_BAD_REQUEST())) {
                    Log.i("--HTTP_RX_REQUEST--", "2parseHttpError " + " " );

                    return Observable.error(new Throwable(BaseApplication.getContext().getString(R.string.Http_internet_error), new Throwable(Constants.HTTP_BAD_REQUEST())));
                }
                else {
                    Log.i("--HTTP_RX_REQUEST--", "3parseHttpError " + " " );
                    return Observable.error(new Throwable(BaseApplication.getContext().getString(R.string.Http_client_error), new Throwable(Constants.HTTP_BAD_REQUEST())));
                }

            } else if (throwable instanceof TimeoutException) {
                Log.i("--HTTP_RX_REQUEST--", "4parseHttpError " + " " );
                return Observable.error(new Throwable(BaseApplication.getContext().getString(R.string.Http_internet_quality), new Throwable(Constants.HTTP_TIME_OUT_EXCEPTION())));

            } else if (throwable instanceof HttpException) {

                Log.i("--HTTP_RX_REQUEST--", " HttpException " + ((HttpException) throwable).response().code());
                Gson gson = new Gson();
                HttpException generalApiException = null;
                try {
                    generalApiException = gson.fromJson(((HttpException) throwable).response().errorBody().string(), HttpException.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (generalApiException.code() < HTTP_SERVER_EXCEPTION && generalApiException.code() > HTTP_CLIENT_EXCEPTION) {
                    if (generalApiException.code() == HTTP_NOT_FOUND_EXCEPTION) {
                        return Observable.error(new Throwable(BaseApplication.getContext().getString(R.string.Http_client_error), new Throwable(Constants.HTTP_RESOURCE_NOT_FOUND())));
                    } else {
                        return observable.retryWhen(new RetryWithDelay());
                    }

                } else if (generalApiException.code() >= HTTP_SERVER_EXCEPTION) {
                    return Observable.error(new Throwable(BaseApplication.getContext().getString(R.string.Http_internal_server_error), new Throwable(Constants.HTTP_INTERNAL_SERVER_ERROR())));
                }

            } else {
                Log.i("--HTTP_RX_REQUEST--", "parseHttpErrors:8");
                return Observable.error(throwable);
            }

            /** if not the kind we're interested in, then just report the same error to onError()**/
            return Observable.error(throwable);
        });

    }
}


class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {

    private int retryCount;
    private static final int MAX_RETRIES = 3;
    private static final int DELAY = 2000;

    public RetryWithDelay() {

        this.retryCount = 0;
    }

    @Override
    public Observable<?> apply(final Observable<? extends Throwable> attempts) {
        return attempts
                .flatMap(new Function<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> apply(final Throwable throwable) {
                        if (++retryCount < MAX_RETRIES) {
                            // When this Observable calls onNext, the original
                            /** Observable will be retried (i.e. re-subscribed).**/
                            Log.i("--HTTP_RX_REQUEST--", "--RETRY--");
                            return Observable.timer(DELAY,
                                    TimeUnit.MILLISECONDS);
                        }

                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    }
                });
    }
}