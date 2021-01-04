package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.KalaZaribForoshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllvKalaZaribForoshResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KalaZaribForoshDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public KalaZaribForoshDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaZaribForoshDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            KalaZaribForoshModel.COLUMN_ccKalaZaribForosh(),
            KalaZaribForoshModel.COLUMN_ccKalaCode(),
            KalaZaribForoshModel.COLUMN_ccGorohMoshtary(),
            KalaZaribForoshModel.COLUMN_ZaribForosh(),
            KalaZaribForoshModel.COLUMN_Darajeh()
        };
    }

    public void fetchAllKalaZaribForosh(final Context context, final String activityNameForLog, final String ccGorohs, String ccMarkazForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvKalaZaribForoshResult> call = apiServiceGet.getAllvKalaZaribForosh(ccGorohs , ccMarkazForosh);
            call.enqueue(new Callback<GetAllvKalaZaribForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvKalaZaribForoshResult> call, Response<GetAllvKalaZaribForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaZaribForoshDAO.class.getSimpleName(), "", "fetchAllKalaZaribForosh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvKalaZaribForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvKalaZaribForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",  t.getMessage(), endpoint), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "onFailure");
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

    public boolean insertGroup(ArrayList<KalaZaribForoshModel> kalaZaribForoshModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (KalaZaribForoshModel kalaZaribForoshModel : kalaZaribForoshModels)
            {
                ContentValues contentValues = modelToContentvalue(kalaZaribForoshModel);
                db.insertOrThrow(KalaZaribForoshModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , KalaZaribForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaZaribForoshDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<KalaZaribForoshModel> getAll()
    {
        ArrayList<KalaZaribForoshModel> kalaZaribForoshModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaZaribForoshModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaZaribForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaZaribForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaZaribForoshDAO" , "" , "getAll" , "");
        }
        return kalaZaribForoshModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KalaZaribForoshModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KalaZaribForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaZaribForoshDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(KalaZaribForoshModel kalaZaribForoshModel)
    {
        ContentValues contentValues = new ContentValues();

        if (kalaZaribForoshModel.getCcKalaZaribForosh() > 0)
        {
            contentValues.put(KalaZaribForoshModel.COLUMN_ccKalaZaribForosh() , kalaZaribForoshModel.getCcKalaZaribForosh());
        }
        contentValues.put(KalaZaribForoshModel.COLUMN_ccKalaCode() , kalaZaribForoshModel.getCcKalaCode());
        contentValues.put(KalaZaribForoshModel.COLUMN_ccGorohMoshtary() , kalaZaribForoshModel.getCcGorohMoshtary());
        contentValues.put(KalaZaribForoshModel.COLUMN_ZaribForosh() , kalaZaribForoshModel.getZaribForosh());
        contentValues.put(KalaZaribForoshModel.COLUMN_Darajeh() , kalaZaribForoshModel.getDarajeh());

        return contentValues;
    }


    private ArrayList<KalaZaribForoshModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaZaribForoshModel> kalaZaribForoshModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaZaribForoshModel kalaZaribForoshModel = new KalaZaribForoshModel();

            kalaZaribForoshModel.setCcKalaZaribForosh(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_ccKalaZaribForosh())));
            kalaZaribForoshModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_ccKalaCode())));
            kalaZaribForoshModel.setCcGorohMoshtary(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_ccGorohMoshtary())));
            kalaZaribForoshModel.setZaribForosh(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_ZaribForosh())));
            kalaZaribForoshModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_Darajeh())));


            kalaZaribForoshModels.add(kalaZaribForoshModel);
            cursor.moveToNext();
        }
        return kalaZaribForoshModels;
    }
    
    
}
