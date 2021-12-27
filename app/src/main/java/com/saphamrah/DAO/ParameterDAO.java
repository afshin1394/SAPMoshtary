package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.ParameterModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetParameterResult;
import com.saphamrah.protos.ParameterGrpc;
import com.saphamrah.protos.ParameterReply;
import com.saphamrah.protos.ParameterReplyList;
import com.saphamrah.protos.ParameterRequest;

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

public class ParameterDAO
{

    private DBHelper dbHelper;
    private Context context;


    public ParameterDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ParameterDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ParameterModel.COLUMN_ccParameter(),
            ParameterModel.COLUMN_NoeParameter(),
            ParameterModel.COLUMN_Name(),
            ParameterModel.COLUMN_GetProgram()
        };
    }

    public void fetchParameterGrpc(final Context context, final String activityNameForLog, String noeTitrSatr, String ccMarkazSazmanForosh, String ccMarkazAnbar, String dateProgram, final RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ParameterDAO.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                ParameterGrpc.ParameterBlockingStub blockingStub = ParameterGrpc.newBlockingStub(managedChannel);
                ParameterRequest request = ParameterRequest.newBuilder().setRowTitleType(noeTitrSatr).setSellOrganizationCenterID(ccMarkazSazmanForosh).setStoreCenterID(ccMarkazAnbar).setDateProgram(dateProgram).build();

                Callable<ParameterReplyList> replyListCallable = () -> blockingStub.getParameter(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<ParameterModel> models = new ArrayList<>();
                            for (ParameterReply reply : replyList.getParametersList()) {
                                ParameterModel model = new ParameterModel();

                                model.setCcParameter(reply.getParameterID());
                                model.setNoeParameter(reply.getParameterType());
                                model.setName(reply.getName());
                                model.setGetProgram(reply.getGetProgram());
                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<ParameterModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<ParameterModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), ParameterDAO.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }


    }


    /**
     *
     * @param context
     * @param activityNameForLog
     * @param noeTitrSatr -> 1 => get titr in splash , 2 => get satr in splash , 3 => get satr in get program
     * @param ccMarkazSazmanForosh
     * @param dateProgram -> date and time of latest get config
     * @param retrofitResponse
     */
    public void fetchParameter(final Context context, final String activityNameForLog, String noeTitrSatr, String ccMarkazSazmanForosh, String ccMarkazAnbar, String dateProgram, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterDAO", activityNameForLog, "fetchParameter", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetParameterResult> call = apiServiceGet.getParameter(noeTitrSatr , ccMarkazSazmanForosh, ccMarkazAnbar, dateProgram);
            call.enqueue(new Callback<GetParameterResult>() {
                @Override
                public void onResponse(Call<GetParameterResult> call, Response<GetParameterResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, "ParameterDAO", "", "fetchParameter", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetParameterResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), "ParameterDAO", activityNameForLog, "fetchParameter", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), "ParameterDAO", activityNameForLog, "fetchParameter", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterDAO", activityNameForLog, "fetchParameter", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ParameterDAO", activityNameForLog, "fetchParameter", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetParameterResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), "ParameterDAO", activityNameForLog, "fetchParameter", "onFailure");
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

    public boolean insertGroup(ArrayList<ParameterModel> parameterModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ParameterModel parameterModel : parameterModels)
            {
                ContentValues contentValues = modelToContentvalue(parameterModel);
                db.insertOrThrow(ParameterModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ParameterModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<ParameterModel> getAll()
    {
        ArrayList<ParameterModel> parameterModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ParameterModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    parameterModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ParameterModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterDAO" , "" , "getAll" , "");
        }
        return parameterModels;
    }


    public ParameterModel getByNameParameter(String parameterName)
    {
        ParameterModel parameterModel = new ParameterModel();
        String query = "SELECT * FROM " + ParameterModel.TableName() + " WHERE " + ParameterModel.COLUMN_Name() + " = " + parameterName + " COLLATE NOCASE";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    parameterModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ParameterModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterDAO" , "" , "getByNameParameter" , "");
        }
        return parameterModel;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ParameterModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ParameterModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ParameterModel parameterModel)
    {
        ContentValues contentValues = new ContentValues();

        if (parameterModel.getCcParameter() != null && parameterModel.getCcParameter() > 0)
        {
            contentValues.put(ParameterModel.COLUMN_ccParameter() , parameterModel.getCcParameter());
        }
        contentValues.put(ParameterModel.COLUMN_NoeParameter() , parameterModel.getNoeParameter());
        contentValues.put(ParameterModel.COLUMN_Name() , parameterModel.getName());
        contentValues.put(ParameterModel.COLUMN_GetProgram() , parameterModel.getGetProgram());

        return contentValues;
    }


    private ArrayList<ParameterModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ParameterModel> parameterModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ParameterModel parameterModel = new ParameterModel();

            parameterModel.setCcParameter(cursor.getInt(cursor.getColumnIndex(ParameterModel.COLUMN_ccParameter())));
            parameterModel.setNoeParameter(cursor.getInt(cursor.getColumnIndex(ParameterModel.COLUMN_NoeParameter())));
            parameterModel.setName(cursor.getString(cursor.getColumnIndex(ParameterModel.COLUMN_Name())));
            parameterModel.setGetProgram(cursor.getInt(cursor.getColumnIndex(ParameterModel.COLUMN_GetProgram())));

            parameterModels.add(parameterModel);
            cursor.moveToNext();
        }
        return parameterModels;
    }

}
