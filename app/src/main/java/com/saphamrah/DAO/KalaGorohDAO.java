package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.saphamrah.Model.KalaGorohModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllvKalaGorohResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KalaGorohDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public KalaGorohDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaGorohDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            KalaGorohModel.COLUMN_ccKalaGoroh(),
            KalaGorohModel.COLUMN_ccKalaCode(),
            KalaGorohModel.COLUMN_ccGoroh(),
            KalaGorohModel.COLUMN_NameGoroh(),
            KalaGorohModel.COLUMN_ccGorohLink(),
            KalaGorohModel.COLUMN_ccRoot()
        };
    }

    public void fetchAllvKalaGoroh(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllvKalaGorohResult> call = apiService.getAllvKalaGoroh();
            call.enqueue(new Callback<GetAllvKalaGorohResult>() {
                @Override
                public void onResponse(Call<GetAllvKalaGorohResult> call, Response<GetAllvKalaGorohResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaGorohDAO.class.getSimpleName(), "", "fetchAllvKalaGoroh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvKalaGorohResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvKalaGorohResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    public void fetchAllvKalaGorohAmargar(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllvKalaGorohResult> call = apiService.getAllKalaGorohAmargar();
            call.enqueue(new Callback<GetAllvKalaGorohResult>() {
                @Override
                public void onResponse(Call<GetAllvKalaGorohResult> call, Response<GetAllvKalaGorohResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaGorohDAO.class.getSimpleName(), "", "fetchAllvKalaGorohAmargar", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvKalaGorohResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvKalaGorohResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "onFailure");
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

    public boolean insertGroup(ArrayList<KalaGorohModel> kalaGorohModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (KalaGorohModel kalaGorohModel : kalaGorohModels)
            {
                ContentValues contentValues = modelToContentvalue(kalaGorohModel);
                db.insertOrThrow(KalaGorohModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , KalaGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGorohDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<KalaGorohModel> getAll()
    {
        ArrayList<KalaGorohModel> kalaGorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaGorohModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaGorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGorohDAO" , "" , "getAll" , "");
        }
        return kalaGorohModels;
    }

    public ArrayList<KalaGorohModel> getByccGoroh(int ccGoroh)
    {
        ArrayList<KalaGorohModel> kalaGorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaGorohModel.TableName(), allColumns(), KalaGorohModel.COLUMN_ccGoroh() + " = " + ccGoroh, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaGorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGorohDAO" , "" , "getAll" , "");
        }
        return kalaGorohModels;
    }


    public ArrayList<KalaGorohModel> getGorohByBrand(int ccBrand)
    {
        ArrayList<KalaGorohModel> kalaGorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from KalaGoroh where ccGoroh in (select ccKalaGoroh from Brand where ccBrand = " + ccBrand + ")";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaGorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGorohDAO" , "" , "getGorohByBrand" , "");
        }
        return kalaGorohModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KalaGorohModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KalaGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGorohDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(KalaGorohModel kalaGorohModel)
    {
        ContentValues contentValues = new ContentValues();

        if (kalaGorohModel.getCcKalaGoroh() > 0)
        {
            contentValues.put(KalaGorohModel.COLUMN_ccKalaGoroh() , kalaGorohModel.getCcKalaGoroh());
        }
        contentValues.put(KalaGorohModel.COLUMN_ccKalaCode() , kalaGorohModel.getCcKalaCode());
        contentValues.put(KalaGorohModel.COLUMN_ccGoroh() , kalaGorohModel.getCcGoroh());
        contentValues.put(KalaGorohModel.COLUMN_NameGoroh() , kalaGorohModel.getNameGoroh());
        contentValues.put(KalaGorohModel.COLUMN_ccGorohLink() , kalaGorohModel.getCcGorohLink());
        contentValues.put(KalaGorohModel.COLUMN_ccRoot() , kalaGorohModel.getCcRoot());

        return contentValues;
    }


    private ArrayList<KalaGorohModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaGorohModel> kalaGorohModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaGorohModel kalaGorohModel = new KalaGorohModel();

            kalaGorohModel.setCcKalaGoroh(cursor.getInt(cursor.getColumnIndex(KalaGorohModel.COLUMN_ccKalaGoroh())));
            kalaGorohModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaGorohModel.COLUMN_ccKalaCode())));
            kalaGorohModel.setCcGoroh(cursor.getInt(cursor.getColumnIndex(KalaGorohModel.COLUMN_ccGoroh())));
            kalaGorohModel.setNameGoroh(cursor.getString(cursor.getColumnIndex(KalaGorohModel.COLUMN_NameGoroh())));
            kalaGorohModel.setCcGorohLink(cursor.getInt(cursor.getColumnIndex(KalaGorohModel.COLUMN_ccGorohLink())));
            kalaGorohModel.setCcRoot(cursor.getInt(cursor.getColumnIndex(KalaGorohModel.COLUMN_ccRoot())));


            kalaGorohModels.add(kalaGorohModel);
            cursor.moveToNext();
        }
        return kalaGorohModels;
    }
    
    
}
