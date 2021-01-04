package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllvElatAdamDarkhastResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElatAdamDarkhastDAO
{

    private DBHelper dbHelper;
    private Context context;

    
    public ElatAdamDarkhastDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ElatAdamDarkhastDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ElatAdamDarkhastModel.COLUMN_ccElatAdamDarkhast_NoeMoshtary(),
            ElatAdamDarkhastModel.COLUMN_ccElatAdamDarkhast(),
            ElatAdamDarkhastModel.COLUMN_NameElatAdamDarkhast(),
            ElatAdamDarkhastModel.COLUMN_CodeSort(),
            ElatAdamDarkhastModel.COLUMN_CodeNoe(),
            ElatAdamDarkhastModel.COLUMN_MoshtaryFaal(),
            ElatAdamDarkhastModel.COLUMN_MoshtaryGheyreFaal(),
            ElatAdamDarkhastModel.COLUMN_GetImage(),
            ElatAdamDarkhastModel.COLUMN_ccNoeMoshtary()
        };
    }

    public void fetchElatAdamDarkhast(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ElatAdamDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamDarkhast", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllvElatAdamDarkhastResult> call = apiServiceGet.getElatAdamDarkhast();
            call.enqueue(new Callback<GetAllvElatAdamDarkhastResult>() {
                @Override
                public void onResponse(Call<GetAllvElatAdamDarkhastResult> call, Response<GetAllvElatAdamDarkhastResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ElatAdamDarkhastDAO.class.getSimpleName(), "", "fetchElatAdamDarkhast", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvElatAdamDarkhastResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ElatAdamDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamDarkhast", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), ElatAdamDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamDarkhast", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ElatAdamDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamDarkhast", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ElatAdamDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamDarkhast", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvElatAdamDarkhastResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ElatAdamDarkhastDAO.class.getSimpleName(), activityNameForLog, "fetchElatAdamDarkhast", "onFailure");
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

    public boolean insertGroup(ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ElatAdamDarkhastModel elatAdamDarkhastModel : elatAdamDarkhastModels)
            {
                ContentValues contentValues = modelToContentvalue(elatAdamDarkhastModel);
                db.insertOrThrow(ElatAdamDarkhastModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ElatAdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElatAdamDarkhastDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<ElatAdamDarkhastModel> getAll()
    {
        ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElatAdamDarkhastModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elatAdamDarkhastModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElatAdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElatAdamDarkhastDAO" , "" , "getAll" , "");
        }
        return elatAdamDarkhastModels;
    }

    public ArrayList<ElatAdamDarkhastModel> getElatAdamDarkhast(int vazeiatMoshtary , int ccNoeMoshtary)
    {
        ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels = new ArrayList<>();
        String query = "";
        if (vazeiatMoshtary == 1)
        {
            query = "SELECT * FROM ElatAdamDarkhast WHERE MoshtaryFaal = 1 AND CodeNoe IN(1) AND " +
                    " ccNoeMoshtary = " + ccNoeMoshtary +
                    " order by CodeSort";
        }
        else
        {
            query = "SELECT * FROM ElatAdamDarkhast WHERE MoshtaryGheyreFaal = 1 AND CodeNoe IN(1) AND " +
                    " ccNoeMoshtary = " + ccNoeMoshtary +
                    " order by CodeSort";
        }

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elatAdamDarkhastModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElatAdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElatAdamDarkhastDAO" , "" , "getElatAdamDarkhast" , "");
        }
        return elatAdamDarkhastModels;
    }

    public ArrayList<ElatAdamDarkhastModel> getElatAdamSefaresh(int vazeiatMoshtary , int ccNoeMoshtary)
    {
        ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels = new ArrayList<>();
        String query = "";
        if (vazeiatMoshtary == 1)
        {
            query = "SELECT * FROM ElatAdamDarkhast WHERE MoshtaryFaal = 1 AND CodeNoe IN(0) AND \n" +
                    " ccNoeMoshtary = " + ccNoeMoshtary +
                    " order by CodeSort\n";
        }
        else
        {
            query = "SELECT * FROM ElatAdamDarkhast WHERE MoshtaryGheyreFaal = 1 AND CodeNoe IN(0) AND " +
                    " ccNoeMoshtary = " + ccNoeMoshtary +
                    " order by CodeSort";
        }

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elatAdamDarkhastModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElatAdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElatAdamDarkhastDAO" , "" , "getElatAdamSefaresh" , "");
        }
        return elatAdamDarkhastModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ElatAdamDarkhastModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ElatAdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElatAdamDarkhastDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private ArrayList<ElatAdamDarkhastModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ElatAdamDarkhastModel elatAdamDarkhastModel = new ElatAdamDarkhastModel();

            elatAdamDarkhastModel.setCcElatAdamDarkhast_NoeMoshtary(cursor.getInt(cursor.getColumnIndex(ElatAdamDarkhastModel.COLUMN_ccElatAdamDarkhast_NoeMoshtary())));
            elatAdamDarkhastModel.setCcElatAdamDarkhast(cursor.getInt(cursor.getColumnIndex(ElatAdamDarkhastModel.COLUMN_ccElatAdamDarkhast())));
            elatAdamDarkhastModel.setNameElatAdamDarkhast(cursor.getString(cursor.getColumnIndex(ElatAdamDarkhastModel.COLUMN_NameElatAdamDarkhast())));
            elatAdamDarkhastModel.setCodeSort(cursor.getInt(cursor.getColumnIndex(ElatAdamDarkhastModel.COLUMN_CodeSort())));
            elatAdamDarkhastModel.setCodeNoe(cursor.getInt(cursor.getColumnIndex(ElatAdamDarkhastModel.COLUMN_CodeNoe())));
            elatAdamDarkhastModel.setMoshtaryFaal(cursor.getInt(cursor.getColumnIndex(ElatAdamDarkhastModel.COLUMN_MoshtaryFaal())));
            elatAdamDarkhastModel.setMoshtaryGheyreFaal(cursor.getInt(cursor.getColumnIndex(ElatAdamDarkhastModel.COLUMN_MoshtaryGheyreFaal())));
            elatAdamDarkhastModel.setGetImage(cursor.getInt(cursor.getColumnIndex(ElatAdamDarkhastModel.COLUMN_GetImage())));
            elatAdamDarkhastModel.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(ElatAdamDarkhastModel.COLUMN_ccNoeMoshtary())));

            elatAdamDarkhastModels.add(elatAdamDarkhastModel);
            cursor.moveToNext();
        }
        return elatAdamDarkhastModels;
    }


    private static ContentValues modelToContentvalue(ElatAdamDarkhastModel elatAdamDarkhastModel)
    {
        ContentValues contentValues = new ContentValues();

        if (elatAdamDarkhastModel.getCcElatAdamDarkhast_NoeMoshtary() != null && elatAdamDarkhastModel.getCcElatAdamDarkhast_NoeMoshtary() > 0)
        {
            contentValues.put(ElatAdamDarkhastModel.COLUMN_ccElatAdamDarkhast_NoeMoshtary() , elatAdamDarkhastModel.getCcElatAdamDarkhast_NoeMoshtary());
        }
        contentValues.put(ElatAdamDarkhastModel.COLUMN_ccElatAdamDarkhast() , elatAdamDarkhastModel.getCcElatAdamDarkhast());
        contentValues.put(ElatAdamDarkhastModel.COLUMN_NameElatAdamDarkhast() , elatAdamDarkhastModel.getNameElatAdamDarkhast());
        contentValues.put(ElatAdamDarkhastModel.COLUMN_CodeSort() , elatAdamDarkhastModel.getCodeSort());
        contentValues.put(ElatAdamDarkhastModel.COLUMN_CodeNoe() , elatAdamDarkhastModel.getCodeNoe());
        contentValues.put(ElatAdamDarkhastModel.COLUMN_MoshtaryFaal() , elatAdamDarkhastModel.getMoshtaryFaal());
        contentValues.put(ElatAdamDarkhastModel.COLUMN_MoshtaryGheyreFaal() , elatAdamDarkhastModel.getMoshtaryGheyreFaal());
        contentValues.put(ElatAdamDarkhastModel.COLUMN_GetImage() , elatAdamDarkhastModel.getGetImage());
        contentValues.put(ElatAdamDarkhastModel.COLUMN_ccNoeMoshtary() , elatAdamDarkhastModel.getCcNoeMoshtary());

        return contentValues;
    }




}
