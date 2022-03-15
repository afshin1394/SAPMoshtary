package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.ElatAdamTahvilDarkhastModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TizerModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.ElatAdamTahvilDarkhastResult;
import com.saphamrah.WebService.ServiceResponse.GetTizeriResult;
import com.saphamrah.protos.ReasonNonDeliveryRequestGrpc;
import com.saphamrah.protos.ReasonNonDeliveryRequestReply;
import com.saphamrah.protos.ReasonNonDeliveryRequestReplyList;
import com.saphamrah.protos.ReasonNonDeliveryRequestRequest;
import com.saphamrah.protos.TizerGrpc;
import com.saphamrah.protos.TizerReply;
import com.saphamrah.protos.TizerReplyList;
import com.saphamrah.protos.TizerRequest;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.grpc.ManagedChannel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElatAdamTahvilDarkhastDAO
{

    ElatAdamTahvilDarkhastModel modelGetTABLE_NAME = new ElatAdamTahvilDarkhastModel();
    private DBHelper dbHelper;




    /*
    create constructor
     */
    public ElatAdamTahvilDarkhastDAO(Context context)
    {

        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ElatAdamTahvilDarkhastDAO" , "" , "constructor" , "");
        }
    }



    public void fetchElatAdamTahvilDarkhastGrpc(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ElatAdamTahvilDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamTahvilDarkhastGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                ReasonNonDeliveryRequestGrpc.ReasonNonDeliveryRequestBlockingStub blockingStub = ReasonNonDeliveryRequestGrpc.newBlockingStub(managedChannel);
                ReasonNonDeliveryRequestRequest request = ReasonNonDeliveryRequestRequest.newBuilder().build();

                Callable<ReasonNonDeliveryRequestReplyList> replyListCallable = () -> blockingStub.getReasonNonDeliveryRequest(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<ElatAdamTahvilDarkhastModel> models = new ArrayList<>();
                            for (ReasonNonDeliveryRequestReply reply : replyList.getReasonNonDeliveryRequestsList()) {
                                ElatAdamTahvilDarkhastModel model = new ElatAdamTahvilDarkhastModel();

                                model.setCcElatAdamTahvilDarkhast(reply.getReasonNonDeliveryRequestID());
                                model.setNameNoeVorod(reply.getEntryTypeName());

                                model.setCodeNoeVorod(reply.getEntryTypeCode());


                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<ElatAdamTahvilDarkhastModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<ElatAdamTahvilDarkhastModel> models) {
                                retrofitResponse.onSuccess(models);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                if (!compositeDisposable.isDisposed()) {
                                    compositeDisposable.dispose();
                                }
                                compositeDisposable.clear();
                            }
                        });

            }
        } catch (Exception exception) {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), ElatAdamTahvilDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchTizerGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }

    /*
    fetch = request server and get result
     */
    public void fetchElatAdamTahvilDarkhast(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ElatAdamTahvilDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamTahvilDarkhast", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);

        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<ElatAdamTahvilDarkhastResult> call = apiServiceGet.getElatTahvilDarkhast();
            call.enqueue(new Callback<ElatAdamTahvilDarkhastResult>()
            {
                @Override
                public void onResponse(Call<ElatAdamTahvilDarkhastResult> call, Response<ElatAdamTahvilDarkhastResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ElatAdamTahvilDarkhastDAO.class.getSimpleName(), "", "fetchElatAdamTahvilDarkhast", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            ElatAdamTahvilDarkhastResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ElatAdamTahvilDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamTahvilDarkhast", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = "";
                                try
                                {
                                    endpoint = call.request().url().toString();
                                    endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                                }catch (Exception e){e.printStackTrace();}
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), ElatAdamTahvilDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamTahvilDarkhast", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = "";
                            try
                            {
                                endpoint = call.request().url().toString();
                                endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                            }catch (Exception e){e.printStackTrace();}
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ElatAdamTahvilDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamTahvilDarkhast", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ElatAdamTahvilDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamTahvilDarkhast", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<ElatAdamTahvilDarkhastResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ElatAdamTahvilDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamTahvilDarkhast", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    /*
    get name all columns in model
     */
    private String[] allColumns()
    {
        return new String[]
        {
                modelGetTABLE_NAME.COLUMN_ccElatAdamTahvilDarkhast(),
                modelGetTABLE_NAME.COLUMN_CodeNoeVorod(),
                modelGetTABLE_NAME.COLUMN_NameNoeVorod()
        };
    }

    /*
    set result model to DB
     */
    public boolean insertGroup(ArrayList<ElatAdamTahvilDarkhastModel> models)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ElatAdamTahvilDarkhastModel model : models)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(modelGetTABLE_NAME.TABLE_NAME , null , contentValues);

            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            if (db.inTransaction())
            {
                db.endTransaction();
            }
            if (db.isOpen())
            {
                db.close();
            }
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorGroupInsert , modelGetTABLE_NAME.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ElatAdamTahvilDarkhastDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    // get all data as db
    public ArrayList<ElatAdamTahvilDarkhastModel> getAll()
    {
        ArrayList<ElatAdamTahvilDarkhastModel> models = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.TABLE_NAME, allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    models = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , modelGetTABLE_NAME.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ElatAdamTahvilDarkhastDAO" , "" , "getAll" , "");
        }
        return models;
    }




    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(modelGetTABLE_NAME.TABLE_NAME, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorDeleteAll , modelGetTABLE_NAME.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ElatAdamTahvilDarkhastDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ElatAdamTahvilDarkhastModel model)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(model.COLUMN_ccElatAdamTahvilDarkhast() , model.getCcElatAdamTahvilDarkhast());
        contentValues.put(model.COLUMN_CodeNoeVorod() , model.getCodeNoeVorod());
        contentValues.put(model.COLUMN_NameNoeVorod() , model.getNameNoeVorod());

        return contentValues;
    }


    /*
    set  cursor to model in get All
     */
    private ArrayList<ElatAdamTahvilDarkhastModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ElatAdamTahvilDarkhastModel> models = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ElatAdamTahvilDarkhastModel model = new ElatAdamTahvilDarkhastModel();

            model.setCcElatAdamTahvilDarkhast(cursor.getInt(cursor.getColumnIndex(model.COLUMN_ccElatAdamTahvilDarkhast())));
            model.setCodeNoeVorod(cursor.getInt(cursor.getColumnIndex(model.COLUMN_CodeNoeVorod())));
            model.setNameNoeVorod(cursor.getString(cursor.getColumnIndex(model.COLUMN_NameNoeVorod())));
            models.add(model);
            cursor.moveToNext();
        }
        return models;
    }



}
