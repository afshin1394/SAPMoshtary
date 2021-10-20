package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ElatAdamMoarefiMoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetElatAdamMoarefiMoshtaryResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElatAdamMoarefiMoshtaryDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "ElatAdamMoarefiMoshtaryDAO";

    public ElatAdamMoarefiMoshtaryDAO(Context context)
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
            ElatAdamMoarefiMoshtaryModel.COLUMN_ccElatAdamMoarefiMoshtary(),
            ElatAdamMoarefiMoshtaryModel.COLUMN_NameElatAdamMoarefiMoshtary(),
            ElatAdamMoarefiMoshtaryModel.COLUMN_ViewInPPC_Foroshandeh()
        };
    }


    public void fetchElatAdamMoarefiMoshtary(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchElatAdamMoarefiMoshtary", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetElatAdamMoarefiMoshtaryResult> call = apiServiceGet.getElatAdamMoarefiMoshtary();
            call.enqueue(new Callback<GetElatAdamMoarefiMoshtaryResult>() {
                @Override
                public void onResponse(Call<GetElatAdamMoarefiMoshtaryResult> call, Response<GetElatAdamMoarefiMoshtaryResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchElatAdamMoarefiMoshtary", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetElatAdamMoarefiMoshtaryResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchElatAdamMoarefiMoshtary", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchElatAdamMoarefiMoshtary", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchElatAdamMoarefiMoshtary", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchElatAdamMoarefiMoshtary", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetElatAdamMoarefiMoshtaryResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchElatAdamMoarefiMoshtary", "onFailure");
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

    public boolean insertGroup(ArrayList<ElatAdamMoarefiMoshtaryModel> elatAdamMoarefiMoshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ElatAdamMoarefiMoshtaryModel model : elatAdamMoarefiMoshtaryModels)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(ElatAdamMoarefiMoshtaryModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ElatAdamMoarefiMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<ElatAdamMoarefiMoshtaryModel> getAll()
    {
        ArrayList<ElatAdamMoarefiMoshtaryModel> elatAdamMoarefiMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElatAdamMoarefiMoshtaryModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elatAdamMoarefiMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElatAdamMoarefiMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return elatAdamMoarefiMoshtaryModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ElatAdamMoarefiMoshtaryModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ElatAdamMoarefiMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ElatAdamMoarefiMoshtaryModel elatAdamMoarefiMoshtaryModel)
    {
        ContentValues contentValues = new ContentValues();

        if (elatAdamMoarefiMoshtaryModel.getCcElatAdamMoarefiMoshtary() > 0)
        {
            contentValues.put(ElatAdamMoarefiMoshtaryModel.COLUMN_ccElatAdamMoarefiMoshtary() , elatAdamMoarefiMoshtaryModel.getCcElatAdamMoarefiMoshtary());
        }
        contentValues.put(ElatAdamMoarefiMoshtaryModel.COLUMN_NameElatAdamMoarefiMoshtary() , elatAdamMoarefiMoshtaryModel.getNameElatAdamMoarefiMoshtary());
        contentValues.put(ElatAdamMoarefiMoshtaryModel.COLUMN_ViewInPPC_Foroshandeh() , elatAdamMoarefiMoshtaryModel.getViewInPPCForoshandeh());

        return contentValues;
    }


    private ArrayList<ElatAdamMoarefiMoshtaryModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ElatAdamMoarefiMoshtaryModel> elatAdamMoarefiMoshtaryModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ElatAdamMoarefiMoshtaryModel elatAdamMoarefiMoshtaryModel = new ElatAdamMoarefiMoshtaryModel();

            elatAdamMoarefiMoshtaryModel.setCcElatAdamMoarefiMoshtary(cursor.getInt(cursor.getColumnIndex(ElatAdamMoarefiMoshtaryModel.COLUMN_ccElatAdamMoarefiMoshtary())));
            elatAdamMoarefiMoshtaryModel.setNameElatAdamMoarefiMoshtary(cursor.getString(cursor.getColumnIndex(ElatAdamMoarefiMoshtaryModel.COLUMN_NameElatAdamMoarefiMoshtary())));
            elatAdamMoarefiMoshtaryModel.setViewInPPCForoshandeh(cursor.getInt(cursor.getColumnIndex(ElatAdamMoarefiMoshtaryModel.COLUMN_ViewInPPC_Foroshandeh())));

            elatAdamMoarefiMoshtaryModels.add(elatAdamMoarefiMoshtaryModel);
            cursor.moveToNext();
        }
        return elatAdamMoarefiMoshtaryModels;
    }


}
