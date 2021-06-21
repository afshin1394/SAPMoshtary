package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.CompanyModel;
import com.saphamrah.Model.KalaOlaviatGheymatModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetKalaOlaviatGheymatResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KalaOlaviatGheymatDAO
{

    private KalaOlaviatGheymatModel modelGetTABLE_NAME = new KalaOlaviatGheymatModel();
    private DBHelper dbHelper;




    /*
    create constructor
     */
    public KalaOlaviatGheymatDAO(Context context)
    {

        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaOlaviatGheymatDAO" , "" , "constructor" , "");
        }
    }

    /*
    fetch = request server and get result
     */
    public void fetchKalaOlaviat(final Context context, final String activityNameForLog,String ccAnbarak ,int ccForoshandeh ,int ccMamorPakhsh , String ccKalaCode ,  final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaOlaviatGheymatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);

        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetKalaOlaviatGheymatResult> call = apiServiceGet.getKalaOlaviatGheymat(ccAnbarak,ccForoshandeh,ccMamorPakhsh,ccKalaCode);
            call.enqueue(new Callback<GetKalaOlaviatGheymatResult>()
            {
                @Override
                public void onResponse(Call<GetKalaOlaviatGheymatResult> call, Response<GetKalaOlaviatGheymatResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaOlaviatGheymatDAO.class.getSimpleName(), "", "fetchKalaOlaviat", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetKalaOlaviatGheymatResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaOlaviatGheymatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = "";
                                try
                                {
                                    endpoint = call.request().url().toString();
                                    endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                                }catch (Exception e){e.printStackTrace();}
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), KalaOlaviatGheymatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = "";
                            try
                            {
                                endpoint = call.request().url().toString();
                                endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                            }catch (Exception e){e.printStackTrace();}
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaOlaviatGheymatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaOlaviatGheymatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetKalaOlaviatGheymatResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), KalaOlaviatGheymatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    /*
    get name all columns in model
     */
    private String[] allColumns()
    {
        return new String[]
        {
                modelGetTABLE_NAME.getCOLUMN_cckalaCode(),
                modelGetTABLE_NAME.getCOLUMN_Olaviat(),
                modelGetTABLE_NAME.getCOLUMN_GheymatForosh(),
                modelGetTABLE_NAME.getCOLUMN_TarikhTolid(),
                modelGetTABLE_NAME.getCOLUMN_Radif(),



        };
    }

    /*
    set result model to DB
     */
    public boolean insertGroup(ArrayList<KalaOlaviatGheymatModel> models)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (KalaOlaviatGheymatModel model : models)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(model.getTABLE_NAME() , null , contentValues);

                Log.i("KalaOlaviatGheymatDAO" , contentValues.toString());
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
            String message = BaseApplication.getContext().getResources().getString(R.string.errorGroupInsert , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "KalaOlaviatGheymatDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    // get all data as db
    public ArrayList<KalaOlaviatGheymatModel> getAll()
    {
        ArrayList<KalaOlaviatGheymatModel> models = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getTABLE_NAME(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    models = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "KalaOlaviatGheymatDAO" , "" , "getAll" , "");
        }
        return models;
    }

    public ArrayList<KalaOlaviatGheymatModel> getByCcKalaCode(int ccKalaCode)
    {
        ArrayList<KalaOlaviatGheymatModel> kalaOlaviatGheymatModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select KalaOlaviatGheymat.*,SUM(KalaMojodi.Tedad) Tedad from KalaOlaviatGheymat \n" +
                    "       inner join KalaMojodi on KalaMojodi.ccKalaCode = KalaOlaviatGheymat.ccKalaCode and KalaMojodi.GheymatForosh = KalaOlaviatGheymat.GheymatForosh\n" +
                    "where KalaOlaviatGheymat.cckalacode =  " + ccKalaCode + " and Tedad > 0";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaOlaviatGheymatModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , CompanyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "KalaOlaviatGheymatDAO" , "" , "getByCcKalaCode" , "");
        }
        return kalaOlaviatGheymatModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(modelGetTABLE_NAME.getTABLE_NAME(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorDeleteAll , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "kalaOlaviatGheymatDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(KalaOlaviatGheymatModel model)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(model.getCOLUMN_cckalaCode() , model.getCckalaCode());
        contentValues.put(model.getCOLUMN_GheymatForosh() , model.getGheymatForosh());
        contentValues.put(model.getCOLUMN_Olaviat() ,model.getOlaviat());
        contentValues.put(model.getCOLUMN_TarikhTolid() ,model.getTarikhTolid());
        contentValues.put(model.getCOLUMN_Radif() ,model.getRadif());

        return contentValues;
    }


    /*
    set  cursor to model in get All
     */
    private ArrayList<KalaOlaviatGheymatModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaOlaviatGheymatModel> models = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaOlaviatGheymatModel kalaOlaviatGheymat = new KalaOlaviatGheymatModel();
            kalaOlaviatGheymat.setCckalaCode(cursor.getInt(cursor.getColumnIndex(kalaOlaviatGheymat.getCOLUMN_cckalaCode())));
            kalaOlaviatGheymat.setOlaviat(cursor.getInt(cursor.getColumnIndex(kalaOlaviatGheymat.getCOLUMN_Olaviat())));
            kalaOlaviatGheymat.setGheymatForosh(cursor.getDouble(cursor.getColumnIndex(kalaOlaviatGheymat.getCOLUMN_GheymatForosh())));
            kalaOlaviatGheymat.setTarikhTolid(cursor.getString(cursor.getColumnIndex(kalaOlaviatGheymat.getCOLUMN_TarikhTolid())));
            kalaOlaviatGheymat.setRadif(cursor.getInt(cursor.getColumnIndex(kalaOlaviatGheymat.getCOLUMN_Radif())));
            kalaOlaviatGheymat.setTedad(cursor.getInt(cursor.getColumnIndex(kalaOlaviatGheymat.getCOLUMN_Tedad())));

            models.add(kalaOlaviatGheymat);
            cursor.moveToNext();
        }
        return models;
    }



}
