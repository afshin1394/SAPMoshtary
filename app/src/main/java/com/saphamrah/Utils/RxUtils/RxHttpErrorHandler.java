package com.saphamrah.Utils.RxUtils;

import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

public class RxHttpErrorHandler {
    private static <T> void insertLogToDatabase(Response<T> t, String errorBody, String CLASS_NAME, String ACTIVITY_NAME, String FUNCTION, String FUNCTION_CHILD) {
        String messageLogger = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), "Exception in Rxjava api call , type:" + messageLogger, CLASS_NAME, ACTIVITY_NAME, FUNCTION, FUNCTION_CHILD);
    }


    public static <T > ObservableTransformer<Response<T>, Response<T>> parseHttpErrors(String CLASS_NAME, String ACTIVITY_NAME, String FUNCTION, String FUNCTION_CHILD) {
        return upstream -> upstream
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(Observable.empty())
                .doOnNext((Consumer<Response<T>>) t -> {
                    Log.i("ObservableTransformer", "doOnNext: code" + t.code() + "body" + t.raw().body());
                    String errorBody = "";
                    String message = "";

                    if (t != null)
                    {
                        RxProperties.HttpErrorCode httpErrorCode = RxProperties.VerifyHttpError(t);
                        if (!httpErrorCode.equals(RxProperties.HttpErrorCode.Success))
                        {

                            switch (httpErrorCode) {


                                case Bad_Gateway:
                                    errorBody = "Bad_Gateway";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;

                                case BadRequest:
                                    errorBody = "BAD_REQUEST";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Forbidden:
                                    errorBody = "Forbidden";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Internal_Server_Error:
                                    errorBody = "Internal_Server_Error";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Not_Found:
                                    errorBody = "Not_Found";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Service_Unavailable:
                                    errorBody = "Service_Unavailable";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Gateway_Timeout:
                                    errorBody = "Gateway_Timeout";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Unauthorized:
                                    errorBody = "Unauthorized";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;

                                case Unknown:
                                    errorBody = "NullData";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;
                            }
                            insertLogToDatabase(t, errorBody, CLASS_NAME, ACTIVITY_NAME, FUNCTION, FUNCTION_CHILD);
                            throw new Exception(new Throwable(message, new Throwable(t.raw().request().url().toString())));


                        }

                    }
                    else
                    {
                        errorBody = "null-response";
                        message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                        insertLogToDatabase(t, errorBody, CLASS_NAME, ACTIVITY_NAME, FUNCTION, FUNCTION_CHILD);
                        throw new Exception(new Throwable(message, new Throwable(t.raw().request().url().toString())));
                    }
                }).retryWhen(new RetryWithDelay(3, 2000));


    }


}
