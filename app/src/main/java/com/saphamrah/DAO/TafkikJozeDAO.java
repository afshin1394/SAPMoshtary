package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TafkikJozeModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetTafkikJozePakhshResult;
import com.saphamrah.protos.PartialSeparationGrpc;
import com.saphamrah.protos.PartialSeparationReply;
import com.saphamrah.protos.PartialSeparationReplyList;
import com.saphamrah.protos.PartialSeparationRequest;

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

public class TafkikJozeDAO
{

    private DBHelper dbHelper;
    private Context context;
    private final String CLASS_NAME = "TafkikJozeDAO";

    public TafkikJozeDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
                {
                        TafkikJozeModel.COLUMN_ccTafkikJoze(),
                        TafkikJozeModel.COLUMN_ShomarehTafkikJoze()
                };
    }

    public void fetchTafkikJozePakhshGrpc(final Context context, final String activityNameForLog, String ccDarkhastFaktors, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            //       ServerIpModel serverIpModel = new ServerIpModel();
            //       serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TafkikJozeDAO.class.getSimpleName(), activityNameForLog, "fetchTafkikJozePakhshGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                PartialSeparationGrpc.PartialSeparationBlockingStub partialSeparationBlockingStub = PartialSeparationGrpc.newBlockingStub(managedChannel);
                PartialSeparationRequest partialSeparationRequest = PartialSeparationRequest.newBuilder().setInvoiceRequestsID(ccDarkhastFaktors).build();
                Callable<PartialSeparationReplyList> partialSeparationReplyListCallable = () -> partialSeparationBlockingStub.getPartialSeparation(partialSeparationRequest);
                RxAsync.makeObservable(partialSeparationReplyListCallable)
                        .map(partialSeparationReplyList ->  {
                            ArrayList<TafkikJozeModel> models = new ArrayList<>();
                            for (PartialSeparationReply reply : partialSeparationReplyList.getPartialSeparationsList()) {
                                TafkikJozeModel model = new TafkikJozeModel();
                                model.setCcTafkikJoze(reply.getPartialSeparationID());
                                model.setShomarehTafkikJoze(reply.getPartialSeparationNumber());
                                models.add(model);
                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<TafkikJozeModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<TafkikJozeModel> rptSanadModels) {
                                retrofitResponse.onSuccess(rptSanadModels);
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
        }catch (Exception exception){
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), TafkikJozeDAO.class.getSimpleName(), activityNameForLog, "fetchTafkikJozePakhshGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }


    public void fetchTafkikJozePakhsh(final Context context, final String activityNameForLog, String ccDarkhastFaktors, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchTafkikJozePakhsh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetTafkikJozePakhshResult> call = apiServiceGet.getTafkikJozePakhsh(ccDarkhastFaktors);
            call.enqueue(new Callback<GetTafkikJozePakhshResult>() {
                @Override
                public void onResponse(Call<GetTafkikJozePakhshResult> call, Response<GetTafkikJozePakhshResult> response)
                {
                    /*try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchTafkikJozePakhsh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}*/
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetTafkikJozePakhshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchTafkikJozePakhsh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchTafkikJozePakhsh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchTafkikJozePakhsh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchTafkikJozePakhsh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetTafkikJozePakhshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchTafkikJozePakhsh", "onFailure");
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

    public boolean insertGroup(ArrayList<TafkikJozeModel> tafkikJozeModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (TafkikJozeModel tafkikJozeModel : tafkikJozeModels)
            {
                ContentValues contentValues = modelToContentvalue(tafkikJozeModel);
                db.insertOrThrow(TafkikJozeModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            if (db.inTransaction())
            {
                db.endTransaction();
            }
            if (db.isOpen())
            {
                db.close();
            }
            String message = context.getResources().getString(R.string.errorGroupInsert , TafkikJozeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<TafkikJozeModel> getAll()
    {
        ArrayList<TafkikJozeModel> tafkikJozeModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TafkikJozeModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    tafkikJozeModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TafkikJozeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return tafkikJozeModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TafkikJozeModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , TafkikJozeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(TafkikJozeModel tafkikJozeModel)
    {
        ContentValues contentValues = new ContentValues();

        if (tafkikJozeModel.getCcTafkikJoze() > 0)
        {
            contentValues.put(TafkikJozeModel.COLUMN_ccTafkikJoze() , tafkikJozeModel.getCcTafkikJoze());
        }
        contentValues.put(TafkikJozeModel.COLUMN_ShomarehTafkikJoze() , tafkikJozeModel.getShomarehTafkikJoze());

        return contentValues;
    }


    private ArrayList<TafkikJozeModel> cursorToModel(Cursor cursor)
    {
        ArrayList<TafkikJozeModel> tafkikJozeModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TafkikJozeModel tafkikJozeModel = new TafkikJozeModel();

            tafkikJozeModel.setCcTafkikJoze(cursor.getLong(cursor.getColumnIndex(TafkikJozeModel.COLUMN_ccTafkikJoze())));
            tafkikJozeModel.setShomarehTafkikJoze(cursor.getInt(cursor.getColumnIndex(TafkikJozeModel.COLUMN_ShomarehTafkikJoze())));

            tafkikJozeModels.add(tafkikJozeModel);
            cursor.moveToNext();
        }
        return tafkikJozeModels;
    }

}
