package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.PosShomarehHesabModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetPosShomarehHesabResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosShomarehHesabDAO
{

    private DBHelper dbHelper;
    private Context context;


    public PosShomarehHesabDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "PosShomarehHesabDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            PosShomarehHesabModel.COLUMN_ccPosShomarehHesab(),
            PosShomarehHesabModel.COLUMN_ccShomarehHesab(),
            PosShomarehHesabModel.COLUMN_PosNumber(),
            PosShomarehHesabModel.COLUMN_ccBank(),
            PosShomarehHesabModel.COLUMN_NameBank(),
            PosShomarehHesabModel.COLUMN_CodeShobeh(),
            PosShomarehHesabModel.COLUMN_NameShobeh(),
            PosShomarehHesabModel.COLUMN_ShomarehHesab()
        };
    }

    public void fetchPosShomareHesab(final Context context, final String activityNameForLog,final String ccPosShomarehHesab, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, PosShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchPosShomareHesab", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetPosShomarehHesabResult> call = apiServiceGet.getPosShomarehHesab(ccPosShomarehHesab);
            call.enqueue(new Callback<GetPosShomarehHesabResult>() {
                @Override
                public void onResponse(Call<GetPosShomarehHesabResult> call, Response<GetPosShomarehHesabResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, PosShomarehHesabDAO.class.getSimpleName(), "", "fetchPosShomareHesab", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetPosShomarehHesabResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), PosShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchPosShomareHesab", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), PosShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "PosShomarehHesabDAO", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, PosShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "PosShomarehHesabDAO", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), PosShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchPosShomareHesab", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetPosShomarehHesabResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), PosShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchPosShomareHesab", "onFailure");
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

    public boolean insertGroup(ArrayList<PosShomarehHesabModel> posShomarehHesabModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (PosShomarehHesabModel posShomarehHesabModel : posShomarehHesabModels)
            {
                ContentValues contentValues = modelToContentvalue(posShomarehHesabModel);
                db.insertOrThrow(PosShomarehHesabModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , PosShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "PosShomarehHesabDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<PosShomarehHesabModel> getAll()
    {
        ArrayList<PosShomarehHesabModel> posShomarehHesabModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(PosShomarehHesabModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    posShomarehHesabModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , PosShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "PosShomarehHesabDAO" , "" , "getAll" , "");
        }
        return posShomarehHesabModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(PosShomarehHesabModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , PosShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "PosShomarehHesabDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(PosShomarehHesabModel posShomarehHesabModel)
    {
        ContentValues contentValues = new ContentValues();

        if (posShomarehHesabModel.getCcPosShomarehHesab() > 0)
        {
            contentValues.put(PosShomarehHesabModel.COLUMN_ccPosShomarehHesab() , posShomarehHesabModel.getCcPosShomarehHesab());
        }
        contentValues.put(PosShomarehHesabModel.COLUMN_ccShomarehHesab() , posShomarehHesabModel.getCcShomarehHesab());
        contentValues.put(PosShomarehHesabModel.COLUMN_PosNumber() , posShomarehHesabModel.getPosNumber());
        contentValues.put(PosShomarehHesabModel.COLUMN_ccBank() , posShomarehHesabModel.getCcBank());
        contentValues.put(PosShomarehHesabModel.COLUMN_NameBank() , posShomarehHesabModel.getNameBank());
        contentValues.put(PosShomarehHesabModel.COLUMN_CodeShobeh() , posShomarehHesabModel.getCodeShobeh());
        contentValues.put(PosShomarehHesabModel.COLUMN_NameShobeh() , posShomarehHesabModel.getNameShobeh());
        contentValues.put(PosShomarehHesabModel.COLUMN_ShomarehHesab() , posShomarehHesabModel.getShomarehHesab());

        return contentValues;
    }


    private ArrayList<PosShomarehHesabModel> cursorToModel(Cursor cursor)
    {
        ArrayList<PosShomarehHesabModel> posShomarehHesabModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            PosShomarehHesabModel posShomarehHesabModel = new PosShomarehHesabModel();

            posShomarehHesabModel.setCcPosShomarehHesab(cursor.getInt(cursor.getColumnIndex(PosShomarehHesabModel.COLUMN_ccPosShomarehHesab())));
            posShomarehHesabModel.setCcShomarehHesab(cursor.getInt(cursor.getColumnIndex(PosShomarehHesabModel.COLUMN_ccShomarehHesab())));
            posShomarehHesabModel.setPosNumber(cursor.getString(cursor.getColumnIndex(PosShomarehHesabModel.COLUMN_PosNumber())));
            posShomarehHesabModel.setCcBank(cursor.getInt(cursor.getColumnIndex(PosShomarehHesabModel.COLUMN_ccBank())));
            posShomarehHesabModel.setNameBank(cursor.getString(cursor.getColumnIndex(PosShomarehHesabModel.COLUMN_NameBank())));
            posShomarehHesabModel.setCodeShobeh(cursor.getString(cursor.getColumnIndex(PosShomarehHesabModel.COLUMN_CodeShobeh())));
            posShomarehHesabModel.setNameShobeh(cursor.getString(cursor.getColumnIndex(PosShomarehHesabModel.COLUMN_NameShobeh())));
            posShomarehHesabModel.setShomarehHesab(cursor.getString(cursor.getColumnIndex(PosShomarehHesabModel.COLUMN_ShomarehHesab())));

            posShomarehHesabModels.add(posShomarehHesabModel);
            cursor.moveToNext();
        }
        return posShomarehHesabModels;
    }
    
}
