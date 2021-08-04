package com.saphamrah.Network.RxNetwork;

import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.BaseResponse;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static io.reactivex.plugins.RxJavaPlugins.onError;
import static io.reactivex.plugins.RxJavaPlugins.setErrorHandler;

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
                Log.i("Responsewww", "onNext: " + d);
                callback.onStart(d);
            }

            @Override
            public void onNext(@NonNull Response<T> t) {
                Log.i("Responsewww", "onNext: " + t);


                if (t != null) {
                    if (t.code() >= HTTP_CLIENT_EXCEPTION && t.code() < 600)
                        onError(new Throwable(BaseApplication.getContext().getString(R.string.errorGetData), new Throwable(Constants.RETROFIT_EXCEPTION())));


                    Log.i("Responsewww", "onNext:" + t.raw());
                    if (t.raw().body() != null) {

                        long contentLength = t.raw().body().contentLength();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, ACTIVITY_NAME, FUNCTION, "onResponse");
                    }
                    if (t.code() == HTTP_BAD_REQUEST) {
                        onError(new Throwable(BaseApplication.getContext().getString(R.string.Http_client_error),new Throwable(Constants.HTTP_BAD_REQUEST())));
                    } else {

                        if (t.isSuccessful())
                            callback.onSuccess(t.body());
                    }
                }
                else {
                    onError(new Throwable(BaseApplication.getContext().getString(R.string.Http_internet_error), new Throwable(Constants.HTTP_NULL_RESPONSE())));
                }
            }

            /**
             * handles all possible errors
             * {@On_Error}
             */
            @Override
            public void onError(@NonNull Throwable e) {

                if (e!=null) {
                    Log.i("--HTTP_RX_REQUEST--", "onError: " + e.getMessage());

                    callback.onError(e.getMessage(), e.getCause().getMessage());
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s ", e.getMessage(), e.getCause().getMessage()), CLASS_NAME, ACTIVITY_NAME, FUNCTION, "onError");
                }
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

            observer.onError(new Throwable(BaseApplication.getContext().getString(R.string.Http_server_not_found), new Throwable(Constants.HTTP_WRONG_ENDPOINT())));
        }

        /**
         * step {@2} subscribe on observable
         * {@Observavle}
         */
        else {

            observable
                    .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, FUNCTION, FUNCTION))
                    .subscribeOn(Schedulers.io())
                    .retry(2)
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(TIME_OUT, TimeUnit.SECONDS)
                    .subscribe(observer);

        }


    }

    public <T extends BaseResponse> Observable executeParallel(ArrayList<Observable<Response<T>>> tasks, String ACTIVITY_NAME, String CLASS_NAME, String FUNCTION ) {

        return Observable.merge(tasks)
                .compose(this.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, FUNCTION))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Response<T>>() {
                    @Override
                    public void accept(Response<T> t) throws Exception {
                        if (t != null) {
                            if (t.code() >= HTTP_CLIENT_EXCEPTION && t.code() < 600)
                                onError(new Throwable(BaseApplication.getContext().getString(R.string.errorGetData), new Throwable(Constants.RETROFIT_EXCEPTION())));


                            Log.i("Responsewww", "onNext: " + t.raw());
                            if (t.raw().body() != null) {

                                long contentLength = t.raw().body().contentLength();
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, ACTIVITY_NAME, FUNCTION, "onResponse");
                            }
                            if (t.code() == HTTP_BAD_REQUEST) {
                                onError(new Throwable(Constants.HTTP_BAD_REQUEST()));
                            }
                        } else {
                            onError(new Throwable(BaseApplication.getContext().getString(R.string.Http_internet_error), new Throwable(Constants.HTTP_NULL_RESPONSE())));
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s ", e.getMessage(), e.getCause().getMessage()), CLASS_NAME, ACTIVITY_NAME, FUNCTION, "onError");
                    }
                });


    }



   public static  <T> ObservableTransformer<Response<T>, Response<T>> parseHttpErrors(String CLASS_NAME, String ACTIVITY_NAME, String FUNCTION) {

//        if (!isExecutable) {
//            onError(new Throwable(BaseApplication.getContext().getString(R.string.Http_server_not_found), new Throwable(Constants.HTTP_WRONG_ENDPOINT())));
//        }
      return upstream -> upstream
              .observeOn(AndroidSchedulers.mainThread())
              .doOnNext(new Consumer<Response<T>>() {
          @Override
          public void accept(Response<T> t) throws Exception {
              Log.i("Responsewww", "onNext: " + t.code() +"sop"+t.raw());
              if (t != null) {
                  if (t.code() >= HTTP_CLIENT_EXCEPTION && t.code() < 600)
                      throw  new Exception(BaseApplication.getContext().getString(R.string.errorGetData), new Throwable(Constants.RETROFIT_EXCEPTION()));


                  Log.i("Responsewww", "onNext: " + t.raw());
                  if (t.raw().body() != null) {

                      long contentLength = t.raw().body().contentLength();
                      PubFunc.Logger logger = new PubFunc().new Logger();
                      logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, ACTIVITY_NAME, FUNCTION, "onResponse");
                  }
                  if (t.code() == HTTP_BAD_REQUEST) {
                      //Todo
                      throw  new Exception(BaseApplication.getContext().getString(R.string.errorGetData), new Throwable(Constants.HTTP_BAD_REQUEST()));                  }
              } else {
                  throw  new Exception(BaseApplication.getContext().getString(R.string.errorGetData), new Throwable(Constants.HTTP_NULL_RESPONSE()));
              }
          }
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
