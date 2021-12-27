package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.KalaOlaviatModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetKalaOlaviatResult;
import com.saphamrah.protos.GoodPriorityGrpc;
import com.saphamrah.protos.GoodPriorityReply;
import com.saphamrah.protos.GoodPriorityReplyList;
import com.saphamrah.protos.GoodPriorityRequest;

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

public class KalaOlaviatDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public KalaOlaviatDAO(Context context)
    {
        this.context = context;
        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaOlaviatDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
                {
                        KalaOlaviatModel.COLUMN_ccKalaCode(),
                        KalaOlaviatModel.COLUMN_Olaviat()
                };


    }


    public void fetchKalaOlaviatGrpc(final Context context, final String activityNameForLog, final String ccAnbarak, final RetrofitResponse retrofitResponse) {

        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
//        serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviatGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                GoodPriorityGrpc.GoodPriorityBlockingStub goodPriorityBlockingStub = GoodPriorityGrpc.newBlockingStub(managedChannel);
                GoodPriorityRequest goodPriorityRequest = GoodPriorityRequest.newBuilder().setBinID(Integer.parseInt(ccAnbarak)).build();

                Callable<GoodPriorityReplyList> goodPriorityReplyListCallable = () -> goodPriorityBlockingStub.getGoodPriority(goodPriorityRequest);
                RxAsync.makeObservable(goodPriorityReplyListCallable)

                        .map(goodPriorityReplyList -> {
                            ArrayList<KalaOlaviatModel> kalaGorohModels = new ArrayList<>();
                            for (GoodPriorityReply goodPriorityReply : goodPriorityReplyList.getGoodPrioritiesList()) {
                                KalaOlaviatModel kalaOlaviatModel = new KalaOlaviatModel();
                                kalaOlaviatModel.setCcKalaCode(goodPriorityReply.getGoodCodeID());
                                kalaOlaviatModel.setOlaviat(goodPriorityReply.getPriority());

                                kalaGorohModels.add(kalaOlaviatModel);
                            }

                            return kalaGorohModels;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<KalaOlaviatModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<KalaOlaviatModel> kalaOlaviatModels) {
                                retrofitResponse.onSuccess(kalaOlaviatModels);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                e.printStackTrace();
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviatGrpc", "CatchException");
                                retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                if (!managedChannel.isShutdown()) {
                                    managedChannel.shutdown();
                                }
                                if (!compositeDisposable.isDisposed()) {
                                    compositeDisposable.dispose();
                                }
                                compositeDisposable.clear();
                            }
                        });

            }
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviatGrpc", "CatchException");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }

    public void fetchKalaOlaviat(final Context context, final String activityNameForLog, final String ccAnbarak, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetKalaOlaviatResult> call = apiServiceGet.getKalaOlaviat(ccAnbarak);
            call.enqueue(new Callback<GetKalaOlaviatResult>() {
                @Override
                public void onResponse(Call<GetKalaOlaviatResult> call, Response<GetKalaOlaviatResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaOlaviatDAO.class.getSimpleName(), "", "fetchKalaOlaviat", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetKalaOlaviatResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetKalaOlaviatResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    private String getEndpoint(Call call)
    {
        String endpoint = "";
        try
        {
            endpoint = call.request().url().toString();
            endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
        }
        catch (Exception e)
        {e.printStackTrace();}
        return endpoint;
    }

    public boolean insertGroup(ArrayList<KalaOlaviatModel> kalaOlaviatModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (KalaOlaviatModel kalaOlaviatModel : kalaOlaviatModels)
            {
                ContentValues contentValues = modelToContentvalue(kalaOlaviatModel);
                db.insertOrThrow(KalaOlaviatModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , KalaOlaviatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaOlaviatDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<KalaOlaviatModel> getAll()
    {
        ArrayList<KalaOlaviatModel> kalaOlaviatModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaOlaviatModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaOlaviatModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaOlaviatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaOlaviatDAO" , "" , "getAll" , "");
        }
        return kalaOlaviatModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KalaOlaviatModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KalaOlaviatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaOlaviatDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(KalaOlaviatModel kalaOlaviatModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(KalaOlaviatModel.COLUMN_ccKalaCode() , kalaOlaviatModel.getCcKalaCode());
        contentValues.put(KalaOlaviatModel.COLUMN_Olaviat() , kalaOlaviatModel.getOlaviat());

        return contentValues;
    }


    private ArrayList<KalaOlaviatModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaOlaviatModel> kalaOlaviatModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaOlaviatModel kalaOlaviatModel = new KalaOlaviatModel();

            kalaOlaviatModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaOlaviatModel.COLUMN_ccKalaCode())));
            kalaOlaviatModel.setOlaviat(cursor.getInt(cursor.getColumnIndex(KalaOlaviatModel.COLUMN_Olaviat())));

            kalaOlaviatModels.add(kalaOlaviatModel);
            cursor.moveToNext();
        }
        return kalaOlaviatModels;
    }
    
}
