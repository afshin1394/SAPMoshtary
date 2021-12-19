package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.Model.NoeFaaliatForMoarefiMoshtaryJadidModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetNoeFaaliatForMoarefiMoshtaryJadidResult;

import java.nio.charset.StandardCharsets;
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

public class NoeFaaliatForMoarefiMoshtaryJadidDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "NoeFaaliatForMoarefiMoshtaryJadidDAO";


    public NoeFaaliatForMoarefiMoshtaryJadidDAO(Context context)
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
            NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccNoeFaaliatForMoarefiMoshtaryJadid(),
            NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccNoeMoshtary(),
            NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccSenfMoshtary()
        };
    }





        public void fetchNoeFaaliatForMoarefiMoshtaryJadid(final Context context, final String activityNameForLog, String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchNoeFaaliatForMoarefiMoshtaryJadid", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetNoeFaaliatForMoarefiMoshtaryJadidResult> call = apiServiceGet.getNoeFaaliatForMoarefiMoshtaryJadid(ccForoshandeh);
            call.enqueue(new Callback<GetNoeFaaliatForMoarefiMoshtaryJadidResult>() {
                @Override
                public void onResponse(Call<GetNoeFaaliatForMoarefiMoshtaryJadidResult> call, Response<GetNoeFaaliatForMoarefiMoshtaryJadidResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchNoeFaaliatForMoarefiMoshtaryJadid", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetNoeFaaliatForMoarefiMoshtaryJadidResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchNoeFaaliatForMoarefiMoshtaryJadid", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchNoeFaaliatForMoarefiMoshtaryJadid", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchNoeFaaliatForMoarefiMoshtaryJadid", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchNoeFaaliatForMoarefiMoshtaryJadid", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetNoeFaaliatForMoarefiMoshtaryJadidResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchNoeFaaliatForMoarefiMoshtaryJadid", "onFailure");
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

    public boolean insertGroup(ArrayList<NoeFaaliatForMoarefiMoshtaryJadidModel> noeFaaliatForMoarefiMoshtaryJadidModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (NoeFaaliatForMoarefiMoshtaryJadidModel noeFaaliatForMoarefiMoshtaryJadidModel : noeFaaliatForMoarefiMoshtaryJadidModels)
            {
                ContentValues contentValues = modelToContentvalue(noeFaaliatForMoarefiMoshtaryJadidModel);
                db.insertOrThrow(NoeFaaliatForMoarefiMoshtaryJadidModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , NoeFaaliatForMoarefiMoshtaryJadidModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }

    public String getDistinctccNoeMoshtary()
    {
        String ccNoeMoshtary = "-1";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select distinct(" + NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccNoeMoshtary() + ") from " + NoeFaaliatForMoarefiMoshtaryJadidModel.TableName();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        ccNoeMoshtary += "," + cursor.getInt(0);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , NoeFaaliatForMoarefiMoshtaryJadidModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getDistinctccNoeMoshtary" , "");
        }
        return ccNoeMoshtary;
    }

    public String getDistinctccSenfMoshtary(int ccNoeMoshtary)
    {
        String ccSenfMoshtary = "-1";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select distinct (" + NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccSenfMoshtary() + ") from " + NoeFaaliatForMoarefiMoshtaryJadidModel.TableName() +
                    " where " + NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccNoeMoshtary() + " = " + ccNoeMoshtary;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        ccSenfMoshtary += "," + cursor.getString(0);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , NoeFaaliatForMoarefiMoshtaryJadidModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getDistinctccSenfMoshtary" , "");
        }
        return ccSenfMoshtary;
    }

    public ArrayList<NoeFaaliatForMoarefiMoshtaryJadidModel> getAll()
    {
        ArrayList<NoeFaaliatForMoarefiMoshtaryJadidModel> noeFaaliatForMoarefiMoshtaryJadidModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(NoeFaaliatForMoarefiMoshtaryJadidModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    noeFaaliatForMoarefiMoshtaryJadidModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , NoeFaaliatForMoarefiMoshtaryJadidModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return noeFaaliatForMoarefiMoshtaryJadidModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(NoeFaaliatForMoarefiMoshtaryJadidModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , NoeFaaliatForMoarefiMoshtaryJadidModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(NoeFaaliatForMoarefiMoshtaryJadidModel noeFaaliatForMoarefiMoshtaryJadidModels)
    {
        ContentValues contentValues = new ContentValues();

        if (noeFaaliatForMoarefiMoshtaryJadidModels.getCcNoeFaaliatForMoarefiMoshtaryJadid() > 0)
        {
            contentValues.put(NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccNoeFaaliatForMoarefiMoshtaryJadid() , noeFaaliatForMoarefiMoshtaryJadidModels.getCcNoeFaaliatForMoarefiMoshtaryJadid());
        }
        contentValues.put(NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccNoeMoshtary() , noeFaaliatForMoarefiMoshtaryJadidModels.getCcNoeMoshtary());
        contentValues.put(NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccSenfMoshtary() , noeFaaliatForMoarefiMoshtaryJadidModels.getCcSenfMoshtary());

        return contentValues;
    }


    private ArrayList<NoeFaaliatForMoarefiMoshtaryJadidModel> cursorToModel(Cursor cursor)
    {
        ArrayList<NoeFaaliatForMoarefiMoshtaryJadidModel> noeFaaliatForMoarefiMoshtaryJadidModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            NoeFaaliatForMoarefiMoshtaryJadidModel noeFaaliatForMoarefiMoshtaryJadidModel = new NoeFaaliatForMoarefiMoshtaryJadidModel();

            noeFaaliatForMoarefiMoshtaryJadidModel.setCcNoeFaaliatForMoarefiMoshtaryJadid(cursor.getInt(cursor.getColumnIndex(NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccNoeFaaliatForMoarefiMoshtaryJadid())));
            noeFaaliatForMoarefiMoshtaryJadidModel.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccNoeMoshtary())));
            noeFaaliatForMoarefiMoshtaryJadidModel.setCcSenfMoshtary(cursor.getInt(cursor.getColumnIndex(NoeFaaliatForMoarefiMoshtaryJadidModel.COLUMN_ccSenfMoshtary())));

            noeFaaliatForMoarefiMoshtaryJadidModels.add(noeFaaliatForMoarefiMoshtaryJadidModel);
            cursor.moveToNext();
        }
        return noeFaaliatForMoarefiMoshtaryJadidModels;
    }


}
