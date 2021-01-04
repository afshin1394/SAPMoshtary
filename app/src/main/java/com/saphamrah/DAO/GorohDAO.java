package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.GorohModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllGorohResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GorohDAO
{


    private DBHelper dbHelper;
    private Context context;



    public GorohDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "GorohDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            GorohModel.COLUMN_ccGoroh(),
            GorohModel.COLUMN_NameGoroh(),
            GorohModel.COLUMN_ccGorohLink(),
            GorohModel.COLUMN_ccRoot(),
            GorohModel.COLUMN_CodeNoeGoroh()
        };
    }

    public void fetchAllGoroh(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, GorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllGoroh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllGorohResult> call = apiServiceGet.getAllGoroh();
            call.enqueue(new Callback<GetAllGorohResult>() {
                @Override
                public void onResponse(Call<GetAllGorohResult> call, Response<GetAllGorohResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, GorohDAO.class.getSimpleName(), "", "fetchAllGoroh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllGorohResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), GorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllGoroh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), GorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllGoroh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, GorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllGoroh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), GorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllGoroh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllGorohResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), GorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllGoroh", "onFailure");
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

    public boolean insertGroup(ArrayList<GorohModel> gorohModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (GorohModel gorohModel : gorohModels)
            {
                ContentValues contentValues = modelToContentvalue(gorohModel);
                db.insertOrThrow(GorohModel.TableName(), null, contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , GorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GorohDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<GorohModel> getAll()
    {
        ArrayList<GorohModel> gorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(GorohModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GorohDAO" , "" , "getAll" , "");
        }
        return gorohModels;
    }

    public ArrayList<GorohModel> getByccGorohLink(int ccGorohLink)
    {
        ArrayList<GorohModel> gorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(GorohModel.TableName(), allColumns(), " ccGoroh NOT IN(348,349,350,351,352,353,354,355,607) AND ccGorohLink= ?", new String[]{String.valueOf(ccGorohLink )}, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GorohModel.TableName()) + " with ccGorohLink = " + ccGorohLink + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GorohDAO" , "" , "getByccGorohLink" , "");
        }
        return gorohModels;
    }

    public ArrayList<GorohModel> getccNoeSenfByccGorohLink(String ccGorohsOfSenf)
    {
        ArrayList<GorohModel> gorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from " + GorohModel.TableName() + " where " + GorohModel.COLUMN_ccGoroh() + " in ( " + ccGorohsOfSenf + ")";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GorohModel.TableName()) + " with ccGorohsOfSenf = " + ccGorohsOfSenf + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GorohDAO" , "" , "getccNoeSenfByccGorohLink" , "");
        }
        return gorohModels;
    }

    public ArrayList<GorohModel> getByccGorohs(String ccGorohs)
    {
        ArrayList<GorohModel> gorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(GorohModel.TableName(), allColumns(), " ccGoroh in (" + ccGorohs + ")", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GorohDAO" , "" , "getByccGorohs" , "");
        }
        return gorohModels;
    }

    public ArrayList<GorohModel> getByccGoroh(int ccGoroh)
    {
        ArrayList<GorohModel> gorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //Cursor cursor = db.query(GorohModel.TableName(), allColumns(), " ccGoroh NOT IN(348,349,350,351,352,353,354,355,607) AND ccGoroh= ?", new String[]{String.valueOf(ccGoroh)}, null, null, null);
            Cursor cursor = db.query(GorohModel.TableName(), allColumns(), " ccGoroh= ?", new String[]{String.valueOf(ccGoroh)}, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GorohModel.TableName()) + " with ccGoroh = " + ccGoroh + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GorohDAO" , "" , "getByccGoroh" , "");
        }
        return gorohModels;
    }

    public String getNameGoroh(int ccGoroh)
    {
        String nameGoroh = "";
        try
        {
            String query = "select " + GorohModel.COLUMN_NameGoroh() + " from " + GorohModel.TableName() + " where " + GorohModel.COLUMN_ccGoroh() + " = " + ccGoroh;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    nameGoroh = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GorohModel.TableName()) + " with ccGoroh = " + ccGoroh + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "GorohDAO" , "" , "getNameGoroh" , "");
        }
        return nameGoroh;
    }
	
    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(GorohModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , GorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GorohDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(GorohModel gorohModel)
    {
        ContentValues contentValues = new ContentValues();

        if (gorohModel.getCcGoroh() != null && gorohModel.getCcGoroh() > 0)
        {
            contentValues.put(GorohModel.COLUMN_ccGoroh() , gorohModel.getCcGoroh());
        }
        contentValues.put(GorohModel.COLUMN_NameGoroh() , gorohModel.getNameGoroh());
        contentValues.put(GorohModel.COLUMN_ccGorohLink() , gorohModel.getCcGorohLink());
        contentValues.put(GorohModel.COLUMN_ccRoot() , gorohModel.getCcRoot());
        contentValues.put(GorohModel.COLUMN_CodeNoeGoroh() , gorohModel.getCodeNoeGoroh());

        return contentValues;
    }

    private ArrayList<GorohModel> cursorToModel(Cursor cursor)
    {
        ArrayList<GorohModel> gorohModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            GorohModel gorohModel = new GorohModel();

            gorohModel.setCcGoroh(cursor.getInt(cursor.getColumnIndex(GorohModel.COLUMN_ccGoroh())));
            gorohModel.setNameGoroh(cursor.getString(cursor.getColumnIndex(GorohModel.COLUMN_NameGoroh())));
            gorohModel.setCcGorohLink(cursor.getInt(cursor.getColumnIndex(GorohModel.COLUMN_ccGorohLink())));
            gorohModel.setCcRoot(cursor.getInt(cursor.getColumnIndex(GorohModel.COLUMN_ccRoot())));
            gorohModel.setCodeNoeGoroh(cursor.getInt(cursor.getColumnIndex(GorohModel.COLUMN_CodeNoeGoroh())));

            gorohModels.add(gorohModel);
            cursor.moveToNext();
        }
        return gorohModels;
    }


}
