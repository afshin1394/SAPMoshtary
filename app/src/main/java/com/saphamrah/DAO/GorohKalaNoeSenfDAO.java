package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.GorohKalaNoeSenfModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllGorohKalaNoeSenfResult;
import com.saphamrah.protos.GuildTypeGoodGroupGrpc;
import com.saphamrah.protos.GuildTypeGoodGroupReply;
import com.saphamrah.protos.GuildTypeGoodGroupReplyList;
import com.saphamrah.protos.GuildTypeGoodGroupRequest;

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

public class GorohKalaNoeSenfDAO
{

    private DBHelper dbHelper;
    private Context context;


    public GorohKalaNoeSenfDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "GorohKalaNoeSenfDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            GorohKalaNoeSenfModel.COLUMN_ccGorohKalaNoeSenf(),
            GorohKalaNoeSenfModel.COLUMN_ccNoeSenf(),
            GorohKalaNoeSenfModel.COLUMN_ccGorohKala()
        };
    }

    public void fetchAllGorohKalaNoeSenfGrpc(final Context context, final String activityNameForLog , final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, GorohKalaNoeSenfDAO.class.getSimpleName(), activityNameForLog, "fetchConfigNoeVosolMojazeFaktorGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                GuildTypeGoodGroupGrpc.GuildTypeGoodGroupBlockingStub blockingStub = GuildTypeGoodGroupGrpc.newBlockingStub(managedChannel);
                GuildTypeGoodGroupRequest request = GuildTypeGoodGroupRequest.newBuilder().build();

                Callable<GuildTypeGoodGroupReplyList> replyListCallable  = () -> blockingStub.getGuildTypeGoodGroup(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<GorohKalaNoeSenfModel> models = new ArrayList<>();
                            for (GuildTypeGoodGroupReply reply : replyList.getGuildTypeGoodGroupsList()) {
                                GorohKalaNoeSenfModel model = new GorohKalaNoeSenfModel();

                                model.setCcGorohKalaNoeSenf(reply.getGuildTypeGoodGroupID());
                                model.setCcNoeSenf(reply.getGuildTypeID());
                                model.setCcGorohKala(reply.getGoodGroupID());


                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<GorohKalaNoeSenfModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<GorohKalaNoeSenfModel> models) {
                                retrofitResponse.onSuccess(models);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(),e.getMessage());
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), GorohKalaNoeSenfDAO.class.getSimpleName(), activityNameForLog, "fetchConfigNoeVosolMojazeFaktorGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }




    }

    public void fetchAllGorohKalaNoeSenf(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, GorohKalaNoeSenfDAO.class.getSimpleName(), activityNameForLog, "fetchAllGorohKalaNoeSenf", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllGorohKalaNoeSenfResult> call = apiServiceGet.getAllGorohKalaNoeSenf();
            call.enqueue(new Callback<GetAllGorohKalaNoeSenfResult>() {
                @Override
                public void onResponse(Call<GetAllGorohKalaNoeSenfResult> call, Response<GetAllGorohKalaNoeSenfResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, GorohKalaNoeSenfDAO.class.getSimpleName(), "", "fetchAllGorohKalaNoeSenf", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllGorohKalaNoeSenfResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), GorohKalaNoeSenfDAO.class.getSimpleName(), activityNameForLog, "fetchAllGorohKalaNoeSenf", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), GorohKalaNoeSenfDAO.class.getSimpleName(), activityNameForLog, "fetchAllGorohKalaNoeSenf", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, GorohKalaNoeSenfDAO.class.getSimpleName(), activityNameForLog, "fetchAllGorohKalaNoeSenf", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), GorohKalaNoeSenfDAO.class.getSimpleName(), activityNameForLog, "fetchAllGorohKalaNoeSenf", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllGorohKalaNoeSenfResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), GorohKalaNoeSenfDAO.class.getSimpleName(), activityNameForLog, "fetchAllGorohKalaNoeSenf", "onFailure");
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

    public boolean insertGroup(ArrayList<GorohKalaNoeSenfModel> gorohKalaNoeSenfModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (GorohKalaNoeSenfModel gorohKalaNoeSenfModel : gorohKalaNoeSenfModels)
            {
                ContentValues contentValues = modelToContentvalue(gorohKalaNoeSenfModel);
                db.insertOrThrow(GorohKalaNoeSenfModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , GorohKalaNoeSenfModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GorohKalaNoeSenfDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<GorohKalaNoeSenfModel> getAll()
    {
        ArrayList<GorohKalaNoeSenfModel> gorohKalaNoeSenfModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(GorohKalaNoeSenfModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohKalaNoeSenfModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GorohKalaNoeSenfModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GorohKalaNoeSenfDAO" , "" , "getAll" , "");
        }
        return gorohKalaNoeSenfModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(GorohKalaNoeSenfModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , GorohKalaNoeSenfModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GorohKalaNoeSenfDAO" , "" , "deleteAll" , "");
            return false;
        }
    }


    private static ContentValues modelToContentvalue(GorohKalaNoeSenfModel gorohKalaNoeSenfModel)
    {
        ContentValues contentValues = new ContentValues();

        if (gorohKalaNoeSenfModel.getCcGorohKalaNoeSenf() != null && gorohKalaNoeSenfModel.getCcGorohKalaNoeSenf() > 0)
        {
            contentValues.put(GorohKalaNoeSenfModel.COLUMN_ccGorohKalaNoeSenf() , gorohKalaNoeSenfModel.getCcGorohKalaNoeSenf());
        }
        contentValues.put(GorohKalaNoeSenfModel.COLUMN_ccNoeSenf() , gorohKalaNoeSenfModel.getCcNoeSenf());
        contentValues.put(GorohKalaNoeSenfModel.COLUMN_ccGorohKala() , gorohKalaNoeSenfModel.getCcGorohKala());

        return contentValues;
    }


    private ArrayList<GorohKalaNoeSenfModel> cursorToModel(Cursor cursor)
    {
        ArrayList<GorohKalaNoeSenfModel> gorohKalaNoeSenfModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            GorohKalaNoeSenfModel gorohKalaNoeSenfModel = new GorohKalaNoeSenfModel();

            gorohKalaNoeSenfModel.setCcGorohKalaNoeSenf(cursor.getInt(cursor.getColumnIndex(GorohKalaNoeSenfModel.COLUMN_ccGorohKalaNoeSenf())));
            gorohKalaNoeSenfModel.setCcNoeSenf(cursor.getInt(cursor.getColumnIndex(GorohKalaNoeSenfModel.COLUMN_ccNoeSenf())));
            gorohKalaNoeSenfModel.setCcGorohKala(cursor.getInt(cursor.getColumnIndex(GorohKalaNoeSenfModel.COLUMN_ccGorohKala())));

            gorohKalaNoeSenfModels.add(gorohKalaNoeSenfModel);
            cursor.moveToNext();
        }
        return gorohKalaNoeSenfModels;
    }

}
